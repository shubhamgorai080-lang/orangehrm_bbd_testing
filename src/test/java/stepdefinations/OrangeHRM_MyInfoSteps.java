package stepdefinations;

import org.testng.Assert;

import SeleniumUtilities.ReusableFunctions;
import base.BaseTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.MyinfoPage;

public class OrangeHRM_MyInfoSteps extends BaseTest{
		
	MyinfoPage infoPage;
	ReusableFunctions rf; 

    @When("User clicks on My Info tab from sidebar menu")
    public void user_clicks_on_my_info_tab_from_sidebar_menu() throws InterruptedException {
        infoPage = new MyinfoPage(driver);
        rf = new ReusableFunctions(driver); // Instantiated safely right at the start
        
        System.out.println("👆 Clicking on My Info Sidebar section...");
        infoPage.clickMyInfoTab();
        Thread.sleep(3000); // Page content layout refresh sync wait
    }

    @Then("User should be redirected to My Info personal details page")
    public void user_should_be_redirected_to_my_info_personal_details_page() throws InterruptedException {
        Thread.sleep(3000); 
        
        String currentUrl = driver.getCurrentUrl();
        //System.out.println("Captured Current URL: " + currentUrl);
        
      
        rf.TakeScreenshot("MyInfo_Personal_Details_Landing_Page");
        
        
        boolean isMatch = currentUrl.contains("viewPersonalDetails") || 
                          currentUrl.contains("personalDetails") || 
                          currentUrl.contains("myInfo");
                          
        Assert.assertTrue(isMatch, "Failed to redirect to My Info personal details page. Current URL is: " + currentUrl);
        System.out.println("Successfully loaded My Info Section Dashboard.");
    }

    @When("User updates Personal Details with new name strings")
    public void user_updates_personal_details_with_new_name_strings() throws InterruptedException {
        System.out.println("Updating Personal Details: First Name -> Rajesh, Middle Name -> kumar, Last Name -> userrr");
        // Parameterized method call step definitions with values
        infoPage.updatePersonalNames("Rajesh", "kumar", "userrr");
        Thread.sleep(1000);
        
        
        rf.TakeScreenshot("MyInfo_Personal_Names_Typed");
    }

    @When("User clicks Save button on Personal Details form")
    public void user_clicks_save_button_on_personal_details_form() throws InterruptedException {
        infoPage.clickPersonalSave();
        System.out.println("Personal Details Save button clicked.");
        Thread.sleep(4000); // OrangeHRM standard DB commit processing delay
    }

    @Then("Personal Details should be saved and verified successfully")
    public void personal_details_should_be_saved_and_verified_successfully() {
        
        rf.TakeScreenshot("MyInfo_Personal_Details_Saved_Success");
        
        String savedFirstName = infoPage.getFirstNameValue();
        Assert.assertEquals(savedFirstName, "Rajesh", "Personal details name mismatch in UI storage.");
        System.out.println("Personal details verified successfully on UI: " + savedFirstName);
    }

    @When("User navigates to Contact Details tab")
    public void user_navigates_to_contact_details_tab() throws InterruptedException {
        Thread.sleep(3000); 
        System.out.println("Navigating to Contact Details tab via JS Executor...");
        infoPage.clickContactDetailsTab();
        Thread.sleep(2000);
    }

    @When("User updates Contact Details for Australia address")
    public void user_updates_contact_details_for_australia_address() throws InterruptedException {
        System.out.println("Filling up Australia Address Details...");
        // Reusable functions and clean strings passed internally
        infoPage.updateAddressDetails("123 George St", "Suite 4", "Sydney", "NSW", "2000");
        
        System.out.println("Selecting Country context via dropdown loop: Australia");
        infoPage.selectCountryFromDropdown("Australia");
        Thread.sleep(1000);
        
        
        rf.TakeScreenshot("MyInfo_Contact_Details_Form_Filled");
    }

    @When("User clicks Save button on Contact Details form")
    public void user_clicks_save_button_on_contact_details_form() throws InterruptedException {
        infoPage.clickContactSave();
        System.out.println(" Contact Details Save button clicked.");
        Thread.sleep(4500); // DOM stable redirect context delay
    }

    @Then("Contact Details should be saved and verified successfully in system")
    public void contact_details_should_be_saved_and_verified_successfully_in_system() throws InterruptedException {
        Thread.sleep(3500); 
        
     
        rf.TakeScreenshot("MyInfo_Contact_Details_Saved_Success_Final");
        
        String actualCountry = infoPage.getCountryDropdownText();
        System.out.println("UI Saved Country: " + actualCountry);
        Assert.assertEquals(actualCountry, "Australia", "Mismatch!");
    }
}
