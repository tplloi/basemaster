package vn.loitp.app.activity.customviews.recyclerview.fastscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import kotlinx.android.synthetic.main.layout_fast_scroll_sample_basic.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.adapter.SampleAdapter
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.db.ListItem
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.db.SAMPLE_DATA_TEXT
import java.util.*

class FilteredFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.layout_fast_scroll_sample_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = listOf(ListItem.DataItem(
                "Every other indicator will be hidden!",
                showInFastScroll = false
        )) + SAMPLE_DATA_TEXT

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SampleAdapter(data)
        }

        fastScrollerView.apply {
            setupWithRecyclerView(
                    recyclerView,
                    { position ->
                        data[position]
                                .takeIf(ListItem::showInFastScroll)
                                ?.let { item ->
                                    FastScrollItemIndicator.Text(
                                            item
                                                    .title
                                                    .substring(0, 1)
                                                    .toUpperCase(Locale.getDefault())
                                    )
                                }
                    },
                    showIndicator = { indicator, indicatorPosition, totalIndicators ->
                        // Hide every other indicator
                        indicatorPosition % 2 == 0
                    }
            )
        }

        fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView = fastScrollerView)
        }
    }
}
