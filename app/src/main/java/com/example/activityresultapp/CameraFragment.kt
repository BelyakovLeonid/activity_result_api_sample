package com.example.activityresultapp

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
import androidx.activity.result.launch
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import kotlinx.android.synthetic.main.f_camera.*

class CameraFragment : Fragment(R.layout.f_camera) {

    private val cameraPermission = registerForActivityResult(RequestPermission()) { granted ->
        when {
            granted -> {
                // user granted permission
                cameraShot.launch()
            }
            !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // user denied permission and set Don't ask again.
                DontAskAgainFragment().show(parentFragmentManager, DontAskAgainFragment.TAG)
            }
            else -> {
                Toast.makeText(context, R.string.denied_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val cameraShot = registerForActivityResult(TakePicturePreview()) { bitmap ->
        if (bitmap != null) {
            imageContainer.setImageBitmap(bitmap)
            setImageIsVisible(true)
        } else {
            // something was wrong
            Toast.makeText(context, R.string.something_wrong, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraButton.setOnClickListener {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // we need to tell user why do we need permission
                RationaleFragment().show(parentFragmentManager, RationaleFragment.TAG)
            } else {
                cameraPermission.launch(Manifest.permission.CAMERA)
            }
        }

        closeButton.setOnClickListener {
            setImageIsVisible(false)
        }

        setFragmentResultListener(RATIONALE_KEY) { _, bundle ->
            val isWantToAllowAfterRationale = bundle.getBoolean(RESULT_KEY)
            if (isWantToAllowAfterRationale) {
                cameraPermission.launch(Manifest.permission.CAMERA)
            }
        }
        setFragmentResultListener(SETTINGS_KEY) { _, bundle ->
            val isWantToOpenSettings = bundle.getBoolean(RESULT_KEY)
            if (isWantToOpenSettings) {
                openSettings()
            }
        }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.fromParts("package", requireContext().packageName, null))
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setImageIsVisible(isVisible: Boolean) {
        cameraButton.isVisible = !isVisible
        closeButton.isVisible = isVisible
        imageContainer.isVisible = isVisible
    }

    companion object {
        const val RATIONALE_KEY = "rationale_tag"
        const val SETTINGS_KEY = "settings_tag"
        const val RESULT_KEY = "camera_result_key"
    }
}