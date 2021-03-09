package se3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Test {
//	protected static URL gridHubUrl;
//	  protected static String baseUrl;
//	  protected static Capabilities capabilities;
//
//	  protected WebDriver driver;
//	
//	@BeforeAll
//	  public static void loadConfig() throws Throwable {
//	    SuiteConfiguration config = new SuiteConfiguration();
//	    baseUrl = config.getProperty("site.url");
//	    if (config.hasProperty("grid.url") && !"".equals(config.getProperty("grid.url"))) {
//	      gridHubUrl = new URL(config.getProperty("grid.url"));
//	    }
//	    capabilities = config.getCapabilities();
//	  };
//	

	private static WebDriver driver;
    private static WebDriverWait wait;
    private static String login;
    private static String password;
    private static String site_url;
    private static String pathToChromeDriver;
    
    
	private static void set_conf() throws IOException {
        try(InputStream inputStream = new FileInputStream("./conf.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            login = properties.getProperty("login");
            password = properties.getProperty("password");
            pathToChromeDriver = properties.getProperty("pathToChromeDriver");
            
            System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
            driver = new ChromeDriver();
	        wait = new WebDriverWait(driver, 10);	
        } catch (IOException io) {
            io.printStackTrace();
        }
	}

	
	public static void main(String[] args) throws InterruptedException, IOException {
	
	set_conf();
	
	//System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");	
	//System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
    
	//WebDriver driver = new ChromeDriver();
	//WebDriverWait wait = new WebDriverWait(driver, 10);	
	
	
	driver.navigate().to("https://github.com");
	driver.manage().window().maximize();
			  		
// Login to GitHub account
	wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Sign in")));  	
	driver.findElement(By.partialLinkText("Sign in")).click();
		
	driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
	driver.findElement(By.name("login")).sendKeys(login);
	driver.findElement(By.name("password")).sendKeys(password);	

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
