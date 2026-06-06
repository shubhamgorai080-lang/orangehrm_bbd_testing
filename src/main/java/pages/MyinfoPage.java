package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumUtilities.ReusableFunctions;

public class MyinfoPage {
		
	 WebDriver driver;
	 ReusableFunctions rf;

	    public MyinfoPage(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        rf = new ReusableFunctions(driver);
	    }
	    
	    
	    
	    @FindBy(xpath = "//span[text()='My Info']")
	     WebElement myInfoTab;
	    
	    // Side Menus inside My Info
	    @FindBy(xpath = "//a[text()='Personal Details']")
	    WebElement personalDetailsSideTab;
	    
	    @FindBy(xpath = "//a[text()='Contact Details']")
	     WebElement contactDetailsSideTab;
	    
	    // Personal Details Fields
	    @FindBy(name = "firstName")
	     WebElement firstNameBox;
	    
	    @FindBy(name = "middleName")
	     WebElement middleNameBox;
	    
	    @FindBy(name = "lastName")
	     WebElement lastNameBox;
	    
	    // Forms Save Buttons (Targeting exact headers to avoid confusion)
	    @FindBy(xpath = "//h6[text()='Personal Details']/ancestor::div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']//button[@type='submit']")
	     WebElement personalSaveBtn;
	    
	    @FindBy(xpath = "//h6[text()='Contact Details']/ancestor::div[@class='orangehrm-horizontal-padding orangehrm-vertical-padding']//button[@type='submit']")
	     WebElement contactSaveBtn;

	    // Contact Details Input Fields
	    @FindBy(xpath = "(//div[@class='oxd-form-row']//input)[1]")
	    WebElement street1Box;
	    
	    @FindBy(xpath = "(//div[@class='oxd-form-row']//input)[2]")
	    WebElement street2Box;
	    
	    @FindBy(xpath = "(//div[@class='oxd-form-row']//input)[3]")
	    WebElement cityBox;
	    
	    @FindBy(xpath = "(//div[@class='oxd-form-row']//input)[4]")
	    WebElement stateBox;
	    
	    @FindBy(xpath = "(//div[@class='oxd-form-row']//input)[5]")
	    WebElement zipBox;
	    
	    // Dropdown structural layout
	    @FindBy(xpath = "//div[@class='oxd-form-row']//div[@class='oxd-select-wrapper']//div[@class='oxd-select-text-input']")
	    WebElement countryDropdown;
	    
	    // Pure page ke dropdown boxes ko clear target karne ke liye direct options pointer
	    @FindBy(xpath = "//div[@role='listbox']//div[@role='option']//span")
	    List<WebElement> countryOptions;
	    
	    
	    public void clickMyInfoTab() {
	        rf.clickElement(myInfoTab);
	    }

	    public void clickPersonalDetailsTab() {
	        rf.clickElement(personalDetailsSideTab);
	    }

	    public void clickContactDetailsTab() {
	    	rf.waitTime(contactDetailsSideTab);
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].click();", contactDetailsSideTab);
	    }

	    // Custom clear input handling utilizing framework helper
	    private void clearAndInsert(WebElement element, String data) {
	        rf.waitTime(element); // Ensure element context is perfectly visible first
	        element.sendKeys(Keys.CONTROL + "a");
	        element.sendKeys(Keys.BACK_SPACE);
	        rf.insertText(element, data);
	    }

	    //enter the firstname, secondname, lastname
	    public void updatePersonalNames(String fName, String mName, String lName) {
	        clearAndInsert(firstNameBox, fName);
	        clearAndInsert(middleNameBox, mName);
	        clearAndInsert(lastNameBox, lName);
	    }
	    
	    //personalinfo save btn
	    public void clickPersonalSave() {
	        rf.clickElement(personalSaveBtn);
	    }
	    
	    //contact details page-------------------------------------------
	    //select the update address
	    public void updateAddressDetails(String s1, String s2, String city, String state, String zip) {
	        clearAndInsert(street1Box, s1);
	        clearAndInsert(street2Box, s2);
	        clearAndInsert(cityBox, city);
	        clearAndInsert(stateBox, state);
	        clearAndInsert(zipBox, zip);
	    }
	    
	    //select the country
	    public void selectCountryFromDropdown(String countryName) throws InterruptedException {
	    	System.out.println("Opening Country Dropdown...");
	        rf.clickElement(countryDropdown);
	        Thread.sleep(2500); 
	        List<WebElement> liveOptions = driver.findElements(By.xpath("//div[@role='listbox']//div[@role='option']"));

	        boolean found = false;
	        for (WebElement option : liveOptions) {
	            String text = option.getText().trim();
	            if (text.isEmpty()) {
	                text = option.getAttribute("textContent").trim();
	            }
	            
	            //System.out.println("Live Check Option: [" + text + "]");

	            if (text.equalsIgnoreCase(countryName)) {
	                System.out.println("Match Found! Clicking via Selenium click operation: " + text);
	                
	                // first scroll then click
	                JavascriptExecutor js = (JavascriptExecutor) driver;
	                js.executeScript("arguments[0].scrollIntoView(true);", option);
	                Thread.sleep(500);
	                
	                // Direct click approach check
	                try {
	                    option.click();
	                } catch (Exception e) {
	                    System.out.println("Standard click failed, retrying via JS Executor...");
	                    js.executeScript("arguments[0].click();", option);
	                }
	                
	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            System.out.println("❌ Error: '" + countryName + "' Not selected from dropdown list!");
	        }
	        Thread.sleep(2000); // Value frame lock sync buffer
	    }

	    public void clickContactSave() {
	        rf.clickElement(contactSaveBtn);
	    }

	    // GETTERS FOR ASSERTION STEPS
	    
	    public String getFirstNameValue() {
	        rf.waitTime(firstNameBox);
	        return firstNameBox.getAttribute("value");
	    }

	    public String getStreet1Value() {
	        rf.waitTime(street1Box);
	        return street1Box.getAttribute("value");
	    }
	    
	    public String getCountryDropdownText() {
	        rf.waitTime(countryDropdown);
	        // ⭐ FIX 2: Fallback fallback mechanism agar .getText() empty return kare
	        String dropdownText = countryDropdown.getText().trim();
	        if (dropdownText.isEmpty()) {
	            dropdownText = countryDropdown.getAttribute("textContent").trim();
	        }
	        return dropdownText;
	    }
	    
}
