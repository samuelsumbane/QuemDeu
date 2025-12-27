package com.samuelsumbane.quemdeu.domain.model

data class Transaction(
    val id: Int,
    val title: String,
    val description: String,
    val amount: Double,
    val personName: String,
    val limitDateTime: Long,
    val paidDateTime: Long,
    val state: TransactionType
)

data class TransactionDraft(
    val title: String,
    val description: String,
    val amount: Double,
    val personName: String,
    val limitDateTime: Long,
    val transactionType: TransactionType,
)