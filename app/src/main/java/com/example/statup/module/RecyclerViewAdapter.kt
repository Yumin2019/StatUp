package com.example.statup.module

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.statup.R

class RecyclerViewAdapter(private val context : Context, private val dataSet : ArrayList<StatItem>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {



        class ViewHolder(view : View) : RecyclerView.ViewHolder(view), View.OnClickListener
        {
            // val textView : TextView
            init {
                view.setOnClickListener(this)
                // define click listener for the viewholder's view
                //  textView = view.findViewById(R.id.textView)
            }

            override fun onClick(v: View?) {
                Log.i("RecyclerView", "clicked")
            }

        }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        //viewHolder.textView.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}