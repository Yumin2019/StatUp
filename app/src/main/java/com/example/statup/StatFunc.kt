package com.example.statup

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class StatFunc {
    companion object
    {

        fun getColorId(index : Int) : Int
        {
            return when(index)
            {
                0 -> R.color.custom_red
                1 -> R.color.custom_orange
                2 -> R.color.custom_yellow
                3 -> R.color.custom_light_green
                4 -> R.color.custom_green
                5 -> R.color.custom_blue
                6 -> R.color.custom_purple
                7 -> R.color.custom_pink
                8 -> R.color.custom_gray
                else -> R.color.black
            }
        }

        fun getDrawableIdByColorIndex(index : Int) : Int
        {
            return when(index)
            {
                0 -> R.drawable.stat_point_button_red
                1 -> R.drawable.stat_point_button_orange
                2 -> R.drawable.stat_point_button_yellow
                3 -> R.drawable.stat_point_button_light_green
                4 -> R.drawable.stat_point_button_green
                5 -> R.drawable.stat_point_button_blue
                6 -> R.drawable.stat_point_button_purple
                7 -> R.drawable.stat_point_button_pink
                8 -> R.drawable.stat_point_button_gray
                else -> R.drawable.stat_point_button_normal
            }
        }

        fun isEmptyText(text : TextView) : Boolean
        {
            return text.text.isEmpty()
        }

        fun textToInt(textView: TextView) : Int
        {
            if(textView.text.isEmpty())
                assert(false)
            return textView.text.toString().toInt()
        }

        fun createAlertDialog(context : Context, str_title : String?, str_message : String)
        {
            var dialog = MaterialAlertDialogBuilder(context)

            if (str_title != null)
                dialog.setTitle(str_title)
            dialog.setMessage(str_message)
                .setPositiveButton(
                    "OK"
                ) { dialog, which ->

                }
                .show()
        }

        fun createAlertDialog(context : Context, title_id : Int?, message_id : Int)
        {
            var dialog = MaterialAlertDialogBuilder(context)

            if (title_id != null)
                dialog.setTitle(Resources.getSystem().getString(title_id!!))
            dialog.setMessage(Resources.getSystem().getString(message_id))
                .setPositiveButton(
                    "OK"
                ) { dialog, which ->

                }
                .show()
        }

    }
}