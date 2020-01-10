package vn.loitp.app.activity.demo.architecturecomponent.coroutine

import com.core.utilities.LLog
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Loitp on 10,January,2020
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
class CoroutineTask : CoroutineScope {
    private val TAG = javaClass.simpleName
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    fun cancel() {
        job.cancel()
        LLog.d(TAG, "cancel " + job.isCancelled)
    }

    fun startTask() = launch {
        LLog.d(TAG, "startTask")
        val result = doSomeBackgroundWork() // runs in background thread without blocking the Main Thread
        doUiStuff(result)
    }

    private suspend fun doSomeBackgroundWork(): String = withContext(Dispatchers.IO) {
        // to run code in Background Thread
        // do async work
        delay(5000) // simulate async work
        return@withContext "SomeResult ${System.currentTimeMillis()}"
    }

    // Runs in Main(UI) Thread
    private fun doUiStuff(result: String) {
        LLog.d(TAG, "doUiStuff result: $result")
    }

}