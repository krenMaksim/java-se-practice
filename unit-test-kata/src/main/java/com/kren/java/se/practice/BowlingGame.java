package com.kren.java.se.practice;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.function.Predicate.isEqual;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

// TBD the code looks good. Let's focus on unit tests
// let's create a backup copy
// let's add missing tests

class BowlingGame {

  private final FallenPinsGenerator fallenPinsGenerator;
  private final Map<Player, Frame> currentFrameByPlayer;

  public BowlingGame(FallenPinsGenerator fallenPinsGenerator) {
    this.fallenPinsGenerator = fallenPinsGenerator;
    this.currentFrameByPlayer = Stream.of(Player.values())
        .collect(toMap(Function.identity(), player -> Frame.newInstance()));
  }

  public boolean isInProgress() {
    FrameNumber currentFrame = getCurrentFrameNumber();
    return !(isCurrentFrameCompletedByAllPlayers() && currentFrame.equals(FrameNumber.TEN));
  }

  public BowlingGame playFrame() {
    if (isCurrentFrameCompletedByAllPlayers()) {
      startNextFrame();
    }
    return this;
  }

  private void startNextFrame() {
    currentFrameByPlayer.entrySet()
        .forEach(entry -> entry.setValue(entry.getValue().next));
  }

  private boolean isCurrentFrameCompletedByAllPlayers() {
    return currentFrameByPlayer.values()
        .stream()
        .filter(not(Frame::isFrameCompleted))
        .findAny()
        .isEmpty();
  }

  public BowlingGame rollBall(Player player) {
    Objects.requireNonNull(player);
    getCurrentPlayer()
        .filter(not(isEqual(player)))
        .ifPresent(p -> {
          throw new IllegalArgumentException(String.format("Current player %s has one more roll", p));
        });

    int fallenPins = fallenPinsGenerator.getNumber();
    currentFrameByPlayer.get(player).logFallenPins(fallenPins);

    return this;
  }

  private Optional<Player> getCurrentPlayer() {
    return currentFrameByPlayer.entrySet()
        .stream()
        .filter(entry -> entry.getValue().isInProgress())
        .map(Map.Entry::getKey)
        .findFirst();
  }

  public FrameNumber getCurrentFrameNumber() {
    return currentFrameByPlayer.values()
        .stream()
        .findFirst()
        .orElseThrow()
        .frameNumber;
  }

  public int getScore(FrameNumber frameNumber, Player player) {
    return currentFrameByPlayer.get(player)
        .findFrame(frameNumber)
        .calculateScore();
  }

  private static class Frame {

    public static Frame newInstance() {
      Frame[] frames = Stream.of(FrameNumber.values())
          .map(Frame::new)
          .toArray(Frame[]::new);

      // TBD
      frames[frames.length - 1] = new FrameTen();

      for (int i = 0; i < frames.length; i++) {
        if (i != 0) {
          frames[i].previous = frames[i - 1];
        }
        if (i != frames.length - 1) {
          frames[i].next = frames[i + 1];
        }
      }

      return frames[0];
    }

    private static final int ALL_PINS = 10;

    private Integer fallenPinsFirstRoll;
    private Integer fallenPinsSecondRoll;
    private Frame next;
    private Frame previous;
    private final FrameNumber frameNumber;

    private Frame(FrameNumber frameNumber) {
      this.frameNumber = frameNumber;
    }

    public void logFallenPins(int fallenPins) {
      if (isNull(fallenPinsFirstRoll)) {
        fallenPinsFirstRoll = fallenPins;
      } else if (isInProgress()) {
        fallenPinsSecondRoll = fallenPins;
      } else {
        throw new IllegalArgumentException("Number rolls exceeded");
      }
    }

    public boolean isInProgress() {
      return nonNull(fallenPinsFirstRoll) && !isStrike() && isNull(fallenPinsSecondRoll);
    }

    private boolean isStrike() {
      return nonNull(fallenPinsFirstRoll)
          && (fallenPinsFirstRoll == ALL_PINS);
    }

    private boolean isSpare() {
      return (nonNull(fallenPinsFirstRoll) && nonNull(fallenPinsSecondRoll))
          && (fallenPinsFirstRoll + fallenPinsSecondRoll == ALL_PINS);
    }

    public boolean isFrameCompleted() {
      return (nonNull(fallenPinsFirstRoll) && nonNull(fallenPinsSecondRoll))
          || isStrike();
    }

    public int calculateScore() {
      return new Score().calculate();
    }

    public Frame findFrame(FrameNumber frameNumber) {
      return findFrame(frameNumber, frame -> frame.next)
          .or(() -> findFrame(frameNumber, frame -> frame.previous))
          .orElseThrow();
    }

    private Optional<Frame> findFrame(FrameNumber frameNumber, UnaryOperator<Frame> iterateOverFrames) {
      return Stream.iterate(this, Objects::nonNull, iterateOverFrames)
          .filter(frame -> frame.frameNumber == frameNumber)
          .findFirst();
    }

    private class Score {

      private static final int NO_SCORE_CALCULATED = -1;

