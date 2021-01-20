package it.univr.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Consultation {
    @Id
    @GeneratedValue()
    private int id;
    @OneToOne(targetEntity = HospitalDoctor.class)
    @JoinColumn(name = "doctor", referencedColumnName = "id")
    private HospitalDoctor doctor;
    @OneToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patient", referencedColumnName = "id")
    private Patient patient;
    private LocalDateTime date;
    private String clinic;
    private String reason;

    public Consultation() {}

    public Consultation(HospitalDoctor doctor, Patient patient, LocalDateTime date, String clinic, String reason) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.clinic = clinic;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public HospitalDoctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getClinic() {
        return clinic;
    }

    public String getReason() {
        return reason;
    }

    public void setDoctor(HospitalDoctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " with Dr. " + doctor.getLastName() + " at " +
                clinic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consultation)) return false;
        Consultation that = (Consultation) o;
        return getDoctor().equals(that.getDoctor()) && getPatient().equals(that.getPatient()) && getDate().equals(that.getDate()) && getClinic().equals(that.getClinic());
    }
}
