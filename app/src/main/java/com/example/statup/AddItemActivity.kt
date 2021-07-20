package com.example.statup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.marginEnd
import com.example.statup.Function.Companion.getColorId
import com.example.statup.Function.Companion.getDrawableIdByColorIndex
import com.example.statup.Function.Companion.isEmptyText

class AddItemActivity : Activity(), AdapterView.OnItemSelectedListener
{
    companion object
    {
        val TAG = "AddItemActivity"
        var COLOR_ITEM_SIZE = 0
        var COLOR_ITEM_MARGIN = 0
        val COLOR_ITEM_COUNT = 9
    }

    // include
    lateinit var add_item_edit_include : View
    lateinit var preview_include : View

    lateinit var scroll_view : ScrollView
    lateinit var main_stat_spinner : Spinner

    // preview_include
    lateinit var item_progress_bar : ProgressBar
    lateinit var item_title_text_view : TextView
    lateinit var item_level_text_view : TextView
    lateinit var item_exp_up_button : Button
    lateinit var item_exp_down_button : Button

    // add_item_edit_include
    // editText
    lateinit var title_edit_text : EditText
    lateinit var initial_level_edit_text : EditText
    lateinit var final_level_edit_text : EditText
    lateinit var description_edit_text : EditText
    lateinit var initial_level_text_view : TextView
    lateinit var final_level_text_view : TextView
    lateinit var button_color_list_view : View
    lateinit var progressbar_color_list_view : View
    lateinit var item_add_button : Button
    lateinit var button_color_selector : ImageView
    lateinit var progressbar_color_selector : ImageView
    lateinit var inputMethodManager : InputMethodManager

    lateinit var title_cross_button : ImageView
    lateinit var initial_cross_button : ImageView
    lateinit var final_cross_button : ImageView
    lateinit var description_cross_button : ImageView

    var button_color_index : Int = 8
    var progressbar_color_index : Int = 8

    @SuppressLint("CutPasteId")
    private fun initPreview()
    {
        item_progress_bar = preview_include.findViewById(R.id.item_progress_bar)
        item_title_text_view = preview_include.findViewById(R.id.item_title_text_view)
        item_level_text_view = preview_include.findViewById(R.id.item_level_text_view)
        item_exp_up_button = findViewById(R.id.exp_up_button)
        item_exp_down_button = findViewById(R.id.exp_down_button)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initAddItemView()
    {
        scroll_view = findViewById(R.id.edit_stat_fragment_scroll_view)
        scroll_view.setOnTouchListener { v, event ->
            super.onTouchEvent(event)

            if(event?.action == MotionEvent.ACTION_DOWN)
            {
                // if keyboard's used and touch other editText it's not touchEvent
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }
            false
        }

        title_edit_text  = add_item_edit_include.findViewById(R.id.title_edit_text)
        initial_level_edit_text  = add_item_edit_include.findViewById(R.id.item_level_text_view)
        final_level_edit_text  = add_item_edit_include.findViewById(R.id.final_level_edit_text)
        description_edit_text  = add_item_edit_include.findViewById(R.id.description_edit_text)
        button_color_list_view  = add_item_edit_include.findViewById(R.id.button_color_list_view)
        progressbar_color_list_view = add_item_edit_include.findViewById(R.id.progressbar_color_list_view)
        item_add_button  = add_item_edit_include.findViewById(R.id.item_add_button)
        button_color_selector = add_item_edit_include.findViewById(R.id.button_color_selector_image_view)
        progressbar_color_selector = add_item_edit_include.findViewById(R.id.progressbar_color_selector_image_view)
        initial_level_text_view = add_item_edit_include.findViewById(R.id.initial_level_text_view)
        final_level_text_view = add_item_edit_include.findViewById(R.id.final_level_text_view)

        title_cross_button = add_item_edit_include.findViewById(R.id.title_cross_button)
        initial_cross_button = add_item_edit_include.findViewById(R.id.initial_level_cross_button)
        final_cross_button = add_item_edit_include.findViewById(R.id.final_level_cross_button)
        description_cross_button = add_item_edit_include.findViewById(R.id.description_cross_button)


        title_cross_button.setOnClickListener {
            title_edit_text.setText("")
        }
        initial_cross_button.setOnClickListener {
            initial_level_edit_text.setText("")
        }
        final_cross_button.setOnClickListener {
            final_level_edit_text.setText("")
        }
        description_cross_button.setOnClickListener {
            description_edit_text.setText("")
        }

        // input Method Manager
        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // calculate the size of color item. it depends on the device's size
        button_color_list_view.post {
            val color_view = add_item_edit_include.findViewById<View>(R.id.button_color_1)
            COLOR_ITEM_MARGIN = color_view.marginEnd
            // c M c M c
            COLOR_ITEM_SIZE = (button_color_list_view.right - button_color_list_view.left) - COLOR_ITEM_MARGIN * (COLOR_ITEM_COUNT - 1)
            COLOR_ITEM_SIZE /= COLOR_ITEM_COUNT
        }

        initial_level_text_view.setOnClickListener {
            initial_level_edit_text.requestFocus()
            inputMethodManager.showSoftInput(initial_level_edit_text, InputMethodManager.SHOW_IMPLICIT)
        }

        final_level_text_view.setOnClickListener {
            final_level_edit_text.requestFocus()
            inputMethodManager.showSoftInput(final_level_edit_text, InputMethodManager.SHOW_IMPLICIT)
        }

            title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {

                if(s?.isEmpty() == true)
                {
                    item_title_text_view.setText("Title")
                }
                else
                {
                    item_title_text_view.setText(s.toString())
                }
            }
        })



