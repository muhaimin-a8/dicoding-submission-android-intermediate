package me.muhaimin.storyapp.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import me.muhaimin.storyapp.R

class PasswordEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): TextInputLayout(context, attrs) {
    init {
        val passwordEdit = TextInputEditText(context, attrs).apply {
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
            doOnTextChanged { text, _, _, _ ->
                error = if (text.toString().length < 8) {
                    ContextCompat.getString(context, R.string.password_error_message)
                } else null
            }
        }
        addView(passwordEdit)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        startIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_baseline_lock_24) as Drawable
        hint = ContextCompat.getString(context, R.string.password)
        endIconMode = END_ICON_PASSWORD_TOGGLE
    }
}