package it.univr.entity;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Nurse extends MedicalStaff {

    public Nurse() {}

    public Nurse(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address, List<String> ids) {
        super(firstName, lastName, birthDate, phoneNumber, address);
        String nurseID = "NUR" + generateId(ids);
        super.setId(nurseID);
    }
}
