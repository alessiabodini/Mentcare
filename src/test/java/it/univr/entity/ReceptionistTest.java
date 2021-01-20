package it.univr.entity;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class ReceptionistTest {
    private Receptionist receptionist;

    @Before
    public void newReceptionist() {
        receptionist = new Receptionist("Martina", "Bronte",
                LocalDate.parse("1998-11-13"), "3987791703",
                "via Geppino 89, Verona", new ArrayList<>(Collections.singletonList("10002")));
    }

    @Test
    public void newEmptyReceptionist() {
        receptionist = new Receptionist();
    }
}
