package it.univr;

import com.gargoylesoftware.htmlunit.ScriptException;
import net.sourceforge.jwebunit.junit.WebTester;
import org.junit.Before;
import org.junit.Test;

public class DoctorTest {
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

    // Test the authentication works with a valid doctor ID
    @Test
    public void authenticationTest() {
        tester.beginAt("/");
        tester.clickLinkWithText("Authenticate");

        tester.assertTitleEquals("Authentication");
        tester.assertTextPresent("ID:");
        tester.setTextField("id", "DOC10000");
        tester.submit();

        tester.assertTitleEquals("Doctor's Homepage");
        tester.assertTextPresent("Welcome, DOC10000!");
        tester.assertTextPresent("Select your patient:");
        tester.assertButtonPresentWithText("View medical record");
        tester.assertLinkPresentWithText("Exit");
    }

    // Test the authentication doesn't work with a non-valid ID
    @Test
    public void authenticationTestFail() {
        tester.beginAt("/");
        tester.clickLinkWithText("Authenticate");

        tester.assertTitleEquals("Authentication");
        tester.setTextField("id", "NUR10000");
        tester.submit();

        tester.assertTextPresent("ACCESS DENIED!");
    }

    // Test the medical record of a patient can be seen (by a doctor)
    @Test
    public void viewRecordTest() {
        // "doctor-home" needs the ID of the person already authenticated
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        tester.assertTitleEquals("Patient's Medical Record");
        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertButtonPresentWithText("Edit medical record");
        tester.assertLinkPresentWithText("Prescribe new medication");
        tester.assertLinkPresentWithText("Prescribe new treatment");
        tester.assertLinkPresentWithText("Home");
        tester.clickLinkWithText("Home");

        tester.assertTextPresent("Welcome, DOC10000!");
    }

    // Test that a medical records can be edited (by a doctor)
    @Test
    public void editRecordTest() {
        // "doctor-home" needs the ID of the person already authenticated
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        tester.assertTitleEquals("Patient's Medical Record");
        tester.assertTextPresent("Medical record: PAT10008");
        tester.submit();

        tester.assertTextPresent("Edit medical record for PAT10008");
        tester.assertButtonPresentWithText("Back");
        tester.assertButtonPresentWithText("Save and continue");
        tester.assertLinkPresentWithText("Home");

        // Add allergy
        tester.setTextField("allergies", "Benzodiazepines,Dust");

        // Uncheck a checked disorder and check an unchecked disorder
        tester.uncheckCheckbox("conditions", "Mood disorder");
        tester.checkCheckbox("conditions", "Anxiety disorder");
        tester.submit();

        // Add a symptom to the new disorder
        tester.assertTextPresent("Edit symptoms for PAT10008");
        tester.setTextField("symptoms", "Panic attacks");
        tester.submit();

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextPresent("Benzodiazepines,Dust");
        tester.assertTextNotPresent("Mood disorder");
        tester.assertTextPresent("Anxiety disorder");
        tester.assertTextPresent("Panic attacks");
    }

