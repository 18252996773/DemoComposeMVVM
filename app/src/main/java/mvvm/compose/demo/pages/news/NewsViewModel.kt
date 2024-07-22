package mvvm.compose.demo.pages.news

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import mvvm.compose.demo.app.AttractionsData
import mvvm.compose.demo.app.NewsData
import mvvm.compose.demo.app.RemoteAPI
import mvvm.compose.demo.pages.attractions.WebDetailFragment

class NewsViewModel : ViewModel() {

    val listData = mutableStateOf<List<NewsData>>(emptyList())
    val detailData = mutableStateOf<NewsData>(NewsData())

    fun getNewsModel() {
        viewModelScope.launch {
            val model = RemoteAPI.getNewsModel()
            listData.value = model.data
        }
    }

    fun setDetailData(index: Int) {
        detailData.value = listData.value[index]
    }

    fun bundleWebDetail(data: AttractionsData): Bundle {
        return bundleOf(
            WebDetailFragment.WEB_TITLE to data.name,
            WebDetailFragment.WEB_LINK to data.url
        )
    }

    fun decodeDetailData(json: String?) {
        json?.let {
            val jsonData = Json.decodeFromString<NewsData>(it)
            detailData.value = jsonData
        }
    }

}