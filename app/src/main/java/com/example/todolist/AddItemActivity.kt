package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

class AddItemActivity : AppCompatActivity() {
    private lateinit var itemEditText: EditText
    private lateinit var urgentCheckBox: CheckBox
    private lateinit var titleTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        itemEditText = findViewById(R.id.list_item_edit_text)
        urgentCheckBox = findViewById(R.id.urgent_checkbox)
        titleTextView = findViewById(R.id.add_item_text_view)

        if(intent.hasExtra("ITEM")) {
            val item:String = intent.getStringExtra("ITEM")
            val urgency = intent.getBooleanExtra("URGENCY", false)

            if (item != null) {
                itemEditText.setText(item)
                titleTextView.text = "Edit List Item"
            }
            if (urgency == true) {
                urgentCheckBox.isChecked = true
            }
        }

    }
    public fun saveAction(view: View){

    }
    public fun cancelAction(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}