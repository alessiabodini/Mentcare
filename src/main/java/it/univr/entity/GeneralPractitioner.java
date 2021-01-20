package it.univr.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GeneralPractitioner extends Doctor {
    private String practice;
    private String clinic;
    @OneToMany(targetEntity = Patient.class)
    @JoinColumn(name = "patients", referencedColumnName = "id")
    private List<Patient> patients;

    public GeneralPractitioner() {}

    public GeneralPractitioner(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address, String email, String practice, String clinic, List<String> ids) {
        super(firstName, lastName, birthDate, phoneNumber, address, email, ids);
        this.practice = practice;
        this.clinic = clinic;
        this.patients = new ArrayList<>();
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public String getPractice() {
        return practice;
    }

    public String getClinic() {
        return clinic;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
