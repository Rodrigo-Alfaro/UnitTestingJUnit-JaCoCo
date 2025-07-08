public class Clients {

    private int id;
    protected String name;
    protected String correo;
    protected Fidelity fidelity;
    protected int streakDias;

    Clients(int id, String name, String correo) {
        if (!isValidEmail(correo)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.id = id;
        this.name = name;
        this.correo = correo;
        this.fidelity = new Fidelity(0);
        this.streakDias = 0;
    }
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public void addPuntos(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("Puntos cannot be negative");
        }
        this.fidelity.addPuntos(puntos);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCorreo() {
        return correo;
    }

    public Fidelity getFidelity() {
        return fidelity;
    }

    public int getStreakDias() {
        return streakDias;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }
    public void setCorreo(String correo) {
        if (!isValidEmail(correo)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.correo = correo;
    }
    public void setStreakDias(int streakDias) {
        if (streakDias < 0) {
            throw new IllegalArgumentException("Streak days cannot be negative");
        }
        this.streakDias = streakDias;
    }

    public int getPuntos() {
        return fidelity.getPuntos();
    }

    public Fidelity.Lvl getLevel() {
        return fidelity.getLvl();
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Email: %s, Points: %d, Level: %s, Streak: %d days", 
                            id, name, correo, getPuntos(), getLevel(), streakDias);
    }
}
