import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.List;

class PurchasesInventoryTest {
    private PurchasesInventory purchasesInventory;
    private LocalDateTime d1;
    private LocalDateTime d2;

    @BeforeEach
    void setUp() {
        purchasesInventory = new PurchasesInventory();
        d1 = LocalDateTime.of(2025, 1, 1, 10, 0);
        d2 = LocalDateTime.of(2025, 1, 2, 10, 0);
    }

    @Test
    void testAddPurchase() {
        Purchases purchase = purchasesInventory.addPurchase(1, 100, d1);
        assertNotNull(purchase);
        assertEquals(1, purchase.getIdCompra());
        assertEquals(1, purchase.getIdCliente());
        assertEquals(100, purchase.getMonto());
        assertEquals(d1, purchase.getDate());
    }

    @Test
    void testAddMultiplePurchases() {
        Purchases purchase1 = purchasesInventory.addPurchase(1, 100, d1);
        Purchases purchase2 = purchasesInventory.addPurchase(2, 200, d2);
        
        assertNotNull(purchase1);
        assertNotNull(purchase2);
        assertEquals(1, purchase1.getIdCompra());
        assertEquals(2, purchase2.getIdCompra());
        assertEquals(100, purchase1.getMonto());
        assertEquals(200, purchase2.getMonto());
    }

    @Test
    void testFindById() {
        Purchases purchase = purchasesInventory.addPurchase(1, 100, d1);
        Purchases foundPurchase = purchasesInventory.getPurchaseById(purchase.getIdCompra());
        
        assertNotNull(foundPurchase);
        assertEquals(purchase.getIdCompra(), foundPurchase.getIdCompra());
    }

    @Test
    void testFindByIdNotFound() {
        Purchases foundPurchase = purchasesInventory.getPurchaseById(999);
        assertNull(foundPurchase);
    }

    @Test
    void testGetPurchases() {
        purchasesInventory.addPurchase(1, 100, d1);
        purchasesInventory.addPurchase(2, 200, d2);
        
        List<Purchases> purchases = purchasesInventory.getPurchases();
        assertEquals(2, purchases.size());
        assertTrue(purchases.stream().anyMatch(p -> p.getIdCompra() == 1));
        assertTrue(purchases.stream().anyMatch(p -> p.getIdCompra() == 2));
    }

    @Test
    void testGetPurchasesEmpty() {
        List<Purchases> purchases = purchasesInventory.getPurchases();
        assertTrue(purchases.isEmpty());
    }

    @Test
    void testUpdatePurchase() {
        Purchases purchase = purchasesInventory.addPurchase(1, 100, d1);
        boolean updated = purchasesInventory.updatePurchase(purchase.getIdCompra(), 2, 150, d2);
        
        assertTrue(updated);
        Purchases updatedPurchase = purchasesInventory.getPurchaseById(purchase.getIdCompra());
        assertNotNull(updatedPurchase);
        assertEquals(2, updatedPurchase.getIdCliente());
        assertEquals(150, updatedPurchase.getMonto());
        assertEquals(d2, updatedPurchase.getDate());
    }

    @Test
    void testUpdatePurchaseNotFound() {
        boolean updated = purchasesInventory.updatePurchase(999, 1, 150, d2);
        assertFalse(updated);
    }

    @Test
    void testDeletePurchase() {
        Purchases purchase = purchasesInventory.addPurchase(1, 100, d1);
        boolean deleted = purchasesInventory.deletePurchase(purchase.getIdCompra());
        
        assertTrue(deleted);
        Purchases foundPurchase = purchasesInventory.getPurchaseById(purchase.getIdCompra());
        assertNull(foundPurchase);
    }

    @Test
    void testDeletePurchaseNotFound() {
        boolean deleted = purchasesInventory.deletePurchase(999);
        assertFalse(deleted);
    }
}