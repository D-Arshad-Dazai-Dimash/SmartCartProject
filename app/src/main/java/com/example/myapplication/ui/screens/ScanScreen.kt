package com.example.myapplication.ui.screens

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.ScanScreen.CameraPreview
import com.example.myapplication.ui.components.ScanScreen.RequestCameraPermission
import com.example.myapplication.viewModel.CartViewModel
import com.example.myapplication.viewModel.getProductDetailsFromFirebase
import kotlinx.coroutines.delay


fun playSound(context: Context) {
    val mediaPlayer = MediaPlayer.create(
        context,
        R.raw.beep
    )
    mediaPlayer.start()
}

@Composable
fun ScanScreen(navController: NavController, cartViewModel: CartViewModel) {
    var permissionGranted by remember { mutableStateOf(false) }
    var scannedBarcode by remember { mutableStateOf<String?>(null) }
    var productName by remember { mutableStateOf<String?>(null) }
    var productAdded by remember { mutableStateOf(false) }

    RequestCameraPermission(
        onPermissionGranted = { permissionGranted = true },
        onPermissionDenied = { }
    )

    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        if (permissionGranted) {
            CameraPreview(
                context = context,
                lifecycleOwner = LocalLifecycleOwner.current,
                onBarcodeScanned = { barcode ->
                    if (scannedBarcode != barcode) {
                        scannedBarcode = barcode
                        getProductDetailsFromFirebase(barcode) { product ->
                            product?.let {
                                cartViewModel.addProduct(it)
                                productName = it.name
                                productAdded = true
                                Log.d("ScanScreen", "Added: ${it.name}")
                            } ?: Log.d("ScanScreen", "Product not found: $barcode")
                        }

                    }
                }
            )
        }

        if (productAdded && productName != null) {
            LaunchedEffect(productName) {
                playSound(context)
                delay(1000)
                productAdded = false
                productName = null
            }

            Text(
                text = "Added: $productName",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                color = Color.White,
                style = TextStyle(fontSize = 18.sp)
            )
        }

        scannedBarcode?.let {
            Text(
                text = "Last Scanned Barcode: $it",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp),
                color = Color.White
            )
        }

        Image(
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = "Back arrow",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .size(45.dp, 44.dp)
                .clickable {
                    navController.popBackStack()
                }
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(16.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val rectWidth = canvasWidth * 0.8f
                val rectHeight = canvasHeight * 0.6f
                val left = (canvasWidth - rectWidth) / 2
                val top = (canvasHeight - rectHeight) / 2

                drawRoundRect(
                    color = Color.White.copy(0.5f),
                    size = Size(rectWidth, rectHeight),
                    topLeft = Offset(left, top),
                    style = Stroke(width = 4.dp.toPx()),
                    cornerRadius = CornerRadius(50f, 50f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    navController.navigate("cart")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(62.dp)
                    .clip(RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Go To Shopping Cart",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )
            }
        }
    }
}

