package mvvm.compose.demo.pages.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import coil.compose.rememberAsyncImagePainter
import mvvm.compose.demo.R

class AttractionsDetailFragment : Fragment() {
    private val attractionsViewModel by viewModels<AttractionsViewModel>()

    companion object {
        const val DETAIL_DATA = "DETAIL_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        attractionsViewModel.decodeDetailData(arguments?.getString(DETAIL_DATA))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DetailCompose()
            }
        }
    }

    @Composable
    fun DetailCompose() {
        val activity = LocalContext.current as? AppCompatActivity ?: return
        val data = remember { attractionsViewModel.detailData }
        Column {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = "<",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            Navigation
                                .findNavController(activity, R.id.fragment_nav_host)
                                .popBackStack(R.id.AttractionsDetailFragment, true)
                        }
                        .constrainAs(createRefs().component1()) {
                            centerVerticallyTo(parent)
                        }
                        .padding(10.dp),
                )
                Text(
                    text = data.value.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(30.dp, 0.dp, 30.dp, 0.dp)
                        .constrainAs(createRefs().component2()) {
                            centerTo(parent)
                        },
                )
            }
            Column(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .verticalScroll(rememberScrollState())
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .border(width = 2.dp, color = Color.LightGray)
                        .padding(3.dp)
                ) {
                    val images = data.value.images
                    val pagerState = rememberPagerState(pageCount = { images.size })
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) { page ->
                        Image(
                            painter = rememberAsyncImagePainter(images[page].src),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )
                    }
                }
                Text(
                    text = "${data.value.introduction}",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .border(width = 2.dp, color = Color.LightGray)
                        .padding(10.dp)
                )
                Text(
                    text = "${data.value.url}",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable {
                            Navigation
                                .findNavController(activity, R.id.fragment_nav_host)
                                .navigate(
                                    R.id.WebDetailFragment,
                                    attractionsViewModel.bundleWebDetail(data.value)
                                )
                        }
                        .padding(5.dp)
                        .fillMaxWidth()
                        .border(width = 2.dp, color = Color.LightGray)
                        .padding(10.dp)
                )
            }
        }
    }

    @Preview
    @Composable
    fun DetailPreview() {
        DetailCompose()
    }
}