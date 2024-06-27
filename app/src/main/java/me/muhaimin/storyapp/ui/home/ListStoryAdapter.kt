package me.muhaimin.storyapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.muhaimin.storyapp.data.dto.Story
import me.muhaimin.storyapp.databinding.StoryItemBinding

class ListStoryAdapter : ListAdapter<Story, ListStoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStoryAdapter.ViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListStoryAdapter.ViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(story)
        }
    }

    inner class ViewHolder(private val binding: StoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            Glide.with(binding.root).load(story.photoUrl).into(binding.imageStory)
            binding.tvName.text = story.name
            binding.tvDescription.text = story.description
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>(){
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(story: Story)
    }
}