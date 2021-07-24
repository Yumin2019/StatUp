package com.example.statup.module

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.statup.R
import com.example.statup.StatFunc.Companion.getColorId
import com.example.statup.fragment.ItemClickListener

class RecyclerViewAdapter(private val context : Context,
                          private val dataSet : ArrayList<StatItem>,
                          private val click_listener : ItemClickListener
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
        {
            val title_text_view : TextView
            val level_text_view : TextView
            val progress_bar : ProgressBar
            val exp_value_text_view : TextView
            val parent_layout : View
            init {
                title_text_view = view.findViewById(R.id.item_title_text_view)
                level_text_view = view.findViewById(R.id.item_level_text_view)
                progress_bar = view.findViewById(R.id.item_progress_bar)
                exp_value_text_view = view.findViewById(R.id.item_exp_value_text_view)
                parent_layout = view.findViewById(R.id.parent_layout)
            }

        }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.stat_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.textView.text = dataSet[position]

        // click event
        viewHolder.parent_layout.setOnClickListener {
            click_listener.OnItemClicked(position)
        }

        // title text view
        viewHolder.title_text_view.text = dataSet[position].str_stat_name

        // level text view
        if(dataSet[position].isFinalLevel())
            viewHolder.level_text_view.text = R.string.str_level_max.toString()
        else
            viewHolder.level_text_view.text = ("Level " + dataSet[position].cur_level)

        // progress bar
        viewHolder.progress_bar.progress = 5 // dataSet[position].cur_exp_value
        viewHolder.progress_bar.progressTintList =
            ColorStateList.valueOf(getColor(context, getColorId(dataSet[position].progress_color_index)))

        // exp value text
        viewHolder.exp_value_text_view.text = ("Exp " + dataSet[position].cur_exp_value + "/" + dataSet[position].max_exp_value)

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}