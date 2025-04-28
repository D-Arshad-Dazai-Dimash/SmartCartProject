import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.components.cartScreen.getProductDetails
import com.example.myapplication.viewModel.CartViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@Composable
fun ScanScreen(navController: NavController, cartViewModel: CartViewModel) {
    var permissionGranted by remember { mutableStateOf(false) }
    var scannedBarcode by remember { mutableStateOf<String?>(null) }

    RequestCameraPermission(
        onPermissionGranted = { permissionGranted = true },
        onPermissionDenied = { /* Handle permission denial if needed */ },
        onBarcodeScanned = { barcode ->
            scannedBarcode = barcode
            val product = getProductDetails(barcode)
            cartViewModel.addProduct(product)
            println("Scanned product: $barcode")
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (permissionGranted) {
            CameraPreview(
                context = LocalContext.current,
                lifecycleOwner = LocalLifecycleOwner.current,
                onBarcodeScanned = { barcode ->
                    scannedBarcode = barcode
                    val product = getProductDetails(barcode)
                    cartViewModel.addProduct(product)
                    println("Scanned product: $barcode")
                }
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
                    navController.popBackStack("home", inclusive = false)
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
            ) {
                Text(text = "Go to Cart with Scanned Barcodes")
            }
        }
    }
}

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onBarcodeScanned: (String) -> Unit
) {
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val cameraProvider = ProcessCameraProvider.getInstance(ctx).get()
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val scanner = BarcodeScanning.getClient()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { imageProxy ->
                        processImageProxy(scanner, imageProxy, onBarcodeScanned)
                    }
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (e: Exception) {
                Log.e("CameraPreview", "Camera binding failed", e)
            }

            previewView
        }
    )
}

@SuppressLint("UnsafeOptInUsageError")
private fun processImageProxy(
    scanner: BarcodeScanner,
    imageProxy: ImageProxy,
    onBarcodeScanned: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    barcode.rawValue?.let { value ->
                        onBarcodeScanned(value)
                    }
                }
            }
            .addOnFailureListener {
                Log.e("BarcodeScanner", "Scanning failed", it)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}

@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onBarcodeScanned: (String) -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}
