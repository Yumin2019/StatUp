package com.example.statup

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.statup.fragment.HomeFragment
import com.example.statup.module.StatItem
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemInfoActivity : Activity()
{
    lateinit var exp_up_button : Button
    lateinit var exp_down_button : Button
    lateinit var progress_bar : ProgressBar
    var item_index : Int = -1

    var item : StatItem = StatItem()

    companion object
    {
        val TAG = "ItemInfoActivity"
        val ITEM_CHANGED_EVENT = 0
        val ITEM_EDITED_EVENT = 1
        val ITEM_DELETE_EVENT = 2

        val ITEM_EDIT_REQUEST = 0
    }

    override fun onBackPressed()
    {
        val prev_cur_level = intent.getIntExtra("cur_level", 1)
        val prev_cur_exp = intent.getIntExtra("cur_exp_value", 0)
        if((prev_cur_level != item.cur_level) || (prev_cur_exp != item.cur_exp_value))
        {
            // changed item data (have to send this to homefragment)
            Log.i(TAG, "item_index = $item_index")
            Log.i(TAG, "cur_level = ${item.cur_level}")
            Log.i(TAG, "cur_exp_value = ${item.cur_exp_value}")

            var returnIntent = Intent()
            returnIntent.putExtra("event", ITEM_CHANGED_EVENT)
            returnIntent.putExtra("item_index", item_index)
            returnIntent.putExtra("cur_level", item.cur_level)
            returnIntent.putExtra("cur_exp_value", item.cur_exp_value)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
        // else : don't need to send values (RESULT_CANCEL)
        super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_info)

        item.str_stat_name = intent.getStringExtra("str_stat_name").toString()
        item.str_main_stat = intent.getStringExtra("str_main_stat").toString()
        item.main_stat_idx =
            intent.getIntExtra("main_stat_idx", AddItemActivity.EXPERIENCE_MAIN_STAT)
        item.initial_level = intent.getIntExtra("initial_level", 1)
        item.final_level = intent.getIntExtra("final_level", 100)
        item.cur_level = intent.getIntExtra("cur_level", 1)
        item.str_description = intent.getStringExtra("str_description").toString()
        item.button_color_index =
            intent.getIntExtra("button_color_index", AddItemActivity.COLOR_ITEM_DEFAULT)
        item.progress_color_index =
            intent.getIntExtra("progress_color_index", AddItemActivity.COLOR_ITEM_DEFAULT)
        item.cur_exp_value = intent.getIntExtra("cur_exp_value", 0)

        // item index
        item_index = intent.getIntExtra("item_index", 0)

        // preview
        findViewById<TextView>(R.id.item_title_text_view).text = item.str_stat_name

        val level_text_view = findViewById<TextView>(R.id.item_level_text_view)
        if (item.isFinalLevel())
            level_text_view.text = R.string.str_level_max.toString()
        else
            level_text_view.text = "Level " + item.cur_level

        progress_bar = findViewById(R.id.item_progress_bar)
        progress_bar.progressTintList =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    StatFunc.getColorId(item.progress_color_index)
                )
            )

        progress_bar.progress = item.cur_exp_value * 1000
        findViewById<TextView>(R.id.item_exp_value_text_view).text =
            "Exp " + item.cur_exp_value + "/" + "10"

        // button color
        exp_up_button = findViewById(R.id.exp_up_button)
        exp_down_button = findViewById(R.id.exp_down_button)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val drawable = getDrawable(StatFunc.getDrawableIdByColorIndex(item.button_color_index))

            exp_up_button.setBackgroundDrawable(drawable)
            exp_down_button.setBackgroundDrawable(drawable)
        }

        // in scroll
        findViewById<TextView>(R.id.title_value_text_view).text = item.str_stat_name
        findViewById<TextView>(R.id.main_stat_droplist).text = item.str_main_stat
        findViewById<TextView>(R.id.initial_level_value_text_view).text =
            item.initial_level.toString()
        findViewById<TextView>(R.id.final_level_value_text_view).text = item.final_level.toString()
        findViewById<TextView>(R.id.desc_value_text_view).text = item.str_description

        // button, progressbar color
        findViewById<ImageView>(R.id.button_color_image_view)
            .setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    StatFunc.getColorId(item.button_color_index)
                )
            )

        findViewById<ImageView>(R.id.progress_bar_color_image_view)
            .setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    StatFunc.getColorId(item.progress_color_index)
                )
            )

        // Button Click Event
        exp_up_button.setOnClickListener {

            if (progress_bar.isAnimating)
                return@setOnClickListener

            ++item.cur_exp_value
            updatePreview(1)

        }

        exp_down_button.setOnClickListener {

            if (item.cur_exp_value == 0 || progress_bar.isAnimating)
                return@setOnClickListener

            --item.cur_exp_value
            updatePreview(-1)
        }

        findViewById<Button>(R.id.item_edit_button).setOnClickListener {

            var intent = Intent(this, AddItemActivity::class.java)

            // data

            overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_stop)
            startActivityForResult(intent, ITEM_EDIT_REQUEST)
        }

        findViewById<Button>(R.id.item_delete_button).setOnClickListener {

            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.str_warning)
                .setMessage(R.string.str_delete_message)
                .setNegativeButton(R.string.str_cancel
                ) { dialog, which ->  }
                .setPositiveButton(R.string.str_delete
                ) { dialog, which ->
                    // when you want to go out of this activity
                    var returnIntent = Intent()
                    returnIntent.putExtra("event", ITEM_DELETE_EVENT)
                    returnIntent.putExtra("item_index", item_index)
                    setResult(RESULT_OK, returnIntent)
                    finish()
                }
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != RESULT_OK || data == null)
            return

        when(requestCode)
        {
            ITEM_EDIT_REQUEST ->
            {
               // data.getIntExtra()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePreview(change_value : Int)
    {
        // animation of progress bar
        var start = (item.cur_exp_value - change_value) * 1000
        var end = item.cur_exp_value * 1000

        var animator = ObjectAnimator.ofInt(progress_bar, "progress", start, end)
        animator.setDuration(150)
        animator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) { super.onAnimationStart(animation, isReverse)}
            override fun onAnimationStart(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) { super.onAnimationEnd(animation, isReverse)}
            override fun onAnimationEnd(animation: Animator?)
            {
                item.checkExp()

                val level_text_view = findViewById<TextView>(R.id.item_level_text_view)
                val exp_text_view = findViewById<TextView>(R.id.item_exp_value_text_view)

                exp_text_view.text = "Exp " + item.cur_exp_value + "/" + "10"

                if (item.isFinalLevel())
                    level_text_view.text = R.string.str_level_max.toString()
                else
                    level_text_view.text = "Level " + item.cur_level

                progress_bar.progress = item.cur_exp_value * 1000
            }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationRepeat(animation: Animator?) {}
        })
        animator.start()

    }

}