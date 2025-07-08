import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.Optional;

class ClientsInventoryTest {

    private ClientsInventory clientsInventory;

    @BeforeEach
    void setUp() {
        clientsInventory = new ClientsInventory();
    }

    @Test
    void testAddClient() {
        Clients client = clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");
        assertNotNull(client);
        assertEquals(1, client.getId());
        assertEquals("Elend Venture", client.getName());
        assertEquals("elend.venture@17shard.com", client.getCorreo());
        assertEquals(1, clientsInventory.getClients().size());
    }

    @Test
    void testAddClients() {
        clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");
        clientsInventory.addClient("Kaladin Stormblessed", "kaladin.stormblessed@17shard.com");
        clientsInventory.addClient("Vin", "vin@17shard.com");

        List<Clients> clients = clientsInventory.getClients();
        assertEquals(3, clients.size());

        assertEquals("Elend Venture", clients.get(0).getName());
        assertEquals("Kaladin Stormblessed", clients.get(1).getName());
        assertEquals("Vin", clients.get(2).getName());
    }

    @Test
    void testFindClientById() {
        Clients client = clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");

        Optional<Clients> f = Optional.ofNullable(clientsInventory.getClientById(client.getId()));
        assertTrue(f.isPresent());
        assertEquals("Elend Venture", f.get().getName());
        assertEquals("elend.venture@17shard.com", f.get().getCorreo());
    }

    @Test
    void testFindbyIdNotF() {
        Optional<Clients> f = Optional.ofNullable(clientsInventory.getClientById(1616));
        assertFalse(f.isPresent());
    }

    @Test
    void testGetClients() {
        clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");
        clientsInventory.addClient("Kaladin Stormblessed", "kaladin.stormblessed@17shard.com");
        clientsInventory.addClient("Vin", "vin@17shard.com");
        List<Clients> clients = clientsInventory.getClients();
        assertEquals(3, clients.size());
    }

    @Test
    void testGetNoClients() {
        List<Clients> clients = clientsInventory.getClients();
        assertTrue(clients.isEmpty());
    }

    @Test
    void testUpdateClient() {
        clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");

        boolean updated = clientsInventory.updateClient(1, "Kaladin Stormblessed", "kaladin.stormblessed@17shard.com");

        assertTrue(updated);

        Clients client = clientsInventory.getClientById(1);
        assertNotNull(client);
        assertEquals("Kaladin Stormblessed", client.getName());
        assertEquals("kaladin.stormblessed@17shard.com", client.getCorreo());

    }

    @Test
    void testFindClientByIdNotFound() {
        Clients client = clientsInventory.getClientById(999);
        assertNull(client);
    }

    @Test
    void testUpdateInvalidEmail() {
        clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");
        
        assertThrows(IllegalArgumentException.class, () -> {
            clientsInventory.updateClient(1, "Elend Venture", "invalid-email");
        });

    }

    
    @Test
    void testDelete() {
        clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");
        
        assertTrue(clientsInventory.clientExists(1));
        boolean deleted = clientsInventory.deleteClient(1);
        assertTrue(deleted);
        assertFalse(clientsInventory.clientExists(1));
    }

    @Test
    void testDeleteNotFound() {
        boolean deleted = clientsInventory.deleteClient(999);
        assertFalse(deleted);
    }

    @Test
    void testclientExists() {
        clientsInventory.addClient("Elend Venture", "elend.venture@17shard.com");

        assertTrue(clientsInventory.clientExists(1));
        assertFalse(clientsInventory.clientExists(999));
    }

    @Test
    void testaddClientWithInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            clientsInventory.addClient("Test", "invalid-email");
        });
    }

}