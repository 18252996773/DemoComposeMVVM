import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import mvvm.compose.demo.R
import mvvm.compose.demo.pages.attractions.WebDetailFragment
import mvvm.compose.demo.pages.news.NewsViewModel

@Composable
fun NewsListCompose(newsViewModel: NewsViewModel) {
    val data = remember { newsViewModel.listData }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        itemsIndexed(data.value) { index, item ->
            NewsItem(newsViewModel, index)
        }
    }
}

@Composable
fun NewsItem(newsViewModel: NewsViewModel, index: Int) {
    val detail = newsViewModel.listData.value[index]
    val activity = LocalContext.current as? AppCompatActivity ?: return
    Column(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(width = 2.dp, color = Color.LightGray)
        .padding(10.dp)
        .clickable {
            Navigation
                .findNavController(activity, R.id.fragment_nav_host)
                .navigate(
                    R.id.WebDetailFragment,
                    bundleOf(
                        WebDetailFragment.WEB_TITLE to detail.title,
                        WebDetailFragment.WEB_LINK to detail.url
                    )
                )
        }) {
        Text(
            text = detail.title,
            fontSize = 18.sp,
            maxLines = 1,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = detail.description,
            fontSize = 16.sp,
            maxLines = 3,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun NewsListComposePreview() {
    NewsListCompose(NewsViewModel())
}