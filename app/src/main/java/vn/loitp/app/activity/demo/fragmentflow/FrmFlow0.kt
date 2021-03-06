package vn.loitp.app.activity.demo.fragmentflow

import android.os.Bundle
import android.view.View
import com.annotation.LogTag
import kotlinx.android.synthetic.main.frm_demo_flow_0.*
import vn.loitp.app.R

@LogTag("FrmFlow0")
class FrmFlow0 : FrmFlowBase() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_demo_flow_0
    }

    override fun onBackClick(): Boolean {
        print("onBackClick")
        popThisFragment()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        print("onViewCreated")
        bt.setOnClickListener {
            if (activity is FragmentFlowActivity) {
                (activity as FragmentFlowActivity).showFragment(FrmFlow1())
            }
        }
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        print("onFragmentResume")
    }

    private fun print(msg: String) {
        if (activity is FragmentFlowActivity) {
            (activity as FragmentFlowActivity).print("FrmFlow0: $msg")
        }
    }
}
