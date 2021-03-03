package com.example.activityresultapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.f_rationale.*

class RationaleFragment : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f_rationale, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rationaleButton.setOnClickListener {
            setFragmentResult(
                CameraFragment.RATIONALE_KEY,
                bundleOf(CameraFragment.RESULT_KEY to true)
            )
            dismiss()
        }
    }

    companion object {
        const val TAG = "rationale_tag"
    }
}