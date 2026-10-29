package fr.esgi.cleancode;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandTest {

    private static Card card(Value value, Suit suit) {
        return new Card(value, suit);
    }

    private static Hand handOf(Card... cards) {
        return new Hand(List.of(cards));
    }

    @Nested
    class HighCard {

        @Test
        void should_return_value_of_highest_card_when_no_combination() {
            Hand hand = handOf(
                card(Value.THREE, Suit.CLUB),
                card(Value.EIGHT, Suit.DIAMOND),
                card(Value.TEN, Suit.HEART),
                card(Value.QUEEN, Suit.SPADE),
                card(Value.KING, Suit.CLUB)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(13, hand.score());
        }

        @Test
        void should_return_14_when_highest_card_is_ace() {
            Hand hand = handOf(
                card(Value.THREE, Suit.CLUB),
                card(Value.SEVEN, Suit.DIAMOND),
                card(Value.NINE, Suit.HEART),
                card(Value.JACK, Suit.SPADE),
                card(Value.ACE, Suit.CLUB)
            );

            assertEquals(14, hand.score());
        }

        @Test
        void should_return_7_when_highest_card_is_seven() {
            Hand hand = handOf(
                card(Value.TWO, Suit.CLUB),
                card(Value.THREE, Suit.DIAMOND),
                card(Value.FOUR, Suit.HEART),
                card(Value.FIVE, Suit.SPADE),
                card(Value.SEVEN, Suit.CLUB)
            );

            assertEquals(7, hand.score());
        }

        @Test
        void should_score_highest_card_when_only_one_pair() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.FIVE, Suit.DIAMOND),
                card(Value.EIGHT, Suit.CLUB),
                card(Value.KING, Suit.SPADE)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(13, hand.score());
        }

        @Test
        void should_score_highest_card_when_two_pairs() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.FIVE, Suit.DIAMOND),
                card(Value.FIVE, Suit.CLUB),
                card(Value.KING, Suit.SPADE)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(13, hand.score());
        }

        @Test
        void should_score_highest_card_when_four_cards_share_suit() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.FIVE, Suit.HEART),
                card(Value.NINE, Suit.HEART),
                card(Value.JACK, Suit.HEART),
                card(Value.KING, Suit.SPADE)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(13, hand.score());
        }
    }

    @Nested
    class SimpleCombinations {

        @Test
        void should_return_30_for_brelan() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.TWO, Suit.DIAMOND),
                card(Value.NINE, Suit.CLUB),
                card(Value.KING, Suit.SPADE)
            );

            assertEquals(Combination.BRELAN, hand.identifyCombination());
            assertEquals(30, hand.score());
        }

        @Test
        void should_return_40_for_suite() {
            Hand hand = handOf(
                card(Value.FIVE, Suit.CLUB),
                card(Value.SIX, Suit.DIAMOND),
                card(Value.SEVEN, Suit.HEART),
                card(Value.EIGHT, Suit.SPADE),
                card(Value.NINE, Suit.CLUB)
            );

            assertEquals(Combination.SUITE, hand.identifyCombination());
            assertEquals(40, hand.score());
        }

        @Test
        void should_return_45_for_couleur() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.SEVEN, Suit.HEART),
                card(Value.NINE, Suit.HEART),
                card(Value.JACK, Suit.HEART),
                card(Value.KING, Suit.HEART)
            );

            assertEquals(Combination.COULEUR, hand.identifyCombination());
            assertEquals(45, hand.score());
        }

        @Test
        void should_return_50_for_full() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.TWO, Suit.DIAMOND),
                card(Value.NINE, Suit.CLUB),
                card(Value.NINE, Suit.SPADE)
            );

            assertEquals(Combination.FULL, hand.identifyCombination());
            assertEquals(50, hand.score());
        }

        @Test
        void should_return_60_for_carre() {
            Hand hand = handOf(
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.TWO, Suit.DIAMOND),
                card(Value.TWO, Suit.CLUB),
                card(Value.NINE, Suit.SPADE)
            );

            assertEquals(Combination.CARRE, hand.identifyCombination());
            assertEquals(60, hand.score());
        }

        @Test
        void should_return_80_for_quinte_flush() {
            Hand hand = handOf(
                card(Value.FIVE, Suit.CLUB),
                card(Value.SIX, Suit.CLUB),
                card(Value.SEVEN, Suit.CLUB),
                card(Value.EIGHT, Suit.CLUB),
                card(Value.NINE, Suit.CLUB)
            );

            assertEquals(Combination.QUINTE_FLUSH, hand.identifyCombination());
            assertEquals(80, hand.score());
        }
    }

    @Nested
    class SuiteEdgeCases {

        @Test
        void should_recognize_suite_when_ace_is_high() {
            Hand hand = handOf(
                card(Value.TEN, Suit.CLUB),
                card(Value.JACK, Suit.DIAMOND),
                card(Value.QUEEN, Suit.HEART),
                card(Value.KING, Suit.SPADE),
                card(Value.ACE, Suit.CLUB)
            );

            assertEquals(Combination.SUITE, hand.identifyCombination());
            assertEquals(40, hand.score());
        }

        @Test
        void should_not_recognize_suite_when_ace_is_low() {
            Hand hand = handOf(
                card(Value.ACE, Suit.CLUB),
                card(Value.TWO, Suit.DIAMOND),
                card(Value.THREE, Suit.HEART),
                card(Value.FOUR, Suit.SPADE),
                card(Value.FIVE, Suit.CLUB)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(14, hand.score());
        }

        @Test
        void should_not_recognize_suite_when_sequence_wraps_around_ace() {
            Hand hand = handOf(
                card(Value.JACK, Suit.CLUB),
                card(Value.QUEEN, Suit.DIAMOND),
                card(Value.KING, Suit.HEART),
                card(Value.ACE, Suit.SPADE),
                card(Value.TWO, Suit.CLUB)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(14, hand.score());
        }

        @Test
        void should_recognize_suite_when_cards_are_not_given_in_order() {
            Hand hand = handOf(
                card(Value.NINE, Suit.CLUB),
                card(Value.FIVE, Suit.DIAMOND),
                card(Value.SEVEN, Suit.HEART),
                card(Value.SIX, Suit.SPADE),
                card(Value.EIGHT, Suit.CLUB)
            );

            assertEquals(Combination.SUITE, hand.identifyCombination());
        }

        @Test
        void should_not_recognize_suite_when_one_value_is_missing() {
            Hand hand = handOf(
                card(Value.FIVE, Suit.CLUB),
                card(Value.SIX, Suit.DIAMOND),
                card(Value.SEVEN, Suit.HEART),
                card(Value.EIGHT, Suit.SPADE),
                card(Value.TEN, Suit.CLUB)
            );

            assertEquals(Combination.CARTE_HAUTE, hand.identifyCombination());
            assertEquals(10, hand.score());
        }
    }

    @Nested
    class QuinteFlushEdgeCases {

        @Test
        void should_return_80_for_royal_quinte_flush() {
            Hand hand = handOf(
                card(Value.TEN, Suit.SPADE),
                card(Value.JACK, Suit.SPADE),
                card(Value.QUEEN, Suit.SPADE),
                card(Value.KING, Suit.SPADE),
                card(Value.ACE, Suit.SPADE)
            );

            assertEquals(Combination.QUINTE_FLUSH, hand.identifyCombination());
            assertEquals(80, hand.score());
        }

        @Test
        void should_only_be_couleur_when_ace_low_sequence_has_same_suit() {
            Hand hand = handOf(
                card(Value.ACE, Suit.CLUB),
                card(Value.TWO, Suit.CLUB),
                card(Value.THREE, Suit.CLUB),
                card(Value.FOUR, Suit.CLUB),
                card(Value.FIVE, Suit.CLUB)
            );

            assertEquals(Combination.COULEUR, hand.identifyCombination());
            assertEquals(45, hand.score());
        }
    }

    @Nested
    class CombinationPriority {

        @Test
        void should_prefer_full_over_brelan() {
            Hand hand = handOf(
                card(Value.THREE, Suit.HEART),
                card(Value.THREE, Suit.SPADE),
                card(Value.THREE, Suit.DIAMOND),
                card(Value.SEVEN, Suit.CLUB),
                card(Value.SEVEN, Suit.SPADE)
            );

            assertEquals(Combination.FULL, hand.identifyCombination());
            assertEquals(50, hand.score());
        }

        @Test
        void should_recognize_full_when_pair_comes_before_brelan() {
            Hand hand = handOf(
                card(Value.NINE, Suit.CLUB),
                card(Value.NINE, Suit.SPADE),
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.TWO, Suit.DIAMOND)
            );

            assertEquals(Combination.FULL, hand.identifyCombination());
        }

        @Test
        void should_prefer_carre_over_brelan() {
            Hand hand = handOf(
                card(Value.NINE, Suit.SPADE),
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.TWO, Suit.DIAMOND),
                card(Value.TWO, Suit.CLUB)
            );

            assertEquals(Combination.CARRE, hand.identifyCombination());
            assertEquals(60, hand.score());
        }

        @Test
        void should_prefer_quinte_flush_over_suite_and_couleur() {
            Hand hand = handOf(
                card(Value.FIVE, Suit.HEART),
                card(Value.SIX, Suit.HEART),
                card(Value.SEVEN, Suit.HEART),
                card(Value.EIGHT, Suit.HEART),
                card(Value.NINE, Suit.HEART)
            );

            assertEquals(Combination.QUINTE_FLUSH, hand.identifyCombination());
            assertEquals(80, hand.score());
        }

        @Test
        void should_return_couleur_when_all_cards_share_suit_without_suite_and_with_ace() {
            Hand hand = handOf(
                card(Value.TWO, Suit.DIAMOND),
                card(Value.FIVE, Suit.DIAMOND),
                card(Value.NINE, Suit.DIAMOND),
                card(Value.QUEEN, Suit.DIAMOND),
                card(Value.ACE, Suit.DIAMOND)
            );

            assertEquals(Combination.COULEUR, hand.identifyCombination());
            assertEquals(45, hand.score());
        }
    }

    @Nested
    class Validation {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 4, 6, 7})
        void should_throw_exception_when_hand_does_not_contain_exactly_five_cards(int cardCount) {
            List<Card> cards = new ArrayList<>();
            Value[] values = Value.values();
            for (int i = 0; i < cardCount; i++) {
                cards.add(card(values[i], Suit.HEART));
            }

            assertThrows(IllegalArgumentException.class, () -> new Hand(cards));
        }

        @Test
        void should_throw_exception_when_cards_are_null() {
            assertThrows(IllegalArgumentException.class, () -> new Hand(null));
        }

        @Test
        void should_not_be_affected_by_later_changes_to_the_provided_list() {
            List<Card> cards = new ArrayList<>(List.of(
                card(Value.TWO, Suit.HEART),
                card(Value.TWO, Suit.SPADE),
                card(Value.TWO, Suit.DIAMOND),
                card(Value.NINE, Suit.CLUB),
                card(Value.NINE, Suit.SPADE)
            ));
            Hand hand = new Hand(cards);

            cards.clear();

            assertEquals(Combination.FULL, hand.identifyCombination());
        }
    }
}