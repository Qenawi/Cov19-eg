package q.tjw.cov19_eg.map.di.app

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import q.tjw.cov19_eg.map.data_layer.reprository.ClientApiServices
import q.tjw.cov19_eg.utilities.RemoteConfiguration.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {
    private var retrofit: Retrofit? = null
    private var apiServices: ClientApiServices? = null
    @Singleton
    @Provides
    fun provideApiService(): ClientApiServices {
        if (apiServices == null) apiServices = provideRetrofit().create(
            ClientApiServices::class.java
        )
        return apiServices as ClientApiServices
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit { // RemoteConfiguration.updateBaseURL(ChangeAPIServer.NONE);
        if (retrofit == null) {
            var client = OkHttpClient()
            client = OkHttpClient.Builder()
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
        return retrofit as Retrofit
    }
}