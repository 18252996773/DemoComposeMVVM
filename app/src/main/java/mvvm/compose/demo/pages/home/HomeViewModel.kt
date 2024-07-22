package mvvm.compose.demo.pages.home

import androidx.compose.runtime.mutableStateOf
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.ViewModel
import mvvm.compose.demo.app.LangResources
import mvvm.compose.demo.pages.attractions.AttractionsViewModel
import mvvm.compose.demo.pages.news.NewsViewModel

class HomeViewModel : ViewModel() {
    val visibilityNewsList = mutableStateOf(Visibility.Visible)
    val visibilityAttractionsList = mutableStateOf(Visibility.Gone)
    val visibilityLanguageDialog = mutableStateOf(false)

    val newsViewModel = NewsViewModel()
    val attractionsViewModel = AttractionsViewModel()

    fun settingLanguage(language: String) {
        LangResources.language = language
    }

    fun refresh() {
        newsViewModel.getNewsModel()
        attractionsViewModel.getAttractionsModel()
    }
}