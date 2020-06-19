package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import com.core.utilities.LLog
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_item_user.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.User

class UsersAdapter(
        private val listUser: ArrayList<User>
) : RecyclerView.Adapter<UsersAdapter.DataViewHolder>() {
    private val TAG = "loitpp" + javaClass.simpleName
    var onClickRootListener: ((User, Int) -> Unit)? = null

    fun setData(listUser: ArrayList<User>) {
        this.listUser.clear()
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            LLog.d(TAG, "bind $bindingAdapterPosition")
            itemView.textViewUserName.text = user.name
            LImageUtil.load(context = itemView.imageViewAvatar.context,
                    url = user.avatar,
                    imageView = itemView.imageViewAvatar)
            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(user, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.view_row_item_user, parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
            holder.bind(listUser[position])

}
