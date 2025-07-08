import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;

class FidelityInventoryTest {
    private FidelityInventory fidelityInventory;
    private ClientsInventory clientsInventory;
    private PurchasesInventory purchasesInventory;
    private Clients client;

    @BeforeEach
    void setUp() {
        clientsInventory = new ClientsInventory();
        purchasesInventory = new PurchasesInventory();
        fidelityInventory = new FidelityInventory(clientsInventory, purchasesInventory);
        client = clientsInventory.addClient("Elend Venture", "elend.venture@example.com");
    }

    @Test
    void testPurchaseBRONCE() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 100, purchaseDate);

        assertEquals(1, client.getPuntos());
    }

    @Test
    void testPurchasePLATA() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 200, purchaseDate);

        assertEquals(2, client.getPuntos());
    }

    @Test
    void testPurchaseORO() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 300, purchaseDate);

        assertEquals(3, client.getPuntos());
    }

    @Test
    void testPurchaseLimit() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 199.99, purchaseDate);

        assertEquals(1, client.getPuntos());
    }

    @Test
    void testPurchaseNoPoints() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 99.99, purchaseDate);

        assertEquals(0, client.getPuntos());
    }

    @Test
    void testPurchase3Points() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 350.00, purchaseDate);

        assertEquals(3, client.getPuntos());
    }

    @Test
    void testMaxBonusInADay() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(1, client.getPuntos());
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(2, client.getPuntos());
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(3, client.getPuntos());
    }

    @Test
    void testMaxBonusInADayLimit() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(1, client.getPuntos());
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(2, client.getPuntos());
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(3, client.getPuntos());
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(4, client.getPuntos());
    }

    @Test
    void testUpdateClientStreakDays() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(1, client.getStreakDias());

        LocalDateTime nextDay = purchaseDate.plusDays(1);
        fidelityInventory.processPurchase(client.getId(), 100.00, nextDay);
        assertEquals(2, client.getStreakDias());

        LocalDateTime gapDay = nextDay.plusDays(2);
        fidelityInventory.processPurchase(client.getId(), 100.00, gapDay);
        assertEquals(3, client.getStreakDias());
    }

    @Test
    void testUpdateClientStreakNonConsecutiveDays() {
        LocalDateTime purchaseDate = LocalDateTime.now();
        fidelityInventory.processPurchase(client.getId(), 100.00, purchaseDate);
        assertEquals(1, client.getStreakDias());

        LocalDateTime nonConsecutiveDay = purchaseDate.plusDays(3);
        fidelityInventory.processPurchase(client.getId(), 100.00, nonConsecutiveDay);
        assertEquals(1, client.getStreakDias());
    }

}