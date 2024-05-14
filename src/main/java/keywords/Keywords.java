package keywords;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.net.UrlChecker.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import excelReader.ExcelReader;
import testBase.LoggerHelper;
import testBase.TestBase;
import testBase.Wait;


public class Keywords extends TestBase{

	private final static Logger logger = LoggerHelper.getLogger(Keywords.class);
	private final static ExcelReader reader=new ExcelReader(".\\src\\test\\java\\testData\\UAT_Stability_Status.xlsx");

	static String parentWindowHandle;
	static String QueryStatus;
	static String ActualText;
	
	public static String SAML_Content;
	
	
	public static WebElement getWebElement(String locator) throws Exception{
		//logger.info("locator data:-"+locator+"is---"+Repository.getProperty(locator));
		String keywordValue = Repository.getProperty(locator);
		return getLocator(keywordValue);
	}

	public static List<WebElement> getWebElements(String locator) throws Exception{
		return getLocators(Repository.getProperty(locator));
	}



	public static String navigate() {
		logger.info("Navigate is called");
		driver.get(webElement);
		return "Pass";
	}

	public static String clickRadioButton() {
		try {
			expliciteWait();
			getWebElement(webElement).click();
		}catch (Throwable t) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}


	public static String clickCheckBox() {
		try {
			expliciteWait();
			getWebElement(webElement).click();
		}catch (Throwable t) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}


	public static String inputText() {
		logger.info("InputText is called");
		try {
			expliciteWait();
			getWebElement(webElement).sendKeys(TestData);
		}catch (Throwable t) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}



