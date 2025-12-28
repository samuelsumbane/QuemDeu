package com.samuelsumbane.quemdeu.domain.usecase

import com.samuelsumbane.quemdeu.domain.model.TransactionDraft
import com.samuelsumbane.quemdeu.domain.repository.TransactionRepository

class AddTransactionUseCase(private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(transactionDraft: TransactionDraft) {
        // Can add something else here.
        transactionRepository.addPayMoney(transactionDraft)
    }

}

class GetTransactionsUseCase(private val transactionRepository: TransactionRepository) {
    operator fun invoke() = transactionRepository.getPayMoney()
}
