package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("id")
        val id: Int = 0,

        @SerializedName("name")
        val name: String = "",

        @SerializedName("avatar")
        val avatar: String = ""
)