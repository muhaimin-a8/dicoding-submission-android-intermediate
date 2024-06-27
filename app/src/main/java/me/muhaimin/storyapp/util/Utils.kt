package me.muhaimin.storyapp.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.progressindicator.LinearProgressIndicator

fun LinearProgressIndicator.showLoading(isLoading: Boolean) {
    this.visibility = if (isLoading) View.VISIBLE else View.GONE
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}