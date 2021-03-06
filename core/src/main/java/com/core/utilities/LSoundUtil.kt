package com.core.utilities

import android.media.MediaPlayer
import java.io.IOException

/**
 * Created by www.muathu@gmail.com on 6/1/2017.
 */

class LSoundUtil {
    companion object {
        fun startMusicFromAsset(fileName: String) {
            val mediaPlayer = MediaPlayer()
            try {
                val assetFileDescriptor = LAppResource.application.assets.openFd(fileName)
                mediaPlayer.setDataSource(
                        assetFileDescriptor.fileDescriptor,
                        assetFileDescriptor.startOffset,
                        assetFileDescriptor.length
                )
                assetFileDescriptor.close()
                mediaPlayer.prepare()
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { player ->
                    var mPlayer = player
                    mPlayer?.let {
                        it.stop()
                        it.reset()
                        it.release()
                        mPlayer = null
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
