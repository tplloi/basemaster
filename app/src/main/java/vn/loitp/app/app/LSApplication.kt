package vn.loitp.app.app

import androidx.multidex.MultiDexApplication
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmConfiguration
import loitp.basemaster.R
import vn.loitp.core.common.Constants
import vn.loitp.core.utilities.LUIUtil
import vn.loitp.data.ActivityData
import vn.loitp.data.AdmobData
import vn.loitp.utils.util.Utils

//TODO bug database ->  read sqlite  asset
//TODO bug custom view -> button -> loading button

//TODO core film plus
//TODO is debug




//GIT
//combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/






class LSApplication : MultiDexApplication() {
    private val TAG = LSApplication::class.java.simpleName

    companion object {
        val gson: Gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()

        Constants.setIsDebug(true)
        Utils.init(this)
        //config admob id
        AdmobData.instance.idAdmobFull = getString(R.string.str_f)
        //config activity transition default
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_FADE

        //config realm
        val realmConfiguration = RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        //config font
        LUIUtil.setFontForAll(vn.loitp.core.common.Constants.FONT_PATH)

        //fcm
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)

        //big imageview
        BigImageViewer.initialize(GlideImageLoader.with(applicationContext))
    }
}