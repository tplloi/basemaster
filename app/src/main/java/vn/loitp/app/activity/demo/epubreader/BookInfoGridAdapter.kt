package vn.loitp.app.activity.demo.epubreader

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.core.utilities.LReaderUtil.decodeBitmapFromByteArray
import com.core.utilities.LReaderUtil.defaultCover
import com.function.epub.model.BookInfo
import vn.loitp.app.R

class BookInfoGridAdapter(private val context: Context, private val bookInfoList: List<BookInfo>)
    : BaseAdapter() {

    private class ViewHolder {
        var tvBookTitle: TextView? = null
        var ivCover: ImageView? = null
    }

    override fun getCount(): Int {
        return bookInfoList.size
    }

    override fun getItem(i: Int): Any {
        return bookInfoList.get(i)
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.row_item_book_epub_reader, parent, false)
            viewHolder = ViewHolder()
            viewHolder.tvBookTitle = view.findViewById(R.id.tvBookTitle)
            viewHolder.ivCover = view.findViewById(R.id.ivCover)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.tvBookTitle?.text = bookInfoList[position].title
        val isCoverImageNotExists = bookInfoList[position].isCoverImageNotExists
        if (!isCoverImageNotExists) {
            val savedBitmap = bookInfoList[position].coverImageBitmap
            if (savedBitmap != null) {
                viewHolder.ivCover?.setImageBitmap(savedBitmap)
            } else {
                val coverImageAsBytes = bookInfoList[position].coverImage
                if (coverImageAsBytes != null) {
                    val bitmap = decodeBitmapFromByteArray(coverImage = coverImageAsBytes, reqWidth = 100, reqHeight = 200)
                    bookInfoList[position].coverImageBitmap = bitmap
                    bookInfoList[position].coverImage = null
                    viewHolder.ivCover?.setImageBitmap(bitmap)
                } else {
                    bookInfoList[position].isCoverImageNotExists = true
                    viewHolder.ivCover?.setImageResource(defaultCover)
                }
            }
        } else {
            viewHolder.ivCover?.setImageResource(defaultCover)
        }
        return view!!
    }

}