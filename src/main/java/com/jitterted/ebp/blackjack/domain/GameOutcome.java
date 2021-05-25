package com.jitterted.ebp.blackjack.domain;

public enum GameOutcome {
    PLAYER_BUSTED("You Busted, so you lose.  💸"),
    DEALER_BUSTED("Dealer went BUST, Player wins! Yay for you!! 💵"),
    PLAYER_BEATS_DEALER("You beat the Dealer! 💵"),
    PLAYER_PUSHES_DEALER("Push: Nobody wins, we'll call it even."),
    PLAYER_LOSES("You lost to the Dealer. 💸"),
    PLAYER_WINS_BLACKJACK("You won Blackjack!");

    private String value;

    public String message() {
        return value;
    }

    GameOutcome(String value) {
        this.value = value;
    }
}