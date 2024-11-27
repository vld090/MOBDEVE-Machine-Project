package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.FragmentCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraProvider: ListenableFuture<ProcessCameraProvider>
    private var imageCapture: ImageCapture? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraProvider = ProcessCameraProvider.getInstance(requireContext())

        cameraProvider.addListener({
            val camProvider = cameraProvider.get()
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraView.surfaceProvider)
            }

            val cameraSelector = androidx.camera.core.CameraSelector.Builder()
                .requireLensFacing(androidx.camera.core.CameraSelector.LENS_FACING_BACK)
                .build()

            imageCapture = ImageCapture.Builder().build()

            try {
                camProvider.unbindAll()
                camProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Failed to bind camera!", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        binding.ibCamCapture.setOnClickListener {
            val photo = File(
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "${SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(Date())}.jpg"
            )
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photo).build()

            imageCapture?.takePicture(outputOptions, ContextCompat.getMainExecutor(requireContext()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val photoUri = Uri.fromFile(photo).toString()

                        val result = Bundle().apply {
                            putString("captured_image_uri", photoUri)
                        }
                        parentFragmentManager.setFragmentResult("camera_result", result)

                        parentFragmentManager.popBackStack()
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Toast.makeText(requireContext(), "Photo capture failed", Toast.LENGTH_SHORT).show()
                    }
                })
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
