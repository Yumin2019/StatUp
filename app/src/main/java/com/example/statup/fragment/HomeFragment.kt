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
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment()
{
    companion object
    {
        val TAG = "HomeFragment"
        val ADD_ITEM_REQUEST = 0
    }

    lateinit var layout_manager : LinearLayoutManager
    lateinit var recycler_view : RecyclerView
    lateinit var recycler_view_apater : RecyclerViewAdapter

    lateinit var add_button : FloatingActionButton

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

            // activity!!.overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_stop);
            //fragmentManager?.beginTransaction()?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_stop)
            startActivityForResult(intent, ADD_ITEM_REQUEST, bundle)
        }


        // gridLayoutManager
        layout_manager = LinearLayoutManager(view?.context)
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())
        stat_item_list.add(StatItem())

        recycler_view_apater = RecyclerViewAdapter(view?.context, stat_item_list)
        recycler_view.adapter = recycler_view_apater
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
            /*
             var returnIntent = Intent()
            returnIntent.putExtra("str_title", title_edit_text.text.toString())
            returnIntent.putExtra("str_main_stat", main_stat_spinner.selectedItem.toString())
            returnIntent.putExtra("main_stat_idx", main_stat_spinner.selectedItemId)
            returnIntent.putExtra("initial_level", StatFunc.textToInt(initial_level_edit_text))
            returnIntent.putExtra("final_level", StatFunc.textToInt(final_level_edit_text))
            returnIntent.putExtra("str_description", description_edit_text.text.toString())
            returnIntent.putExtra("button_color_index", button_color_index)
            returnIntent.putExtra("progress_color_index", progressbar_color_index)
             */
            ADD_ITEM_REQUEST ->
            {
                var str_title = data.getStringExtra("str_title")
                var str_main_stat = data.getStringExtra("str_main_stat")
                var main_stat_idx = data.getIntExtra("selectedItemId", AddItemActivity.EXPERIENCE_MAIN_STAT)
                var initial_level = data.getIntExtra("initial_level", 1)
                var final_level = data.getIntExtra("final_level", 100)
                var str_description = data.getStringExtra("str_description")
                var button_color_index = data.getIntExtra("button_color_index", AddItemActivity.COLOR_ITEM_DEFAULT)
                var progress_color_index = data.getIntExtra("progress_color_index", AddItemActivity.COLOR_ITEM_DEFAULT)

                var item  = StatItem(str_title, str_main_stat, main_stat_idx, initial_level,
                final_level, initial_level, str_description, button_color_index, progress_color_index)
                stat_item_list.add(item)
                recycler_view_apater.notifyDataSetChanged()
            }
        }

    }
}