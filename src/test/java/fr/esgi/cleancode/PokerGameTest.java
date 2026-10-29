package fr.esgi.cleancode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerGameTest {

    @Test
    void should_return_148_for_the_example_hands_of_the_subject() {
        PokerGame game = new PokerGame();

        int totalScore = game.calculateTotalScore(List.of(
            new Hand(List.of(
                new Card(Value.TWO, Suit.HEART),
                new Card(Value.TWO, Suit.SPADE),
                new Card(Value.TWO, Suit.DIAMOND),
                new Card(Value.NINE, Suit.CLUB),
                new Card(Value.NINE, Suit.SPADE)
            )),
            new Hand(List.of(
                new Card(Value.FIVE, Suit.CLUB),
                new Card(Value.SIX, Suit.DIAMOND),
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.EIGHT, Suit.SPADE),
                new Card(Value.NINE, Suit.CLUB)
            )),
            new Hand(List.of(
                new Card(Value.TWO, Suit.HEART),
                new Card(Value.SEVEN, Suit.HEART),
                new Card(Value.NINE, Suit.HEART),
                new Card(Value.JACK, Suit.HEART),
                new Card(Value.KING, Suit.HEART)
            )),
            new Hand(List.of(
                new Card(Value.THREE, Suit.CLUB),
                new Card(Value.EIGHT, Suit.DIAMOND),
                new Card(Value.TEN, Suit.HEART),
                new Card(Value.QUEEN, Suit.SPADE),
                new Card(Value.KING, Suit.CLUB)
            ))
        ));

        assertEquals(148, totalScore);
    }
}
