package com.example.activityresultapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.f_dont_ask_again.*

class DontAskAgainFragment : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_dont_ask_again, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsButton.setOnClickListener {
            setFragmentResult(
                CameraFragment.SETTINGS_KEY,
                bundleOf(CameraFragment.RESULT_KEY to true)
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = "dont_ask_tag"
    }
}