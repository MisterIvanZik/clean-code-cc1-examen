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
        return cards.stream()
                .map(Card::value)
                .mapToInt(Value::getPoints)
                .max()
                .orElse(0);
    }
}