package basic;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CodingChallenge {

	/* Required Global Variables */
	static WebDriver driver;
	static WebDriverWait wait;
	static JavascriptExecutor js;
	static String url = "https://www.noon.com/";

	public static void sectionName(String section) {

		/* Required Local Variables */
		TreeSet<String> outputText = new TreeSet<String>();
		int sno = 1;
		boolean notFound = true;
		
		/* XPath Generation */
		String headerXpath = "//h3[text()='" + section + "']";
		String elementsXpath = "//h3[text()='" + section + "']/parent::div/parent::div/following-sibling::div//div[@data-qa='product-name']/div";
		String nextXpath = "//h3[text()='" + section + "']/parent::div/parent::div/following-sibling::div/div/following-sibling::div";
		
		/* Scrolling until the Section is in View */
		do {
			js.executeScript("window.scrollBy(0,500)");
			try {
				js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(headerXpath)));
				notFound = false;
			} catch (Exception e) {
			}
		} while (notFound);

		/* Getting all Elements */
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(headerXpath)));
		List<WebElement> allElements = driver.findElements(By.xpath(elementsXpath));

		/* Looping through all Elements and fetching Data */
		for (WebElement element : allElements) {
			if (!element.getText().equalsIgnoreCase("")) {
				outputText.add(element.getText());
			} else {
				try {
					/* Clicking next if an element data is empty */
					wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(nextXpath)));
					driver.findElement(By.xpath(nextXpath)).click();
					Thread.sleep(1000);
					outputText.add(element.getText());
				} catch (Exception e) {
				}
			}
		}

		/* Printing Data - For Every Section */
		System.out.println("Total Element in Section - \"" + section + "\" : " + outputText.size());
		for (String text : outputText) {
			System.out.println(sno + " - " + text);
			sno++;
		}
		System.out.println("--- End of Section ---\n");

	}

	public static void main(String[] args) {

		/* Setup & Initialization */
		System.setProperty("webdriver.chrome.driver", "D:/Live Directory/eclipse/BasicOfWebAutomation/Libraries & Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 20);
		js = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20000, TimeUnit.SECONDS);
		driver.get(url);

		/* Section Data */ 
		sectionName("Recommended For You");
		sectionName("Save big on mobiles & tablets");
		sectionName("Top picks in electronics");
		sectionName("Top picks in laptops");
		sectionName("Save more with beauty gift boxes");

		/*Closure*/
		driver.close();
		driver.quit();

	}

}
