import java.util.*;


public class ClientsInventory {
    private Map<Integer, Clients> clientsMap;
    private int next;

    ClientsInventory() {
        this.clientsMap = new HashMap<>();
        this.next = 1;
    }

    public Clients addClient(String name, String email) {
        Clients client = new Clients(next++, name, email);
        clientsMap.put(client.getId(), client);
        return client;
    }

    public List<Clients> getClients() {
        return new ArrayList<>(clientsMap.values());
    }

    public Clients getClientById(int id) {
        return clientsMap.get(id);
    }

    public boolean updateClient(int id, String name, String email) {
        Clients client = clientsMap.get(id);
        if (client != null){
            client.setName(name);
            client.setCorreo(email);
            return true;
        }
        return false;
    }

    public boolean deleteClient(int id) {
        return clientsMap.remove(id) != null;
    }

    public boolean clientExists(int id) {
        return clientsMap.containsKey(id);
    }
}
