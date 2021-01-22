package it.univr;

import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

public class NurseTest {
    private WebTester tester;

    // Set the web page
    @Before
    public void homeTest() {
        tester = new WebTester();
        tester.setBaseUrl("http://localhost:8080/");
        tester.beginAt("/");
        tester.assertTitleEquals("Welcome to Mentcare");
        tester.assertTextPresent("Welcome to Mentcare");
        tester.assertLinkPresentWithText("Authenticate");
    }

    // Test the authentication works with a valid nurse ID
    @Test
    public void authenticationTest() {
        tester.beginAt("/");
        tester.clickLinkWithText("Authenticate");

        tester.assertTitleEquals("Authentication");
        tester.assertTextPresent("ID:");
        tester.setTextField("id", "NUR10004");
        tester.submit();

        tester.assertTitleEquals("Nurse's Homepage");
        tester.assertTextPresent("Welcome, NUR10004!");
        tester.assertTextPresent("Select your patient:");
        tester.assertButtonPresentWithText("View medical record");
        tester.assertLinkPresentWithText("Exit");
    }

    // Test the medical record of a patient can be seen (by a nurse)
    @Test
    public void viewRecordTest() {
        // "nurse-home" needs the ID of the person already authenticated
        tester.beginAt("nurse-home?staff=NUR10004");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit("view-record");

        tester.assertTitleEquals("Patient's Medical Record");
        tester.assertTextPresent("Medical record: PAT10009");
        tester.assertButtonPresentWithText("Edit medical record");
        tester.assertLinkNotPresentWithText("Prescribe new medication");
        tester.assertLinkNotPresentWithText("Prescribe new treatment");
        tester.assertLinkPresentWithText("Home");
        tester.clickLinkWithText("Home");

        tester.assertTextPresent("Welcome, NUR10004!");
    }

    // Test that a medical records can be edited (by a nurse)
    @Test
    public void editRecordTest() {
        // "nurse-home" needs the ID of the person already authenticated
        tester.beginAt("nurse-home?staff=NUR10004");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit("view-record");

        tester.assertTitleEquals("Patient's Medical Record");
        tester.assertTextPresent("Medical record: PAT10009");
        tester.submit();

        tester.assertTextPresent("Edit medical record for PAT10009");
        tester.assertButtonPresentWithText("Back");
        tester.assertButtonPresentWithText("Save and continue");
        tester.assertLinkPresentWithText("Home");

        // Add allergy
        tester.setTextField("allergies", "Bees");

        // Uncheck a checked disorder and check an unchecked disorder
        tester.uncheckCheckbox("conditions", "Eating disorder");
        tester.checkCheckbox("conditions", "Mood disorder");
        tester.submit();

        // Add a symptom to the new disorder
        tester.assertTextPresent("Edit symptoms for PAT10009");
        tester.setTextField("symptoms", "Sadness");
        tester.submit();

        tester.assertTextPresent("Medical record: PAT10009");
        tester.assertTextPresent("Bees");
        tester.assertTextNotPresent("Eating disorder");
        tester.assertTextPresent("Mood disorder");
        tester.assertTextPresent("Sadness");
    }

}
