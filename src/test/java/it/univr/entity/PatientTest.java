package it.univr.entity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class PatientTest {
    private Patient patient;

    @Before
    public void newPatient() {
        GeneralPractitioner doctor = new GeneralPractitioner("Mauro", "Varese",
                LocalDate.parse("1980-06-27"), "3275758903",
                "via Montese 2", "mauro.varese@gmail.com", "Psychology",
                "Armonia", new ArrayList<>());
        List<Condition> conditionList = new ArrayList<>(Collections.singletonList(
                new Condition("Eating disorder",
                        new ArrayList<>(Arrays.asList("Weight loss", "Weakness", "Fatigue")))));
        List<String> allergies = new ArrayList<>(Arrays.asList("Bees", "Dust"));

        patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", doctor, conditionList, allergies,
                new ArrayList<>());
    }

    @Test
    public void newEmptyPatient() {
        patient = new Patient();
    }

    @Test
    public void generalPractitionerTest() {
        GeneralPractitioner doctor = new GeneralPractitioner("Vincenzo", "Varese",
                LocalDate.parse("1980-06-27"), "3275758903",
                "via Montese 2", "mauro.varese@gmail.com", "Psychology",
                "Armonia", new ArrayList<>());
        patient.setGeneralPractitioner(doctor);
        assertEquals(doctor, patient.getGeneralPractitioner());
    }

    @Test
    public void conditionsTest() {
        List<Condition> conditionList = new ArrayList<>(Arrays.asList(
                new Condition("Eating disorder",
                        new ArrayList<>(Arrays.asList("Weight loss", "Weakness"))),
                new Condition("Mood disorder", new ArrayList<>())));
        patient.setConditions(conditionList);
        assertEquals(conditionList, patient.getConditions());
    }

    @Test
    public void medicationsTest1() {
        List<Medication> medicationList = new ArrayList<>(Collections.singletonList(
                new Medication("Xanax Plus", 0.5, "mg", new ArrayList<>())));
        patient.setMedications(medicationList);
        assertEquals(medicationList, patient.getMedications());
    }

    @Test
    public void medicationsTest2() {
        Medication med = new Medication("Xanax Plus", 0.5, "mg", new ArrayList<>());
        int size = patient.getMedications().size();
        patient.addMedication(med);
        List<Medication> meds = patient.getMedications();
        assertEquals(med, meds.get(meds.size() - 1));
        patient.removeMedication(med);
        assertEquals(size, patient.getMedications().size());
    }

    @Test
    public void treatmentsTest1() {
        List<Treatment> treatmentList = new ArrayList<>(Collections.singletonList(
                new Treatment("Meetings with psychologist", "Weekly")));
        patient.setTreatments(treatmentList);
        assertEquals(treatmentList, patient.getTreatments());
    }

    @Test
    public void treatmentsTest2() {
        Treatment treat = new Treatment("Meetings with psychiatric",
                "Weekly");
        int size = patient.getTreatments().size();
        patient.addTreatment(treat);
        List<Treatment> treats = patient.getTreatments();
        assertEquals(treat, treats.get(treats.size() - 1));
        patient.removeTreatment(treat);
        assertEquals(size, patient.getTreatments().size());
    }

    @Test
    public void consultationsTest() {
        HospitalDoctor doctor = new HospitalDoctor("Beatrice", "Sole",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "beatrice.sole@gmail.com",
                "Neuropsychiatry", "43212", new ArrayList<>());
        List<Consultation> consultations = new ArrayList<>(Arrays.asList(
                new Consultation(doctor, patient, LocalDateTime.parse("2021-02-12T11:00:00"), "Armonia",
                        "Weekly meeting"),
                new Consultation(doctor, patient, LocalDateTime.parse("2021-02-14T11:00:00"), "Xperia",
                        "Weekly meeting")));
        patient.setConsultations(consultations);
        assertEquals(consultations, patient.getConsultations());
    }

    @Test
    public void addConsultationTest() {
        HospitalDoctor doctor = new HospitalDoctor("Beatrice", "Sole",
                LocalDate.parse("1976-03-16"), "3275758903",
                "via Gonzaga 4, Mantova", "beatrice.sole@gmail.com",
                "Neuropsychiatry", "43212", new ArrayList<>());
        Consultation newConsultation = new Consultation(doctor, patient, LocalDateTime.parse("2021-02-12T11:00:00"),
                "Xperia", "Monthly meeting");
        int size = patient.getConsultations().size();
        patient.addConsultation(newConsultation);
        List<Consultation> consultations = patient.getConsultations();
        assertEquals(newConsultation, consultations.get(size));
        patient.removeConsultation(newConsultation);
        assertEquals(size, patient.getConsultations().size());
    }

    @Test
    public void allergiesTest() {
        List<String> allergies = new ArrayList<>(Arrays.asList("Bees", "Dust"));
        patient.setAllergies(allergies);
        assertEquals(allergies, patient.getAllergies());
        assertEquals(allergies.toString().replace("[","")
                        .replace("]","").replace(" ", ""),
                patient.printAllergies());
    }

    @Test
    public void equalsTest() {
        patient = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Patient patient2 = new Patient("Alessandro", "Cremonini",
                LocalDate.parse("1961-03-05"), "3678965342",
                "via Dalla Spina 77, Verona", new GeneralPractitioner(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(Collections.singletonList("PAT10000")));
        assertEquals(patient, patient2);
    }

}
