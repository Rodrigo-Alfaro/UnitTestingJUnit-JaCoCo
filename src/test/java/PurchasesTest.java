import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


class PurchasesTest {
    private Purchases purchase;
    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        date = LocalDateTime.of(2025, 1, 15, 10, 30);
        purchase = new Purchases(1, 101, 500, date);
    }

    @Test
    void testPurchaseCreation() {
        assertEquals(1, purchase.getIdCompra());
        assertEquals(101, purchase.getIdCliente());
        assertEquals(500, purchase.getMonto());
        assertEquals(date, purchase.getDate());
    }

    @Test
    void testToString() {
        String ts = purchase.toString();
        assertTrue(ts.contains("ID: 1"));
        assertTrue(ts.contains("Client ID: 101"));
        assertTrue(ts.contains("Amount: 500"));
        assertTrue(ts.contains("Date: 2025-01-15T10:30"));
    }
}