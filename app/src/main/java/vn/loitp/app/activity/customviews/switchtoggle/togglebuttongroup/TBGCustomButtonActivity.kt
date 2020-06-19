package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup.OnCheckedStateChangeListener
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup
import kotlinx.android.synthetic.main.activity_switch_tbg_custom_button.*
import vn.loitp.app.R

class TBGCustomButtonActivity : BaseFontActivity() {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_tbg_custom_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        groupSingleRadioButton.setOnCheckedChangeListener(SingleSelectListener())
        groupMultiCustomCompoundButton.setOnCheckedChangeListener(MultiSelectListener())
        groupMultiCustomToggleButton.setOnCheckedChangeListener(MultiSelectListener())
        groupSingleCustomCompoundToggleButton.setOnCheckedChangeListener(SingleSelectListener())
    }

    private class SingleSelectListener : SingleSelectToggleGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: SingleSelectToggleGroup, checkedId: Int) {
            LLog.d("SingleSelectListener", "onCheckedChanged(): $checkedId")
        }
    }

    private class MultiSelectListener : OnCheckedStateChangeListener {
        override fun onCheckedStateChanged(group: MultiSelectToggleGroup, checkedId: Int, isChecked: Boolean) {
            LLog.d("MultiSelectListener", "onCheckedStateChanged(): $checkedId, isChecked = $isChecked")
        }
    }
}
