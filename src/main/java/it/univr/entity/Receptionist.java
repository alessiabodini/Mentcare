package it.univr.entity;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Receptionist extends Person {

    public Receptionist() {}

    public Receptionist(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address, List<String> ids) {
        super(firstName, lastName, birthDate, phoneNumber, address);
        String receptionistID = "REC" + super.generateId(ids);
        super.setId(receptionistID);
    }
}
