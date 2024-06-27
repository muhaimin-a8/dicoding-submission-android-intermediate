package me.muhaimin.storyapp.data.dto

data class GetStoryByIdResponse(
	val error: Boolean? = null,
	val message: String? = null,
	val story: StoryDetail? = null
)

data class StoryDetail(
	val photoUrl: String? = null,
	val createdAt: String? = null,
	val name: String? = null,
	val description: String? = null,
	val lon: Any? = null,
	val id: String? = null,
	val lat: Any? = null
)

