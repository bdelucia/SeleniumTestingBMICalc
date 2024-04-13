/*
 * Name: Robert DeLucia Jr.
 * Class: CSE 464, #17549
 * ASUID: 1218933794
 * Date: 4/12/2024
 * Description: Tests a BMI calculator program deployed in an ASU Webstrar server. Uses Selenium to automate testing.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class bmiTest {
	public static int testCaseNum = 1;
	public static void main(String[] main)
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\bobby\\OneDrive\\Desktop\\ASU\\ASU Spring 24\\CSE 464\\hw7\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");
		String[] expectedOutputs = {
				"Height or weight cannot be negative: height < 0 or weight < 0", // 0
				"Height or weight cannot be zero: height = 0 or weight = 0", // 1
				"Underweight", // 2
				"Normal weight", // 3
				"Overweight", // 4
				"Obese" // 5
		};
		
		// testing when one input is valid and the other is invalide
		Boolean test1 = testBMI("0", "100", "0", expectedOutputs[1]);
		printTestCaseResults(test1);
		Boolean test2 = testBMI("100", "0", "0", expectedOutputs[1]);
		printTestCaseResults(test2);
		Boolean test3 = testBMI("-1", "100", "0", expectedOutputs[0]);
		printTestCaseResults(test3);
		Boolean test4 = testBMI("100", "-1", "0", expectedOutputs[0]);
		printTestCaseResults(test4);
		
		// testing when both inputs are valid
		Boolean test5 = testBMI("180", "50", "1.08487654320988", expectedOutputs[2]);
		printTestCaseResults(test5);
		Boolean test6 = testBMI("67.5", "125", "19.2866941015089", expectedOutputs[3]);
		printTestCaseResults(test6);
		Boolean test7 = testBMI("67.5", "180", "27.7728395061728", expectedOutputs[4]);
		printTestCaseResults(test7);
		Boolean test8 = testBMI("70", "230", "32.9979591836735", expectedOutputs[5]);
		printTestCaseResults(test8);
		
		// testing when both inputs are invalid
		Boolean test9 = testBMI("-1", "-1", "0", expectedOutputs[0]);
		printTestCaseResults(test9);
		Boolean test10 = testBMI("0", "0", "0", expectedOutputs[1]);
		printTestCaseResults(test10);
		
	}
	
	static public boolean testBMI(String height, String weight, String expectedBMI, String expectedMessage)
	{
		WebDriver driver = new ChromeDriver();
		
		driver.get("http://webstrar100.fulton.asu.edu/page2/");
		
		
		WebElement heightElement = driver.findElement(By.name("H"));
		
		heightElement.sendKeys(height);
		
		WebElement weightElement = driver.findElement(By.name("W"));
		
		weightElement.sendKeys(weight);
		
		WebElement calcButton = driver.findElement(By.name("Button1"));
		
		calcButton.click();		
        
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
	    WebElement BMI = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("BMI")));	
		WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Message")));

		
		String actualBMI = BMI.getAttribute("value");
		String actualMessage = message.getAttribute("value");
		
		System.out.print("Actual BMI: " + actualBMI);
		System.out.println(", Expected BMI: " + expectedBMI);
		
		System.out.print("Actual Message: " + actualMessage);
		System.out.println(", Expected Message: " + expectedMessage);
			
		driver.quit();
		
		if(actualBMI.equals(expectedBMI))
		{
			System.out.println("BMIs match!");
			if(actualMessage.equals(expectedMessage))
			{
				System.out.println("Messages also match!");
				return true;
			} 
			else
			{
				System.out.println("BMIs match, but messages do not.");
				return false;
			}
		}
		else if(actualMessage.equals(expectedMessage))
		{
			System.out.println("BMIs don't match, but messages match.");
			return false;
		}
		else
		{
			System.out.println("Both BMI and Message don't match.");
			return false;
		}
			
	}
	
	static public void printTestCaseResults(Boolean output)
	{
		if(output)
		{
			System.out.println("Passed test case #" + testCaseNum);
			System.out.println("-------------------------------------------------------------------");
		}
		else
		{
			System.out.println("Failed test case #" + testCaseNum);
			System.out.println("-------------------------------------------------------------------");
		}
		
		testCaseNum++;
	}

}
