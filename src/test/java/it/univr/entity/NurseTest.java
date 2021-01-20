package it.univr.entity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class NurseTest {
    private Nurse nurse;

    @Before
    public void newNurse() {
        nurse = new Nurse("Miranda", "Dotti",
                LocalDate.parse("1985-12-28"), "3186791703",
                "via Cassia 45, Verona", new ArrayList<>(Collections.singletonList("10004")));
    }

    @Test
    public void newEmptyNurse() {
        nurse = new Nurse();
    }
}
