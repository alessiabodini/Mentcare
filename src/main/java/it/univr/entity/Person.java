package it.univr.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Inheritance
@Table(name = "person_table")
public abstract class Person {
    @Id
    @Column(name = "id")
    private String id; // defined in the subclasses Patient and Doctor
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;

    public Person() {}

    public Person(String firstName, String lastName, LocalDate birthDate, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Generate a random ID (for patients and doctors),
    // checking that it doesn't already exists in PersonRepository
    protected String generateId(List<String> ids) {
        int num = 10000;
        if (ids != null && !ids.isEmpty())
            num = Integer.parseInt(ids.get(ids.size()-1).substring(3)) + 1;
        return String.valueOf(num);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getId(), person.getId()) ||
                (getFirstName().equals(person.getFirstName())
                && getLastName().equals(person.getLastName())
                && getBirthDate().equals(person.getBirthDate())
                && getPhoneNumber().equals(person.getPhoneNumber())
                && getAddress().equals(person.getAddress()));
    }
}
