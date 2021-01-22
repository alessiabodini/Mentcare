package it.univr.entity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ConsultationTest {
    private Consultation consultation;

    @Before
    public void newConsultation() {
        HospitalDoctor doctor = new HospitalDoctor("Beatrice", "Sole",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "beatrice.sole@gmail.com",
                "Neuropsychiatry", "43212", new ArrayList<>());
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        consultation = new Consultation(doctor, patient, LocalDateTime.parse("2021-02-12T11:00:00"),
                "Armonia", "Weekly meeting");

        assertEquals(consultation.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " with Dr. " + doctor.getLastName() + " at " +
                consultation.getClinic(), consultation.toString());
    }

    @Test
    public void newEmptyConsultation() {
        consultation = new Consultation();
    }

    @Test
    public void dateTest() {
        consultation.setDate(LocalDateTime.parse("2021-03-12T12:00:00"));
        assertEquals(LocalDateTime.parse("2021-03-12T12:00:00"), consultation.getDate());
    }

    @Test
    public void clinicTest() {
        consultation.setClinic("Xperia");
        assertEquals("Xperia", consultation.getClinic());
    }

    @Test
    public void reasonTest() {
        consultation.setReason("Monthly meetings");
        assertEquals("Monthly meetings", consultation.getReason());
    }

    @Test
    public void idTest() {
        System.out.println(consultation.getId());
    }

    @Test
    public void doctorTest() {
        HospitalDoctor doctor = new HospitalDoctor("Saverio", "Monte",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "saverio.monte@gmail.com",
                "Neuropsychiatry", "43212", new ArrayList<>());
        consultation.setDoctor(doctor);
        assertEquals(doctor, consultation.getDoctor());
    }

    @Test
    public void patientTest() {
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        consultation.setPatient(patient);
        assertEquals(patient, consultation.getPatient());
    }

    @Test
    public void equalsTest() {
        HospitalDoctor doctor = new HospitalDoctor("Beatrice", "Sole",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "beatrice.sole@gmail.com",
                "Neuropsychiatry", "43212", new ArrayList<>());
        Patient patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), Arrays.asList("DOC10000"));
        consultation = new Consultation(doctor, patient, LocalDateTime.parse("2021-02-12T11:00:00"),
                "Armonia", "Weekly meeting");
        Consultation consultation1 = new Consultation(doctor, patient, LocalDateTime.parse("2021-02-12T11:00:00"),
                "Armonia", "Weekly meeting");
        assertEquals(consultation1, consultation);

        consultation1.setClinic("Xperia");
        assertNotEquals(consultation, consultation1);
        consultation1.setDate(LocalDateTime.parse("2021-02-12T12:00:00"));
        assertNotEquals(consultation, consultation1);
        Patient patient1 = new Patient("Alessio", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), Arrays.asList("DOC10000", "PAT10001", "DOC10002"));
        consultation1.setPatient(patient1);
        assertNotEquals(consultation, consultation1);
        HospitalDoctor doctor1 = new HospitalDoctor("Benedetta", "Soli",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "beatrice.sole@gmail.com",
                "Neuropsychiatry", "43212", Arrays.asList("DOC10000", "PAT10001"));
        consultation1.setDoctor(doctor1);
        assertNotEquals(consultation, consultation1);
    }
}