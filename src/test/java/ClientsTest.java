import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
class ClientsTest {

    private Clients clients;

    @BeforeEach
    void setUp() {
        clients = new Clients(1, "Elend Venture", "Elend.venture@17shard.com");
    }

    @Test 
    void testAddClient() {
        assertEquals(1, clients.getId());
        assertEquals("Elend Venture", clients.getName());
        assertEquals("Elend.venture@17shard.com", clients.getCorreo());
        assertEquals(0, clients.fidelity.getPuntos());
        assertEquals(Fidelity.Lvl.BRONCE, clients.getLevel());
        assertEquals(0, clients.getStreakDias());
        assertNotNull(clients.getFidelity());
    }

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clients(2, "Invalid Client", "invalid-email");
        });
    }
    
    @Test
    void testValidEmail() {
        assertDoesNotThrow(() -> {
            new Clients(1, "Test", "test@domain.com");
        });

        assertDoesNotThrow(() -> {
            new Clients(1, "Test", "a@b.cl");
        });
    }

    @Test
    void testAddPuntos() {
        clients.addPuntos(100);
        assertEquals(100, clients.getPuntos());
        assertEquals(Fidelity.Lvl.BRONCE, clients.getLevel());
        
        clients.addPuntos(2000);
        assertEquals(2100, clients.getPuntos());
        assertEquals(Fidelity.Lvl.ORO, clients.getLevel());
    }


    @Test 
    void testSetName() {
        clients.setName("Kaladin Stormblessed");
        assertEquals("Kaladin Stormblessed", clients.getName());
        
        assertThrows(IllegalArgumentException.class, () -> {
            clients.setName("");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            clients.setName(null);
        });
    }

    @Test
    void testSetCorreo() {
        clients.setCorreo("kaladin@stormblessed.com");
        assertEquals("kaladin@stormblessed.com", clients.getCorreo());

        assertThrows(IllegalArgumentException.class, () -> {
            clients.setCorreo("");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            clients.setCorreo("invalid-email");
        });
    }

    @Test
    void testSetStreakDias() {
        clients.setStreakDias(14);
        assertEquals(14, clients.getStreakDias());

        assertThrows(IllegalArgumentException.class, () -> {
            clients.setStreakDias(-1);
        });
    }

    @Test
    void testAddPoints() {
        clients.addPuntos(100);
        assertEquals(100, clients.getPuntos());
        assertEquals(Fidelity.Lvl.BRONCE, clients.getLevel());

        clients.addPuntos(400);
        assertEquals(500, clients.getPuntos());
        assertEquals(Fidelity.Lvl.PLATA, clients.getLevel());
    }

    @Test
    void testLvlProg() {
        assertEquals(Fidelity.Lvl.BRONCE, clients.getLevel());

        clients.addPuntos(500);
        assertEquals(Fidelity.Lvl.PLATA, clients.getLevel());

        clients.addPuntos(1000);
        assertEquals(Fidelity.Lvl.ORO, clients.getLevel());

        clients.addPuntos(1500);
        assertEquals(Fidelity.Lvl.PLATINO, clients.getLevel());
    }

    
}