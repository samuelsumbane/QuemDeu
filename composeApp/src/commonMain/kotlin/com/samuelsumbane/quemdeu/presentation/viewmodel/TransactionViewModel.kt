package com.samuelsumbane.quemdeu.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samuelsumbane.quemdeu.domain.model.TransactionType
import com.samuelsumbane.quemdeu.domain.model.TransactionDraft
import com.samuelsumbane.quemdeu.domain.usecase.AddTransactionUseCase
import com.samuelsumbane.quemdeu.domain.usecase.GetTransactionsUseCase
import com.samuelsumbane.quemdeu.presentation.uistates.TransactionUiState
import com.samuelsumbane.quemdeu.ui.InputNames
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {
    val _state = MutableStateFlow(TransactionUiState())
    val state = _state.asStateFlow()

   init {
       getInTransactions()
       getOutTransactions()
   }

    fun getInTransactions() {
        viewModelScope.launch {
            val inTransactions = getTransactionsUseCase().filter { tra -> tra.state == TransactionType.IN }
            println("in are $inTransactions")
            _state.update { it.copy(inTransactions = inTransactions) }
        }
    }
    fun getOutTransactions() {
        viewModelScope.launch {
            val outTransactions = getTransactionsUseCase().filter { it.state == TransactionType.OUT }
            println("out are $outTransactions")
            _state.update { it.copy(outTransactions = outTransactions) }
        }
    }

    fun submitForm() {
        viewModelScope.launch {
            if (state.value.title.isBlank()) {
                setError(InputNames.Title, "O titulo é obrigatorio")
            } else {
                setError(InputNames.Title, "")
                return@launch
            }

            if (state.value.title.isBlank()) {
                setError(InputNames.PersonName, "O nome da pessoa é obrigatorio")
            } else {
                setError(InputNames.PersonName, "")
                return@launch
            }

            if (state.value.title.isBlank()) {
                setError(InputNames.Amount, "O valor é obrigatorio")
            } else {
                setError(InputNames.Amount, "")
                return@launch
            }

            val transactionDraft = TransactionDraft(
                title = state.value.title,
                description = state.value.description,
                amount = state.value.amount,
                personName = state.value.personName,
                limitDateTime = 0L,
                transactionType = state.value.transactionType
            )

            addTransactionUseCase(transactionDraft)
            if (transactionDraft.transactionType == TransactionType.IN) getInTransactions() else getOutTransactions()
        }

    }


    fun fillFormFields(
        title: String? = null,
        description: String? = null,
        personName: String? = null,
        amount: Double? = null,
    ) {
        title?.let { newValue -> _state.update { it.copy(title = newValue) } }
        description?.let { newValue -> _state.update { it.copy(description = newValue) } }
        personName?.let { newValue -> _state.update { it.copy(personName = newValue) } }
        amount?.let { newValue -> _state.update { it.copy(amount = newValue) } }
    }

    fun setError(inputNames: InputNames, error: String) {
        _state.update {
            it.copy(errors = it.errors.toMutableMap().apply { put(inputNames, error) })
        }
    }

    fun cleanForm() {
        _state.update {
            it.copy(
                title = "",
                description = "",
                personName = "",
                amount = 0.0,
                limitDateTime = 0L
            )
        }
    }
}