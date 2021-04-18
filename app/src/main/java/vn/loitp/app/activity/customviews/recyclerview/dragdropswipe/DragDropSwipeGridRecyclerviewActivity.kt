package vn.loitp.app.activity.customviews.recyclerview.dragdropswipe

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.android.synthetic.main.activity_recycler_drag_drop_swipe_grid.*
import vn.loitp.app.R

//https://github.com/ernestoyaquello/DragDropSwipeRecyclerview
@LogTag("DragDropSwipeGridRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeGridRecyclerviewActivity : BaseFontActivity() {

    private var dragDropAdapter: DragDropAdapter? = null
    private val numberOfColumns = 2

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_drag_drop_swipe_grid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setData(): ArrayList<String> {
        val dataSet = ArrayList<String>()
        for (i in 0..20) {
            dataSet.add(element = "Item $i")
        }
        return dataSet
    }

    private fun setupViews() {
        dragDropAdapter = DragDropAdapter(setData())

        dragDropSwipeRecyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        dragDropSwipeRecyclerView.adapter = dragDropAdapter

        setIsRestrictingDraggingDirections()
        setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = false)

        swLayoutBehind.setOnCheckedChangeListener { _, isChecked ->
            setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = isChecked)
        }

        //listener -> check DragDropSwipeListHorizontalRecyclerviewActivity
    }

    private fun setIsRestrictingDraggingDirections() {
        dragDropSwipeRecyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.GRID_LIST_WITH_HORIZONTAL_SWIPING
        dragDropSwipeRecyclerView.numOfColumnsPerRowInGridList = numberOfColumns
    }

    private fun setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems: Boolean) {
        // We set to null all the properties that can be used to display something behind swiped items
        // In XML: app:behind_swiped_item_bg_color="@null"
        dragDropSwipeRecyclerView.behindSwipedItemBackgroundColor = null

        // In XML: app:behind_swiped_item_bg_color_secondary="@null"
        dragDropSwipeRecyclerView.behindSwipedItemBackgroundSecondaryColor = null

        // In XML: app:behind_swiped_item_icon="@null"
        dragDropSwipeRecyclerView.behindSwipedItemIconDrawableId = null

        // In XML: app:behind_swiped_item_icon_secondary="@null"
        dragDropSwipeRecyclerView.behindSwipedItemIconSecondaryDrawableId = null

        // In XML: app:behind_swiped_item_custom_layout="@null"
        dragDropSwipeRecyclerView.behindSwipedItemLayoutId = null

        // In XML: app:behind_swiped_item_custom_layout_secondary="@null"
        dragDropSwipeRecyclerView.behindSwipedItemSecondaryLayoutId = null

        if (isDrawingBehindSwipedItems) {
            // We set our custom layouts to be displayed behind swiped items
            // In XML: app:behind_swiped_item_custom_layout="@layout/behind_swiped_vertical_list"
            dragDropSwipeRecyclerView.behindSwipedItemLayoutId = R.layout.layout_behind_swiped_vertical_list

            // In XML: app:behind_swiped_item_custom_layout_secondary="@layout/behind_swiped_vertical_list_secondary"
            dragDropSwipeRecyclerView.behindSwipedItemSecondaryLayoutId = R.layout.layout_behind_swiped_vertical_list_secondary
        }
    }

}