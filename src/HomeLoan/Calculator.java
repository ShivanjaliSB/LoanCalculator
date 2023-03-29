package HomeLoan;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Calculator {

	WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass()
	{
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
	}

	@Before
	public void setUp() {
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("https://www.anz.com.au/personal/home-loans/calculators-tools/much-borrow/");
	}

	@Test
	public void test001() {
		//Applicant Type
		driver.findElement(By.xpath("//input[@id='application_type_single']")).click();

		//Number of Dependents
		WebElement denpendantsEle=driver.findElement(By.cssSelector("select[title='Number of dependants']"));
		Select sel=new Select(denpendantsEle);
		sel.selectByVisibleText("0");

		//Property you would like to buy
		driver.findElement(By.xpath("//input[@id='borrow_type_home']")).click();

		//Your annual income (before tax)
		driver.findElement(By.xpath("//span[@id='q2q1i1']/following::input[@aria-labelledby='q2q1']")).sendKeys("80000");

		//Your annual other income (optional) Tooltip
		driver.findElement(By.xpath("//span[@id='q2q2i1']/following::input[@aria-labelledby='q2q2']")).sendKeys("10000");

		//Monthly living expenses Tooltip
		driver.findElement(By.xpath("//span[@id='q3q1i1']/following::input[@aria-labelledby='q3q1']")).sendKeys("500");

		//Current home loan monthly repayments Tooltip
		driver.findElement(By.xpath("//span[@id='q3q2i1']/following::input[@aria-labelledby='q3q2']")).sendKeys("0");

		//Other loan monthly repayments 
		driver.findElement(By.xpath("//span[@id='q3q3i1']/following::input[@aria-labelledby='q3q3']")).sendKeys("100");

		//Other monthly commitments 
		driver.findElement(By.xpath("//span[@id='q3q4i1']/following::input[@aria-labelledby='q3q4']")).sendKeys("0");

		//Total credit card limits
		driver.findElement(By.xpath("//span[@id='q3q5i1']/following::input[@aria-labelledby='q3q5']")).sendKeys("10000");

		//Work out barrow button
		driver.findElement(By.xpath("//button[@id='btnBorrowCalculater']")).click();		

		try 
		{ 
			Thread.sleep(3000);
			String actual=driver.findElement(By.xpath("//span[@id='borrowResultTextAmount']")).getText();
			System.out.println(actual);
			Assert.assertEquals("$415,000", actual);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	//***********************************************************************************
	@Test
	public void test002()
	{	
		//click on start over button
		driver.findElement(By.xpath("//button[@class='start-over']/ancestor::div[@class='result__restart']")).click();

		//verify applicant type
		boolean isSingle = driver.findElement(By.xpath("//input[@id='application_type_single']")).isSelected();
		Assert.assertEquals(true, isSingle);

		//verify number of dependant
		Select select = new Select(driver.findElement(By.cssSelector("select[title='Number of dependants']"))); 
		WebElement denpendantsEle = select.getFirstSelectedOption();
		String selectedOption = denpendantsEle.getText();
		Assert.assertEquals("0", selectedOption);

		//verify Property you would like to buy
		boolean homeType = driver.findElement(By.xpath("//input[@id='borrow_type_home']")).isSelected();
		Assert.assertEquals(true, homeType);

		//Your annual income (before tax)
		WebElement annualIncomeEle = driver.findElement(By.xpath("//span[@id='q2q1i1']/following::input[@aria-labelledby='q2q1']"));
		String annualIncome = annualIncomeEle.getAttribute("value");
		Assert.assertEquals("0", annualIncome);

		//Your annual other income (optional) Tooltip
		WebElement otherIncomeEle = driver.findElement(By.xpath("//span[@id='q2q2i1']/following::input[@aria-labelledby='q2q2']"));
		String otherIncome = otherIncomeEle.getAttribute("value");
		Assert.assertEquals("0", otherIncome);

		//Monthly living expenses Tooltip
		WebElement monthlyExpensesEle = driver.findElement(By.xpath("//span[@id='q3q1i1']/following::input[@aria-labelledby='q3q1']"));
		String monthlyExpense = monthlyExpensesEle.getAttribute("value");
		Assert.assertEquals("0", monthlyExpense);

		//Current home loan monthly repayments Tooltip
		WebElement monthlyRepaymentEle = driver.findElement(By.xpath("//span[@id='q3q2i1']/following::input[@aria-labelledby='q3q2']"));
		String monthlyRepayment = monthlyRepaymentEle.getAttribute("value");
		Assert.assertEquals("0", monthlyRepayment);

		//Other loan monthly repayments 
		WebElement otherLoanMonthlyRepayEle = driver.findElement(By.xpath("//span[@id='q3q3i1']/following::input[@aria-labelledby='q3q3']"));
		String otherLoanMonthlyCommitmentspay = otherLoanMonthlyRepayEle.getAttribute("value");
		Assert.assertEquals("0", otherLoanMonthlyCommitmentspay);

		//Other monthly commitments 
		WebElement otherMonthlyCommitmentsEle = driver.findElement(By.xpath("//span[@id='q3q4i1']/following::input[@aria-labelledby='q3q4']"));
		String otherLoanMonthlyCommitments = otherMonthlyCommitmentsEle.getAttribute("value");
		Assert.assertEquals("0", otherLoanMonthlyCommitments);

		//Total credit card limits
		WebElement creditCardLimitEle = driver.findElement(By.xpath("//span[@id='q3q5i1']/following::input[@aria-labelledby='q3q5']"));
		String creditCardLimit = creditCardLimitEle.getAttribute("value");
		Assert.assertEquals("0", creditCardLimit);
	}

	//***********************************************************************************
	@Test
	public void test003()
	{
		//Monthly living expenses Tooltip
		driver.findElement(By.xpath("//input[@aria-labelledby='q3q1']")).sendKeys("1");

		//Work out barrow button
		driver.findElement(By.xpath("//button[@id='btnBorrowCalculater']")).click();
		String errorMessage = driver.findElement(By.xpath("//div[@class='borrow__error__text']")).getText();
		System.out.println(errorMessage);
		Assert.assertEquals("Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions, call us on 1800 035 500.", errorMessage);
	}

	@After
	public void tesrDown()
	{
		driver.quit();
	}
}

