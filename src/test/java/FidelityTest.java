import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


class FidelityTest {
    private Fidelity fidelity;

    @BeforeEach
    void setUp() {
        fidelity = new Fidelity(0);
    }

    @Test
    void testInitialLvl() {
        assertEquals(Fidelity.Lvl.BRONCE, fidelity.getLvl());
        assertEquals(0, fidelity.getPuntos());
        assertEquals(1.0, fidelity.getMultiplier());
    }

    @Test
    void testBRONZELvl() {
        fidelity.addPuntos(499);
        assertEquals(Fidelity.Lvl.BRONCE, fidelity.getLvl());
        assertEquals(499, fidelity.getPuntos());
        assertEquals(1.0, fidelity.getMultiplier());
    }

    @Test
    void testPLATALvl() {
        fidelity.addPuntos(500);
        assertEquals(Fidelity.Lvl.PLATA, fidelity.getLvl());
        assertEquals(500, fidelity.getPuntos());
        assertEquals(1.2, fidelity.getMultiplier());
    }

    @Test
    void testOROLvl() {
        fidelity.addPuntos(1500);
        assertEquals(Fidelity.Lvl.ORO, fidelity.getLvl());
        assertEquals(1500, fidelity.getPuntos());
        assertEquals(1.5, fidelity.getMultiplier());
    }

    @Test
    void testPLATINOLvl() {
        fidelity.addPuntos(4000);
        assertEquals(Fidelity.Lvl.PLATINO, fidelity.getLvl());
        assertEquals(4000, fidelity.getPuntos());
        assertEquals(2.0, fidelity.getMultiplier());
    }

    @Test
    void testAddPuntos() {
        fidelity.addPuntos(500);
        assertEquals(500, fidelity.getPuntos());
        assertEquals(Fidelity.Lvl.PLATA, fidelity.getLvl());
        assertEquals(1.2, fidelity.getMultiplier());
    }

    @Test
    void testAddNegativePuntos() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fidelity.addPuntos(-100);
        });
        assertEquals("Puntos cannot be negative", exception.getMessage());
    }

    @Test
    void testFidelityProg() {
        fidelity.addPuntos(499);
        assertEquals(Fidelity.Lvl.BRONCE, fidelity.getLvl());
        assertEquals(499, fidelity.getPuntos());
        assertEquals(1.0, fidelity.getMultiplier());

        fidelity.addPuntos(600);
        assertEquals(Fidelity.Lvl.PLATA, fidelity.getLvl());
        assertEquals(1099, fidelity.getPuntos());
        assertEquals(1.2, fidelity.getMultiplier());

        fidelity.addPuntos(1000);
        assertEquals(Fidelity.Lvl.ORO, fidelity.getLvl());
        assertEquals(2099, fidelity.getPuntos());
        assertEquals(1.5, fidelity.getMultiplier());

        fidelity.addPuntos(4000);
        assertEquals(Fidelity.Lvl.PLATINO, fidelity.getLvl());
        assertEquals(6099, fidelity.getPuntos());
        assertEquals(2.0, fidelity.getMultiplier());
    }

    @Test
    void testLvlMultpliers() {
        assertEquals(1.0, Fidelity.Lvl.BRONCE.getMultiplier());
        assertEquals(1.2, Fidelity.Lvl.PLATA.getMultiplier());
        assertEquals(1.5, Fidelity.Lvl.ORO.getMultiplier());
        assertEquals(2.0, Fidelity.Lvl.PLATINO.getMultiplier());
    }

}