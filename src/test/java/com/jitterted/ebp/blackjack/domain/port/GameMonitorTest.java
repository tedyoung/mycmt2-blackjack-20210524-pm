package com.jitterted.ebp.blackjack.domain.port;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameMonitorTest {

    @Test
    public void playerStandsCompletesGameSendsToMonitor() throws Exception {
        // creates the spy based on the interface
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(new Deck(), gameMonitorSpy);

        game.playerStands();

        // verify that the roundCompleted method was called with any instance of a Game class
        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsAndBustsSendsResultToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.createPlayerHitsAndGoesBustDeck(),
                             gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsDoesNotBustIsNoResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.createPlayerHitsDoesNotBust(),
                             gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }

    @Test
    public void playerDealtBlackjackResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.createPlayerDealtBlackjack(),
                             gameMonitorSpy);

        game.initialDeal();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }
}