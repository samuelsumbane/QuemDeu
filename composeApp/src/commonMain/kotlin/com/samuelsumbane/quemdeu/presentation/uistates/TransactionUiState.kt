package com.samuelsumbane.quemdeu.presentation.uistates

import com.samuelsumbane.quemdeu.domain.model.Transaction
import com.samuelsumbane.quemdeu.domain.model.TransactionType
import com.samuelsumbane.quemdeu.ui.InputNames

data class TransactionUiState(
    val inTransactions: List<Transaction> = emptyList(),
    val outTransactions: List<Transaction> = emptyList(),
    val title: String = "",
    val description: String = "",
    val personName: String = "",
    val amount: Double = 0.0,
    val limitDateTime: Long = 0L,
    val transactionType: TransactionType = TransactionType.IN,
    val errors: Map<InputNames, String> = emptyMap()
)
