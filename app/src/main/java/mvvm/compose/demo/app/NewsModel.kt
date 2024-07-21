package mvvm.compose.demo.app

import kotlinx.serialization.Serializable

@Serializable
data class NewsModel(
    val total: Int,
    val data: List<NewsData>,
)

@Serializable
data class NewsData(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val posted: String = "",
    val url: String = "",
    val links: List<LinkData> = emptyList(),
)

@Serializable
data class LinkData(
    val src: String,
    val subject: String,
)
