package com.learning.baseprojectforkan.common.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.learning.baseprojectforkan.BuildConfig
import com.learning.baseprojectforkan.common.data.remote.api.ApiHelper
import com.learning.baseprojectforkan.common.data.remote.api.ApiHelperImpl
import com.learning.baseprojectforkan.common.data.remote.api.ApiService
import com.learning.baseprojectforkan.common.utils.Constants.Companion.BASE_URL
import com.learning.baseprojectforkan.common.utils.NetworkHelper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**Here we created the functions we want to provide as dependencies.*/

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)


private fun provideOkHttpClientWithChucker(context: Context) = if (BuildConfig.DEBUG) {
    //start-> this snippet of code for chucker
    val chuckerInterceptor = ChuckerInterceptor.Builder(context).apply {
        maxContentLength(10000)
    }.build() //end

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chuckerInterceptor)//added chucker interceptor
        .build()
} else OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)


/**inside the module, we will pass the single instance of all the functions we created
 * Here, to provide the dependency as a singleton instance we use single{} and inside it,
 * we will pass the function which will return the dependency as an instance to be used across the app.
 * We are using get() here to pass the dependency to the constructor.
 * Using get it will only provide the constructor whose instance is already been provided by Koin.*/

val appModule = module {
    single { provideOkHttpClientWithChucker(androidContext()) }
    single { provideRetrofit(get(), BASE_URL = BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }
    /**Here, we are providing a type if ApiHelper, and returning ApiHelperImpl and in ApiHelperImpl
     * it takes ApiService as a constructor parameter which we are already providing from provideApiService function.*/
    single<ApiHelper> {
        return@single ApiHelperImpl(get())
    }
}