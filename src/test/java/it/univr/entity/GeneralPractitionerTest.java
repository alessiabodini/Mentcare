package it.univr.entity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeneralPractitionerTest {
    private GeneralPractitioner doctor;

    @Before
    public void newDoctor() {
        doctor = new GeneralPractitioner("Mauro", "Varese",
                LocalDate.parse("1980-06-27"), "3275758903",
                "via Montese 2", "mauro.varese@gmail.com", "Psychology",
                "Armonia",
                new ArrayList<>(Arrays.asList("10001", "10002")));
    }

    @Test
    public void newEmptyDoctor() {
        doctor = new GeneralPractitioner();
    }

    @Test
    public void practiceTest() {
        doctor.setPractice("General");
        assertEquals("General", doctor.getPractice());
    }

    @Test
    public void clinicTest() {
        doctor.setClinic("Xperia");
        assertEquals("Xperia", doctor.getClinic());
    }

    @Test
    public void patientsTest1() {
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", doctor, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());
        doctor.setPatients(Collections.singletonList(patient));
        assertEquals(Collections.singletonList(patient), doctor.getPatients());
    }

    @Test
    public void patientsTest2() {
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", doctor, new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());
        int size = doctor.getPatients().size();
        doctor.addPatient(patient);
        List<Patient> patients = doctor.getPatients();
        assertEquals(patient, patients.get(size));
        doctor.removePatient(patient);
        assertEquals(size, doctor.getPatients().size());
    }

    @Test
    public void toStringTest() {
        assertEquals(doctor.getFirstName() + " " + doctor.getLastName(), doctor.toString());
    }
}
