import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FidelityInventory {

    private ClientsInventory clientsInventory;
    private PurchasesInventory purchasesInventory;

    FidelityInventory(ClientsInventory clientsInventory, PurchasesInventory purchasesInventory) {
        this.clientsInventory = clientsInventory;
        this.purchasesInventory = purchasesInventory;
    }

    public void processPurchase(int clientId, double amount, LocalDateTime date) {
        Clients client = clientsInventory.getClientById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        purchasesInventory.addPurchase(clientId, (int) amount, date);
        int basePoints = (int) (amount / 100);
        double multiplier = client.getFidelity().getMultiplier();
        int totalPoints = (int) (basePoints * multiplier);

        int bonusPoints = calculateBonus(clientId, date);
        totalPoints += bonusPoints;
        client.addPuntos(totalPoints);

        updateStreak(clientId, date);

        System.out.printf("Compra procesada: Puntos base=%d, Multiplicador=%.1fx, Bonus=%d, Total=%d%n",
                basePoints, multiplier, bonusPoints, totalPoints);
    }

    private int calculateBonus(int clientId, LocalDateTime date) {
        LocalDate today = date.toLocalDate();
        
        List<Purchases> todayPurchases = PurchasesInventory.getPurchasesByClientId(clientId)
                .stream()
                .filter(p -> p.getDate().toLocalDate().equals(today))
                .collect(Collectors.toList());

        if (todayPurchases.size() == 3) {
        }

        return 0;
    }

    public void updateStreak(int clientId, LocalDateTime date) {
        Clients client = clientsInventory.getClientById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client not found");
        }

        List<Purchases> clientPurchases = PurchasesInventory.getPurchasesByClientId(clientId);
        
        if (clientPurchases.isEmpty()) {
            client.setStreakDias(0);
            return;
        }

        int streak = calculateStreakFromPurchases(clientPurchases, date);
        client.setStreakDias(streak);

        System.out.println("Streak updated for client " + client.getName() + ": " + streak + " days.");
    }

    public void updateClientStreak(int clientId, LocalDateTime lastPurchaseDate) {
        updateStreak(clientId, lastPurchaseDate);
    }

    private int calculateStreakFromPurchases(List<Purchases> purchases, LocalDateTime currentPurchaseDate) {
        if (purchases.isEmpty()) return 1; 

        List<LocalDate> uniqueDates = purchases.stream()
                .map(p -> p.getDate().toLocalDate())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        LocalDate currentDate = currentPurchaseDate.toLocalDate();
        
        if (!uniqueDates.contains(currentDate)) {
            uniqueDates.add(currentDate);
            uniqueDates.sort(LocalDate::compareTo);
        }

        if (uniqueDates.size() <= 1) return uniqueDates.size();

        int streak = 1;
        LocalDate lastDate = uniqueDates.get(uniqueDates.size() - 1);
        
        for (int i = uniqueDates.size() - 2; i >= 0; i--) {
            LocalDate currentDateInList = uniqueDates.get(i);
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(currentDateInList, lastDate);
            if (daysBetween < 3) {
                streak++;
                lastDate = currentDateInList;
            } else { break; }
        }

        return streak;
    }

}




