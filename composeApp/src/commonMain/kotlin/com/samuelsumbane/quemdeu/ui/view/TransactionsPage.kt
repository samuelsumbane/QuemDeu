package com.samuelsumbane.quemdeu.ui.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Down
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Up
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuelsumbane.quemdeu.domain.model.Transaction
import com.samuelsumbane.quemdeu.domain.model.TransactionType
import com.samuelsumbane.quemdeu.presentation.viewmodel.TransactionViewModel
import com.samuelsumbane.quemdeu.ui.InputField
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.java.KoinJavaComponent.getKoin
import quemdeu.composeapp.generated.resources.Res
import quemdeu.composeapp.generated.resources.add
import quemdeu.composeapp.generated.resources.search


class TransactionScreen : Screen {
    @Composable
    override fun Content() {
        TransactionsPage()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionsPage() {
    val tabs = listOf("Entrada", "Saída")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val primaryColor = MaterialTheme.colorScheme.primary
    val navigator = LocalNavigator.currentOrThrow


    val transactionViewModel by remember { mutableStateOf(getKoin().get<TransactionViewModel>())}
    val transactionUiState by transactionViewModel.state.collectAsState()
    var searchValue by remember { mutableStateOf("") }


    val inTransactionsList = filteredData(searchValue, transactionUiState.inTransactions)
    val outTransactionsList = filteredData(searchValue, transactionUiState.outTransactions)


    @Composable
    fun tabContent(transactionType: TransactionType) {
        if (transactionType == TransactionType.IN) {
            if (transactionUiState.inTransactions.isEmpty()) {
                dataNotFound(text = "Nenhuma transação de saída encontrada")
            } else {
                lazyColumn {
                    items(inTransactionsList) { ItemRow(navigator,it) }
                }
            }
        } else {
            if (transactionUiState.outTransactions.isEmpty()) {
                dataNotFound(text = "Nenhuma transação de entrada encontrada")
            } else {
                lazyColumn {
                    items(outTransactionsList) { ItemRow(navigator, it) }
                }
            }
        }
    }


    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
//                Column()
                // title, description, personname, amount,
                    Text("Adicionar transacao", fontWeight = FontWeight.Bold)

                    InputField(
                        label = "Titulo",
                        inputValue = transactionUiState.title,
                        onValueChanged = { transactionViewModel.fillFormFields(title = it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    InputField(
                        label = "Descrição",
                        inputValue = transactionUiState.description,
                        onValueChanged = { transactionViewModel.fillFormFields(description = it) },
                        modifier = Modifier.fillMaxWidth()

                    )
                    InputField(
                        label = "Titular",
                        inputValue = transactionUiState.personName,
                        onValueChanged = { transactionViewModel.fillFormFields(personName = it) },
                        modifier = Modifier.fillMaxWidth()

                    )
                    InputField(
                        label = "Montante",
                        inputValue = transactionUiState.amount.toString(),
                        onValueChanged = { transactionViewModel.fillFormFields(amount = it.toDouble()) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = KeyboardType.Decimal
                    )

                    InputField(
                        label = "Data limite",
                        inputValue = transactionUiState.limitDateTime.toString(),
                        onValueChanged = {  },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxWidth(),
//                            .background(Color.Green),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    transactionViewModel.cleanForm()
                                    scaffoldState.bottomSheetState.hide()
                                }
                            }
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = { transactionViewModel.submitForm() }
                        ) {
                            Text("Submeter")
                        }
                    }
            }
        },
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {}
//            ) {
//                Icon(painterResource(Res.drawable.add), contentDescription = "Add record")
//            }
//        }
    ) {
        Box(
           modifier = Modifier
               .padding(it)
        ) {

            Column(
                modifier = Modifier
//                .padding(it)
                    .fillMaxSize()
                    .background(Color.DarkGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SecondaryTabRow(
                    selectedTabIndex = 0,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(0.95f)
                        .height(50.dp)
//                    .background(Color.Red)
                    ,
                    containerColor = Color.Transparent,
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, tab ->
                        Tab(
                            text = {
                                Text(
                                    tab,
                                    color = if (selectedTabIndex == index) Color.White else primaryColor
                                )
                            },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            modifier = Modifier
                                .background(
                                    color = if (selectedTabIndex == index) primaryColor else Color.Transparent,
                                    shape = RoundedCornerShape(8.dp),
                                ),
                            selectedContentColor = primaryColor,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(10.dp, 17.dp)
                        .fillMaxWidth()
//                    .height(55.dp)
                        .background(Color.Red)
                ) {
                    SearchInput(
                        value = searchValue,
                        onValueChangedValue = { searchValue = it }
                    )
                }


                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        slideIntoContainer(
                            animationSpec = tween(400, easing = EaseIn), towards = Up
                        ).togetherWith(
                            slideOutOfContainer(
                                animationSpec = tween(450, easing = EaseOut), towards = Down
                            )
                        )
                    },
                ) { selectedTabIndex ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
//                        println(selectedTabIndex)
                        when (selectedTabIndex) {
                            0 -> tabContent(TransactionType.IN)
                            1 -> tabContent(TransactionType.OUT)
                        }
                    }
                }
            }

            ElevatedButton(
                onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                shape = RoundedCornerShape(30),
                modifier = Modifier
                    .padding(end = 30.dp, bottom = 30.dp)
//                    .size(60.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.add),
                    "add",
                    modifier = Modifier.size(30.dp))
            }
        }
    }
}


@Composable
fun dataNotFound(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun ItemRow(navigator: Navigator, transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
            .padding(10.dp)
            .clickable { navigator.push(EachTransactionDetailScreen(transaction)) }
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text(transaction.personName)
            Text(transaction.title)
        }
        Text(
            text = transaction.amount.toString(),
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}


@Composable
fun lazyColumn(content: LazyListScope.() -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) { content() }
}

@Composable
fun SearchInput(
    value: String,
    onValueChangedValue: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = { onValueChangedValue(it) },
        placeholder = { Text("Search") },
        leadingIcon = { Icon(painterResource(Res.drawable.search), contentDescription = "Search") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    )
}


@Composable
fun filteredData(searchValue: String, transactionList: List<Transaction>): List<Transaction> {
    val inTransactionsList = remember (searchValue, transactionList) {
        if (searchValue.isBlank()) {
            transactionList
        } else {
            transactionList
                .filter {
                    it.title.contains(searchValue, ignoreCase = true) ||
                    it.personName.contains(searchValue, ignoreCase = true) ||
                    it.amount.toString().startsWith(searchValue)
                }
        }
    }
    return inTransactionsList
}