      public int calculate() {
        int fallenPinsTotal = calculateFallenPinsTotal();
        if (fallenPinsTotal != NO_SCORE_CALCULATED) {
          return Optional.ofNullable(previous)
              .map(previousFrame -> previousFrame.calculateScore() + fallenPinsTotal)
              .orElse(fallenPinsTotal);
        } else {
          return NO_SCORE_CALCULATED;
        }
      }

      protected int calculateFallenPinsTotal() {
        if (isFrameCompleted()) {
          if (isSpare() && isOneExtraBallRolled()) {
            return fallenPinsFirstRoll + fallenPinsSecondRoll + next.fallenPinsFirstRoll;
          } else if (isStrike() && areTwoExtraBallsRolled()) {
            return fallenPinsFirstRoll + next.fallenPinsFirstRoll + next.fallenPinsSecondRoll;
          } else if (isStrike() && areTwoExtraBallsRolledWhenFirstRollIsStrike()) {
            return fallenPinsFirstRoll + next.fallenPinsFirstRoll + next.next.fallenPinsFirstRoll;
          } else if (!isSpare() && !isStrike()) {
            return fallenPinsFirstRoll + fallenPinsSecondRoll;
          }
        }
        return NO_SCORE_CALCULATED;
      }

      protected boolean isOneExtraBallRolled() {
        return nonNull(next) && nonNull(next.fallenPinsFirstRoll);
      }

      protected boolean areTwoExtraBallsRolled() {
        return isOneExtraBallRolled() && nonNull(next.fallenPinsSecondRoll);
      }

      private boolean areTwoExtraBallsRolledWhenFirstRollIsStrike() {
        return isOneExtraBallRolled()
            && next.isStrike()
            && nonNull(next.next)
            && nonNull(next.next.fallenPinsFirstRoll);
      }
    }
  }

  public static class FrameTen extends Frame {

    private Integer fallenPinsFirstExtraRoll;
    private Integer fallenPinsSecondExtraRoll;

    FrameTen() {
      super(FrameNumber.TEN);
    }

    @Override
    public void logFallenPins(int fallenPins) {
      if (isNull(super.fallenPinsFirstRoll)) {
        super.fallenPinsFirstRoll = fallenPins;
      } else if (!super.isStrike() && isNull(super.fallenPinsSecondRoll)) {
        super.fallenPinsSecondRoll = fallenPins;
      } else if ((super.isSpare() || super.isStrike()) && isNull(fallenPinsFirstExtraRoll)) {
        fallenPinsFirstExtraRoll = fallenPins;
      } else if (super.isStrike() && isNull(fallenPinsSecondExtraRoll)) {
        fallenPinsSecondExtraRoll = fallenPins;
      } else {
        throw new IllegalArgumentException("Number rolls exceeded");
      }
    }

    @Override
    public boolean isInProgress() {
      return (!super.isSpare() && !super.isStrike() && nonNull(super.fallenPinsFirstRoll) && isNull(super.fallenPinsSecondRoll))
          || (super.isSpare() && isNull(fallenPinsFirstExtraRoll))
          || (super.isStrike() && (isNull(fallenPinsFirstExtraRoll) || isNull(fallenPinsSecondExtraRoll)));
    }

    @Override
    public boolean isFrameCompleted() {
      return (nonNull(super.fallenPinsFirstRoll) && nonNull(super.fallenPinsSecondRoll) && !super.isSpare())
          || (super.isSpare() && nonNull(fallenPinsFirstExtraRoll))
          || (super.isStrike() && nonNull(fallenPinsFirstExtraRoll) && nonNull(fallenPinsSecondExtraRoll));
    }

    @Override
    public int calculateScore() {
      return new ScoreTen().calculate();
    }

    private class ScoreTen extends Frame.Score {

      @Override
      protected int calculateFallenPinsTotal() {
        if (FrameTen.super.isFrameCompleted()) {
          if (FrameTen.super.isSpare() && isOneExtraBallRolled()) {
            return FrameTen.super.fallenPinsFirstRoll + FrameTen.super.fallenPinsSecondRoll + fallenPinsFirstExtraRoll;
          } else if (FrameTen.super.isStrike() && areTwoExtraBallsRolled()) {
            return FrameTen.super.fallenPinsFirstRoll + fallenPinsFirstExtraRoll + fallenPinsSecondExtraRoll;
          } else if (!FrameTen.super.isSpare() && !FrameTen.super.isStrike()) {
            return FrameTen.super.fallenPinsFirstRoll + FrameTen.super.fallenPinsSecondRoll;
          }
        }
        return Frame.Score.NO_SCORE_CALCULATED;
      }

      @Override
      protected boolean isOneExtraBallRolled() {
        return nonNull(fallenPinsFirstExtraRoll);
      }

      @Override
      protected boolean areTwoExtraBallsRolled() {
        return isOneExtraBallRolled() && nonNull(fallenPinsSecondExtraRoll);
      }
    }
  }

  public enum Player {
    ONE, TWO
  }

  public enum FrameNumber {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(-1) {
      @Override
      public FrameNumber next() {
        throw new IllegalArgumentException("Game is over already");
      }
    };

    private final int nextFrameIndex;

    FrameNumber(int nextFrameIndex) {
      this.nextFrameIndex = nextFrameIndex;
    }

    public FrameNumber next() {
      return FrameNumber.values()[nextFrameIndex];
    }
  }

  public interface FallenPinsGenerator {

    int getNumber();
  }
}
