package se3;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Test {
		

	public static void main(String[] args) throws InterruptedException {
	
	System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
    
	WebDriver driver = new ChromeDriver();
	WebDriverWait wait = new WebDriverWait(driver, 10);	
	
	
	driver.navigate().to("https://github.com");
	driver.manage().window().maximize();
			  		
    // Login to GitHub account
	wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Sign in")));  	
	driver.findElement(By.partialLinkText("Sign in")).click();
		
	driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
	driver.findElement(By.name("login")).sendKeys("kuguk@ukr.net");
	driver.findElement(By.name("password")).sendKeys("Githubgithubgithub");	

	// Form submit
	driver.findElement(By.name("password")).submit();	

	// Create a new repository:
	
	// Call up-right menu
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//summary[@class='Header-link'][*[@class='octicon octicon-plus']]"))); 
	driver.findElement(By.xpath("//summary[@class='Header-link'][*[@class='octicon octicon-plus']]")).click();
	driver.findElement(By.partialLinkText("New repository")).click();
	
	// Creation of a new repository
	driver.findElement(By.id("repository_name")).sendKeys("TestRepository");
	driver.findElement(By.id("repository_gitignore_template_toggle")).click();
	
	// add "ignore" file
	driver.findElement(By.xpath("//i[contains(text(),'.gitignore template:')]")).click();	
	driver.findElement(By.xpath("//span[contains(text(),'Java')]")).click();

	//Form submit
	driver.findElement(By.id("repository_gitignore_template_toggle")).submit();
	
	// Adding a README-file:
	driver.findElement(By.xpath("//a[contains(text(),'Add a README')]")).click();
      //Change content README-file
	driver.findElement(By.xpath("//span[@role='presentation']")).sendKeys(Keys.chord(Keys.CONTROL, "a"),"My Test Repository");
	driver.findElement(By.xpath("//input[@id='commit-summary-input']")).sendKeys("Add readme file");

	// Commit
	driver.findElement(By.xpath("//input[@id='commit-summary-input']")).submit();
	
	// repository Removing
	driver.findElement(By.xpath("//span[@data-content='Settings']")).click();
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//summary[contains(text(),'Delete this repository')]")));
	driver.findElement(By.xpath("//summary[contains(text(),'Delete this repository')]")).click();
	
	driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you "
			+ "want to delete this repository.']")).sendKeys("kuguk/TestRepository");

	//Form submit
	driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you "
			+ "want to delete this repository.']")).submit();
	
    //Sign out
	driver.findElement(By.xpath("//summary[@class='Header-link'][img[@alt='@kuguk']]")).click();

	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Sign out')]")));
	driver.findElement(By.xpath("//button[contains(text(),'Sign out')]")).click();

	//Thread.sleep(5000);
 
	driver.quit();
	 
	}
	
}
