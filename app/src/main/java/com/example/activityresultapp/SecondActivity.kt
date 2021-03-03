package com.example.activityresultapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity(R.layout.activity_second) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterResultText.doOnTextChanged { text, _, _, _ ->
            buttonGetResult.isEnabled = !text.isNullOrEmpty()
        }

        buttonGetResult.setOnClickListener {
            setResult(
                Activity.RESULT_OK,
                Intent().putExtra(SecondActivityContract.RESULT_KEY, 42)
            )
            finish()
        }
    }
}