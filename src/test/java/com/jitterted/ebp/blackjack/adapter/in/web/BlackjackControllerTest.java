package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Card;
import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import com.jitterted.ebp.blackjack.domain.Suit;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BlackjackControllerTest {

    @Test
    public void startGameResultsInCardsDealtToPlayer() throws Exception {
        Game game = new Game(new Deck());
        BlackjackController blackjackController = new BlackjackController(game);

        String redirectPage = blackjackController.startGame();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");

        assertThat(game.playerHand().cards())
                .hasSize(2);
    }

    @Test
    public void gameViewPopulatesViewModelWithCards() throws Exception {
        Deck stubDeck = new StubDeck(List.of(new Card(Suit.DIAMONDS, Rank.TEN),
                                             new Card(Suit.HEARTS, Rank.TWO),
                                             new Card(Suit.DIAMONDS, Rank.KING),
                                             new Card(Suit.CLUBS, Rank.THREE)));
        Game game = new Game(stubDeck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.gameView(model);

        GameView gameView = (GameView) model.getAttribute("gameView");

        assertThat(gameView.getDealerCards())
                .containsExactly("2♥", "3♣");

        assertThat(gameView.getPlayerCards())
                .containsExactly("10♦", "K♦");
    }

    @Test
    public void hitCommandDealsThirdCardToPlayer() throws Exception {
        Deck playerHitsDoesNotBust = StubDeck.createPlayerHitsDoesNotBust();
        Game game = new Game(playerHitsDoesNotBust);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/game");

        assertThat(game.playerHand().cards())
                .hasSize(3);
    }

    @Test
    public void playerHitsGoesBustResultsInRedirectToDonePage() throws Exception {
        Deck playerHitsAndGoesBustDeck = StubDeck.createPlayerHitsAndGoesBustDeck();
        Game game = new Game(playerHitsAndGoesBustDeck);
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        String redirectPage = blackjackController.hitCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");

        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    public void donePageShowsFinalGameStateWithOutcome() throws Exception {
        Game game = new Game(new Deck());
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        Model model = new ConcurrentModel();
        blackjackController.viewDone(model);

        assertThat(model.containsAttribute("gameView"))
                .isTrue();

        String outcome = (String) model.getAttribute("outcome");

        assertThat(outcome)
                .isNotBlank();
    }

    @Test
    public void standCommandResultsInGamePlayerIsDoneAndRedirectsToDone() throws Exception {
        Game game = new Game(new Deck());
        BlackjackController blackjackController = new BlackjackController(game);
        blackjackController.startGame();

        String redirectPage = blackjackController.standCommand();

        assertThat(redirectPage)
                .isEqualTo("redirect:/done");

        assertThat(game.isPlayerDone())
                .isTrue();
    }

}