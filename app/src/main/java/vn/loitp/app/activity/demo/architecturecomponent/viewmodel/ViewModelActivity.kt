package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LDateUtil
import kotlinx.android.synthetic.main.activity_demo_view_model.*
import vn.loitp.app.R
import vn.loitp.app.activity.pattern.mvp.User
import java.util.*

//https://codinginfinite.com/architecture-component-viewmodel-example/

@LogTag("ViewModelActivity")
@IsFullScreen(false)
class ViewModelActivity : BaseFontActivity() {

    private lateinit var colorChangerViewModel: ColorChangerViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var timeChangerViewModel: TimeChangerViewModel

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_view_model
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        colorChangerViewModel = ViewModelProvider(this).get(ColorChangerViewModel::class.java)
        colorChangerViewModel.colorResource.observe(this, Observer {
            ll.setBackgroundColor(it)
        })
        btChangeColor.setOnClickListener {
            colorChangerViewModel.colorResource.value = generateRandomColor()
        }

        val defUser = User()
        defUser.fullName = "Loitp"
        defUser.email = "www.muathu@gmail.com"
        val factory = UserViewModel.CustomViewModelFactory(defUser)
        userViewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        textView.text = BaseApplication.gson.toJson(userViewModel.mUser)
        btChangeUser.setOnClickListener {
            val user = User()
            user.fullName = "Loitp" + System.currentTimeMillis()
            user.email = "www.muathu@gmail.com" + System.currentTimeMillis()
            userViewModel.mUser = user
            textView.text = BaseApplication.gson.toJson(userViewModel.mUser)
        }

        timeChangerViewModel = ViewModelProvider(this).get(TimeChangerViewModel::class.java)
        var countToStop = 0
        timeChangerViewModel.timerValue.observe(this, Observer {
            countToStop++
            logD("countToStop $countToStop")
            if (countToStop >= 15) {
                logD("countToStop $countToStop -> STOP")
                tvTime.text = "countToStop: $countToStop -> STOP"
                timeChangerViewModel.timerValue.removeObservers(this)
                return@Observer
            }
            tvTime.text = "countToStop: $countToStop -> $it -> " + LDateUtil.getDateCurrentTimeZoneMls(it, "yyyy-MM-dd HH:mm:ss")
        })
    }

    private fun generateRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

}
