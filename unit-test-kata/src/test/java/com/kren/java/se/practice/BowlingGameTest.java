package com.kren.java.se.practice;

import com.kren.java.se.practice.BowlingGame.FallenPinsGenerator;
import com.kren.java.se.practice.BowlingGame.FrameNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static com.kren.java.se.practice.BowlingGame.Player;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/*
  A game of bowling consists of ten frames. In each frame, the player is given
  two attempts to roll a ball toward ten wooden pins in order to knock them
  down. The number of pins knocked down by a ball is the score for that ball.
  If all ten pins are knocked down by the first ball, it is called a strike. If
  knocking all ten pins down requires both balls, then it is called a spare. The
  dreaded gutter ball (Figure 2.3) yields no points at all.

  The scoring rules can be stated succinctly as follows:
  • If the frame is a strike, the score is 10 plus the next two balls.
  • If the frame is a spare, the score is 10 plus the next ball.
  • Otherwise, the score is the two balls in the frame.
 */

@ExtendWith(MockitoExtension.class)
class BowlingGameTest {

  // mvn install -pl unit-test-kata
  // -ea -XX:+EnableDynamicAgentLoading -javaagent:C:\Users\MaksimKren\
  // .m2\repository\net\bytebuddy\byte-buddy-agent\1.17.5\byte-buddy-agent-1.17.5.jar

  @Mock
  private FallenPinsGenerator generator;

  @InjectMocks
  private BowlingGame game;