	public static String clickOnLink() {
		logger.info("ClickOnLink is called");
		try {
			expliciteWait();
			getWebElement(webElement).click();
		}catch (Throwable t) {
			t.printStackTrace();
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}

	public static String verifyText() {
		logger.info("VerifyText is called");
		try {
			expliciteWait();
			String ActualText= getWebElement(webElement).getText();
			logger.info(ActualText);
			if(!ActualText.equals(TestData)) {
				return "Failed - Actual text "+ActualText+" is not equal to to expected text "+TestData;
			}
		}catch (Throwable t) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}

	public static String verifyAppText() {
		logger.info("VerifyText is called");
		try {
			expliciteWait();
			String ActualText= getWebElement(webElement).getText();
			if(!ActualText.equals(AppText.getProperty(webElement))) {
				return "Failed - Actual text "+ActualText+" is not equal to to expected text "+AppText.getProperty(webElement);
			}
		}catch (Throwable t) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}

		
	public static String mouseOver(){
		try {
			expliciteWait();
			WebElement element = getWebElement(webElement);
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
			Thread.sleep(1000);
		} catch (Exception e) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}

	public static String selectByValue(){
		try {
			expliciteWait();
			WebElement element = getWebElement(webElement);
			Select select = new  Select(element);
			select.selectByValue(TestData);
		} catch (Exception e) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}

	public static String selectByVisibleText(){
		try {
			expliciteWait();
			WebElement element = getWebElement(webElement);
			Select select = new  Select(element);
			select.selectByVisibleText(TestData);
		} catch (Exception e) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}

	public static String selectByIndex(){
		try {
			WebElement element = getWebElement(webElement);
			Select select = new  Select(element);
			select.selectByIndex(Integer.parseInt(TestData));
		} catch (Exception e) {
			return "Failed - Element not found "+webElement;
		}
		return "Pass";
	}


   /**
    * This Method will return web element.
    * @param locator
    * @return
    * @throws Exception
    */
	public static WebElement getLocator(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		if (locatorType.toLowerCase().equals("id"))
			return driver.findElement(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElement(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return driver.findElement(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return driver.findElement(By.tagName(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return driver.findElement(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElement(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return driver.findElement(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElement(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}

	public static List<WebElement> getLocators(String locator) throws Exception {
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];

		if (locatorType.toLowerCase().equals("id"))
			return driver.findElements(By.id(locatorValue));
		else if (locatorType.toLowerCase().equals("name"))
			return driver.findElements(By.name(locatorValue));
		else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
			return driver.findElements(By.className(locatorValue));
		else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
			return driver.findElements(By.linkText(locatorValue));
		else if (locatorType.toLowerCase().equals("partiallinktext"))
			return driver.findElements(By.partialLinkText(locatorValue));
		else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
			return driver.findElements(By.cssSelector(locatorValue));
		else if (locatorType.toLowerCase().equals("xpath"))
			return driver.findElements(By.xpath(locatorValue));
		else
			throw new Exception("Unknown locator type '" + locatorType + "'");
	}


	public static void expliciteWait() throws Exception {
		try {
			logger.info("Waiting for webElement..."+webElement.toString());
			WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(Wait.getExplicitWait()));
			wait.until(ExpectedConditions.visibilityOf(getWebElement(webElement)));
			logger.info("Element found..."+webElement.toString());
		} catch (Throwable e) {
			throw new TimeoutException(webElement, e);

		}

	}
	
	
	
	private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
		logger.debug("");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(pollingEveryInMiliSec, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}
	
	public void waitForElementVisible(WebElement locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		logger.info(locator);
		WebDriverWait wait = getWait(300, 5);
		wait.until(ExpectedConditions.visibilityOf(locator));
	}

	/*
	public static String expliciteWait(){
     try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		return "Failed - unable to load the page";
	}
     return "Pass";
	}
	*/

	public static String clickWhenReady(By locator, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		element.click();
		return "Pass";

	}



	public static String waitFor() throws InterruptedException {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			return "Failed - unable to load the page";
		}
		return "Pass";
	}
	
	
	public static String waitForDocumentsToGenerate() throws InterruptedException {
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			return "Failed - unable to load the page";
		}
		return "Pass";
	}
	

	public static String openNewWindowTab()
	{
		String windowTab = "window.open('about:blank','_blank');";
		((JavascriptExecutor) driver).executeScript(windowTab);
		return "Pass";
	}
	

	public static String read_SAML_DATA_XMLFile() throws InterruptedException
	{
		
		driver.get("E:\\AN4\\CAN_UAT_Satya_SR.xml");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        
       SAML_Content = driver.findElement(By.id("folder0")).getText();

		return "Pass";
	}
	
	
	public static String read_PM_Approve_SAML_DATA_XMLFile() throws InterruptedException
	{
		
		driver.get("E:\\AN4\\PM_APPROVE_SANDEEPPM.xml");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        
       SAML_Content = driver.findElement(By.id("folder0")).getText();

		return "Pass";
	}
	
	public static String paste_SAML()
	{
		driver.findElement(By.id("SAMLResponse")).sendKeys(SAML_Content);
		return "Pass";
	}
	
	public static String getParentWindowhandle()
	{
		parentWindowHandle = driver.getWindowHandle();
		System.out.println("Parent window's handle -> " + parentWindowHandle);
		return "Pass";

	}

	public static String switchToWindow() {
		Set<String> allWindowHandles = driver.getWindowHandles();

		for (String handle : allWindowHandles)
		{
			System.out.println("Window handle - > " + handle);
			driver.switchTo().window(handle);
			driver.manage().window().maximize();
		}
		return "Pass";
	}
	
	public static String switchToParentWindow() {
		logger.info("switching to parent window...");
		//driver.switchTo().defaultContent();
		
		driver.switchTo().window(parentWindowHandle);
		return "Pass";
	}

	
	public static String resizeBroserWindow()
	{
		Dimension d = new Dimension(1250,870);
	    //Resize the current window to the given dimension
	    driver.manage().window().setSize(d);
	    return "Pass";
	}
	
	
	public static Object executeScript(String script){
		JavascriptExecutor exe = (JavascriptExecutor)driver;
		return exe.executeScript(script);
	}

	public static String scrollDownVertically()
	{
		executeScript("window.scrollTo(0,document.body.scrollHeight)");
		return "Pass";
	}
	
	public static String scrollTopVertically()
	{
		executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		return "Pass";
	}
	
	public static String scrollDownVerticallyByPixel()
	{
		executeScript("window.scrollBy(0,550)");
		return "Pass";
	}
	
	public static String acceptPopUpMessage()
	{
		Alert alert = driver.switchTo().alert();
		alert.accept();
		return "Pass";
	}
	
	public void switchToFrame() throws Exception{
		WebElement element = getWebElement(webElement);
		driver.switchTo().frame(element);
		logger.info("switched to frame "+element.toString());
	}
	
	
	public static String switchToDialog() throws Exception
	{
		getWebElement(webElement);
		return "Pass";
	}
	static String transactionid;
	
	public static String getText()
	{
		logger.info("GetText is called");
		try {
			expliciteWait();
			ActualText= getWebElement(webElement).getText();
			String[] result = ActualText.split(" ");
		    transactionid = result[3];
			logger.info(ActualText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Pass";
	}
	
	public static String clearText()
	{
		logger.info("GetText is called");
		try {
			expliciteWait();
			getWebElement(webElement).clear();
			logger.info(ActualText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Pass";
	}
	
	
	public static String getDistributerName()
	{
		getText();
		reader.setCellData("Status", "Distributor", 2, ActualText);
		return "Pass";
	}
	
	public static String getCarrierName()
	{
		getText();
		reader.setCellData("Status", "Carrier", 2, ActualText);
		return "Pass";
	}
	

	public static String getTxnStatus()
	{
		getText();
		reader.setCellData("Status", "Txn_Status", 2, ActualText);
		return "Pass";
	}
	
	public static String getEbixTxnID()
	{
		getText();
		TestStepData.setCellData("TC02", "Ebix_Txn_Id", 2, transactionid);
		return "Pass";
	}
	
	LocalDate time = LocalDate.now();
	public static String getCurrentTime()
	{
		getText();
		Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        String strDate = dateFormat.format(date);  
		reader.setCellData("Status", "Date", 2, strDate);
		return "Pass";
	}
	
	/*
	public static String selectDaysInDropDown() throws Exception{
		RegistrationPage reg = new RegistrationPage();
		String status = reg.selectDaysInDropDown();
		return status;
	}

	public static String selectMonthInDropDown() throws Exception{
		RegistrationPage reg = new RegistrationPage();
		return reg.selectMonthInDropDown();
	}

	public static String selectYearInDropDown() throws Exception{
		RegistrationPage reg = new RegistrationPage();
		return reg.selectYearInDropDown();
	}

	public static String selectYourAddressCountry() throws Exception{
		RegistrationPage reg = new RegistrationPage();
		return reg.selectYourAddressCountry();
	}
	*/
	public static void closeBrowser(){
		driver.quit();
	}

}
