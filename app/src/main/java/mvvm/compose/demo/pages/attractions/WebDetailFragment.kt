package mvvm.compose.demo.pages.attractions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import mvvm.compose.demo.R

class WebDetailFragment : Fragment() {
    private val attractionsViewModel by viewModels<AttractionsViewModel>()

    companion object {
        const val WEB_TITLE = "WEB_TITLE"
        const val WEB_LINK = "WEB_LINK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString(WEB_TITLE)?.let { attractionsViewModel.webTitle.value = it }
        arguments?.getString(WEB_LINK)?.let { attractionsViewModel.webLink.value = it }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val activity = LocalContext.current as? AppCompatActivity ?: return@setContent
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
                                        .popBackStack(R.id.WebDetailFragment, true)
                                }
                                .constrainAs(createRefs().component1()) {
                                    centerVerticallyTo(parent)
                                }
                                .padding(10.dp),
                        )
                        Text(
                            text = attractionsViewModel.webTitle.value,
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
                        AndroidView(modifier = Modifier, factory = {
                            WebView(it).apply {
                                settings.javaScriptEnabled = true
                                webViewClient = WebViewClient()
                                settings.loadWithOverviewMode = true
                                settings.useWideViewPort = true
                                settings.setSupportZoom(true)
                            }
                        }, update = {
                            val mUrl = attractionsViewModel.webLink.value
                            it.loadUrl(mUrl)
                        })
                    }
                }
            }
        }
    }
}