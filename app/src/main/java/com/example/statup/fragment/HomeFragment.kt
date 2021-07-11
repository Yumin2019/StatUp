package com.example.statup.fragment

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statup.AddItemActivity
import com.example.statup.R
import com.example.statup.module.RecyclerViewAdapter
import com.example.statup.module.StatItem


class HomeFragment : Fragment()
{
    companion object
    {
        val TAG = "HomeFragment"
        val ADD_ITEM_REQUEST = 0
    }

    lateinit var layout_manager : LinearLayoutManager
    lateinit var recycler_view : RecyclerView

    lateinit var add_button : ImageView

    // dataSet
    var stat_item_list : ArrayList<StatItem> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view = view.findViewById(R.id.recycler_view)
        add_button = view.findViewById(R.id.add_button)

        add_button.setOnClickListener {
            // activity
            var intent = Intent(view.context, AddItemActivity::class.java)

            // if you wanna move to new activity from a fragment with two animations
            // you can use bundle
            // https://stackoverflow.com/questions/18810994/no-animation-when-switching-from-fragment-to-activity-and-back/19432471
            val bundle = ActivityOptions.makeCustomAnimation(
                activity,
                R.anim.slide_in_right,
                R.anim.slide_stop
            ).toBundle()
            startActivityForResult(intent, ADD_ITEM_REQUEST, bundle)

        }


        // gridLayoutManager
        layout_manager = LinearLayoutManager(view?.context)
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())

        val adapter = view?.context?.let { RecyclerViewAdapter(it, stat_item_list) }
        recycler_view.adapter = adapter
        recycler_view.layoutManager = layout_manager
        recycler_view.setHasFixedSize(true)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(data == null || resultCode != Activity.RESULT_OK)
            return

        when(requestCode)
        {
            ADD_ITEM_REQUEST ->
            {
                val k = 0
            }
        }

    }
}