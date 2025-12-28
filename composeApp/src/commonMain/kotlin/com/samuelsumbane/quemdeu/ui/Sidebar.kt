package com.samuelsumbane.quemdeu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.samuelsumbane.quemdeu.domain.isAndroid
import com.samuelsumbane.quemdeu.domain.isDesktop
import com.samuelsumbane.quemdeu.isMobilePortrait
import org.jetbrains.compose.resources.painterResource
import quemdeu.composeapp.generated.resources.Res
import quemdeu.composeapp.generated.resources.home
import quemdeu.composeapp.generated.resources.ic_music
import quemdeu.composeapp.generated.resources.settings

@Composable
fun AppSideBar(navigator: Navigator, activePage: String = "home") {
    if ((isAndroid() && !isMobilePortrait()) || isDesktop()) {
        Column(
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            SidebarNav(
                activePage,
                onEachBtnClicked = { page ->
                    appRouter(navigator, page)
                }
            )
        }
    }
}

fun Modifier.sidebarHeight(): Modifier {
    return if (isDesktop()) {
        this.height(400.dp)
    } else {
        this.fillMaxHeight()
    }
}

@Composable
fun SidebarNav(
    activePage: String,
    onEachBtnClicked: (String) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val bottomBgColor = colorScheme.background

    NavigationRail(
        modifier = Modifier.fillMaxSize()
            .background(if (activePage == PageName.HOME.value) Color.Transparent else bottomBgColor),
        containerColor = Color.Transparent,
    ) {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(6.dp, 0.dp, 6.dp, 0.dp)
                    .sidebarHeight()
                    .width(80.dp)
                    .background(
                        bottomBgColor,
                        shape = RoundedCornerShape(15.dp)
                    ),
                elevation = CardDefaults.elevatedCardElevation(3.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(if (isDesktop()) colorScheme.secondary else Color.Transparent),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MenuContent(
                        activePage,
                        onMenuBtnClicked = { page ->
                            onEachBtnClicked(page)
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun MenuContent(
    activePage: String,
    onMenuBtnClicked: (String) -> Unit
) {
    val homePainter = painterResource(Res.drawable.home)
    val transactionPainter = painterResource(Res.drawable.ic_music)
    val mePainter = painterResource(Res.drawable.settings)
    val colorScheme = MaterialTheme.colorScheme

    val pages = buildList {
        add(PageName.HOME.value)
        add(PageName.TRANSACTIONS.value)
        add(PageName.Me.value)
    }

    val btnText = buildMap {
//        set(PageName.HOME.value, stringResource(Res.string.home))
        set(PageName.HOME.value, "Home")
        set(PageName.TRANSACTIONS.value, "Transactions")
        set(PageName.Me.value, "Me")
    }

    val btnIcons = buildMap {
        set(PageName.HOME.value, homePainter)
        set(PageName.TRANSACTIONS.value, transactionPainter)
        set(PageName.Me.value, mePainter)
    }

    pages.forEach {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clickable { onMenuBtnClicked(it) }
                .drawWithContent {
                    drawContent()
                    if (activePage == it) {
                        drawLine(
                            color = lerp(colorScheme.primary, Color.White, 0.15f),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 3.dp.toPx(),
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier.width(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                btnIcons[it]?.let { painter ->
                    Icon(
                        painter = painter,
                        contentDescription = btnText[it],
                        modifier = Modifier.size(25.dp),
                        tint = colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = btnText[it]!!,
                    modifier = Modifier.padding(2.dp),
                    color = if (isDesktop() || activePage == PageName.HOME.value) Color.White else MaterialTheme.colorScheme.tertiary,
                    fontSize = if (isDesktop()) 10.sp else 13.sp
                )
            }
        }
    }
}
