package vn.loitp.app.activity.api.retrofit2

import retrofit2.Call
import retrofit2.http.GET
import vn.loitp.app.activity.api.retrofit2.model.SOAnswersResponse

interface SOService {
    @get:GET("/answers?order=desc&sort=activity&site=stackoverflow")
    val answers: Call<SOAnswersResponse>
}
