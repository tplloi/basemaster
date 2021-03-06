package vn.loitp.app.activity.api.coroutine.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import com.service.model.UserTest
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.item_user_1.view.*
import vn.loitp.app.R

class UserListAdapter(private val context: Context,
                      private val callback: (Int, UserTest) -> Unit) :
        RecyclerView.Adapter<UserListAdapter.UserTestViewHolder>() {

    private val logTag = javaClass.simpleName

    private var userTestList = ArrayList<UserTest>()

    inner class UserTestViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(userTest: UserTest) {
            userTest.avatar?.let {
                LImageUtil.load(context, it, itemView.ivAvt)
            }
            itemView.tvEmail.text = userTest.email
            itemView.tvFirstName.text = userTest.firstName
            itemView.tvId.text = "Id ${userTest.id}"
            itemView.tvLastName.text = userTest.lastName
            itemView.rootView.setSafeOnClickListener {
                callback.invoke(bindingAdapterPosition, userTest)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_1, parent, false)
        return UserTestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserTestViewHolder, position: Int) {
        val userTest = userTestList[position]
        holder.bind(userTest)
    }

    override fun getItemCount(): Int {
        return userTestList.size
    }

    fun setList(userTestList: ArrayList<UserTest>?) {
        if (userTestList.isNullOrEmpty()) {
            this.userTestList.clear()
        } else {
            this.userTestList = userTestList
        }
        notifyDataSetChanged()
    }
}
