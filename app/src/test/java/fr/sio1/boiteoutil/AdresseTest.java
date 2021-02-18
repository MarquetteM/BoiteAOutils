package fr.sio1.boiteoutil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class AdresseTest {
    private  Adresse adresse;

    @Before
    public void setUp() {
        adresse = new Adresse(4,"Victor hugo",73000,"Chambéry");
    }

    @After
    public void tearDown() {

    }

    @Test
    public void calculerTest() {
        assertEquals(73000, adresse.getCp());
    }

    @Test
    public void getVilleTest() {
        adresse.setVille("Annecy");
        assertEquals("Annecy", adresse.getVille());
    }
}
