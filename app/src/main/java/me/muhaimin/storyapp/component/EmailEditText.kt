package me.muhaimin.storyapp.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import me.muhaimin.storyapp.R

class EmailEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): TextInputLayout(context, attrs){
    init {
        val emailEdit = TextInputEditText(context, attrs).apply {
            setEms(10)
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        addView(emailEdit)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        startIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_email_24) as Drawable
        hint = ContextCompat.getString(context, R.string.email)
        hintTextColor = ContextCompat.getColorStateList(context, R.color.black)
    }
}