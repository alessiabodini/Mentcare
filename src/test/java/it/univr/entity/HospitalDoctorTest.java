package it.univr.entity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HospitalDoctorTest {
    private HospitalDoctor doctor;

    @Before
    public void newDoctor() {
        doctor = new HospitalDoctor("Mauro", "Varese",
                LocalDate.parse("1980-06-27"), "3275758903",
                "via Montese 2, Mantova", "mauro.varese@gmail.com", "Psychology",
                "34589", new ArrayList<>(Arrays.asList("10001", "10002")));
    }

    @Test
    public void newEmptyDoctor() {
        doctor = new HospitalDoctor();
    }

    @Test
    public void newDoctorMinimal() {
        doctor = new HospitalDoctor("Mauro", "Varese",
                LocalDate.parse("1980-06-27"), "", "",
                "", "Psychology", "", new ArrayList<>());
    }

    @Test
    public void firstNameTest() {
        doctor.setFirstName("Vincenzo");
        assertEquals("Vincenzo", doctor.getFirstName());
    }

    @Test
    public void lastNameTest() {
        doctor.setLastName("Rossi");
        assertEquals("Rossi", doctor.getLastName());
    }

    @Test
    public void birthDateTest() {
        doctor.setBirthDate(LocalDate.parse("1980-04-12"));
        assertEquals(LocalDate.parse("1980-04-12"), doctor.getBirthDate());
    }

    @Test
    public void phoneNumberTest() {
        doctor.setPhoneNumber("3227894553");
        assertEquals("3227894553", doctor.getPhoneNumber());
    }

    @Test
    public void addressTest() {
        doctor.setAddress("via Malaspina 43, Mantova");
        assertEquals("via Malaspina 43, Mantova", doctor.getAddress());
    }

    @Test
    public void emailTest() {
        doctor.setEmail("mauro.varese@libero.it");
        assertEquals("mauro.varese@libero.it", doctor.getEmail());
    }

    @Test
    public void staffTest() {
        doctor.setStaff("Psychiatry");
        assertEquals("Psychiatry", doctor.getStaff());
    }

    @Test
    public void pagerTest() {
        doctor.setPager("12345");
        assertEquals("12345", doctor.getPager());
    }

    @Test
    public void consultationsTest() {
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        List<Consultation> consultations = new ArrayList<>(Arrays.asList(
                new Consultation(doctor, patient, LocalDateTime.parse("2021-02-12T11:00:00"), "Armonia",
                        "Weekly meeting"),
                new Consultation(doctor, patient, LocalDateTime.parse("2021-02-14T11:00:00"), "Xperia",
                "Weekly meeting")));
        doctor.setConsultations(consultations);
        assertEquals(consultations, doctor.getConsultations());
    }

    @Test
    public void addConsultationTest() {
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Consultation newConsultation = new Consultation(doctor, patient,
                LocalDateTime.parse("2021-02-12T11:00:00"), "Armonia", "Weekly meeting");
        doctor.addConsultation(newConsultation);
        List<Consultation> consultations = doctor.getConsultations();
        assertEquals(newConsultation, consultations.get(consultations.size() - 1));
    }

    @Test
    public void equalsTest() {
        // A Person is equal to someone with the same ID
        assertEquals(doctor, new HospitalDoctor("", "", null,
                "", "", "", "", "",
                new ArrayList<>(Arrays.asList("10001", "10002"))));
    }
}
