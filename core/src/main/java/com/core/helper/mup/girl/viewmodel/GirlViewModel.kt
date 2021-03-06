package com.core.helper.mup.girl.viewmodel

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.helper.mup.girl.db.GirlDatabase
import com.core.helper.mup.girl.model.GirlPage
import com.core.helper.mup.girl.model.GirlPageDetail
import com.core.helper.mup.girl.service.BaseGirlViewModel
import com.core.helper.mup.girl.service.GirlApiClient
import com.core.helper.mup.girl.service.GirlRepository
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("GirlViewModel")
class GirlViewModel : BaseGirlViewModel() {
    private val repository: GirlRepository = GirlRepository(GirlApiClient.apiService)

    val pageActionLiveData: ActionLiveData<ActionData<ArrayList<GirlPage>>> = ActionLiveData()
    val pageDetailActionLiveData: ActionLiveData<ActionData<ArrayList<GirlPageDetail>>> = ActionLiveData()
    val likeGirlPageActionLiveData: ActionLiveData<ActionData<GirlPage>> = ActionLiveData()
    val pageLikedActionLiveData: ActionLiveData<ActionData<List<GirlPage>>> = ActionLiveData()

    fun getPage(pageIndex: Int, keyWord: String?, isSwipeToRefresh: Boolean) {
        pageActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.getPage(
                    pageIndex = pageIndex,
                    keyWord = keyWord
            )
            logD("<<<getPage " + BaseApplication.gson.toJson(response))
            if (response.items == null) {
                pageActionLiveData.postAction(
                        getErrorRequestGirl(response)
                )
            } else {
                val data = response.items
                data.forEach { girlPage ->
                    val findGirlPage = GirlDatabase.instance?.girlPageDao()?.find(girlPage.id)
//                    logD(">>>findGirlPage " + BaseApplication.gson.toJson(findGirlPage))
                    girlPage.isFavorites = !(findGirlPage == null || !findGirlPage.isFavorites)
                }

                pageActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = data,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page,
                                isSwipeToRefresh = isSwipeToRefresh
                        )
                )
            }
        }

    }

    fun getDetail(id: String?) {
        pageDetailActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            logD(">>>getDetail id $id")
            val response = repository.getPageDetail(
                    id = id
            )
            if (response.items == null) {
                pageDetailActionLiveData.postAction(
                        getErrorRequestGirl(response)
                )
            } else {
                val findGirlPage = GirlDatabase.instance?.girlPageDao()?.find(id)
                logD(">>>findGirlPage " + BaseApplication.gson.toJson(findGirlPage))
                val data = response.items
                if (findGirlPage == null || !findGirlPage.isFavorites) {
                    data.forEach {
                        it.isFavorites = false
                    }
                } else {
                    data.forEach {
                        it.isFavorites = true
                    }
                }
                pageDetailActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = data,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page
                        )
                )
            }
        }

    }

    fun likeGirlPage(girlPage: GirlPage) {
        logD(">>>likeGirlPage before " + BaseApplication.gson.toJson(girlPage))
        girlPage.isFavorites = !girlPage.isFavorites
        logD(">>>likeGirlPage after " + BaseApplication.gson.toJson(girlPage))
        likeGirlPageActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            val id = GirlDatabase.instance?.girlPageDao()?.insert(girlPage)
            logD("<<<likeGirlPage id $id")
            likeGirlPageActionLiveData.post(
                    ActionData(isDoing = false, data = girlPage, isSuccess = true)
            )
        }
    }

    fun getListLikeGirlPage(currentKeyword: String, isDelay: Boolean) {
        pageLikedActionLiveData.set(ActionData(isDoing = true))
        logD(">>>getListLikeGirlPage currentKeyword $currentKeyword")
        ioScope.launch {
            if (isDelay) {
                delay(300)
            }
            val listGirlPageFavorites = GirlDatabase.instance?.girlPageDao()?.getListGirlPage(currentKeyword)
            logD("<<<getListLikeGirlPage " + BaseApplication.gson.toJson(listGirlPageFavorites))
            pageLikedActionLiveData.postAction(
                    ActionData(isDoing = false, data = listGirlPageFavorites, isSuccess = true)
            )
        }
    }

}
