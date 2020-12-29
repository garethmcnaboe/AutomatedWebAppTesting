package cs265;

import static org.testng.AssertJUnit.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PizzaHouseSystemTest {

	public static enum Payment {CARD, APPLEPAY};
	
    static WebDriver driver ;
	static Wait<WebDriver> wait;
	// TODO: set URL - Done
	static String URL_TO_TEST = "http://wc2.cs.nuim.ie/rbierig/cs265/pizzaHouse/pizzaHouse.php";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// TODO: point to driver - Done
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\garet\\Desktop\\Driver & Jars for Web App testing\\Chrome Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10);
		System.out.println("Lab Exam 02 PizzaHouseSystemTest TESTING READY TO BEGIN");
		driver.get(URL_TO_TEST);//additional line of code added
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
		driver.quit(); // close the drivers
		System.out.println("Lab Exam 02 PizzaHouseSystemTest TESTING FINISHED");
	}	

	@BeforeMethod
	public void setUp() throws Exception {
        driver.get(URL_TO_TEST);
    	// wait until displayed
    	// The page is fully loaded when the SUBMIT BUTTON Appears
     // TODO: find ID - done
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("doCheck")));
	}	
	
	// Create a template of the test we want to run repeatedly. Needs to match the INPUT form (and 
	// any other input we have) as well as the expected output or answer. 
	// ALL INPUT TO FORMS IN HTML ARE STRING DATA TYPE, EVEN THOUGH THERE ARE TREATED AS NUMBERS IN THE WEB APPLICATION
	public static void TestTemplate(String plainNumber, String spicyNumber, Payment payment, String expected) {

		// print some messages to the console. 
		System.out.println("START: The page title is now " + driver.getTitle()); 

		// Look into the source code (VIEW SOURCE) to identify all IDs
    
	    // This code selects one either 'Cash', 'Card', or the 'Apple Pay' option.
		if (payment == Payment.CARD){
			driver.findElement(By.id("form_radio_card")).click();
		} else if (payment == Payment.APPLEPAY){
			driver.findElement(By.id("form_radio_apple")).click();
		}
		
	    // TODO: find ID - done
	    driver.findElement(By.id("number_plain")).sendKeys(plainNumber);
	    
	    // TODO: find ID - done
	    driver.findElement(By.id("number_spicy")).sendKeys(spicyNumber);
	    
	    // hit the submit button automatically
	    
	    // TODO: find ID - done
	    driver.findElement(By.id("doCheck")).submit();
	    
	    System.out.println("PROCESS: The page title is now " + driver.getTitle());
	   
	    // The result
	    // TODO: find ID-done
	    String answerGenerated = driver.findElement(By.id("result")).getText();
	    System.out.println("Lab Exam 02 PizzaHouseSystemTest: Answer: [" + answerGenerated + "]");
	    
	    // This is where we put the expected output of our test
	    // based on our input above. 
	    
	    String expectedAnswer = expected;
	    
	    // Write some stuff to the console. 
	    System.out.println("Lab Exam 02 PizzaHouseSystemTest: Expected answer: [" + expectedAnswer + "]");
	
	    // Wait for four seconds so you can physically see the output 
	    // 1000 milliseconds is 1 seconds
		try {
			Thread.sleep(500);
			System.out.println("Lab Exam 02 PizzaHouseSystemTest: FINISHED SLEEPING ...zzzzzzz");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}    
	    // Get TestNG to compare the two answers
		// Report a failure if this is the case
		// Compare the expected answer and the generated answer from the application
	    assertEquals(expectedAnswer,answerGenerated);
   
	}	
	
	// Generate a test with VALID input data. 
	@Test(dataProvider="validData", description="Testing with VALID input data")
	public void validDataTests(String plainNumber, String spicyNumber, 
			Payment payment, String expected) {
		TestTemplate(plainNumber, spicyNumber, payment, expected);
	}

	// Generate a test with INVALID input data. 
	@Test(dataProvider="invalidData", description="Testing with INVALID input data")
	public void invalidDataTests(String plainNumber, String spicyNumber, 
			Payment payment, String expected){
		TestTemplate(plainNumber, spicyNumber, payment, expected);
	}	
	
	// Create valid data for the valid data tests. 
	@DataProvider(name="validData")
	public static Object[][] createValidData()
	{
		// Test Input data is added here 
		Object[][] validData = 
		{
			{"1","1", Payment.CARD,"26"},
			{"1","1", Payment.APPLEPAY, "25"},
			{"0","1", Payment.CARD,"16"},
			{"0","1", Payment.APPLEPAY,"15"},
			{"1","0", Payment.CARD, "11"},
			{"1","0", Payment.APPLEPAY, "10"},
			{"2","1", Payment.CARD,"36"},
			{"2","1", Payment.APPLEPAY,"35"},
			{"1","2", Payment.CARD, "41"},
			{"1","2", Payment.APPLEPAY, "40"},
			{"2","2", Payment.CARD,"51"},
			{"2","2", Payment.APPLEPAY, "50"},
			{"0","2", Payment.CARD,"31"},
			{"0","2", Payment.APPLEPAY,"30"},
			{"2","0", Payment.CARD, "21"},
			{"2","0", Payment.APPLEPAY, "20"}
		};
		return validData;
	
	}
	
	// Create invalid data for the invalid data tests. 
	@DataProvider(name="invalidData")
	public static Object[][] createInValidData()
	{
		Object[][] invalidData =
		{
			{"0","0", Payment.CARD,"0"},
			{"0","0", Payment.APPLEPAY,"0"},
			{"0","-1", Payment.CARD,"0"},
			{"0","-1", Payment.APPLEPAY,"0"},
			{"-1","0", Payment.CARD,"0"},
			{"-1","0", Payment.APPLEPAY,"0"},
			{"3","0", Payment.CARD,"0"},
			{"3","0", Payment.APPLEPAY,"0"},
			{"0","3", Payment.CARD,"0"},
			{"0","3", Payment.APPLEPAY,"0"},
		};
		return invalidData;

	}


	/*
	 * This is a very useful method - this method will take a timestamped screenshot
	 * automatically of ANY test that fails. It will take a screenshot of the 
	 * web-browser in the state that cause the failure. 
	 * The screenshots are in PNG format and shall be written directly to your Eclipse
	 * workspace where the folder for this project is stored.  
	 */
    @AfterMethod
    public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            System.out.println(testResult.getStatus());
            // Create a reasonably unique filename 
            // with the current timestamp in mm-ss resolution
            
            ITestNGMethod method = testResult.getMethod();
            String methodName = method.getMethodName();
            
            System.out.println("**************" + method.getDescription());
            
            String fn = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()).toString();
            
            fn =  methodName + "_LabExam02_FailedTest_" + fn + ".png";
            
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(fn));
        }
    }
	
}