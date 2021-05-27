package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private final Game game; // Don't do this: in reality you would have a GameService

    @Autowired
    public BlackjackController(Game game) {
        this.game = game;
    }

    @PostMapping("/start-game")
    public String startGame() {
        // gameService.newGame() -> Game ID
        game.initialDeal();
        return redirectBasedOnGameState();
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        // gameService.viewOf(Game ID)
        populateViewModel(model);
        return "blackjack";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        // gameService.playerHits(Game ID)
        game.playerHits();
        return redirectBasedOnGameState();
    }

    @GetMapping("/done")
    public String viewDone(Model model) {
        populateViewModel(model);
        String outcome = game.determineOutcome().toString();
        model.addAttribute("outcome", outcome);
        return "done";
    }

    @PostMapping("/stand")
    public String standCommand() {
        // gameService.playerStands(gameId, playerId)
        game.playerStands();
        return redirectBasedOnGameState();
    }

    private String redirectBasedOnGameState() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

    private void populateViewModel(Model model) {
        GameView gameView = GameView.of(game);
        model.addAttribute("gameView", gameView);
    }
}
