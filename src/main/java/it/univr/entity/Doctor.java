package it.univr.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance
public abstract class Doctor extends MedicalStaff {
    private String email;

    public Doctor() {}

    public Doctor(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address, String email, List<String> ids) {
        super(firstName, lastName, birthDate, phoneNumber, address);
        String patientID = "DOC" + generateId(ids);
        setId(patientID);
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
