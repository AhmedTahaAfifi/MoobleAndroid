package com.example.moobleandroid.data.api

import com.google.gson.annotations.SerializedName

data class TokenResponse(
    @SerializedName("token")
    val token: String?,
    @SerializedName("error")
    val error: String?,
)

data class UserInfoResponse(
    @SerializedName("userid")
    val userId: Int,
)

data class CourseResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("shortname")
    val shortName: String,
    @SerializedName("fullname")
    val fullName: String,
    @SerializedName("displayname")
    val displayName: String,
    @SerializedName("progress")
    val progress: Float? = null,
    @SerializedName("courseimage")
    val courseImage: String? = null
)

data class CourseContentResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("visible")
    val visible: Int?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("modules")
    val modules: List<ModuleResponse>
)

data class ModuleResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("modname")
    val modName: String,
    @SerializedName("modicon")
    val modIcon: String?,
)

data class GradeReportResponse(
    @SerializedName("usergrades")
    val userGrades: List<UserGrade>? = null
)

data class UserGrade(
    @SerializedName("courseid")
    val courseId: Int,
    @SerializedName("gradeitems")
    val gradeItems: List<GradeItem>? = null
)

data class GradeItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("itemname")
    val itemName: String?,
    @SerializedName("itemtype")
    val itemType: String?,
    @SerializedName("gradeformatted")
    val gradeFormatted: String?,
    @SerializedName("graderaw")
    val rawGrade: Double?,
    @SerializedName("grademax")
    val gradeMax: Double?,
    @SerializedName("grademin")
    val gradeMin: Double?,
    @SerializedName("percentageformatted")
    val percentageFormatted: String?
)
