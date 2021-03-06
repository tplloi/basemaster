package vn.loitp.app.activity.customviews.edittext.ledittext

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAppResource
import com.core.utilities.LKeyBoardUtil
import com.core.utilities.LScreenUtil
import com.views.edittext.leditext.LEditText
import kotlinx.android.synthetic.main.activity_editext_l_edit_text.*
import vn.loitp.app.R

@LogTag("LEditTextActivity")
@IsFullScreen(false)
class LEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_l_edit_text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lEditTextId.apply {
            colorFocus = LAppResource.getColor(R.color.black)
            colorUnfocus = LAppResource.getColor(R.color.blue)
            colorError = LAppResource.getColor(R.color.red)
            ivLeft.setImageResource(R.mipmap.ic_launcher)
            ivRight.setImageResource(R.drawable.ic_close_black_48dp)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Account"
            setMaxLines(1)
            setWidthRootView(LScreenUtil.screenWidth * 3 / 4)
            setHeightRootView(350)
            //disableEditing()
            setInputType(InputType.TYPE_CLASS_TEXT)
            setImeiActionEditText(EditorInfo.IME_ACTION_NEXT, Runnable {
                lEditTextPw.editText.requestFocus()
            })
            callback = object : LEditText.Callback {
                override fun onClickIvRight(imageView: ImageView) {
                    setText("")
                }

                override fun onTextChanged(s: String) {
                    if (s.isEmpty()) {
                        ivRight.visibility = View.GONE
                    } else {
                        ivRight.visibility = View.VISIBLE
                    }
                    hideMessage()
                }

                override fun setOnFocusChangeListener(isFocus: Boolean) {
                    logD("setOnFocusChangeListener isFocus: $isFocus")
                }

            }
        }
        var isShowPw = false
        lEditTextPw.apply {
            colorFocus = LAppResource.getColor(R.color.black)
            colorUnfocus = LAppResource.getColor(R.color.blue)
            colorError = LAppResource.getColor(R.color.red)
            ivLeft.setImageResource(R.mipmap.ic_launcher)
            ivRight.setImageResource(R.drawable.ic_visibility_black_48dp)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Password"
            setMaxLines(1)
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setImeiActionEditText(EditorInfo.IME_ACTION_DONE, Runnable {
                LKeyBoardUtil.hide(this@LEditTextActivity)
            })
            callback = object : LEditText.Callback {
                override fun onClickIvRight(imageView: ImageView) {
                    if (isShowPw) {
                        ivRight.setImageResource(R.drawable.ic_visibility_black_48dp)
                        editText.transformationMethod = PasswordTransformationMethod.getInstance()
                        setLastCursorEditText()
                        isShowPw = false
                    } else {
                        ivRight.setImageResource(R.drawable.ic_visibility_off_black_48dp)
                        editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        setLastCursorEditText()
                        isShowPw = true
                    }
                }

                override fun onTextChanged(s: String) {
                    if (s.isEmpty()) {
                        ivRight.visibility = View.GONE
                    } else {
                        ivRight.visibility = View.VISIBLE
                    }
                    hideMessage()
                }

                override fun setOnFocusChangeListener(isFocus: Boolean) {
                    logD("setOnFocusChangeListener isFocus: $isFocus")
                }

            }
        }

        btLogin.setOnClickListener {
            val id = lEditTextId.editText.text.toString()
            val pw = lEditTextPw.editText.text.toString()
            var isCorrectId = false
            var isCorrectPw = false

            if (id == "loitp") {
                isCorrectId = true
            } else {
                lEditTextId.showMessage("Wrong id!!!")
            }
            if (pw == "123456789") {
                isCorrectPw = true
            } else {
                lEditTextPw.showMessage("Wrong pw!!!")
            }
            if (isCorrectId && isCorrectPw) {
                showShortInformation("Correct!!!")
            }
        }
    }

}
