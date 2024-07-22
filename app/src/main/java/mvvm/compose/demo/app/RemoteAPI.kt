package mvvm.compose.demo.app

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

object RemoteAPI {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.travel.taipei/open-api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(ApiService::class.java)

    suspend fun getNewsModel(): NewsModel {
        return withContext(Dispatchers.IO) {
            apiService.getNewsModel(LangResources.language)
        }
    }

    suspend fun getAttractionsModel(): AttractionsModel {
        return withContext(Dispatchers.IO) {
            apiService.getAttractionsModel(LangResources.language)
        }
    }
}

interface ApiService {
    @Headers("accept: application/json")
    @GET("{lang}/Events/News")
    suspend fun getNewsModel(@Path("lang") lang: String): NewsModel

    @Headers("accept: application/json")
    @GET("{lang}/Attractions/All")
    suspend fun getAttractionsModel(@Path("lang") lang: String): AttractionsModel
}