package mvvm.compose.demo.pages.home

import AttractionsListCompose
import NewsListCompose
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import mvvm.compose.demo.R
import mvvm.compose.demo.app.LangResources
import mvvm.compose.demo.app.LangResources.getResString
import java.util.Locale

class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.refresh()
        context?.let { LangResources.setLocalizedResources(it, Locale.TAIWAN) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HomePageCompose(homeViewModel)
                LanguageDialog(homeViewModel)
            }
        }
    }

    @Composable
    fun HomePageCompose(homeViewModel: HomeViewModel) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (topBar, newsList, attractionsList, bottomTab) = createRefs()
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White)
                    .constrainAs(topBar) {
                        top.linkTo(parent.top)
                    }
            ) {
                val (title, language) = createRefs()
                Text(
                    text = getResString(R.string.app_name),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(title) {
                        centerTo(parent)
                    },
                )
                Text(
                    text = getResString(R.string.language),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            homeViewModel.visibilityLanguageDialog.value = true
                        }
                        .padding(10.dp)
                        .constrainAs(language) {
                            centerVerticallyTo(parent)
                            end.linkTo(parent.end)
                        },
                )
            }
            val visibilityNewsList = remember { homeViewModel.visibilityNewsList }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red)
                    .constrainAs(newsList) {
                        top.linkTo(topBar.bottom)
                        bottom.linkTo(bottomTab.top)
                        height = Dimension.fillToConstraints
                        visibility = visibilityNewsList.value
                    }
            ) {
                NewsListCompose(homeViewModel.newsViewModel)
            }
            val visibilityAttractionsList = remember { homeViewModel.visibilityAttractionsList }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
                    .constrainAs(attractionsList) {
                        top.linkTo(topBar.bottom)
                        bottom.linkTo(bottomTab.top)
                        height = Dimension.fillToConstraints
                        visibility = visibilityAttractionsList.value
                    }
            ) {
                AttractionsListCompose(homeViewModel.attractionsViewModel)
            }
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.LightGray)
                    .border(width = 2.dp, color = Color.Blue)
                    .constrainAs(bottomTab) {
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                val (newsTab, centerLine, attractionsTab) = createRefs()
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clickable {
                            homeViewModel.visibilityNewsList.value = Visibility.Visible
                            homeViewModel.visibilityAttractionsList.value = Visibility.Gone
                        }
                        .constrainAs(newsTab) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(centerLine.start)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }) {
                    Text(
                        text = getResString(R.string.news),
                        color = Color.Blue,
                        fontSize = 20.sp,
                        fontWeight = if (visibilityNewsList.value == Visibility.Visible) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier,
                    )
                }
                Spacer(modifier = Modifier
                    .padding(5.dp)
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(Color.Blue)
                    .constrainAs(centerLine) {
                        centerHorizontallyTo(parent)
                    })
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clickable {
                            homeViewModel.visibilityNewsList.value = Visibility.Gone
                            homeViewModel.visibilityAttractionsList.value = Visibility.Visible
                        }
                        .constrainAs(attractionsTab) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(centerLine.end)
                            end.linkTo(parent.end)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                ) {
                    Text(
                        text = getResString(R.string.attractions),
                        color = Color.Blue,
                        fontSize = 20.sp,
                        fontWeight = if (visibilityAttractionsList.value == Visibility.Visible) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier,
                    )
                }
            }
        }
    }

    @Composable
    fun LanguageDialog(homeViewModel: HomeViewModel) {
        var showDialog = remember { homeViewModel.visibilityLanguageDialog }
        if (!showDialog.value) return
        val dismiss = { homeViewModel.visibilityLanguageDialog.value = false }
        val activity = LocalContext.current as? AppCompatActivity ?: return
        Dialog(onDismissRequest = dismiss) {
            Card {
                Column(
                    modifier = Modifier
                        .padding(30.dp)
                ) {
//                    Text(text = "選擇語言", fontSize = 18.sp)
//                    Spacer(modifier = Modifier.height(8.dp))
                    val localList =
                        arrayOf(Locale.TAIWAN, Locale.SIMPLIFIED_CHINESE, Locale.ENGLISH)
                    // "ja", "ko", "es", "id", "th", "vi"
                    val langList = arrayOf("zh-tw", "zh-cn", "en")
                    // "日文", "韓文", "西班牙文", "印尼文", "泰文", "越南文"
                    val titleList = arrayOf("正體中文", "簡體中文", "英文")
                    langList.forEachIndexed { index, lang ->
                        Button(onClick = {
                            LangResources.setLocalizedResources(
                                activity.baseContext,
                                localList[index]
                            )
                            homeViewModel.settingLanguage(lang)
                            homeViewModel.refresh()
                            dismiss.invoke()
                        }) {
                            Text(titleList[index])
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun HomePageComposePreview() {
        HomePageCompose(HomeViewModel())
    }
}