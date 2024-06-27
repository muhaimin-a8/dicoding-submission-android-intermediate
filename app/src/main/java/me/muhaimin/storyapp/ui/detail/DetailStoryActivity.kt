package me.muhaimin.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import me.muhaimin.storyapp.R
import me.muhaimin.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val storyID = intent.getStringExtra(EXTRA_STORY_ID)

        Toast.makeText(this, storyID, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_STORY_ID = "extra_story_id"
    }
}