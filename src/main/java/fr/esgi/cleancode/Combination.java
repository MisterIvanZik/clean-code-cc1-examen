package fr.esgi.cleancode;

public enum Combination {
    QUINTE_FLUSH(80),
    CARRE(60),
    FULL(50),
    COULEUR(45),
    SUITE(40),
    BRELAN(30),
    CARTE_HAUTE(0);

    private final int points;

    Combination(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}