package com.core.helper.mup.comic.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class Comic(

        @SerializedName("createdBy")
        @Expose
        val createdBy: String? = null,

        @SerializedName("createdDate")
        @Expose
        val createdDate: String? = null,

        @SerializedName("description")
        @Expose
        val description: String? = null,

        @SerializedName("id")
        @Expose
        val id: String? = null,

        @SerializedName("imageId")
        @Expose
        val imageId: String? = null,

        @SerializedName("imageSrc")
        @Expose
        val imageSrc: String? = null,

        @SerializedName("isDelete")
        @Expose
        val isDelete: Boolean? = null,

        @SerializedName("modifiedBy")
        @Expose
        val modifiedBy: String? = null,

        @SerializedName("modifiedDate")
        @Expose
        val modifiedDate: String? = null,

        @SerializedName("title")
        @Expose
        val title: String? = null,

        @SerializedName("totalChapter")
        @Expose
        val totalChapter: Int? = null,

        @SerializedName("viewCount")
        @Expose
        val viewCount: Int? = null
) : Serializable {

//    fun getImageSrc(): String {
//        return if (imageSrc?.contains("http:/") == true) {
//            imageSrc
//        } else {
//            "http:/$imageSrc"
//        }
//    }
}
