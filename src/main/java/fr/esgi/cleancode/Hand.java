package fr.esgi.cleancode;

import java.util.List;

public class Hand {
    private final List<Card> cards;

    public Hand(List<Card> cards) {
        if (cards == null || cards.size() != 5) {
            throw new IllegalArgumentException("Une main doit comporter 5 cartes");
        }
        this.cards = List.copyOf(cards);
    }

    public int score() {
        int best = 0;
        if (isQuinteFlush()) best = Math.max(best, 80);
        if (isCarre()) best = Math.max(best, 60);
        if (isFull()) best = Math.max(best, 50);
        if (isCouleur()) best = Math.max(best, 45);
        if (isSuite()) best = Math.max(best, 40);
        if (isBrelan()) best = Math.max(best, 30);
        if (best == 0) {
            best = cards.stream().map(Card::value).mapToInt(Value::getPoints).max().orElse(0);
        }
        return best;
    }

    private boolean isCouleur() {
        return cards.stream().map(Card::suit).distinct().count() == 1;
    }

    private boolean isSuite() {
        java.util.List<Integer> values = cards.stream().map(Card::value).map(Value::getPoints).distinct().sorted().toList();
        if (values.size() != 5) return false;
        return values.get(4) - values.get(0) == 4;
    }

    private java.util.Map<Value, Long> valueCounts() {
        return cards.stream().collect(java.util.stream.Collectors.groupingBy(Card::value, java.util.stream.Collectors.counting()));
    }

    private boolean isBrelan() {
        return valueCounts().values().stream().anyMatch(c -> c == 3);
    }

    private boolean isCarre() {
        return valueCounts().values().stream().anyMatch(c -> c == 4);
    }

    private boolean isFull() {
        java.util.Collection<Long> counts = valueCounts().values();
        return counts.contains(3L) && counts.contains(2L);
    }

    private boolean isQuinteFlush() {
        return isSuite() && isCouleur();
    }
}