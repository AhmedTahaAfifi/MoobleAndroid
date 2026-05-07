package com.example.moobleandroid.di

import androidx.room.Room
import com.example.moobleandroid.data.api.MoodleApiService
import com.example.moobleandroid.data.local.MoodleDatabase
import com.example.moobleandroid.data.repository.AuthRepository
import com.example.moobleandroid.data.repository.CourseRepository
import com.example.moobleandroid.data.repository.GradeRepository
import com.example.moobleandroid.presentation.viewmodel.course.CourseViewModel
import com.example.moobleandroid.presentation.viewmodel.coursedetails.CourseDetailViewModel
import com.example.moobleandroid.presentation.viewmodel.grades.GradeViewModel
import com.example.moobleandroid.presentation.viewmodel.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://moodle.itcorner.qzz.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create(MoodleApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            MoodleDatabase::class.java,
            "moodle_db"
        ).build()
    }

    single { get<MoodleDatabase>().userDao() }

    singleOf(::AuthRepository)
    singleOf(::CourseRepository)
    singleOf(::GradeRepository)

    viewModelOf(::LoginViewModel)
    viewModelOf(::CourseViewModel)
    viewModelOf(::CourseDetailViewModel)
    viewModelOf(::GradeViewModel)
}
