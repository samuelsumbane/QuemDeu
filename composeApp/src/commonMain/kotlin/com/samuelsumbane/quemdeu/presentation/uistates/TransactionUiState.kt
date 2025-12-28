package com.samuelsumbane.quemdeu.presentation.uistates

import com.samuelsumbane.quemdeu.domain.model.Transaction
import com.samuelsumbane.quemdeu.domain.model.TransactionType
import com.samuelsumbane.quemdeu.ui.InputNames

data class TransactionUiState(
    val inTransactions: List<Transaction> = emptyList(),
    val outTransactions: List<Transaction> = emptyList(),
    val title: String = "emprestimo simples",
    val description: String = "pedido de 400 mt",
    val personName: String = "Roma Nove",
    val amount: Double = 10.0,
    val limitDateTime: Long = 0L,
    val transactionType: TransactionType = TransactionType.IN,
    val errors: Map<InputNames, String> = emptyMap()
)
