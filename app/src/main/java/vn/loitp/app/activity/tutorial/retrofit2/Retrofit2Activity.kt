package vn.loitp.app.activity.tutorial.retrofit2

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFontActivity
import com.restapi.restclient.RestClient2
import com.views.LToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_retrofit_2.*
import vn.loitp.app.R
import vn.loitp.app.app.LApplication
import java.util.*

//https://code.tutsplus.com/tutorials/connect-to-an-api-with-retrofit-rxjava-2-and-kotlin--cms-32133
class Retrofit2Activity : BaseFontActivity(), Retrofit2Adapter.Listener {
    private var retrofit2Adapter: Retrofit2Adapter? = null
    private var retroCryptoArrayList: ArrayList<RetroCrypto> = ArrayList()
    private val BASE_URL = "https://api.nomics.com/v1/"
    private lateinit var sampleService: SampleService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RestClient2.init(BASE_URL)
        sampleService = RestClient2.createService(SampleService::class.java)
        initRecyclerView()
        loadData()
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
    }

    private fun loadData() {
        logD("loadData")
        compositeDisposable.add(sampleService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    logD("loadData success " + LApplication.gson.toJson(it))
                    retroCryptoArrayList.clear()
                    retroCryptoArrayList.addAll(it)
                    retrofit2Adapter = Retrofit2Adapter(retroCryptoArrayList, this)
                    rv.adapter = retrofit2Adapter
                }, {
                    logE("loadData error $it")
                    LToast.show(activity, it.toString(), R.drawable.l_bkg_horizontal)
                }))
    }

    override fun onItemClick(retroCrypto: RetroCrypto) {
        LToast.show(activity, "You clicked: ${retroCrypto.currency}")
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_retrofit_2
    }

}
