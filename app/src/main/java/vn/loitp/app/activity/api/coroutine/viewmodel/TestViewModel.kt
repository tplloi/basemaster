package vn.loitp.app.activity.api.coroutine.viewmodel

import androidx.lifecycle.MutableLiveData
import com.core.base.BaseViewModel
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import com.service.model.UserTest
import kotlinx.coroutines.launch
import vn.loitp.app.activity.api.coroutine.repository.TestRepository
import vn.loitp.app.activity.api.coroutine.service.TestApiClient

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

class TestViewModel : BaseViewModel() {
    private val logTag = javaClass.simpleName
    private val repository: TestRepository = TestRepository(TestApiClient.apiService)

    // action
    val userTestListLiveData: MutableLiveData<ArrayList<UserTest>?> = MutableLiveData()
    val userActionLiveData: ActionLiveData<ActionData<ArrayList<UserTest>>> = ActionLiveData()

    fun getUserTestListByPage(page: Int, isRefresh: Boolean) {
        userActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.getUserTest(page = page)
            if (response.data != null) {
                userActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                isSwipeToRefresh = isRefresh,
                                data = response.data
                        )
                )
            } else {
                userActionLiveData.postAction(getErrorRequest(response))
            }
        }

    }

    fun addUserList(userTestList: ArrayList<UserTest>, isRefresh: Boolean?) {
        var currentUserTestList = userTestListLiveData.value
        if (isRefresh == true) {
            currentUserTestList?.clear()
        }
        if (currentUserTestList == null) {
            currentUserTestList = ArrayList()
        }
        currentUserTestList.addAll(userTestList)
        userTestListLiveData.post(currentUserTestList)
    }
}
