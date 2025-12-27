package com.samuelsumbane.quemdeu

import com.samuelsumbane.quemdeu.data.repository.TransactionImpl
import com.samuelsumbane.quemdeu.domain.repository.TransactionRepository
import com.samuelsumbane.quemdeu.domain.usecase.AddTransactionUseCase
import com.samuelsumbane.quemdeu.domain.usecase.GetTransactionsUseCase
import com.samuelsumbane.quemdeu.presentation.viewmodel.TransactionViewModel
import org.koin.dsl.module

// Repository
//single<ClientRepository> { ClientRepositoryImpl() }
//
//// Use cases
//factory { GetClientsUseCase(get()) }
//factory { AddClientUseCase(get()) }
//factory { EditClientUseCase(get()) }
//
//// ViewModel (desktop não tem lifecycle do Android, mas você pode registrar normal)
//factory { ClientViewModel(get(), get(), get()) }

val appModule = module {
    single<TransactionRepository> { TransactionImpl() }

    factory { AddTransactionUseCase(get()) }
    factory { GetTransactionsUseCase(get()) }

    factory { TransactionViewModel(get(), get()) }
}