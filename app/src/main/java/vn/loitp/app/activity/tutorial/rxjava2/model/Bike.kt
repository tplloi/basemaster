package vn.loitp.app.activity.tutorial.rxjava2.model

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class Bike : BaseModel {
    var name: String? = null
    var model: String? = null
    var price: Int = 0

    constructor(n: String?, m: String?) {
        name = n
        model = m
    }

    constructor(n: String?, m: String?, p: Int) {
        name = n
        model = m
        price = p
    }

    constructor()
}
