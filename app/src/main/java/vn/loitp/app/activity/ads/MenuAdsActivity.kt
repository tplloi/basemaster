package vn.loitp.app.activity.ads

import android.content.Intent
import android.os.Bundle
import android.view.View

import loitp.basemaster.R
import vn.loitp.app.activity.ads.admobbanner.AdmobBannerActivity
import vn.loitp.app.activity.ads.admobinterstitial.AdmobInterstitialActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.loitp.admobrewardedvideo.AdmobRewardedVideoActivity
import vn.loitp.core.utilities.LActivityUtil

class MenuAdsActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        findViewById<View>(R.id.bt_1).setOnClickListener {
            val intent = Intent(activity, AdmobBannerActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt_2).setOnClickListener {
            val intent = Intent(activity, AdmobInterstitialActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt_3).setOnClickListener {
            val intent = Intent(activity, AdmobRewardedVideoActivity::class.java)
            intent.putExtra(AdmobRewardedVideoActivity.APP_ID, getString(R.string.str_app_id))
            intent.putExtra(AdmobRewardedVideoActivity.ID_REWARD, getString(R.string.str_reward))
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_ads
    }
}