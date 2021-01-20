package it.univr.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Medication {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Double dose;
    private String unit;
    @ElementCollection(targetClass=String.class)
    private List<String> allergies;

    public Medication() {}

    public Medication(String name, Double dose, String unit, List<String> allergies) {
        this.name = name;
        this.dose = dose;
        this.unit = unit;
        this.allergies = allergies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDose(Double dose) {
        this.dose = dose;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getDose() {
        return dose;
    }

    public String getUnit() {
        return unit;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public String printAllergies() {
        return allergies.toString().replace("[","")
                .replace("]","").replace(" ", "");
    }

    @Override
    public String toString() {
        return name + " (" + dose + " " + unit + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication)) return false;
        Medication that = (Medication) o;
        return name.equals(that.getName()) && dose.equals(that.getDose()) && unit.equals(that.getUnit());
    }
}
