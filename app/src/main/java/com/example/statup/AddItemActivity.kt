package com.example.statup

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd

class AddItemActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener
{
    companion object
    {
        var COLOR_ITEM_SIZE = 0
        var COLOR_ITEM_MARGIN = 0
        val COLOR_ITEM_COUNT = 9
    }


    lateinit var main_stat_spinner : Spinner

    // editText
    lateinit var title_edit_text : EditText
    lateinit var initial_level_edit_text : EditText
    lateinit var final_level_edit_text : EditText
    lateinit var description_edit_text : EditText
    lateinit var button_color_list_view : View
    lateinit var progressbar_color_list_view : View
    lateinit var item_add_button : Button
    lateinit var button_color_selector : ImageView
    lateinit var progressbar_color_selector : ImageView
    lateinit var inputMethodManager : InputMethodManager

    var button_color_index : Int = 8
    var progressbar_color_index : Int = 8




    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        title_edit_text  = findViewById(R.id.title_edit_text)
        initial_level_edit_text  = findViewById(R.id.initial_level_edit_text)
        final_level_edit_text  = findViewById(R.id.final_level_edit_text)
        description_edit_text  = findViewById(R.id.description_edit_text)
        button_color_list_view  = findViewById(R.id.button_color_list_view)
        progressbar_color_list_view = findViewById(R.id.progressbar_color_list_view)
        item_add_button  = findViewById(R.id.item_add_button)
        button_color_selector = findViewById(R.id.button_color_selector_image_view)
        progressbar_color_selector = findViewById(R.id.progressbar_color_selector_image_view)

        // input Method Manager
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // calculate the size of color item. it depends on the device's size
        button_color_list_view.post {
            val color_view = findViewById<View>(R.id.button_color_1)
            COLOR_ITEM_MARGIN = color_view.marginEnd
            // c M c M c
            COLOR_ITEM_SIZE = (button_color_list_view.right - button_color_list_view.left) - COLOR_ITEM_MARGIN * (COLOR_ITEM_COUNT - 1)
            COLOR_ITEM_SIZE /= COLOR_ITEM_COUNT
        }



        // customizing_layout's onTouch func (in this we calculate what kind of color people select)
        val view : View = findViewById(R.id.customizing_layout)
        view.setOnTouchListener { v, motionEvent ->

            if(motionEvent.action == MotionEvent.ACTION_DOWN)
            {
                // relative position in constraintLayout
                val posX = motionEvent.x
                val posY = motionEvent.y

                // button_color_list_view
                var left = button_color_list_view.left
                var top  = button_color_list_view.top
                var right = button_color_list_view.right
                var bottom = button_color_list_view.bottom

                // if dot is in rectangle.
                if(posX >= left && posX <= right && posY >= top && posY <= bottom)
                {
                    val relativeX : Int = posX.toInt() - left.toInt()
                    val indexX  : Int = relativeX / (COLOR_ITEM_SIZE + COLOR_ITEM_MARGIN)
                    // c M / c M / c (M)
                    // we have to find out if someone touch the margin amongst colors
                    // we can know what slice you selected with indexX

                    if(relativeX <= ( (COLOR_ITEM_SIZE + COLOR_ITEM_MARGIN) * indexX + COLOR_ITEM_SIZE) )
                    {
                        // change the pos of this view programmatically (margin)
                        val params = button_color_selector.layoutParams as ConstraintLayout.LayoutParams
                        params.leftMargin = indexX * (COLOR_ITEM_SIZE + COLOR_ITEM_MARGIN)
                        button_color_selector.requestLayout()
                        button_color_index = indexX // color index
                    }
                    // else : clicked margin

                }

                // progressbar_color_list_view
                left = progressbar_color_list_view.left
                top  = progressbar_color_list_view.top
                right = progressbar_color_list_view.right
                bottom = progressbar_color_list_view.bottom

                if(posX >= left && posX <= right && posY >= top && posY <= bottom)
                {
                    val relativeX : Int = posX.toInt() - left.toInt()
                    val indexX  : Int = relativeX / (COLOR_ITEM_SIZE + COLOR_ITEM_MARGIN)

                    if(relativeX <= ( (COLOR_ITEM_SIZE + COLOR_ITEM_MARGIN) * indexX + COLOR_ITEM_SIZE) )
                    {
                        val params = progressbar_color_selector.layoutParams as ConstraintLayout.LayoutParams
                        params.leftMargin = indexX * (COLOR_ITEM_SIZE + COLOR_ITEM_MARGIN)
                        progressbar_color_selector.requestLayout()
                        progressbar_color_index = indexX
                    }
                }
            }


            false
        }


        item_add_button.setOnClickListener {

        }

        // spinner for down list
        main_stat_spinner = findViewById(R.id.main_stat_droplist)
        ArrayAdapter.createFromResource(
            this,
            R.array.main_stat_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            main_stat_spinner.adapter = adapter
        }

        /*
        <item>Heath Point</item>
        <item>Mana Point</item>
        <item>Strength</item>
        <item>Intelligence</item>
        <item>Luck</item>
        <item>Wisdom</item>
        <item>Experience</item>
        <item>etc.</item>
         */

        main_stat_spinner.onItemSelectedListener = this
        main_stat_spinner.setSelection(6) // Experience


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if(event?.action == MotionEvent.ACTION_DOWN)
        {
            // if keyboard's used and touch other editText it's not touchEvent
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }

        return super.onTouchEvent(event)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long)
    {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>)
    {
        // Another interface callback
    }

}
