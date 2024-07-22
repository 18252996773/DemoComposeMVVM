import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.Navigation
import coil.compose.rememberAsyncImagePainter
import mvvm.compose.demo.R
import mvvm.compose.demo.pages.attractions.AttractionsViewModel


@Composable
fun AttractionsListCompose(attractionsViewModel: AttractionsViewModel) {
    val data = remember { attractionsViewModel.listData }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        itemsIndexed(data.value) { index, item ->
            attractionsViewModel.setDetailData(index)
            AttractionsItem(attractionsViewModel, index)
        }
    }
}

@Composable
fun AttractionsItem(attractionsViewModel: AttractionsViewModel, index: Int) {
    val detail = attractionsViewModel.listData.value[index]
    val activity = LocalContext.current as? AppCompatActivity ?: return
    ConstraintLayout(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(width = 2.dp, color = Color.LightGray)
        .padding(10.dp)
        .clickable {
            Navigation
                .findNavController(activity, R.id.fragment_nav_host)
                .navigate(
                    R.id.AttractionsDetailFragment,
                    attractionsViewModel.bundleDetailData(detail)
                )
        }) {
        val (image, title, introduction) = createRefs()
        if (detail.images.isEmpty()) {
            Spacer(modifier = Modifier.constrainAs(image) {
                start.linkTo(parent.start)
            })
        } else {
            Image(
                painter = rememberAsyncImagePainter(detail.images[0].src),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

        }
        Text(text = detail.name,
            fontSize = 18.sp,
            maxLines = 1,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(title) {
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
        Text(
            text = detail.introduction,
            fontSize = 16.sp,
            maxLines = 5,
            color = Color.LightGray,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(introduction) {
                    top.linkTo(title.bottom)
                    start.linkTo(image.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
    }
}

@Preview
@Composable
fun AttractionsListComposePreview() {
    AttractionsListCompose(AttractionsViewModel())
}