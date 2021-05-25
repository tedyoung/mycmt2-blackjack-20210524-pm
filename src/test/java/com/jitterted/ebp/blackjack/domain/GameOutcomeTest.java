package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    public void playerBeatsDealerReportedAsOutcome() throws Exception {
        Game game = new Game(StubDeck.createPlayerStandsAndBeatsDealerDeck());
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualTo("You beat the Dealer! 💵");
    }

    @Test
    public void playerHitsAndGoesBustResultsInPlayerBusted() throws Exception {
        Game game = new Game(StubDeck.createPlayerHitsAndGoesBustDeck());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualTo("You Busted, so you lose.  💸");
    }

}