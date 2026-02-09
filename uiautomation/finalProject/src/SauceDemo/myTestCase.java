package SauceDemo;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class myTestCase {
	
	WebDriver driver;
	String TheWebSite = "https://www.saucedemo.com/";
	Random random = new Random();
	String TheEmail = "standard_user";
	String ThePassword = "secret_sauce";
	

	@BeforeTest

	public void mySetup() throws InterruptedException {
		
		driver = new FirefoxDriver();
		driver.get(TheWebSite);
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	    driver.manage().window().maximize();
		
	}

	@Test(priority = 1)

	public void Login() {
	

		WebElement theUserNameInputField = driver.findElement(By.id("user-name"));
		theUserNameInputField.sendKeys(TheEmail);

		WebElement thePasswordInputfield = driver.findElement(By.id("password"));
		thePasswordInputfield.sendKeys(ThePassword);

		WebElement theLoginButton = driver.findElement(By.id("login-button"));
		theLoginButton.click();
		
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
	
	}
	
	@Test(priority = 2)
	
	public void randomSorting() {

	    WebElement sortDropdown = driver.findElement(By.className("product_sort_container"));
	    Select sort = new Select(sortDropdown);

	    String[] sortOptions = {"az", "za", "lohi", "hilo"};
	    String randomSortOption = sortOptions[random.nextInt(sortOptions.length)];
	    sort.selectByValue(randomSortOption);

	    System.out.println("Sorting applied: " + randomSortOption);

	    Assert.assertTrue(true); 
	    }

	@Test(priority = 3)
	public void AddAllItems()  {

		List<WebElement> addToCartButtons = driver
				.findElements(By.cssSelector(".btn.btn_primary.btn_small.btn_inventory"));
		
		for(int i =0 ; i <addToCartButtons.size();i++) {
			addToCartButtons.get(i).click();
		}
	}

	
	@Test(priority = 4)
	public void RemoveItem() {
		
		List<WebElement> removeFromThecartButtons = driver
				.findElements(By.cssSelector(".btn.btn_secondary.btn_small.btn_inventory"));
		
		for(int i =0 ; i <removeFromThecartButtons.size();i++) {	
			removeFromThecartButtons.get(i).click();
				}


	}
	
	@Test(priority = 5)
    public void addItemFromProductPage() {
		
		WebElement product1Page = driver.findElement(By.className("inventory_item_name"));
		product1Page.click();

		WebElement addToCartButton1 = driver.findElement(By.xpath("//button[contains(text(),'Add to cart')]"));
		addToCartButton1.click();
        
		WebElement backToProductButton1 = driver.findElement(By.id("back-to-products"));
		backToProductButton1.click();
	
    }

    @Test(priority = 6)
    public void removeItemFromCart() {
    	
    	WebElement goToCart = driver.findElement(By.className("shopping_cart_link"));
    	goToCart.click();
    	
    	WebElement RemoveFromCAart = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));
    	RemoveFromCAart.click();

    }
    
    @Test(priority = 7)
    public void checkoutProcess() {
    	
    	WebElement checkoutButton = driver.findElement(By.id("checkout"));
    	checkoutButton.click();

    	WebElement firstNameInput = driver.findElement(By.id("first-name"));
    	firstNameInput.sendKeys("Mai");

    	WebElement lastNameInput = driver.findElement(By.id("last-name"));
    	lastNameInput.sendKeys("Taha");

    	WebElement postalCodeInput = driver.findElement(By.id("postal-code"));
    	postalCodeInput.sendKeys("1703");

    	WebElement continueButton = driver.findElement(By.id("continue"));
    	continueButton.click();

    	WebElement finishButton = driver.findElement(By.id("finish"));
    	finishButton.click();
    	
    	Assert.assertTrue(driver.getPageSource().contains("Thank you for your order"));
    	
    	WebElement backHomeButton = driver.findElement(By.id("back-to-products"));
    	backHomeButton.click();

       }
    
    
    @Test(priority = 8)
    public void logout() {
    	
    	WebElement menuButton = driver.findElement(By.id("react-burger-menu-btn"));
    	menuButton.click();

    	WebElement logoutButton = driver.findElement(By.id("logout_sidebar_link"));
    	logoutButton.click();

        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"));
    }

    @Test(priority = 9)
    public void invalidLogin() {
    	
    	WebElement userNameInput = driver.findElement(By.id("user-name"));
    	userNameInput.sendKeys("invalid_user");

    	WebElement passwordInput = driver.findElement(By.id("password"));
    	passwordInput.sendKeys("wrong_password");

    	WebElement loginButton = driver.findElement(By.id("login-button"));
    	loginButton.click();
    	
    	WebElement errorMessage = driver.findElement(By.cssSelector("[data-test='error']"));
    	
    	Assert.assertTrue(errorMessage.isDisplayed());

    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
    

}
