package it.univr.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patient extends Person {
    @OneToOne(targetEntity = GeneralPractitioner.class)
    @JoinColumn(name = "generalPractitioner", referencedColumnName = "id")
    private GeneralPractitioner generalPractitioner;
    @OneToMany(targetEntity = Condition.class, cascade=CascadeType.ALL)
    private List<Condition> conditions;
    @OneToMany(targetEntity = Medication.class, cascade=CascadeType.ALL)
    private List<Medication> medications;
    @ElementCollection(targetClass=String.class)
    private List<String> allergies;
    @OneToMany(targetEntity = Treatment.class, cascade=CascadeType.ALL)
    private List<Treatment> treatments;
    @OneToMany(targetEntity = Consultation.class, cascade=CascadeType.ALL)
    @JoinColumn(name = "consultations", referencedColumnName = "id")
    private List<Consultation> consultations;

    public Patient() {}

    public Patient(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address, GeneralPractitioner generalPractitioner, List<Condition> conditions, List<String> allergies, List<String> ids) {
        super(firstName, lastName, birthDate, phoneNumber, address);
        String patientID = "PAT" + super.generateId(ids);
        super.setId(patientID);
        this.generalPractitioner = generalPractitioner;
        this.conditions = conditions;
        this.allergies = allergies;
        this.medications = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.consultations = new ArrayList<>();
    }

    public void setGeneralPractitioner(GeneralPractitioner generalPractitioner) {
        this.generalPractitioner = generalPractitioner;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public void addMedication(Medication medication) {
        medications.add(medication);
    }

    public void removeMedication(Medication medication) {
        medications.remove(medication);
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public void addTreatment(Treatment treatment) {
        treatments.add(treatment);
    }

    public void removeTreatment(Treatment treatment) {
        treatments.remove(treatment);
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public void removeConsultation(Consultation consultation) {
        consultations.remove(consultation);
    }

    public GeneralPractitioner getGeneralPractitioner() {
        return generalPractitioner;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public String printAllergies() {
        return allergies.toString().replace("[","")
                .replace("]","").replace(" ", "");
    }
}
