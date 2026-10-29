package fr.esgi.cleancode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {

    private static Hand handOf(Object... valuesAndSuits) {
        return new Hand(List.of());
    }

    @Test
    void should_return_highest_card_value_when_no_combination() {
        Hand hand = new Hand(List.of(
            new Card(Value.THREE, Suit.CLUB),
            new Card(Value.EIGHT, Suit.DIAMOND),
            new Card(Value.TEN, Suit.HEART),
            new Card(Value.QUEEN, Suit.SPADE),
            new Card(Value.KING, Suit.CLUB)
        ));

        assertEquals(13, hand.score());
    }

    @Test
    void should_return_30_for_brelan() {
        Hand hand = new Hand(List.of(
            new Card(Value.TWO, Suit.HEART),
            new Card(Value.TWO, Suit.SPADE),
            new Card(Value.TWO, Suit.DIAMOND),
            new Card(Value.NINE, Suit.CLUB),
            new Card(Value.KING, Suit.SPADE)
        ));

        assertEquals(30, hand.score());
    }

    @Test
    void should_return_40_for_suite() {
        Hand hand = new Hand(List.of(
            new Card(Value.FIVE, Suit.CLUB),
            new Card(Value.SIX, Suit.DIAMOND),
            new Card(Value.SEVEN, Suit.HEART),
            new Card(Value.EIGHT, Suit.SPADE),
            new Card(Value.NINE, Suit.CLUB)
        ));

        assertEquals(40, hand.score());
    }

    @Test
    void should_return_45_for_couleur() {
        Hand hand = new Hand(List.of(
            new Card(Value.TWO, Suit.HEART),
            new Card(Value.SEVEN, Suit.HEART),
            new Card(Value.NINE, Suit.HEART),
            new Card(Value.JACK, Suit.HEART),
            new Card(Value.KING, Suit.HEART)
        ));

        assertEquals(45, hand.score());
    }

    @Test
    void should_return_50_for_full() {
        Hand hand = new Hand(List.of(
            new Card(Value.TWO, Suit.HEART),
            new Card(Value.TWO, Suit.SPADE),
            new Card(Value.TWO, Suit.DIAMOND),
            new Card(Value.NINE, Suit.CLUB),
            new Card(Value.NINE, Suit.SPADE)
        ));

        assertEquals(50, hand.score());
    }

    @Test
    void should_return_60_for_carre() {
        Hand hand = new Hand(List.of(
            new Card(Value.TWO, Suit.HEART),
            new Card(Value.TWO, Suit.SPADE),
            new Card(Value.TWO, Suit.DIAMOND),
            new Card(Value.TWO, Suit.CLUB),
            new Card(Value.NINE, Suit.SPADE)
        ));

        assertEquals(60, hand.score());
    }

    @Test
    void should_return_80_for_quinte_flush() {
        Hand hand = new Hand(List.of(
            new Card(Value.FIVE, Suit.CLUB),
            new Card(Value.SIX, Suit.CLUB),
            new Card(Value.SEVEN, Suit.CLUB),
            new Card(Value.EIGHT, Suit.CLUB),
            new Card(Value.NINE, Suit.CLUB)
        ));

        assertEquals(80, hand.score());
    }
}
