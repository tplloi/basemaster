package com.core.helper.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.common.Constants
import com.views.textview.textdecorator.LTextDecorator
import kotlinx.android.synthetic.main.l_frm_donate.*

@LogTag("FrmDonate")
class FrmDonate : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        frmRootView = inflater.inflate(R.layout.l_frm_donate, container, false)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val text = Constants.DONATION_INFOR_LOITP

        LTextDecorator
                .decorate(textView, text)
                .setTextColor(R.color.red,
                        "❤ Vietcombank", "0371000106443",
                        "❤ Techcombank", "19034585806016",
                        "❤ Viet Capital Bank - Ngân hàng TMCP Bản Việt", "8007041105519",
                        "❤ VPBank", "166210585",
                        "❤ Momo", "0764088864")
                //.setBackgroundColor(R.color.colorPrimary, "dolor", "elit")
                //.strikethrough("Duis", "Praesent")
                .underline("Chân thành cảm ơn!")
                //.setSubscript("vitae")
                //.makeTextClickable(new OnTextClickListener() {
                //    @Override
                //    public void onClick(View view, String text) {
                //
                //    }
                //}, false, "porta", "commodo", "tempor venenatis nulla")
                //.setTextColor(android.R.color.holo_green_light, "porta", "commodo", "tempor venenatis nulla")
                .build()
    }
}
