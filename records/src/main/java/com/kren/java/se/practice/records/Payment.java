package com.kren.java.se.practice.records;

sealed interface Payment permits CashPayment, CardPayment, CryptoPayment {
}

record CashPayment() implements Payment {
}

record CardPayment() implements Payment {
}

record CryptoPayment() implements Payment {
}


