package it.univr;

import com.gargoylesoftware.htmlunit.ScriptException;
import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

public class ReceptionistTest {
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

    // Test the authentication works with a valid receptionist ID
    @Test
    public void authenticationTest() {
        tester.beginAt("/");
        tester.clickLinkWithText("Authenticate");

        tester.assertTitleEquals("Authentication");
        tester.assertTextPresent("ID:");
        tester.setTextField("id", "REC10006");
        tester.submit();

        tester.assertTitleEquals("Receptionist's Homepage");
        tester.assertTextPresent("Welcome, REC10006!");
        tester.assertTextPresent("Select your patient:");
        tester.assertSubmitButtonPresent();
        tester.assertLinkPresentWithText("Register a new patient");
        tester.assertLinkPresentWithText("Exit");
    }

    // Test that a receptionist can view the personal information a patient
    @Test
    public void viewInfoTest() {
        // "receptionist-home" needs the ID of the person already authenticated
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit("view-info");

        tester.assertTitleEquals("Patient's personal information");
        tester.assertTextPresent("Personal information for PAT10009");
        tester.assertButtonPresentWithText("Edit");
        tester.assertLinkPresentWithText("Book a consultation");
        tester.assertLinkPresentWithText("Home");
        tester.clickLinkWithText("Home");

        tester.assertTextPresent("Welcome, REC10006!");
    }

    // Test that the personal information of a patient can be edited
    @Test
    public void editInfoTest() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit();
        tester.submit();

        tester.assertTextPresent("Edit personal information for PAT10009");
        tester.setTextField("phoneNumber", "3526681536");
        tester.selectOption("doctor", "Stefano Scorsese");
        tester.submit();

        tester.assertTextPresent("Personal information for PAT10009");
        tester.assertTextPresent("3526681536");
        tester.assertTextPresent("Stefano Scorsese");
        tester.clickLinkWithText("Home");

        tester.assertTitleEquals("Receptionist's Homepage");
    }

    // Test that a patient can't be edited if the date inserted isn't in the right format
    @Test (expected = ScriptException.class)
    public void editInfoTestFail() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit();
        tester.submit();

        tester.assertTextPresent("Edit personal information for PAT10009");
        tester.setTextField("birthDate", "1-02-1998");
        tester.submit();
    }

    // Test that a new patient can be registered (called by "deletePatientTest")
    public void registerPatientTest() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.clickLinkWithText("Register a new patient");

        tester.assertTitleEquals("Register patient");
        tester.assertTextPresent("Register a new patient");
        tester.setTextField("firstName", "Maria");
        tester.setTextField("lastName", "Rovere");
        tester.setTextField("birthDate", "1952-05-05");
        tester.setTextField("phoneNumber", "3526681536");
        tester.setTextField("address", "via Peri 78, Porto Mantovano");
        tester.selectOption("doctor", "Gianni Mondadori");
        tester.submit();

        tester.assertTextPresent("Personal information for ");
        tester.assertTextPresent("Maria");
        tester.assertTextPresent("Rovere");
        tester.assertTextPresent("1952-05-05");
        tester.assertTextPresent("3526681536");
        tester.assertTextPresent("via Peri 78, Porto Mantovano");
        tester.assertTextPresent("Gianni Mondadori");
        tester.clickLinkWithText("Home");

        tester.assertTitleEquals("Receptionist's Homepage");
    }

    // Test that a patient with all info already in the database can't be added
    @Test (expected = ScriptException.class)
    public void registerPatientTestFail() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.beginAt("receptionist-home?staff=REC10006");
        // Assert that the patient is in the database
        try {
            tester.assertSelectOptionPresent("patient", "Maria Rovere");
        }
        catch (AssertionError e) {
            registerPatientTest();
        }
        tester.clickLinkWithText("Register a new patient");

        tester.setTextField("firstName", "Maria");
        tester.setTextField("lastName", "Rovere");
        tester.setTextField("birthDate", "1952-05-05");
        tester.setTextField("phoneNumber", "3526681536");
        tester.setTextField("address", "via Peri 78, Porto Mantovano");
        tester.selectOption("doctor", "Gianni Mondadori");
        tester.submit();
    }

    // Test that a patient can be deleted
    @Test
    public void deletePatientTest() {
        tester.beginAt("receptionist-home?staff=REC10006");
        // Assert that the patient is in the database
        try {
            tester.assertSelectOptionValuePresent("patient", "PAT10010");
        }
        catch (AssertionError e) {
            registerPatientTest();
        }

        tester.selectOptionByValue("patient", "PAT10010");
        tester.submit();
        tester.submit();

        tester.assertTextPresent("Edit personal information for PAT10010");
        tester.clickLinkWithText("Delete");

        tester.assertTitleEquals("Receptionist's Homepage");
        tester.assertSelectOptionValueNotPresent("patient",
                "PAT10010");
    }

    // Test that a consultation can be deleted
    @Test
    public void deleteConsultationTest() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit();

        try {
            tester.assertLinkPresent("delete-2021-03-21T10:30");
        }
        catch (AssertionError e) {
            bookConsultationTest();
        }

        tester.clickLink("delete-2021-03-21T10:30");
        tester.assertTextPresent("Personal information for PAT10009");
        tester.assertTextNotPresent("2021-03-21 10:30 with Dr. Sole at Xperia");
    }

    // Test that a receptionist can book a consultation for a patient
    // (called by "deleteConsultationTest" and "bookConsultationFail2")
    public void bookConsultationTest() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit();
        tester.clickLinkWithText("Book a consultation");

        tester.assertTitleEquals("Book consultation");
        tester.assertTextPresent("Book consultation for Ilaria Bonetti (PAT10009)");
        tester.assertButtonPresentWithText("Save");
        tester.assertButtonPresentWithText("Back");
        tester.assertLinkPresentWithText("Home");

        tester.selectOption("doctor", "Beatrice Sole");
        tester.setTextField("date", "2021-03-21 10:30");
        tester.selectOption("clinic", "Xperia");
        tester.setTextField("reason", "Annual check-up");
        tester.submit();

        tester.assertTextPresent("Personal information for PAT10009");
        tester.assertTextPresent("2021-03-21 10:30 with Dr. Sole at Xperia");
    }



    // Test that a receptionist can't book a consultation if the date is in the wrong format
    @Test (expected = ScriptException.class)
    public void bookConsultationTestFail1() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit();
        tester.clickLinkWithText("Book a consultation");

        tester.assertTextPresent("Book consultation for Ilaria Bonetti (PAT10009)");
        tester.selectOption("doctor", "Beatrice Sole");
        tester.setTextField("date", "21-03-2021 10:30");
        tester.selectOption("clinic", "Xperia");
        tester.setTextField("reason", "Annual check-up");
        tester.submit();
    }

    // Test that a receptionist can't book a consultation if it's already present
    @Test (expected = ScriptException.class)
    public void bookConsultationTestFail2() {
        tester.beginAt("receptionist-home?staff=REC10006");
        tester.selectOptionByValue("patient", "PAT10009");
        tester.submit();

        try {

            tester.assertTextPresent("2021-03-21 10:30 with Dr. Sole at Xperia");
        }
        catch (AssertionError e) {
            bookConsultationTest();
        }

        tester.clickLinkWithText("Book a consultation");
        tester.assertTextPresent("Book consultation for Ilaria Bonetti (PAT10009)");
        tester.selectOption("doctor", "Beatrice Sole");
        tester.setTextField("date", "21-03-2021 10:30");
        tester.selectOption("clinic", "Xperia");
        tester.setTextField("reason", "Annual check-up");
        tester.submit();
    }
}
