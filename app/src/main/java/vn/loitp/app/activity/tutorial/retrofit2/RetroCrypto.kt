package vn.loitp.app.activity.tutorial.retrofit2

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class RetroCrypto : BaseModel() {
    @SerializedName("currency")
    @Expose
    var currency: String? = null

    @SerializedName("price")
    @Expose
    var price: String? = null
}
