package me.muhaimin.storyapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import me.muhaimin.storyapp.R
import me.muhaimin.storyapp.data.ResultState
import me.muhaimin.storyapp.data.dto.Story
import me.muhaimin.storyapp.databinding.ActivityHomeBinding
import me.muhaimin.storyapp.ui.ViewModelFactory
import me.muhaimin.storyapp.ui.detail.DetailStoryActivity
import me.muhaimin.storyapp.ui.login.LoginActivity
import me.muhaimin.storyapp.ui.new_story.NewStoryActivity
import me.muhaimin.storyapp.util.showLoading
import me.muhaimin.storyapp.util.showToast

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.getSession().observe(this) { user ->
            Toast.makeText(this, "${user.isLogin}", Toast.LENGTH_SHORT).show()
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddNewStory.setOnClickListener {
            startActivity(
                Intent(this, NewStoryActivity::class.java)
            )
        }

        setupListStory()
    }

    private fun setupListStory() {
        binding.rvStories.layoutManager = LinearLayoutManager(this)
        binding.rvStories.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        viewModel.getAllStories().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        binding.progressIndicator.showLoading(true)
                    }

                    is ResultState.Success -> {
                        showToast(this,result.data.message.toString())
                        binding.progressIndicator.showLoading(false)

                        val adapter = ListStoryAdapter()
                        adapter.submitList(result.data.listStory)
                        adapter.setItemClickCallback(object: ListStoryAdapter.OnItemClickCallback {
                            override fun onItemClicked(story: Story) {
                                val intent = Intent(this@HomeActivity, DetailStoryActivity::class.java)
                                intent.putExtra(DetailStoryActivity.EXTRA_STORY_ID, story.id)

                                startActivity(intent)
                            }

                        })
                        binding.rvStories.adapter = adapter
                    }

                    is ResultState.Error -> {
                        showToast(this, result.error)
                        binding.progressIndicator.showLoading(false)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_logout -> {
                viewModel.logout()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}