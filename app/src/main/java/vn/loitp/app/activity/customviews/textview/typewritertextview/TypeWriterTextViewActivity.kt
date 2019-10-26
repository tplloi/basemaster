package vn.loitp.app.activity.customviews.textview.typewritertextview

import android.os.Bundle
import android.view.View
import android.widget.Button

import com.core.base.BaseFontActivity
import com.views.textview.typewriter.LTypeWriterTextView

import loitp.basemaster.R

class TypeWriterTextViewActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val btn = findViewById<Button>(R.id.btn)
        val tv = findViewById<LTypeWriterTextView>(R.id.tv)
        btn.setOnClickListener { v ->
            tv.text = ""
            tv.setCharacterDelay(150)
            tv.animateText("Type Writer Effect")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_type_writer_textview
    }

}