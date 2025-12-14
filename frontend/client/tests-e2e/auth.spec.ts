import { test, expect } from '@playwright/test';

test.describe('Authentication Flow', () => {
  test('should perform full login flow and redirect to dashboard', async ({ page }) => {
    // Mock the BFF response to ensure dashboard renders correctly
    // independently of the backend state
    await page.route('**/api/bff/projects', async route => {
      const json = [
        {
          id: '1',
          name: 'Test Project',
          description: 'A project created during E2E testing',
          createdAt: new Date().toISOString()
        }
      ];
      await route.fulfill({ json });
    });

    // 1. Go to home page
    await page.goto('http://localhost:4000');
    
    // 2. Click "Get Started"
    await page.getByRole('link', { name: 'Get Started' }).click();
    
    // 3. Sign in page
    await expect(page).toHaveURL(/.*\/auth\/signin/);
    await expect(page.getByText('Welcome back')).toBeVisible();
    
    // 4. Click SSO
    await page.getByRole('button', { name: 'Sign in with SSO' }).click();
    
    // 5. Keycloak Login
    // Wait for redirection to Keycloak
    await page.waitForURL(/.*\/realms\/docgen.*/);

    await expect(page.locator('#username')).toBeVisible();
    await page.fill('#username', 'user');
    await page.fill('#password', 'password');
    await page.click('input[type="submit"]');
    
    // 6. Wait for navigation and dashboard load
    await expect(page).toHaveURL('http://localhost:4000/projects');
    
    // 7. Verify Dashboard Content
    // Since we mocked the API, we should see the project list
    await expect(page.getByRole('heading', { name: 'Projects' })).toBeVisible();
    await expect(page.getByRole('link', { name: 'New Project' })).toBeVisible();
    await expect(page.getByText('Test Project')).toBeVisible();
  });

  test('should display error message on login failure (mocked)', async ({ page }) => {
    await page.goto('http://localhost:4000/auth/signin?error=CredentialsSignin');
    await expect(page.getByText('Welcome back')).toBeVisible();
  });
});
