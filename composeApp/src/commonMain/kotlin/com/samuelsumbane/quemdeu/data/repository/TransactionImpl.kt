package com.samuelsumbane.quemdeu.data.repository

import com.samuelsumbane.quemdeu.domain.model.TransactionType
import com.samuelsumbane.quemdeu.domain.model.Transaction
import com.samuelsumbane.quemdeu.domain.model.TransactionDraft
import com.samuelsumbane.quemdeu.domain.repository.TransactionRepository

class TransactionImpl : TransactionRepository {
    var records = mutableListOf<Transaction>()
    init {
        defaultRecords.forEach { records.add(it) }
    }

    override fun getPayMoney(): List<Transaction> {
//        println("from primary get: $records")
        return records
    }

    override suspend fun addPayMoney(transactionDraft: TransactionDraft) {
        val id = records.last().id + 1
        with(transactionDraft) {
            records.add(
                Transaction(
                    id = id,
                    title = title,
                    description = description,
                    amount = amount,
                    personName = personName,
                    limitDateTime = limitDateTime,
                    paidDateTime = 0L,
                    state = transactionType
                )
            )
        }
        println("now all are $records")

    }

}



val defaultRecords = listOf(
    Transaction(
        id = 1,
        title = "emprestimo",
        description = "me pagou os 20 meticais",
        amount = 20.00,
        personName = "Ana bela",
        limitDateTime = 0L,
        paidDateTime = 0L,
        state = TransactionType.IN
    ),
    Transaction(
        id = 2,
        title = "Float de mpesa",
        description = "Dar empretado 100 meticais a florinda",
        amount = 100.00,
        personName = "Florinda",
        limitDateTime = 0L,
        paidDateTime = 0L,
        state = TransactionType.OUT
    ),
    Transaction(
        id = 4,
        title = "compra de animal",
        description = "Dar empretado 100 meticais a florinda",
        amount = 100.00,
        personName = "Elias",
        limitDateTime = 0L,
        paidDateTime = 0L,
        state = TransactionType.IN
    ),
)