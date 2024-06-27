package me.muhaimin.storyapp.data.dto

data class GetAllStoryResponse(
	val listStory: List<Story?>? = null,
	val error: Boolean? = null,
	val message: String? = null
)

data class Story(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val lon: Any? = null,
	val id: String? = null,
	val lat: Any? = null
)