        initial_level_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                initial_level_text_view.setText(s.toString())
                item_level_text_view.setText("Level " + s.toString())
            }
        })

        final_level_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {  final_level_text_view.setText(s.toString()) }
        })

        // customizing_layout's onTouch func (in this we calculate what kind of color people select)
        val customizing_layout_view : View = add_item_edit_include.findViewById(R.id.customizing_layout)
        customizing_layout_view.setOnTouchListener { view, motionEvent ->

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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            val drawable = getDrawable(getDrawableIdByColorIndex(indexX))

                            item_exp_up_button.setBackgroundDrawable(drawable)
                            item_exp_down_button.setBackgroundDrawable(drawable)
                        }
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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        {
                            item_progress_bar.progressTintList = ColorStateList.valueOf(getColor(getColorId(indexX)))
                        }
                    }
                }
            }

            false
        }



        // spinner for down list
        main_stat_spinner = add_item_edit_include.findViewById(R.id.main_stat_droplist)
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

        main_stat_spinner.setOnTouchListener { v, event ->
            super.onTouchEvent(event)

            if(event?.action == MotionEvent.ACTION_DOWN)
            {
                // if keyboard's used and touch other editText it's not touchEvent
                inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }

            false
        }


        main_stat_spinner.onItemSelectedListener = this
        main_stat_spinner.setSelection(6) // Experience

        // item add button (we'll check if these data is right
        item_add_button.setOnClickListener {

            if(isEmptyText(title_edit_text))
            {
                var toast = Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT)
                toast.show()
                /*customizing_layout_view.requestFocus()*/
                return@setOnClickListener
            }
            else if(isEmptyText(initial_level_edit_text))
            {
                var toast = Toast.makeText(this, "level is empty", Toast.LENGTH_SHORT)
                    toast.show()
                final_level_text_view.requestFocus()
                return@setOnClickListener
            }
            else if(isEmptyText(final_level_edit_text))
            {
                var toast = Toast.makeText(this, "level is empty", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                    toast.show()
                final_level_edit_text.requestFocus()
                inputMethodManager.showSoftInput(final_level_edit_text, InputMethodManager.SHOW_IMPLICIT)
                return@setOnClickListener
            }
            else if(Function.textToInt(initial_level_edit_text) > Function.textToInt(final_level_edit_text))
            {
                var toast = Toast.makeText(this, "level is low than max", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                    toast.show()
                initial_level_edit_text.requestFocus()
                inputMethodManager.showSoftInput(initial_level_edit_text, InputMethodManager.SHOW_IMPLICIT)
                return@setOnClickListener
            }
            // if spinner, selected one value at first and then click some values. never set null
            // if description, it's not essential, empty or not doesn't matter
            // if color index, same with spinner case

            Log.i(TAG, "str_title : " + title_edit_text.text.toString())
            Log.i(TAG, "str_spinner : " + main_stat_spinner.selectedItem.toString())
            Log.i(TAG, "spinner_index : ${main_stat_spinner.selectedItemId}")
            Log.i(TAG, "initial_level : ${Function.textToInt(initial_level_edit_text)}")
            Log.i(TAG, "final_level : ${Function.textToInt(final_level_edit_text)}")
            Log.i(TAG, "str_description : " + description_edit_text.text.toString())
            Log.i(TAG, "button_color_index : $button_color_index")
            Log.i(TAG, "progress_color_index : $progressbar_color_index")

            var returnIntent = Intent()
            returnIntent.putExtra("str_title", title_edit_text.text.toString())
            returnIntent.putExtra("str_spinner", main_stat_spinner.selectedItem.toString())
            returnIntent.putExtra("spinner_index", main_stat_spinner.selectedItemId)
            returnIntent.putExtra("initial_level", Function.textToInt(initial_level_edit_text))
            returnIntent.putExtra("final_level", Function.textToInt(final_level_edit_text))
            returnIntent.putExtra("str_description", description_edit_text.text.toString())
            returnIntent.putExtra("button_color_index", button_color_index)
            returnIntent.putExtra("progress_color_index", progressbar_color_index)
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // include
        add_item_edit_include = findViewById(R.id.add_item_edit_include)
        preview_include = findViewById(R.id.preview_include)

        initPreview()
        initAddItemView()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        super.onTouchEvent(event)

        if(event?.action == MotionEvent.ACTION_DOWN)
        {
            // if keyboard's used and touch other editText it's not touchEvent
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            currentFocus?.clearFocus()
        }

        return true
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
