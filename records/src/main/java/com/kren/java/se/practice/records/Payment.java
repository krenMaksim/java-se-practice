package com.kren.java.se.practice.records;

interface Payment {
}

record CashPayment() implements Payment {
}

record CardPayment() implements Payment {
}

record CryptoPayment() implements Payment {
}


