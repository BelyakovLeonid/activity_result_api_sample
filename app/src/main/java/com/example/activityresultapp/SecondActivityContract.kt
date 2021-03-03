package com.example.activityresultapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SecondActivityContract : ActivityResultContract<Unit, Int?>() {
    override fun createIntent(context: Context, input: Unit?): Intent {
        return Intent(context, SecondActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Int? {
        if (resultCode != Activity.RESULT_OK) return null
        return intent?.extras?.getInt(RESULT_KEY)
    }

    companion object {
        const val RESULT_KEY = "my_result_key"
    }
}