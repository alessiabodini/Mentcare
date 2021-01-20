package it.univr.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HospitalDoctor extends Doctor {
    private String staff;
    private String pager;
    @OneToMany(targetEntity = Consultation.class, cascade=CascadeType.ALL)
    private List<Consultation> consultations;

    public HospitalDoctor() {}

    public HospitalDoctor(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address, String email, String staff, String pager, List<String> ids) {
        super(firstName, lastName, birthDate, phoneNumber, address, email, ids);
        this.staff = staff;
        this.pager = pager;
        this.consultations = new ArrayList<>();
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public void addConsultation(Consultation consultation) {
        consultations.add(consultation);
    }

    public String getStaff() {
        return staff;
    }

    public String getPager() {
        return pager;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }
}
