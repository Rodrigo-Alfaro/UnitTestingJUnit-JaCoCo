import java.time.LocalDateTime;

public class Purchases {

    private int idCompra;
    private int idCliente;
    protected int monto;
    protected LocalDateTime fecha;
    
    Purchases(int idCompra, int idCliente, int monto, LocalDateTime fecha) {
        this.idCompra = idCompra;
        this.idCliente = idCliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getIdCompra() {
        return idCompra;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public int getMonto() {
        return monto;
    }
    public LocalDateTime getDate() {
        return fecha;
    }
    public void setMonto(int monto) {
        this.monto = monto;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Client ID: %d, Amount: %d, Date: %s", 
                            idCompra, idCliente, monto, fecha);
    }
}
