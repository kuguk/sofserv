package se3;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class Test {
		
	public static void main(String[] args) throws InterruptedException {
	
	System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");

	WebDriver driver = new ChromeDriver();
	//WebDriverWait wait = new WebDriverWait(driver, 10);	
	
	driver.navigate().to("https://github.com");
	driver.manage().window().maximize();
			  		
    //wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Sign in")));  
	driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
	driver.findElement(By.partialLinkText("Sign in")).click();
	
	driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
	driver.findElement(By.name("login")).sendKeys("kuguk@ukr.net");
	
	driver.findElement(By.name("password")).sendKeys("Githubgithubgithub");
	
	driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
	driver.findElement(By.name("password")).submit();	

	driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
	driver.findElement(By.xpath("//summary[@class='Header-link'][*[@class='octicon octicon-plus']]")).click();
	
	driver.findElement(By.partialLinkText("New repository")).click();
	driver.findElement(By.id("repository_name")).sendKeys("TestRepository");
	
	driver.findElement(By.id("repository_gitignore_template_toggle")).click();
	
	driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
	driver.findElement(By.xpath("//i[contains(text(),'.gitignore template:')]")).click();
	
	driver.findElement(By.xpath("//span[contains(text(),'Java')]")).click();
	
	driver.findElement(By.id("repository_gitignore_template_toggle")).submit();

	
	driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
	driver.findElement(By.xpath("//a[contains(text(),'Add a README')]")).click();
	
	//driver.findElement(By.xpath("//span[contains(text(),'# TestRepository')]")).clear();
	//driver.findElement(By.xpath("//span[@role='presentation']")).sendKeys("My Test Repository");
	
	driver.findElement(By.xpath("//span[@role='presentation']")).sendKeys(Keys.chord(Keys.CONTROL, "a"),"My Test Repository");		
	
	driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
	driver.findElement(By.xpath("//input[@id='commit-summary-input']")).sendKeys("Add readme file");
	driver.findElement(By.xpath("//input[@id='commit-summary-input']")).submit();
	
	driver.findElement(By.xpath("//span[@data-content='Settings']")).click();
	driver.findElement(By.xpath("//summary[contains(text(),'Delete this repository')]")).click();
	
	driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
	driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you "
			+ "want to delete this repository.']")).sendKeys("kuguk/TestRepository");
	driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you "
			+ "want to delete this repository.']")).submit();
	
    
	driver.findElement(By.xpath("//summary[@class='Header-link'][img[@alt='@kuguk']]")).click();
     driver.findElement(By.xpath("//button[contains(text(),'Sign out')]")).click();
	
	//System.out.println("Title of the page is -> " + driver.getTitle());
	//Thread.sleep(5000);

	driver.quit();
	 
	}
}