    private void addMedicationProzac() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");
        tester.clickLinkWithText("Prescribe new medication");
        tester.selectOption("name", "Prozac");
        tester.setTextField("dose", "20.0");
        tester.selectOption("unit", "mg");
        tester.submit();
    }

    // Test that a medication can be deleted
    @Test
    public void deleteMedicationTest() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        // Assert that that medication has been prescribed
        try {
            tester.assertLinkPresent("edit-med-Prozac-20.0");
        }
        catch (AssertionError e) {
            addMedicationProzac();
        }

        tester.clickLink("edit-med-Prozac-20.0");
        tester.assertTitleEquals("Edit medication");
        tester.assertTextPresent("Edit medication for PAT10008");
        tester.assertLinkPresentWithText("Delete");
        tester.assertButtonPresentWithText("Save");
        tester.assertButtonPresentWithText("Back");
        tester.assertLinkPresentWithText("Home");
        tester.clickLinkWithText("Delete");

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextNotPresent("Prozac (20.0 mg)");
    }

    // Test that a medication can be edit in the dose and unit
    @Test
    public void editMedicationTest() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        // Assert that that medication has been previously prescribed
        try {
            tester.assertLinkPresent("edit-med-Prozac-20.0");
        }
        catch (AssertionError e) {
            addMedicationProzac();
        }
        // Assert that that medication has been previously prescribed
        try {
            tester.assertLinkNotPresent("edit-med-Prozac-28.0");
        }
        catch (AssertionError e) {
            tester.clickLink("edit-med-Prozac-28.0");
            tester.clickLinkWithText("Delete");
        }

        tester.clickLink("edit-med-Prozac-20.0");
        tester.assertTextPresent("Edit medication for PAT10008");
        tester.setTextField("dose", "28");
        tester.selectOption("unit", "units");
        tester.submit();

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextPresent("Prozac (28.0 units)");
    }

    // Test that a medication can't be edited in one already prescribed
    @Test (expected = ScriptException.class)
    public void editMedicationTestFail() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        try {
            tester.assertLinkPresent("edit-med-Prozac-20.0");
        }
        catch (AssertionError e) {
            addMedicationProzac();
        }

        tester.clickLink("edit-med-Prozac-20.0");
        tester.assertTextPresent("Edit medication for PAT10008");
        tester.setTextField("dose", "1.5");
        tester.selectOption("unit", "units");
        tester.submit();
    }

    // Test that a new medication can be prescribed
    @Test
    public void prescribeMedicationTest() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        try {
            tester.assertLinkNotPresent("edit-med-Xanax-0.5");
        }
        catch (AssertionError e) {
            tester.clickLink("edit-med-Xanax-0.5");
            tester.clickLinkWithText("Delete");
        }

        tester.clickLinkWithText("Prescribe new medication");
        tester.assertTitleEquals("Prescribe medication");
        tester.assertTextPresent("Prescribe medication for PAT10008");
        tester.assertButtonPresentWithText("Back");
        tester.assertButtonPresentWithText("Save");
        tester.assertLinkPresentWithText("Home");

        tester.selectOption("name", "Xanax");
        tester.setTextField("dose", "0.5");
        tester.selectOption("unit", "mg");
        tester.assertCheckboxPresent("checkbox");
        tester.submit();

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextPresent("Xanax (0.5 mg)");
    }

    // Test that a medication can't be prescribed if already present
    @Test (expected = ScriptException.class)
    public void prescribeMedicationTestFail1() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");
        tester.clickLinkWithText("Prescribe new medication");

        tester.assertTitleEquals("Prescribe medication");
        tester.assertTextPresent("Prescribe medication for PAT10008");
        tester.assertButtonPresentWithText("Back");
        tester.assertButtonPresentWithText("Save");
        tester.assertLinkPresentWithText("Home");

        tester.selectOption("name", "Xanax");
        tester.setTextField("dose", "1");
        tester.selectOption("unit", "mg");
        tester.submit();
    }

    // Test that a medication not in the system (not real) can't be prescribed
    @Test (expected = ScriptException.class)
    public void prescribeMedicationTestFail2() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");
        tester.clickLinkWithText("Prescribe new medication");

        tester.assertTitleEquals("Prescribe medication");
        tester.assertTextPresent("Prescribe medication for PAT10008");
        tester.assertButtonPresentWithText("Back");
        tester.assertButtonPresentWithText("Save");
        tester.assertLinkPresentWithText("Home");

        tester.selectOption("name", "Xanax");
        tester.setTextField("dose", "5");
        tester.selectOption("unit", "mg");
        tester.submit();
    }

    private void addTreatment12() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");
        tester.clickLinkWithText("Prescribe new treatment");
        tester.setTextField("description", "Meetings with doctor");
        tester.selectOption("frequency", "Monthly");
        tester.submit();
    }

    // Test that a treatment already prescribed can be deleted
    @Test
    public void deleteTreatmentTest() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        try {
            tester.assertLinkPresent("edit-treat-Meetings with doctor-Monthly");
        }
        catch (AssertionError e) {
            addTreatment12();
        }

        tester.clickLink("edit-treat-Meetings with doctor-Monthly");
        tester.assertTitleEquals("Edit treatment");
        tester.assertTextPresent("Edit treatment for PAT10008");
        tester.assertLinkPresentWithText("Delete");
        tester.assertButtonPresentWithText("Save");
        tester.assertButtonPresentWithText("Back");
        tester.assertLinkPresentWithText("Home");
        tester.clickLinkWithText("Delete");

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextNotPresent("Meetings with doctor (Monthly)");
    }

    // Test that a treatment can be edited in description and frequency
    @Test
    public void editTreatmentTest() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        // Assert the treatment to edit is present
        try {
            tester.assertTextPresent("Meetings with doctor (Monthly)");
        }
        catch (AssertionError e) {
            addTreatment12();
        }

        // Assert the final treatment isn't already present
        try {
            tester.assertTextNotPresent("Meetings with psychiatric (Annually)");
        }
        catch (AssertionError e) {
            tester.clickLink("edit-treat-Meetings with psychiatric-Annually");
            tester.clickLinkWithText("Delete");
        }

        tester.clickLink("edit-treat-Meetings with doctor-Monthly");
        tester.assertTextPresent("Edit treatment for PAT10008");
        tester.setTextField("description", "Meetings with psychiatric");
        tester.selectOption("frequency", "Annually");
        tester.submit();

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextPresent("Meetings with psychiatric (Annually)");
    }

    // Test that a treatment can be prescribed (if not already present)
    @Test
    public void prescribeTreatmentTest() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        // Assert that the treatment inserted isn't already present
        try {
            tester.assertTextNotPresent("Meetings with doctor (Monthly)");
        }
        catch (AssertionError e) {
            deleteTreatmentTest();
        }

        tester.clickLinkWithText("Prescribe new treatment");
        tester.assertTitleEquals("Prescribe treatment");
        tester.assertTextPresent("Prescribe treatment for PAT10008");
        tester.setTextField("description", "Meetings with doctor");
        tester.selectOption("frequency", "Monthly");
        tester.submit();

        tester.assertTextPresent("Medical record: PAT10008");
        tester.assertTextPresent("Meetings with doctor (Monthly)");
    }

    // Test that a treatment can't be prescribed if already present
    @Test (expected = ScriptException.class)
    public void prescribeTreatmentTestFail() {
        tester.beginAt("doctor-home?staff=DOC10000");
        tester.selectOptionByValue("patient", "PAT10008");
        tester.submit("view-record");

        // Assert that the treatment is already present
        try {
            tester.assertTextPresent("Meetings with doctor (Monthly)");
        }
        catch (AssertionError e) {
            addTreatment12();
        }

        tester.clickLinkWithText("Prescribe new treatment");
        tester.assertTitleEquals("Prescribe treatment");
        tester.assertTextPresent("Prescribe treatment for PAT10008");
        tester.setTextField("description", "Meetings with doctor");
        tester.selectOption("frequency", "Monthly");
        tester.submit();
    }

}
