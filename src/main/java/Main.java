import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static ClientsInventory clientsInventory;
    private static PurchasesInventory purchasesInventory;
    private static FidelityInventory fidelityInventory;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        clientsInventory = new ClientsInventory();
        purchasesInventory = new PurchasesInventory();
        fidelityInventory = new FidelityInventory(clientsInventory, purchasesInventory);
        
        showMainMenu();
        scanner.close();
    }

    public static void showMainMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n=== SISTEMA DE FIDELIDAD ===");
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Gestión de Compras");
            System.out.println("3. Mostrar Puntos / Nivel de un Cliente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    case 1:
                        showClientMenu();
                        break;
                    case 2:
                        showPurchaseMenu();
                        break;
                    case 3:
                        showClientPointsAndLevel();
                        break;
                    case 4:
                        running = false;
                        System.out.println("¡Gracias por usar el sistema!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static void showClientMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== GESTIÓN DE CLIENTES ===");
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Actualizar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    case 1:
                        addClient();
                        break;
                    case 2:
                        listClients();
                        break;
                    case 3:
                        updateClient();
                        break;
                    case 4:
                        deleteClient();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static void addClient() {
        try {
            System.out.print("Ingrese el nombre: ");
            String name = scanner.nextLine();
            
            System.out.print("Ingrese el correo: ");
            String email = scanner.nextLine();
            
            Clients client = clientsInventory.addClient(name, email);
            System.out.printf("Cliente creado exitosamente: %s%n", client);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listClients() {
        List<Clients> clients = clientsInventory.getClients();
        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("\n=== LISTA DE CLIENTES ===");
            clients.forEach(System.out::println);
        }
    }

    private static void updateClient() {
        try {
            System.out.print("Ingrese el ID del cliente a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            if (!clientsInventory.clientExists(id)) {
                System.out.println("Cliente no encontrado.");
                return;
            }
            
            System.out.print("Ingrese el nuevo nombre: ");
            String name = scanner.nextLine();
            
            System.out.print("Ingrese el nuevo correo: ");
            String email = scanner.nextLine();
            
            if (clientsInventory.updateClient(id, name, email)) {
                System.out.println("Cliente actualizado exitosamente.");
            } else {
                System.out.println("Error al actualizar el cliente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteClient() {
        try {
            System.out.print("Ingrese el ID del cliente a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            if (clientsInventory.deleteClient(id)) {
                System.out.println("Cliente eliminado exitosamente.");
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void showPurchaseMenu() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== GESTIÓN DE COMPRAS ===");
            System.out.println("1. Registrar Compra");
            System.out.println("2. Listar Compras");
            System.out.println("3. Actualizar Compra");
            System.out.println("4. Eliminar Compra");
            System.out.println("5. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            try {
                int option = Integer.parseInt(scanner.nextLine());
                
                switch (option) {
                    case 1:
                        registerPurchase();
                        break;
                    case 2:
                        listPurchases();
                        break;
                    case 3:
                        updatePurchase();
                        break;
                    case 4:
                        deletePurchase();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static void registerPurchase() {
        try {
            System.out.print("Ingrese el ID del cliente: ");
            int clientId = Integer.parseInt(scanner.nextLine());
            
            if (!clientsInventory.clientExists(clientId)) {
                System.out.println("Cliente no encontrado.");
                return;
            }
            
            System.out.print("Ingrese el monto de la compra: ");
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("El monto debe ser mayor a cero.");
                return;
            }
            
            LocalDateTime fecha = LocalDateTime.now();
            fidelityInventory.processPurchase(clientId, amount, fecha);
            fidelityInventory.updateStreak(clientId, fecha);
            
            System.out.println("Compra registrada exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Datos inválidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listPurchases() {
        List<Purchases> purchases = purchasesInventory.getPurchases();
        if (purchases.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            System.out.println("\n=== LISTA DE COMPRAS ===");
            purchases.forEach(System.out::println);
        }
    }

    private static void updatePurchase() {
        try {
            System.out.print("Ingrese el ID de la compra a actualizar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            if (!purchasesInventory.purchaseExists(id)) {
                System.out.println("Compra no encontrada.");
                return;
            }
            
            System.out.print("Ingrese el nuevo monto: ");
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("El monto debe ser mayor a cero.");
                return;
            }
            
            Purchases currentPurchase = purchasesInventory.getPurchaseById(id);
            LocalDateTime fecha = LocalDateTime.now();
            
            if (purchasesInventory.updatePurchase(id, currentPurchase.getIdCliente(), (int)amount, fecha)) {
                System.out.println("Compra actualizada exitosamente.");
            } else {
                System.out.println("Error al actualizar la compra.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Datos inválidos.");
        }
    }

    private static void deletePurchase() {
        try {
            System.out.print("Ingrese el ID de la compra a eliminar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            if (purchasesInventory.deletePurchase(id)) {
                System.out.println("Compra eliminada exitosamente.");
            } else {
                System.out.println("Compra no encontrada.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void showClientPointsAndLevel() {
        try {
            System.out.print("Ingrese el ID del cliente: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            Clients client = clientsInventory.getClientById(id);
            if (client != null) {
                System.out.printf("\n=== INFORMACIÓN DEL CLIENTE ===%n");
                System.out.printf("Nombre: %s%n", client.getName());
                System.out.printf("Correo: %s%n", client.getCorreo());
                System.out.printf("Puntos actuales: %d%n", client.getPuntos());
                System.out.printf("Nivel actual: %s%n", client.getLevel());
                System.out.printf("Multiplicador: %.1fx%n", client.getFidelity().getMultiplier());
                System.out.printf("Días consecutivos: %d%n", client.getStreakDias());
                
                // Mostrar información del siguiente nivel
                showNextLevelInfo(client);
            } else {
                System.out.println("Cliente no encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void showNextLevelInfo(Clients client) {
        Fidelity.Lvl currentLevel = client.getLevel();
        Fidelity.Lvl nextLevel = getNextLevel(currentLevel);
        
        if (nextLevel != null) {
            int pointsNeeded = nextLevel.getTheshold() - client.getPuntos();
            System.out.printf("Próximo nivel: %s (necesita %d puntos más)%n", 
                    nextLevel.name(), pointsNeeded);
        } else {
            System.out.println("¡Ha alcanzado el nivel máximo!");
        }
    }

    private static Fidelity.Lvl getNextLevel(Fidelity.Lvl currentLevel) {
        switch (currentLevel) {
            case BRONCE:
                return Fidelity.Lvl.PLATA;
            case PLATA:
                return Fidelity.Lvl.ORO;
            case ORO:
                return Fidelity.Lvl.PLATINO;
            default:
                return null;
        }
    }
}
