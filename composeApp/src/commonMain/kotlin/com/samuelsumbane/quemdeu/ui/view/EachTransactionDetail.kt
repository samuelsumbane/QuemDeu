package com.samuelsumbane.quemdeu.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.samuelsumbane.quemdeu.domain.model.Transaction
import org.jetbrains.compose.resources.painterResource
import quemdeu.composeapp.generated.resources.Res
import quemdeu.composeapp.generated.resources.arrow_back

//class EachTransactionDetailScreen : Screen {
//    @Composable
//    override fun Content() {
//        EachTransactionDetail()
//    }
//}
data class EachTransactionDetailScreen(val transaction: Transaction) : Screen {
    @Composable
    override fun Content() {
        EachTransactionDetail(transaction = transaction)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachTransactionDetail(transaction: Transaction) {
    val bottomScaffoldState = rememberBottomSheetScaffoldState()
    val navigator = LocalNavigator.currentOrThrow

    BottomSheetScaffold(
        scaffoldState = bottomScaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {

        },
        topBar = {
            TopAppBar(
                title = { Text("Conteudo da transação") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(painterResource(Res.drawable.arrow_back), "go backwark")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//                .background(Color.Red)
            ,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .background(color = Color.Black.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                    .border(width = 1.4.dp, color = Color.White, RoundedCornerShape(12.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(45.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                with(transaction) {
                    BodyText("Titulo", title)
                    BodyText("Descrição", description)
                    BodyText("Titular", personName)
                    BodyText("Montante", "$amount mt")
//                    BodyText("Data limite", limitDateTime)
                }

                Button(
                    onClick = {

                    }
                ) {
                    Text("Pagar")
                }
            }
        }
    }
}


@Composable
fun BodyText(title: String, text: String) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold)
        Text(text)
    }
}