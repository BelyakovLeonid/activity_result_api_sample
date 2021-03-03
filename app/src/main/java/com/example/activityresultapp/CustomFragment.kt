package com.example.activityresultapp

import android.os.Bundle
import android.view.View
import androidx.activity.result.launch
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.f_custom.*

class CustomFragment : Fragment(R.layout.f_custom) {

    private val secondActivity = registerForActivityResult(SecondActivityContract()) { result ->
        if (result != null) {
            resultText.text = result.toString()
            noResultText.isVisible = false
            resultText.isVisible = true
            buttonGetResult.setText(R.string.get_result_again_text)
        } else {
            noResultText.isVisible = true
            resultText.isVisible = false
            buttonGetResult.setText(R.string.get_result_text)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonGetResult.setOnClickListener {
            secondActivity.launch()
        }
    }
}