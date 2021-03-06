package vn.loitp.app.activity.ads.admobbanner

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import kotlinx.android.synthetic.main.activity_admob_banner.*
import vn.loitp.app.R

@LogTag("AdMobBannerActivity")
@IsFullScreen(false)
class AdMobBannerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_admob_banner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adView = LUIUtil.createAdBanner(adView = adView)
        adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(loadAdError: LoadAdError?) {
                super.onAdFailedToLoad(loadAdError)
                logD("onAdFailedToLoad loadAdError $loadAdError")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                logD("onAdOpened")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                logD("onAdClicked")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                logD("onAdLoaded")
            }
        }

        val poem = LStoreUtil.readTxtFromRawFolder(R.raw.loitp)
        textView.text = poem
    }

    public override fun onPause() {
        adView.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView.resume()
        super.onResume()
    }

    public override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}
