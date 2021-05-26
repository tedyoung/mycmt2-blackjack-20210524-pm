package com.jitterted.ebp.blackjack;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlackjackGameApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlackjackGameApplication.class, args);
  }

  // allows Spring to assemble classes from our domain without polluting it
  @Bean
  public Game createGame() {
    return new Game(new Deck());
  }

}
