package mvvm.compose.demo.app

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

object LangResources {
    val res = mutableStateOf<Resources?>(null)
    var language = "zh-tw"
    fun getResString(id: Int): String {
        val str = res.value?.let {
            it.getString(id)
        } ?: ""
        return str
    }
    fun setLocalizedResources(context: Context, desiredLocale: Locale?) {
        var conf: Configuration = context.resources.configuration
        conf = Configuration(conf)
        conf.setLocale(desiredLocale)
        val localizedContext = context.createConfigurationContext(conf)
        res.value = localizedContext.resources
    }
}