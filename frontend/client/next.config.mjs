const nextConfig = {
  async rewrites() {
    return [
      {
        source: '/api/bff/:path*',
        destination: `${process.env.BFF_API_URL || 'http://localhost:4001'}/:path*`,
      },
    ];
  },
};

export default nextConfig;
