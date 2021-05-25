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

        assertThat(game.isPlayerDone())
                .isTrue();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    public void playerHitsAndGoesBustResultsInPlayerBusted() throws Exception {
        Game game = new Game(StubDeck.createPlayerHitsAndGoesBustDeck());
        game.initialDeal();

        game.playerHits();

        assertThat(game.isPlayerDone())
                .isTrue();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    public void playerDealtAceAndTenCardWinsBlackjackGameIsOver() throws Exception {
        Deck playerDealtBlackjack = new StubDeck(Rank.QUEEN, Rank.EIGHT,
                                                 Rank.ACE, Rank.JACK);
        Game game = new Game(playerDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isTrue();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
    }

}