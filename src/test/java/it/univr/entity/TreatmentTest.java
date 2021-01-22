package it.univr.entity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TreatmentTest {
    private Treatment treatment;

    @Before
    public void newTreatment() {
        treatment = new Treatment("Meetings with psychologist", "Weekly");
    }

    @Test
    public void newEmptyTreatment() {
        treatment = new Treatment();
    }

    @Test
    public void descriptionTest() {
        treatment.setDescription("First meetings");
        assertEquals("First meetings", treatment.getDescription());
    }

    @Test
    public void frequencyTest() {
        treatment.setFrequency("Monthly");
        assertEquals("Monthly", treatment.getFrequency());
    }

    @Test
    public void idTest() {
        int id = treatment.getId();
        treatment.setId(id);
    }

    @Test
    public void toStringTest() {
        assertEquals(treatment.getDescription() + " (" + treatment.getFrequency() + ")",
                treatment.toString());
    }

    @Test
    public void equalsTest() {
        Treatment treat = new Treatment("Meetings with psychologist", "Weekly");
        assertEquals(treatment, treat);

        treat.setFrequency("Monthly");
        assertNotEquals(treatment, treat);
        treat.setDescription("Meetings");
        assertNotEquals(treatment, treat);
    }
}
