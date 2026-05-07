package com.example.moobleandroid.data.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MoodleApiService {

    @FormUrlEncoded
    @POST("login/token.php")
    fun login(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("service") service: String = "moodle_mobile_app"
    ): Call<TokenResponse>

    @FormUrlEncoded
    @POST("webservice/rest/server.php")
    fun getUserInfo(
        @Field("wstoken") token: String,
        @Field("wsfunction") function: String = "core_webservice_get_site_info",
        @Field("moodlewsrestformat") format: String = "json"
    ): Call<UserInfoResponse>

    @FormUrlEncoded
    @POST("webservice/rest/server.php")
    fun getEnrolledCourses(
        @Field("wstoken") token: String,
        @Field("userid") userId: Int,
        @Field("wsfunction") function: String = "core_enrol_get_users_courses",
        @Field("moodlewsrestformat") format: String = "json"
    ): Call<List<CourseResponse>>

    @FormUrlEncoded
    @POST("webservice/rest/server.php")
    fun getCourseContents(
        @Field("wstoken") token: String,
        @Field("courseid") courseId: Int,
        @Field("wsfunction") function: String = "core_course_get_contents",
        @Field("moodlewsrestformat") format: String = "json"
    ): Call<List<CourseContentResponse>>

    @FormUrlEncoded
    @POST("webservice/rest/server.php")
    fun getUserGrades(
        @Field("wstoken") token: String,
        @Field("userid") userId: Int,
        @Field("wsfunction") function: String = "gradereport_user_get_grade_items",
        @Field("moodlewsrestformat") format: String = "json"
    ): Call<GradeReportResponse>
}