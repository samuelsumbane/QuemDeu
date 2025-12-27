package com.samuelsumbane.quemdeu

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuelsumbane.quemdeu.domain.model.Transaction
import com.samuelsumbane.quemdeu.domain.model.TransactionType
import com.samuelsumbane.quemdeu.presentation.viewmodel.TransactionViewModel
import com.samuelsumbane.quemdeu.ui.theme.AppTheme
import com.samuelsumbane.quemdeu.ui.view.EachTransactionDetailScreen
import com.samuelsumbane.quemdeu.ui.view.TransactionScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.java.KoinJavaComponent.getKoin

class AppScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        App(navigator)
    }
}

@Composable
@Preview
fun App(navigator: Navigator) {
    AppTheme(darkMode = true) {
        var showContent by remember { mutableStateOf(false) }
        val transactionViewModel by remember { mutableStateOf(getKoin().get<TransactionViewModel>())}
        val transactionUiState by transactionViewModel.state.collectAsState()

//        navigator.push(TransactionScreen())
        navigator.push(EachTransactionDetailScreen(Transaction(
            id = 1,
            title = "emprestimo",
            description = "me pagou os 20 meticais",
            amount = 20.00,
            personName = "Ana bela",
            limitDateTime = 0L,
            paidDateTime = 0L,
            state = TransactionType.IN
        )))

//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//
//            Text("In: ${transactionUiState.inTransactions.sumOf { it.amount }}")
//            Text("Out: ${transactionUiState.outTransactions.sumOf { it.amount }}")
//
////            Button(
////                onClick = {
////                    transactionViewModel.addTransaction(
////                        TransactionDraft(
////                            title = "dfa",
////                            description = "outro emprestimo do senhor X",
////                            amount = 300.0,
////                            personName = "no name",
////                            limitDateTime = 0L,
////                            state = MoneyState.OUT
////                        )
////                    )
////                }
////            ) {
////                Text("Add")
////            }
////
////            Text(text = "${transactionUiState.outTransactions}")
//        }
    }
}