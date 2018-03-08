package adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.widget.TextView
import com.example.sankar_5526.helloworld.R


/**
 * Created by sankar-5526 on 27/02/18.
 */

class GridViewAdapter(context: Context) : BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder: ViewHolder? = null
        var rowItemView = p1
        if (p1 == null) {
            rowItemView = mInflater?.inflate(R.layout.gridview_layout,
                    p2, false)
            holder = ViewHolder()
            holder.textViewId = rowItemView!!.findViewById(R.id.txtId)
            holder.textViewId.setPadding(100, 10, 10, 10)
            holder.textViewName = rowItemView.findViewById(R.id.txtName)
            holder.textViewName.setPadding(100, 10, 10, 10)

            if (p0 === 0) {
                rowItemView.tag = holder
            }
        } else {
            holder = rowItemView!!.tag as ViewHolder
        }
        holder.textViewId.text = id[p0]
        holder.textViewName.text = name[p0]
        return rowItemView
    }

    override fun getItem(p0: Int): Any {
        return p0
    }

    override fun getItemId(p0: Int): Long {
        return p0 as Long
    }

    override fun getCount(): Int {
        return id.size
    }

    internal class ViewHolder {
        lateinit var textViewId: TextView
        lateinit var textViewName: TextView
    }

    var mContext: Context? = context
    private val id = arrayOf("S001", "S002", "S003", "S004", "S005", "S006", "S007")
    private val name = arrayOf("Rohit", "Rahul", "Ravi", "Amit", "Arun", "Anil", "Kashif")
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(context)
    }

}