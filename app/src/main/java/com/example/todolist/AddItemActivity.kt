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
    private var isNewItem = true
    private lateinit var oldItem: TodoItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        itemEditText = findViewById(R.id.list_item_edit_text)
        urgentCheckBox = findViewById(R.id.urgent_checkbox)
        titleTextView = findViewById(R.id.add_item_text_view)

        if(intent.hasExtra("ITEM_NAME")) {
            val itemName:String = intent.getStringExtra("ITEM_NAME")
            val urgency = intent.getBooleanExtra("ITEM_URGENCY", false)

            if (itemName != null) {
                itemEditText.setText(itemName)
                titleTextView.text = "Edit List Item"
                isNewItem = false
                oldItem = TodoItem(itemName)
            }
            if (urgency == true) {
                urgentCheckBox.isChecked = true
                oldItem.isUrgent = urgency
            }
        }

    }
    public fun saveAction(view: View){
        var itemName:String = itemEditText.text.toString()
        val isUrgent = urgentCheckBox.isChecked
        val newItem = TodoItem(itemName,isUrgent)
        val dbo = DatabaseOperations(this)
        if(isNewItem){
            dbo.addItem(dbo,newItem)
        }else{
            dbo.updateItem(dbo,oldItem,newItem)
        }
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    public fun cancelAction(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}