  @Test
  void isGameInProgressAfterStart() {
    assertTrue(game.isInProgress());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9})
  void isGameInProgressAfterNumberFramesPlayed(int framesPlayed) {
    playGame(framesPlayed);

    assertTrue(game.isInProgress());
  }

  @Test
  void isGameOverAfterTenFramesPlayed() {
    playGame(10);

    assertFalse(game.isInProgress());
  }

  private void playGame(int frames) {
    for (int frame = 1; frame <= frames; frame++) {
      game.playFrame()
          .rollBall(Player.ONE)
          .rollBall(Player.ONE)
          .rollBall(Player.TWO)
          .rollBall(Player.TWO);
    }
  }

  @Test
  void playFullGame() { // TBD it does not consider FallenPins generator
    assertDoesNotThrow(() -> {
      while (game.isInProgress()) {
        game.playFrame()
            .rollBall(Player.ONE)
            .rollBall(Player.ONE)
            .rollBall(Player.TWO)
            .rollBall(Player.TWO);
      }
    });
  }

  @Test
  void continueCurrentFrameTillCompleted() {
    var frame = game.getCurrentFrameNumber();

    var newFrame = game.playFrame()
        .rollBall(Player.ONE)
        .getCurrentFrameNumber();

    assertEquals(frame, newFrame);
  }

  @Test
  void startNextFrameWhenCurrentCompleted() {
    var frame = game.getCurrentFrameNumber();

    playGame(1);
    var newFrame = game.playFrame().getCurrentFrameNumber();

    assertNotEquals(frame, newFrame);
  }

  //  @Test
  //  void someTest() {
  //    System.out.println(new BowlingGame.FrameTen().isInProgress());
  //  }

  @Test
  void startGameWithFirstFrame() {
    game.playFrame();

    assertEquals(FrameNumber.ONE, game.getCurrentFrameNumber());
  }

  @Test
  void rollBallByNullPlayer() {
    game.playFrame();

    assertThrows(
        NullPointerException.class,
        () -> game.rollBall(null));
  }

  @Test
  void rollThreeBallsForTheSamePlayer() {
    game.playFrame()
        .rollBall(Player.ONE)
        .rollBall(Player.ONE);

    var ex = assertThrows(
        IllegalArgumentException.class,
        () -> game.rollBall(Player.ONE));
    assertEquals("Number rolls exceeded", ex.getMessage());
  }

  @Test
  void rollOneBallAndSwitchPlayer() {
    game.playFrame()
        .rollBall(Player.ONE);

    var ex = assertThrows(
        IllegalArgumentException.class,
        () -> game.rollBall(Player.TWO));
    assertEquals("Current player ONE has one more roll", ex.getMessage());
  }

  @Test
  void calculateScoreForNormalFrame() {
    when(generator.getNumber()).thenReturn(
        3, 5, // Player.ONE
        4, 2 // Player.TWO
    );

    playGame(1);

    assertEquals(8, game.getScore(FrameNumber.ONE, Player.ONE));
    assertEquals(6, game.getScore(FrameNumber.ONE, Player.TWO));
  }

  @Test
  void calculateScoreForOneRoll() {
    when(generator.getNumber()).thenReturn(3);

    game.playFrame().rollBall(Player.ONE);

    assertEquals(-1, game.getScore(FrameNumber.ONE, Player.ONE));
  }

  @Test
  void calculateScoreForSpare() {
    when(generator.getNumber()).thenReturn(
        3, 7, // Player.ONE
        0, 0,
        4 // Player.ONE
    );

    playGame(1);
    game.playFrame().rollBall(Player.ONE);

    assertEquals(14, game.getScore(FrameNumber.ONE, Player.ONE));
  }

  @Test
  void calculateScoreForSpareWhenAdditionalBallNotRolled() {
    when(generator.getNumber()).thenReturn(3, 7);

    playGame(1);

    assertEquals(-1, game.getScore(FrameNumber.ONE, Player.ONE));
  }

  @Test
  void completeFrameWhenStrikeHappened() {
    when(generator.getNumber()).thenReturn(10, 3, 4);
    FrameNumber currentFrame = game.getCurrentFrameNumber();

    game.playFrame()
        .rollBall(Player.ONE)
        .rollBall(Player.TWO)
        .rollBall(Player.TWO)
        .playFrame();

    assertEquals(currentFrame.next(), game.getCurrentFrameNumber());
  }

  @Test
  void calculateScoreForStrike() {
    when(generator.getNumber()).thenReturn(
        10, // Player.ONE
        0, 0,
        4, 3// Player.ONE
    );

    game.playFrame()
        .rollBall(Player.ONE)
        .rollBall(Player.TWO)
        .rollBall(Player.TWO);
    game.playFrame()
        .rollBall(Player.ONE)
        .rollBall(Player.ONE)
        .rollBall(Player.TWO)
        .rollBall(Player.TWO);

    assertEquals(17, game.getScore(FrameNumber.ONE, Player.ONE));
  }

  @Test
  void calculateScoreForStrikeWhenFirstAdditionalBallNotRolled() {
    when(generator.getNumber()).thenReturn(
        10, // Player.ONE
        0, 0
    );

    game.playFrame()
        .rollBall(Player.ONE)
        .rollBall(Player.TWO)
        .rollBall(Player.TWO);

    assertEquals(-1, game.getScore(FrameNumber.ONE, Player.ONE));
  }

  @Test
  void calculateScoreForStrikeWhenSecondAdditionalBallNotRolled() {
    when(generator.getNumber()).thenReturn(
        10, // Player.ONE
        0, 0,
        5 // Player.ONE
    );

    game.playFrame()
        .rollBall(Player.ONE)
        .rollBall(Player.TWO)
        .rollBall(Player.TWO);
    game.playFrame()
        .rollBall(Player.ONE);

    assertEquals(-1, game.getScore(FrameNumber.ONE, Player.ONE));
  }

  @Test
  void calculateScoreForTwoFrames() {
    when(generator.getNumber()).thenReturn(
        5, 4, // FrameNumber.ONE Player.ONE
        1, 0,        // FrameNumber.ONE Player.TWO
        5, 2,        // FrameNumber.TWO Player.ONE
        4, 4         // FrameNumber.TWO Player.TWO
    );

    playGame(2);

    assertEquals(9, game.getScore(FrameNumber.ONE, Player.ONE));
    assertEquals(1, game.getScore(FrameNumber.ONE, Player.TWO));
    assertEquals(16, game.getScore(FrameNumber.TWO, Player.ONE));
    assertEquals(9, game.getScore(FrameNumber.TWO, Player.TWO));
  }

  @Test
  @Timeout(3)
  void calculateScoreForAllStrikes() {
    when(generator.getNumber()).thenReturn(10);

    while (game.isInProgress()) {
      if (game.playFrame().getCurrentFrameNumber() == FrameNumber.TEN) {
        game.playFrame()
            .rollBall(Player.ONE)
            .rollBall(Player.ONE)
            .rollBall(Player.ONE)
            .rollBall(Player.TWO)
            .rollBall(Player.TWO)
            .rollBall(Player.TWO);
      } else {
        game.playFrame()
            .rollBall(Player.ONE)
            .rollBall(Player.TWO);
      }
    }

    Stream.of(Player.values()).forEach(player -> {
      assertEquals(30, game.getScore(FrameNumber.ONE, player));
      assertEquals(60, game.getScore(FrameNumber.TWO, player));
      assertEquals(90, game.getScore(FrameNumber.THREE, player));
      assertEquals(120, game.getScore(FrameNumber.FOUR, player));
      assertEquals(150, game.getScore(FrameNumber.FIVE, player));
      assertEquals(180, game.getScore(FrameNumber.SIX, player));
      assertEquals(210, game.getScore(FrameNumber.SEVEN, player));
      assertEquals(240, game.getScore(FrameNumber.EIGHT, player));
      assertEquals(270, game.getScore(FrameNumber.NINE, player));
      assertEquals(300, game.getScore(FrameNumber.TEN, player));
    });
  }

  @Test
  @Timeout(3)
  void calculateScoreForAllSpares() {
    when(generator.getNumber()).thenReturn(5);

    while (game.isInProgress()) {
      if (game.playFrame().getCurrentFrameNumber() == FrameNumber.TEN) {
        game.playFrame()
            .rollBall(Player.ONE)
            .rollBall(Player.ONE)
            .rollBall(Player.ONE)
            .rollBall(Player.TWO)
            .rollBall(Player.TWO)
            .rollBall(Player.TWO);
      } else {
        game.playFrame()
            .rollBall(Player.ONE)
            .rollBall(Player.ONE)
            .rollBall(Player.TWO)
            .rollBall(Player.TWO);
      }
    }

    Stream.of(Player.values()).forEach(player -> {
      assertEquals(15, game.getScore(FrameNumber.ONE, player));
      assertEquals(30, game.getScore(FrameNumber.TWO, player));
      assertEquals(45, game.getScore(FrameNumber.THREE, player));
      assertEquals(60, game.getScore(FrameNumber.FOUR, player));
      assertEquals(75, game.getScore(FrameNumber.FIVE, player));
      assertEquals(90, game.getScore(FrameNumber.SIX, player));
      assertEquals(105, game.getScore(FrameNumber.SEVEN, player));
      assertEquals(120, game.getScore(FrameNumber.EIGHT, player));
      assertEquals(135, game.getScore(FrameNumber.NINE, player));
      assertEquals(150, game.getScore(FrameNumber.TEN, player));
    });
  }

  // TBD sequence of spares

  // calculate Score for sequence of strikes
  // calculate score for strike
  // calculate score for full game
}
