package com.kren.java.se.practice.records;

import org.junit.jupiter.api.Test;

class PaymentTest {

  @Test
  void trySwitchWithSealed() {
    // TBD
  }

  /*

  since Java 21

  private String getDescription(Payment p) {
    return  switch (p) {
      case CashPayment cash -> "CashPayment";
      case CardPayment card -> "CardPayment";
      case CryptoPayment crypto -> "CryptoPayment";
    }
  }
   */
}