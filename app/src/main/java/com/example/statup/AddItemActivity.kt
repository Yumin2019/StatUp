package com.example.statup

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginEnd
import androidx.fragment.app.FragmentActivity
import com.example.statup.fragment.addItem.AddItemFragment
import com.example.statup.fragment.addItem.PreviewFragment

class AddItemActivity : FragmentActivity()
{
    lateinit var preview_fragment : PreviewFragment
    lateinit var add_item_edit_fragment : AddItemFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

      /*  preview_fragment = PreviewFragment()
        add_item_edit_fragment = AddItemFragment()

        supportFragmentManager.beginTransaction().add(R.id.preview_container, preview_fragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.add_item_edit_container, add_item_edit_fragment).commit()*/
    }
}
