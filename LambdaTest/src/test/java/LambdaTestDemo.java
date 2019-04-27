import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LambdaTestDemo {
	public String username = "leandro.nares";
	public String accesskey = "Usg0en36cV12kv2HButbaOkDCQIPErUvWl4t25seV2aLZJa2dE";
	public static RemoteWebDriver driver = null;
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	boolean status = false;

	@BeforeClass
	@org.testng.annotations.Parameters(value = { "browser", "version", "platform" })
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("build", "Primeiro teste LambdaTest");
		capabilities.setCapability("name", "Simples ToDo Application");
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Internet Explorer");
		capabilities.setCapability("version", "11.0");
		capabilities.setCapability("visual", true);
		capabilities.setCapability("ie.compatibility", 11001);
		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testSimple() throws Exception {
		try {
			// Change it to production page
			driver.get("https://lambdatest.github.io/sample-todo-app/");

			// Let's mark done first two items in the list.
			driver.findElement(By.name("li1")).click();
			driver.findElement(By.name("li2")).click();

			// Let's add an item in the list.
			driver.findElement(By.id("sampletodotext")).sendKeys("Yey, Let's add it to list");
			driver.findElement(By.id("addbutton")).click();

			// Let's check that the item we added is added in the list.
			String enteredText = driver.findElementByXPath("/html/body/div/div/div/ul/li[6]/span").getText();
			if (enteredText.equals("Yey, Let's add it to list")) {
				status = true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@AfterClass
	public void tearDown() throws Exception {
		if (driver != null) {
			driver.quit();
		}
	}
}