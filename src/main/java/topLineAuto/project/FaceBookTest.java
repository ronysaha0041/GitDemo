package topLineAuto.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class FaceBookTest {

	ChromeDriver driver;

	
	@BeforeTest
	public void openBrowser() throws IOException {
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\ronys\\Downloads\\chromedriver_win32_new\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		   	   
		driver.get("http://www.facebook.com/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		Properties data = new Properties(); //this is for the global parameter for globaldata.properties
		FileInputStream file = new FileInputStream("C:\\Users\\ronys\\OneDrive\\Desktop\\Bittech\\Java\\MavenProject\\globalData.properties");
		data.load(file); //cant go into other test cases
		System.out.println(data.getProperty("text"));
		}
	
	@Test
	public void getUrl() {
		
		String url = driver.getCurrentUrl();
		System.out.println("The URL is : " + url);
		String title = driver.getTitle();
		System.out.println("current Title is :" + title);
		}
	
	@Test(dependsOnMethods = {"getUrl"})
	public void checkLogo() {
		try {
		driver.findElementByXPath("//i[@class='fb_logo img sp_0eOQhLgQG2U sx_fbcf0b']");
		}
		catch(NoSuchElementException e) {
				System.out.println("Logo is absent");
		}
	}
	
		
	@Test(dataProvider="getdata", dependsOnMethods = {"checkLogo"})
	public void typeDummyEmail(String username, String pass) throws InterruptedException {
		driver.findElementById("email").sendKeys(username);
		driver.findElementById("pass").sendKeys(pass);
		driver.findElementById("u_0_b").click();
		Thread.sleep(6000);
		driver.navigate().back();
		Thread.sleep(2000);
	}
	
	@DataProvider
	public Object[][] getdata() {
		Object[][] data = new Object [2][2]; //2 combination and 2 sets of filed for data
		data[0][0] ="dummy@dummy.com";
		data[0][1] ="password";
		
		data[1][0] ="dummysecnd@dummy.com";
		data[1][1] ="passwordsecond";
		return data;
	}
	/*
	@Test
	public void navigateBack() throws InterruptedException {
		driver.navigate().back();
		Thread.sleep(2000);
		driver.navigate().refresh();
		System.out.println("Page has been refreshed");
	}*/
	
	@AfterTest
	public void close() {
		
		driver.close();
	}

}
