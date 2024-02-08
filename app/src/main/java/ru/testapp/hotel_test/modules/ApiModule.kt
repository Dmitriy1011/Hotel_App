package ru.testapp.hotel_test.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.testapp.hotel_test.BuildConfig
import ru.testapp.hotel_test.api.HotelApiService
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()

    @Provides
    @Singleton
    fun provideRetrofitForHotel(
        okhttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttpClient)
        .build()


    @Provides
    @Singleton
    fun providesApiService(
        retrofit: Retrofit
    ): HotelApiService = retrofit.create()
}