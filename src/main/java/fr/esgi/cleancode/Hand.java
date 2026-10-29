package fr.esgi.cleancode;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    private final List<Card> cards;

    public Hand(List<Card> cards) {
        if (cards == null || cards.size() != 5) {
            throw new IllegalArgumentException("Une main doit comporter exactement 5 cartes.");
        }
        this.cards = List.copyOf(cards);
    }

    public Combination identifyCombination() {
        if (isQuinteFlush()) return Combination.QUINTE_FLUSH;
        if (isCarre()) return Combination.CARRE;
        if (isFull()) return Combination.FULL;
        if (isCouleur()) return Combination.COULEUR;
        if (isSuite()) return Combination.SUITE;
        if (isBrelan()) return Combination.BRELAN;
        return Combination.CARTE_HAUTE;
    }

    public int score() {
        Combination combination = identifyCombination();
        if (combination == Combination.CARTE_HAUTE) {
            return highestCardValue();
        }
        return combination.getPoints();
    }

    public int highestCardValue() {
        return cards.stream()
                .map(Card::value)
                .mapToInt(Value::getPoints)
                .max()
                .orElse(0);
    }

    private boolean isCouleur() {
        return cards.stream()
                .map(Card::suit)
                .distinct()
                .count() == 1;
    }

    private boolean isSuite() {
        List<Integer> values = cards.stream()
                .map(Card::value)
                .map(Value::getPoints)
                .distinct()
                .sorted()
                .toList();

        if (values.size() != 5) {
            return false;
        }
        return values.get(4) - values.get(0) == 4;
    }

    private Map<Value, Long> valueCounts() {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::value, Collectors.counting()));
    }

    private boolean isBrelan() {
        return valueCounts().containsValue(3L);
    }

    private boolean isCarre() {
        return valueCounts().containsValue(4L);
    }

    private boolean isFull() {
        Collection<Long> counts = valueCounts().values();
        return counts.contains(3L) && counts.contains(2L);
    }

    private boolean isQuinteFlush() {
        return isSuite() && isCouleur();
    }
}