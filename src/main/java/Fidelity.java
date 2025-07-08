public class Fidelity {

    public enum Lvl{
        BRONCE(0, 1),
        PLATA(500, 1.2),
        ORO(1500, 1.5),
        PLATINO(3000,2.0);

        private final int theshold;
        private final double multiplier;

        Lvl(int theshold, double multiplier) {
            this.theshold = theshold;
            this.multiplier = multiplier;
        }

        public int getTheshold() {
            return theshold;
        }

        public double getMultiplier() {
            return multiplier;
        }
    }

    private int puntos;
    private Lvl lvl;

    Fidelity(int puntos) {
        this.puntos = puntos;
        this.lvl = Lvl.BRONCE; 
        updateLevel();
    }

    public void updateLevel() {
        if (puntos >= Lvl.PLATINO.getTheshold()) {
            lvl = Lvl.PLATINO;
        } else if (puntos >= Lvl.ORO.getTheshold()) {
            lvl = Lvl.ORO;
        } else if (puntos >= Lvl.PLATA.getTheshold()) {
            lvl = Lvl.PLATA;
        } else {
            lvl = Lvl.BRONCE;
        }
    }

    public void addPuntos(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("Puntos cannot be negative");
        }
        this.puntos += puntos;
        updateLevel();
    }
    public int getPuntos() {
        return puntos;
    }
    public Lvl getLvl() {
        return lvl;
    }
    public double getMultiplier() {
        return lvl.getMultiplier();
    }
    public int getThreshold() {
        return lvl.getTheshold();
    }


}
