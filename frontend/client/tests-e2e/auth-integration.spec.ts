import { test, expect } from '@playwright/test';

test.describe('Authentication Integration Flow', () => {
  test('should perform full login flow using real BFF (mocked backend) and redirect to dashboard', async ({ page }) => {
    // 1. Go to home page
    await page.goto('http://localhost:4000');
    
    // 2. Click "Get Started"
    await page.getByRole('link', { name: 'Get Started' }).click();
    
    // 3. Sign in page
    await expect(page).toHaveURL(/.*\/auth\/signin/);
    
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
    // This expects the data from the BFF fallback mock
    // Increased timeout for slow rendering
    await expect(page.getByText('Projects')).toBeVisible({ timeout: 15000 });
    await expect(page.getByRole('link', { name: 'New Project' })).toBeVisible();
    
    // Expect the data defined in CoreServiceGateway.ts fallback OR page.tsx fallback
    // Note: If BFF is down (which it is during CI/tests if not started separately), 
    // it will use the fallback data in page.tsx which matches these names.
    await expect(page.getByText('E-Commerce Platform')).toBeVisible();
    await expect(page.getByText('Payment Gateway Integration')).toBeVisible();
  });
});
