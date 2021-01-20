package it.univr.entity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ConditionTest {
    private Condition condition;

    @Before
    public void newCondition() {
        condition = new Condition("Anxiety disorder", new ArrayList<>(
                Arrays.asList("Feeling nervous, restless or tense", "Sweating")));
    }

    @Test
    public void newEmptyCondition() {
        condition = new Condition();
    }

    @Test
    public void printSymptomsTest() {
        condition = new Condition("Anxiety disorder", new ArrayList<>(
                Arrays.asList("Feeling nervous, restless or tense", "Sweating")));
        assertEquals(condition.getSymptoms().toString().replace("[","").replace("]",""),
                condition.printSymptoms());
    }

    @Test
    public void conditionTest() {
        condition.setName("Mood disorder");
        assertEquals("Mood disorder", condition.getName());
    }

    @Test
    public void symptomsTest() {
        condition.setSymptoms(new ArrayList<>(Collections.singletonList("Anxiety")));
        assertEquals(Collections.singletonList("Anxiety"), condition.getSymptoms());
    }

    @Test
    public void idTest() {
        System.out.println(condition.getId());
    }
}
