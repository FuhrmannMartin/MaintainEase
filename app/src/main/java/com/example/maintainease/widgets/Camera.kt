package com.example.maintainease.widgets

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

@Composable
fun CameraAdd(
    modifier: Modifier = Modifier,
    onImageCaptured: (Uri) -> Unit,
    onError: (Exception) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var preview by remember { mutableStateOf<Preview?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    LaunchedEffect(cameraProviderFuture) {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val previewConfig = Preview.Builder().build()
            val imageCaptureConfig = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            preview = previewConfig
            imageCapture = imageCaptureConfig

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    previewConfig,
                    imageCaptureConfig
                )
            } catch (exc: Exception) {
                onError(exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }

                // Initialize camera preview with the Preview object
                preview?.setSurfaceProvider(previewView.surfaceProvider)

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )

        Button(
            onClick = {
                val externalMediaDirs = context.getExternalFilesDirs(null)
                val photoFile = File(
                    externalMediaDirs.firstOrNull(),
                    "IMG_${System.currentTimeMillis()}.jpg"
                )
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
                imageCapture?.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                            onImageCaptured(outputFileResults.savedUri ?: Uri.fromFile(photoFile))
                        }

                        override fun onError(exception: ImageCaptureException) {
                            onError(exception)
                        }
                    }
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Capture")
        }
    }
}

private const val REQUEST_CODE_PERMISSIONS = 10
@Composable
fun CheckPermissions(
    permission: String,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity

    if (ContextCompat.checkSelfPermission(context, permission)
        != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(
            activity, arrayOf(permission), REQUEST_CODE_PERMISSIONS)
    } else {
        onPermissionGranted()
    }
}