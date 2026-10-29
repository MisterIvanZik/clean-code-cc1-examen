package fr.esgi.cleancode;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokerGameTest {

    private final PokerGame game = new PokerGame();

    private static Hand handOf(Card... cards) {
        return new Hand(List.of(cards));
    }

    private static Hand fullHand() {
        return handOf(
            new Card(Value.TWO, Suit.HEART),
            new Card(Value.TWO, Suit.SPADE),
            new Card(Value.TWO, Suit.DIAMOND),
            new Card(Value.NINE, Suit.CLUB),
            new Card(Value.NINE, Suit.SPADE)
        );
    }

    private static Hand suiteHand() {
        return handOf(
            new Card(Value.FIVE, Suit.CLUB),
            new Card(Value.SIX, Suit.DIAMOND),
            new Card(Value.SEVEN, Suit.HEART),
            new Card(Value.EIGHT, Suit.SPADE),
            new Card(Value.NINE, Suit.CLUB)
        );
    }

    private static Hand couleurHand() {
        return handOf(
            new Card(Value.TWO, Suit.HEART),
            new Card(Value.SEVEN, Suit.HEART),
            new Card(Value.NINE, Suit.HEART),
            new Card(Value.JACK, Suit.HEART),
            new Card(Value.KING, Suit.HEART)
        );
    }

    private static Hand highCardHand() {
        return handOf(
            new Card(Value.THREE, Suit.CLUB),
            new Card(Value.EIGHT, Suit.DIAMOND),
            new Card(Value.TEN, Suit.HEART),
            new Card(Value.QUEEN, Suit.SPADE),
            new Card(Value.KING, Suit.CLUB)
        );
    }

    private static Hand brelanHand() {
        return handOf(
            new Card(Value.FOUR, Suit.HEART),
            new Card(Value.FOUR, Suit.SPADE),
            new Card(Value.FOUR, Suit.DIAMOND),
            new Card(Value.SEVEN, Suit.CLUB),
            new Card(Value.KING, Suit.SPADE)
        );
    }

    private static Hand carreHand() {
        return handOf(
            new Card(Value.JACK, Suit.HEART),
            new Card(Value.JACK, Suit.SPADE),
            new Card(Value.JACK, Suit.DIAMOND),
            new Card(Value.JACK, Suit.CLUB),
            new Card(Value.THREE, Suit.SPADE)
        );
    }

    @Test
    void should_return_148_for_the_example_hands_of_the_subject() {
        int totalScore = game.calculateTotalScore(
            List.of(fullHand(), suiteHand(), couleurHand(), highCardHand())
        );

        assertEquals(148, totalScore);
    }

    @Test
    void should_return_0_when_there_is_no_hand() {
        assertEquals(0, game.calculateTotalScore(List.of()));
    }

    @Test
    void should_return_the_score_of_the_hand_when_there_is_a_single_hand() {
        assertEquals(50, game.calculateTotalScore(List.of(fullHand())));
    }

    @Test
    void should_add_the_score_of_each_hand() {
        int totalScore = game.calculateTotalScore(List.of(brelanHand(), carreHand()));

        assertEquals(90, totalScore);
    }

    @Test
    void should_return_the_same_total_whatever_the_order_of_the_hands() {
        int forwardTotal = game.calculateTotalScore(
            List.of(fullHand(), suiteHand(), couleurHand(), highCardHand())
        );
        int reversedTotal = game.calculateTotalScore(
            List.of(highCardHand(), couleurHand(), suiteHand(), fullHand())
        );

        assertEquals(forwardTotal, reversedTotal);
    }

    @Test
    void should_score_a_combination_each_time_it_appears() {
        int totalScore = game.calculateTotalScore(List.of(brelanHand(), brelanHand()));

        assertEquals(60, totalScore);
    }
}