package vn.loitp.app.activity.database.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_item_floor_plan.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.room.model.FloorPlan

class FloorPlanAdapter : RecyclerView.Adapter<FloorPlanAdapter.ViewHolder>() {

    private val listFloorPlan = ArrayList<FloorPlan>()

    var onClickRootView: ((FloorPlan) -> Unit)? = null
    var onClickUpDate: ((FloorPlan) -> Unit)? = null
    var onClickDelete: ((FloorPlan) -> Unit)? = null

    fun setListFloorPlan(listFloorPlan: List<FloorPlan>) {
        this.listFloorPlan.clear()
        this.listFloorPlan.addAll(listFloorPlan)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(floorPlan: FloorPlan) {
            itemView.tvFloorPlan.text = "${floorPlan.id} - ${floorPlan.name}"
            itemView.rootView.setSafeOnClickListener {
                onClickRootView?.invoke(floorPlan)
            }
            itemView.ivUpdate.setSafeOnClickListener {
                onClickUpDate?.invoke(floorPlan)
            }
            itemView.ivDelete.setSafeOnClickListener {
                onClickDelete?.invoke(floorPlan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_floor_plan, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFloorPlan[position])
    }

    override fun getItemCount(): Int {
        return listFloorPlan.size
    }
}
