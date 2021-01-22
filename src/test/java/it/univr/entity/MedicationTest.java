package it.univr.entity;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MedicationTest {
    private Medication medication;

    @Before
    public void newMedication() {
        medication = new Medication("Xanax", 5.0,
                "mg", new ArrayList<>(Collections.singletonList("Antibiotics")));
    }

    @Test
    public void newEmptyMedication() {
        medication = new Medication();
    }

    @Test
    public void nameTest() {
        medication.setName("Xanax Plus");
        assertEquals("Xanax Plus", medication.getName());
    }

    @Test
    public void doseTest() {
        medication.setDose(10.0);
        assertEquals(new Double(10), medication.getDose());
    }

    @Test
    public void unitTest() {
        medication.setUnit("g");
        assertEquals("g", medication.getUnit());
    }

    @Test
    public void allergiesTest() {
        medication.setAllergies(new ArrayList<>(Collections.singletonList("Antibiotics, Bees")));
        assertEquals(Collections.singletonList("Antibiotics, Bees"), medication.getAllergies());
        assertEquals(medication.getAllergies().toString()
                .replace("[","").replace("]","")
                        .replace(" ", ""),
                medication.printAllergies());
    }

    @Test
    public void idTest() {
        System.out.println(medication.getId());
    }

    @Test
    public void toStringTest() {
        assertEquals(medication.getName() + " (" + medication.getDose() + " " +
                medication.getUnit() + ")", medication.toString());
    }

    @Test
    public void equalsTest() {
        Medication med = new Medication("Xanax", 5.0,
                "mg", new ArrayList<>(Collections.singletonList("Antibiotics")));
        assertEquals(medication, med);

        med.setUnit("ml");
        assertNotEquals(medication, med);
        med.setDose(20.0);
        assertNotEquals(medication, med);
        med.setName("Prozac");
        assertNotEquals(medication, med);
    }
}
