package vn.loitp.app.activity.customviews.layout.draggablepanel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.frm_test.*
import vn.loitp.app.R

class FrmTestBottom : Fragment() {

    companion object {
        fun newInstance(): FrmTestBottom {
            return FrmTestBottom()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frm_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView.setImageResource(R.drawable.iv)
    }

}
