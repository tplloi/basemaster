package vn.loitp.app.activity.ads

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.admobrewardedvideo.AdmobRewardedVideoActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_ads.*
import vn.loitp.app.R
import vn.loitp.app.activity.ads.admobbanner.AdmobBannerActivity
import vn.loitp.app.activity.ads.admobinterstitial.AdmobInterstitialActivity

@LayoutId(R.layout.activity_menu_ads)
@LogTag("MenuAdsActivity")
@IsFullScreen(false)
class MenuAdsActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt1.setOnClickListener {
            val intent = Intent(this, AdmobBannerActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        bt2.setOnClickListener {
            val intent = Intent(this, AdmobInterstitialActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        bt3.setOnClickListener {
            val intent = Intent(this, AdmobRewardedVideoActivity::class.java)
            intent.putExtra(AdmobRewardedVideoActivity.APP_ID, getString(R.string.str_app_id))
            intent.putExtra(AdmobRewardedVideoActivity.ID_REWARD, getString(R.string.str_reward))
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }

}

