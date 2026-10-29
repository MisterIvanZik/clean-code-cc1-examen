package fr.esgi.cleancode;

import java.util.List;

public class PokerGame {

    public int calculateTotalScore(List<Hand> hands) {
        if (hands == null || hands.isEmpty()) {
            return 0;
        }

        return hands.stream()
                .mapToInt(Hand::score)
                .sum();
    }
}