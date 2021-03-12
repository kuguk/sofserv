package se3;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Test {
	
	private static WebDriver driver;
	private static WebElement element;
	private static WebDriverWait wait;
    private static String login;
    private static String password;
    private static String site_url;
    private static String pathToChromeDriver;
    private static String pathToconf_properties = "./conf.properties";
    
    private static Test expt = new Test();
    
/* create own type of Exception   */
    protected class BadLogin extends Exception {	
		
		private static final long serialVersionUID = 1L;
		public BadLogin(String message) {
	      super(message);
		}
		public BadLogin() { };
	}
 	
    private static void set_conf() throws IOException {
        
       InputStream inputStream = new FileInputStream(pathToconf_properties) ;
       Properties properties = new Properties();
       properties.load(inputStream);
            
            login = properties.getProperty("login");
            password = properties.getProperty("password");
            pathToChromeDriver = properties.getProperty("pathToChromeDriver");
            site_url = properties.getProperty("site.url");
            
            System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
            driver = new ChromeDriver();

            wait = new WebDriverWait(driver, 5);
	}
    
   public static void main(String[] args)  {
		  
	try {
		set_conf();
	} 
	catch (IOException io) {
		System.out.println(io.getMessage());
		return;
    }
    catch (Exception ex) {        	
      	System.out.println(ex.getMessage());
      	driver.quit();
      	return;
    }

  try {		

 /* Login to GitHub account  */
	driver.navigate().to(site_url);
	driver.manage().window().maximize();
			  		
	wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Sign in")));  	
	driver.findElement(By.partialLinkText("Sign in")).click();
		
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login")));
	//driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
	driver.findElement(By.name("login")).sendKeys(login);
	driver.findElement(By.name("password")).sendKeys(password);	

    /* Form submit  */
	driver.findElement(By.name("password")).submit();	
	
	
	try {
		boolean element;
		element = driver.findElement(By.xpath("//*[@class='flash flash-full flash-error ']")).isDisplayed(); 
		
		if (element) throw expt.new BadLogin("Incorrect username or password.");
	}
	catch (BadLogin bl_ex)  {	        	
		System.out.println(bl_ex.getMessage());  	
		 driver.quit();
		 return;
	}		
	catch (Exception ex)  {	
		if (element != null) {
		System.out.println(ex.getMessage());  	
		driver.quit();
		return;}			
	}
			
/* Create a new repository:  */
	
	/* Call up-right menu */
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//summary[@class='Header-link'][*[@class='octicon octicon-plus']]"))); 
	driver.findElement(By.xpath("//summary[@class='Header-link'][*[@class='octicon octicon-plus']]")).click();
	driver.findElement(By.partialLinkText("New repository")).click();
	
	/* Creation of a new repository  */
	driver.findElement(By.id("repository_name")).sendKeys("TestRepository");
	driver.findElement(By.id("repository_gitignore_template_toggle")).click();
	
	/* Add "ignore" file  */
	driver.findElement(By.xpath("//i[contains(text(),'.gitignore template:')]")).click();	
	driver.findElement(By.xpath("//span[contains(text(),'Java')]")).click();

	/* Form submit  */
	driver.findElement(By.id("repository_gitignore_template_toggle")).submit();
	
	/* Adding a README-file:  */
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Add a README')]"))); 
	driver.findElement(By.xpath("//a[contains(text(),'Add a README')]")).click();
      //Change content README-file
	driver.findElement(By.xpath("//span[@role='presentation']")).sendKeys(Keys.chord(Keys.CONTROL, "a"),"My Test Repository");
	driver.findElement(By.xpath("//input[@id='commit-summary-input']")).sendKeys("Add readme file");

	/* Commit  */
	driver.findElement(By.xpath("//input[@id='commit-summary-input']")).submit();
	
/* Repository removing  */
	driver.findElement(By.xpath("//span[@data-content='Settings']")).click();
	
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//summary[contains(text(),'Delete this repository')]")));
	driver.findElement(By.xpath("//summary[contains(text(),'Delete this repository')]")).click();
	
	driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you "
			+ "want to delete this repository.']")).sendKeys("kuguk/TestRepository");

	/* Form submit */
	driver.findElement(By.xpath("//input[@aria-label='Type in the name of the repository to confirm that you "
			+ "want to delete this repository.']")).submit();
	
/* Sign out  */
	driver.findElement(By.xpath("//summary[@class='Header-link'][img[@alt='@kuguk']]")).click();

	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Sign out')]")));
	driver.findElement(By.xpath("//button[contains(text(),'Sign out')]")).click();

	//Thread.sleep(5000);
	//driver.quit(); 
	}
 catch (Exception ex) 
   {        	
  	System.out.println(ex.getMessage());  	
   }
  
  driver.quit();
    
  }	
}
