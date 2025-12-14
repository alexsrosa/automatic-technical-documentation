import { NextAuthOptions } from "next-auth";
import KeycloakProvider from "next-auth/providers/keycloak";
import { JWT } from "next-auth/jwt";

async function refreshAccessToken(token: JWT): Promise<JWT> {
  try {
    const url = `${process.env.KEYCLOAK_ISSUER}/protocol/openid-connect/token`;
    const response = await fetch(url, {
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      method: "POST",
      body: new URLSearchParams({
        client_id: process.env.KEYCLOAK_CLIENT_ID || "",
        client_secret: process.env.KEYCLOAK_CLIENT_SECRET || "",
        grant_type: "refresh_token",
        refresh_token: token.refreshToken || "",
      }),
    });

    const refreshedTokens = await response.json();

    if (!response.ok) {
      throw refreshedTokens;
    }

    return {
      ...token,
      accessToken: refreshedTokens.access_token,
      expiresAt: Math.floor(Date.now() / 1000) + (refreshedTokens.expires_in || 0),
      refreshToken: refreshedTokens.refresh_token ?? token.refreshToken, // Fallback to old refresh token
    };
  } catch (error) {
    console.error("Error refreshing access token", error);
    return {
      ...token,
      error: "RefreshAccessTokenError",
    };
  }
}

export const authOptions: NextAuthOptions = {
  providers: [
    KeycloakProvider({
      clientId: process.env.KEYCLOAK_CLIENT_ID || "",
      clientSecret: process.env.KEYCLOAK_CLIENT_SECRET || "",
      issuer: process.env.KEYCLOAK_ISSUER,
    }),
  ],
  callbacks: {
    async jwt({ token, account, user }) {
      // Initial sign in
      if (account && user) {
        return {
          ...token,
          accessToken: account.access_token,
          expiresAt: account.expires_at, // OIDC provider returns expires_at in seconds
          refreshToken: account.refresh_token,
          idToken: account.id_token, // Needed for federated logout
          // @ts-ignore - realm_access is part of Keycloak JWT but not standard OIDC type
          roles: account.access_token?.realm_access?.roles || [], 
        };
      }

      // Return previous token if the access token has not expired yet
      if (token.expiresAt && Date.now() < token.expiresAt * 1000) {
        return token;
      }

      // Access token has expired, try to update it
      return refreshAccessToken(token);
    },
    async session({ session, token }) {
      session.accessToken = token.accessToken;
      session.error = token.error;
      session.roles = token.roles;
      return session;
    },
  },
  pages: {
    signIn: "/auth/signin",
  },
  events: {
    async signOut({ token }) {
      if (token?.idToken) {
        try {
          const issuerUrl = process.env.KEYCLOAK_ISSUER;
          const logOutUrl = new URL(`${issuerUrl}/protocol/openid-connect/logout`);
          logOutUrl.searchParams.set("id_token_hint", token.idToken);
          // Optional: post_logout_redirect_uri can be added if we want Keycloak to redirect back
          // logOutUrl.searchParams.set("post_logout_redirect_uri", process.env.NEXTAUTH_URL || "");
          
          await fetch(logOutUrl.toString());
        } catch (error) {
          console.error("Error logging out from Keycloak:", error);
        }
      }
    },
  },
};
