package fr.esgi.cleancode;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HandTest {

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
}