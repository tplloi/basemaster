package vn.loitp.app.activity.customviews.dialog.originaldialog

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import kotlinx.android.synthetic.main.activity_dialog_original.*
import vn.loitp.app.R

@LogTag("DialogOriginalActivity")
@IsFullScreen(false)
class DialogOriginalActivity : BaseFontActivity(), OnClickListener {
    private var testRun: TestRun? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_original
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShowList.setOnClickListener(this)
        btProgressDialog.setOnClickListener(this)
        btInputDialog.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        testRun?.cancel(true)
    }

    override fun onClick(v: View) {
        when (v) {
            btShow1 -> show1()
            btShow2 -> show2()
            btShow3 -> show3()
            btShowList -> showList()
            btProgressDialog -> showProgress()
            btInputDialog -> {
                showInputDialog()
            }
        }
    }

    private fun show1() {
        LDialogUtil.showDialog1(
                context = this,
                title = "Title",
                msg = "Msg",
                button1 = "Button 1",
                onClickButton1 = {
                    showShortInformation("Click 1")
                }
        )
    }

    private fun show2() {
        LDialogUtil.showDialog2(
                context = this,
                title = "Title",
                msg = "Msg",
                button1 = "Button 1",
                button2 = "Button 2",
                onClickButton1 = {
                    showShortInformation("Click 1")
                },
                onClickButton2 = {
                    showShortInformation("Click 2")
                }
        )
    }

    private fun show3() {
        LDialogUtil.showDialog3(
                context = this,
                title = "Title",
                msg = "Msg",
                button1 = "Button 1",
                button2 = "Button 2",
                button3 = "Button 3",
                onClickButton1 = {
                    showShortInformation("Click 1")
                },
                onClickButton2 = {
                    showShortInformation("Click 2")
                },
                onClickButton3 = {
                    showShortInformation("Click 3")
                }
        )
    }

    private fun showList() {
        val size = 50
        val arr = arrayOfNulls<String?>(size)
        for (i in 0 until size) {
            arr[i] = "Item $i"
        }
        LDialogUtil.showDialogList(
                context = this,
                title = "Title",
                arr = arr,
                onClick = { position ->
                    showShortInformation("Click position " + position + ", item: " + arr[position])
                }
        )
    }

    private fun showProgress() {
        testRun?.cancel(true)
        testRun = TestRun(this)
        testRun?.execute()
    }

    private class TestRun(private val context: Context) : AsyncTask<Void, Int, Void>() {
        private var progressDialog: ProgressDialog? = null

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = LDialogUtil.showProgressDialog(
                    context = context,
                    max = 100,
                    title = "Title",
                    msg = "Message",
                    isCancelAble = false,
                    style = ProgressDialog.STYLE_HORIZONTAL,
                    buttonTitle = null
            )
        }

        override fun doInBackground(vararg voids: Void): Void? {
            progressDialog?.max?.let {
                for (i in 0 until it) {
                    publishProgress(i)
                    try {
                        Thread.sleep(25)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                progressDialog?.progress = it
            }
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            progressDialog?.dismiss()
        }
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Title")

        val editText = EditText(this)
        //input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(editText)

        builder.setPositiveButton("OK") { _, _ ->
            val text = editText.text.toString()
            showShortInformation("Text $text")
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        val dialog = builder.create()
        dialog.show()
    }
}
