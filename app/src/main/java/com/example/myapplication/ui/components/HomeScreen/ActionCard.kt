package com.example.myapplication.ui.components.HomeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ActionCard(
    label: String,
    count: String,
    cardHeight: Dp = 100.dp,
    cardWidth: Dp = 200.dp,
    isSwappable: Boolean = false,
    pagerItems: List<String> = emptyList(),
    backgroundImages: List<Painter> = emptyList(),
    icon: Painter? = null
) {
    Card(
        modifier = Modifier
            .height(cardHeight)
            .width(cardWidth)
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7EFEE),
            disabledContentColor = Color(0xFFF7EFEE),
            contentColor = Color(0xFFF7EFEE),
            disabledContainerColor = Color(0xFFF7EFEE)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isSwappable && pagerItems.isNotEmpty()) {
                SwipeablePager(pagerItems = pagerItems , backgroundImages)
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    icon?.let {
                        Image(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier.size(55.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = count,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.Gray
                        )
                    )
                }
            }
            if (isSwappable && pagerItems.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                ) {
                    PagerIndicators(pagerItemCount = pagerItems.size)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicators(pagerItemCount: Int) {
    val pagerState = rememberPagerState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerItemCount) { index ->
            val dotColor = if (index == pagerState.currentPage) Color.White else Color.White.copy(0.5f)
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(dotColor)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.padding(end = 5.28.dp))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwipeablePager(pagerItems: List<String>, backgroundImages: List<Painter>) {
    val pagerState = rememberPagerState()

    HorizontalPager(
        count = pagerItems.size,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = backgroundImages[page],
                contentDescription = "Card Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = pagerItems[page],
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
        }
    }
}
