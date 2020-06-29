package vn.loitp.app.activity.customviews.switchtoggle.toggle

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.views.switchtoggle.toggle.LabeledSwitch
import com.views.switchtoggle.toggle.interfaces.OnToggledListener
import kotlinx.android.synthetic.main.activity_switch_toggle.*
import vn.loitp.app.R

//https://github.com/Angads25/android-toggle?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=6778
class ToggleActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        labeledSwitch.setOnToggledListener(object : OnToggledListener {
            override fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean) {
                showShort("isOn $isOn")
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_toggle
    }
}