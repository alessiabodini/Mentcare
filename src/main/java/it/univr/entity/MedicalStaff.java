package it.univr.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import java.time.LocalDate;

@Entity
@Inheritance
public abstract class MedicalStaff extends Person {

    public MedicalStaff() {}

    public MedicalStaff(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address) {
        super(firstName, lastName, birthDate, phoneNumber, address);
    }
}
