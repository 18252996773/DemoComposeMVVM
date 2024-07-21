package mvvm.compose.demo.app

import kotlinx.serialization.Serializable

@Serializable
data class AttractionsModel(
    val total: Int,
    val data: List<AttractionsData>,
)

@Serializable
data class AttractionsData(
    val id: Int = 0,
    val name: String = "",
    val open_status: Int = 0,
    val introduction: String = "",
    val open_time: String = "",
    val url: String = "",
    val images: List<ImageData> = emptyList(),
)

@Serializable
data class ImageData(
    val src: String,
    val ext: String,
)
