package vn.loitp.app.activity.customviews.imageview.pinchtozoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.fragment_iv.*
import loitp.basemaster.R

class FrmIvPinchToZoom : Fragment() {

    companion object {
        const val KEY_URL = "KEY_URL"
    }

    private var urlIv: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_iv_pinch_to_zoom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urlIv = arguments?.getString(KEY_URL)
        urlIv?.let { u ->
            LImageUtil.load(view.context, u, iv)
            LUIUtil.setImageViewZoom(iv)
        }
    }
}