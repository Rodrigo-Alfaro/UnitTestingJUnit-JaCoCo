import java.util.*;
import java.time.LocalDateTime;


public class PurchasesInventory {

    private static Map<Integer, Purchases> purchasesMap;
    private int next;

    PurchasesInventory() {
        this.purchasesMap = new HashMap<>();
        this.next = 1;
    }

    public Purchases addPurchase(int idCliente, int monto, LocalDateTime fecha) {
        Purchases purchase = new Purchases(next++, idCliente, monto, fecha);
        purchasesMap.put(purchase.getIdCompra(), purchase);
        return purchase;
    }

    public List<Purchases> getPurchases() {
        return new ArrayList<>(purchasesMap.values());
    }

    public Purchases getPurchaseById(int id) {
        return purchasesMap.get(id);
    }

    public static List<Purchases> getPurchasesByClientId(int idCliente) {
        List<Purchases> clientPurchases = new ArrayList<>();
        for (Purchases purchase : purchasesMap.values()) {
            if (purchase.getIdCliente() == idCliente) {
                clientPurchases.add(purchase);
            }
        }
        return clientPurchases;
    }
    public boolean updatePurchase(int idCompra, int idCliente, int monto, LocalDateTime fecha) {
        Purchases purchase = purchasesMap.get(idCompra);
        if (purchase != null) {
            purchase = new Purchases(idCompra, idCliente, monto, fecha);
            purchasesMap.put(idCompra, purchase);
            return true;
        }
        return false;
    }

    public boolean deletePurchase(int idCompra) {
        return purchasesMap.remove(idCompra) != null;
    }

    public boolean purchaseExists(int id) {
        return purchasesMap.containsKey(id);
    }
}
