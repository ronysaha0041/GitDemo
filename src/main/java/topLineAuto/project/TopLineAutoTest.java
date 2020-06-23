package topLineAuto.project;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchContextException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TopLineAutoTest {
	WebDriver driver;
	
	@BeforeTest
	public void openBrowser() {
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\ronys\\Downloads\\chromedriver_win32_new\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.toplineautony.com/");
	}

	@Test
	public void getUrl() {
		String url = driver.getCurrentUrl();
		System.out.println("The URL is : " + url);
	
		String title = driver.getTitle();
		System.out.println("current Title is :" + title);
		
		
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='divLogo']/a/img")).isDisplayed(), "Logo present"); 
	}
	
	@Test(dependsOnMethods= {"getUrl"}) //Can have multiple dependencies. just use"," and "" to add another dependencies
	public void clickInventory() throws InterruptedException {
		driver.findElement(By.xpath("//ul[@id='main_menu']/li[2]")).click();
		Thread.sleep(3000);
	
		driver.findElement(By.id("DwInvListImage_1")).click();
		Thread.sleep(3000);
	}
	
	@Test(enabled = false)// now it wont run
	public void enable () {
		System.out.println("This is to enable true or false for the test case to execute");
	}
	
	@Test(dependsOnMethods= {"clickInventory"})
	public void inputEstPrice() throws InterruptedException, IOException {
		driver.findElement(By.id("txtPurchasePrice")).clear();
		driver.findElement(By.id("txtPurchasePrice")).sendKeys("22000");
	
		driver.findElement(By.id("txtDownpayment")).clear();
		driver.findElement(By.id("txtDownpayment")).sendKeys("4000");
	
		driver.findElement(By.id("txtTradeValue")).clear();
		driver.findElement(By.id("txtTradeValue")).sendKeys("3000");
	
		driver.findElement(By.id("txtAPR")).clear();
		driver.findElement(By.id("txtAPR")).sendKeys("7.99");
	
		driver.findElement(By.xpath("//input[@id='TenureOpt4']")).sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		String a = driver.findElement(By.xpath("//span[@id='htmSpan_EstimatedPayments']")).getText();
		System.out.println(a);
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("C:\\Users\\ronys\\OneDrive\\Desktop\\Bittech\\Java\\screenshot2.png"));
	}
	
	@Test(dependsOnMethods= {"inputEstPrice"})
	public void carfax() throws InterruptedException, IOException {
		try {
		driver.findElement(By.xpath("//span[@id='LB_CarFax']/img")).sendKeys(Keys.ENTER);
		}
		catch(NoSuchContextException e) {
			System.out.println("Element is not found");
		}
		
		Set<String> ids = driver.getWindowHandles();
		Iterator<String> a = ids.iterator();
		String Parents = a.next();
		String child = a.next();
		driver.switchTo().window(child);
		
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("C:\\Users\\ronys\\OneDrive\\Desktop\\Bittech\\Java\\screenshot3.png"));
		
		//System.out.println(driver.findElement(By.id("vhrHeaderRow1")).getText());
		Thread.sleep(3000);
		driver.switchTo().window(Parents);
	}			
	
	@Test(dependsOnMethods = {"carfax"})
	public void search() throws InterruptedException {
		WebElement search = driver.findElement(By.id("searchInventoryGlobal"));
		search.sendKeys("Mercedes");
		for(int i=0; i<3; i++ ) {
			search.sendKeys(Keys.ARROW_DOWN);
		}
			search.sendKeys(Keys.ENTER);
			String a = driver.findElement(By.xpath("//h2[@id='dwitem-heading_0']")).getText();
			System.out.println(a);
	}
	
	@Test(timeOut=40000)
	public void timeout() {
		System.out.println("It gives extra time when implecit wait to wait for 40 seconds");
	}
	
	@Parameters({"URL", "userId"})
	@Test
	public void Parameterize(String UrlName, String userId) {
		System.out.println("This is to have a gloval value from XML. Need to write a testNG XML code after suite and "
				+ "before test which can be brough forward to any test cases" + UrlName);
	}
	
	@AfterTest		
	public void Scrollclose() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,10000)");
		Thread.sleep(5000);
		System.out.println("Window Scrolled");
		driver.close();
	}

}
