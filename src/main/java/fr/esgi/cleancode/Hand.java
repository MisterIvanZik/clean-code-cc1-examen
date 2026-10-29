package fr.esgi.cleancode;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {

    private static final int HAND_SIZE = 5;
    private static final long PAIRE_SIZE = 2;
    private static final long BRELAN_SIZE = 3;
    private static final long CARRE_SIZE = 4;

    private final List<Card> cards;

    public Hand(List<Card> cards) {
        if (cards == null || cards.size() != HAND_SIZE) {
            throw new IllegalArgumentException(
                    "Une main doit comporter exactement " + HAND_SIZE + " cartes");
        }
        this.cards = List.copyOf(cards);
    }

    public Combination identifyCombination() {
        if (isQuinteFlush()) {
            return Combination.QUINTE_FLUSH;
        }
        if (isCarre()) {
            return Combination.CARRE;
        }
        if (isFull()) {
            return Combination.FULL;
        }
        if (isCouleur()) {
            return Combination.COULEUR;
        }
        if (isSuite()) {
            return Combination.SUITE;
        }
        if (isBrelan()) {
            return Combination.BRELAN;
        }
        return Combination.CARTE_HAUTE;
    }

    public int score() {
        Combination combination = identifyCombination();
        if (combination == Combination.CARTE_HAUTE) {
            return highestCardValue();
        }
        return combination.getPoints();
    }

    private int highestCardValue() {
        return cards.stream()
                .map(Card::value)
                .mapToInt(Value::getPoints)
                .max()
                .orElseThrow();
    }

    private boolean isQuinteFlush() {
        return isSuite() && isCouleur();
    }

    private boolean isCarre() {
        return hasGroupOfSize(CARRE_SIZE);
    }

    private boolean isFull() {
        return hasGroupOfSize(BRELAN_SIZE) && hasGroupOfSize(PAIRE_SIZE);
    }

    private boolean isCouleur() {
        return cards.stream()
                .map(Card::suit)
                .distinct()
                .count() == 1;
    }

    private boolean isSuite() {
        List<Integer> values = sortedDistinctValues();
        if (values.size() != HAND_SIZE) {
            return false;
        }
        int lowest = values.get(0);
        int highest = values.get(HAND_SIZE - 1);
        return highest - lowest == HAND_SIZE - 1;
    }

    private boolean isBrelan() {
        return hasGroupOfSize(BRELAN_SIZE);
    }

    private boolean hasGroupOfSize(long size) {
        return countByValue().containsValue(size);
    }

    private Map<Value, Long> countByValue() {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::value, Collectors.counting()));
    }

    private List<Integer> sortedDistinctValues() {
        return cards.stream()
                .map(Card::value)
                .map(Value::getPoints)
                .distinct()
                .sorted()
                .toList();
    }
}