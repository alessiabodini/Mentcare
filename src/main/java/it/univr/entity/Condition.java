package it.univr.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Condition {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ElementCollection(targetClass=String.class)
    private List<String> symptoms;

    public Condition() {}

    public Condition(String name, List<String> symptoms) {
        this.name = name;
        this.symptoms = symptoms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = new ArrayList<>();
        for (String symptom: symptoms)
            addSymptom(symptom);
    }

    public void addSymptom(String symptom) {
        symptoms.add(symptom);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public String printSymptoms() {
        return symptoms.toString().replace("[","").replace("]","");
    }
}
