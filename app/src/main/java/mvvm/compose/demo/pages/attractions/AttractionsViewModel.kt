package mvvm.compose.demo.pages.attractions

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mvvm.compose.demo.app.AttractionsData
import mvvm.compose.demo.app.RemoteAPI

class AttractionsViewModel : ViewModel() {
    val listData = mutableStateOf<List<AttractionsData>>(emptyList())
    val detailData = mutableStateOf<AttractionsData>(AttractionsData())
    val webTitle = mutableStateOf<String>("")
    val webLink = mutableStateOf<String>("")

    fun getAttractionsModel() {
        viewModelScope.launch {
            val model = RemoteAPI.getAttractionsModel()
            listData.value = model.data
        }
    }

    fun setDetailData(index: Int) {
        detailData.value = listData.value[index]
    }

    fun bundleDetailData(data: AttractionsData): Bundle {
        val json = Json.encodeToString(data)
        return bundleOf(AttractionsDetailFragment.DETAIL_DATA to json)
    }

    fun bundleWebDetail(data: AttractionsData): Bundle {
        return bundleOf(
            WebDetailFragment.WEB_TITLE to data.name,
            WebDetailFragment.WEB_LINK to data.url
        )
    }

    fun decodeDetailData(json: String?) {
        json?.let {
            val jsonData = Json.decodeFromString<AttractionsData>(it)
            detailData.value = jsonData
        }
    }
}