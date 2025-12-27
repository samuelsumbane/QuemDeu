package com.samuelsumbane.quemdeu.domain.repository

import com.samuelsumbane.quemdeu.domain.model.Transaction
import com.samuelsumbane.quemdeu.domain.model.TransactionDraft

interface TransactionRepository {
    fun getPayMoney(): List<Transaction>
    suspend fun addPayMoney(transactionDraft: TransactionDraft)
}