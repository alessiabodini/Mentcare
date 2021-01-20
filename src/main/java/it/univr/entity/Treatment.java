package it.univr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Treatment {
    @Id
    @GeneratedValue
    private int id;
    private String description;
    private String frequency;

    public Treatment() {}

    public Treatment(String description, String frequency) {
        this.description = description;
        this.frequency = frequency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getFrequency() {
        return frequency;
    }

    @Override
    public String toString() {
        return description + " (" + frequency + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Treatment)) return false;
        Treatment treatment = (Treatment) o;
        return getDescription().equals(treatment.getDescription()) && getFrequency().equals(treatment.getFrequency());
    }
}
