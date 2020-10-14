package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var recyclerAdaptor: TodoListAdaptor
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    var todoItemsList = ArrayList<TodoItem>()
    var todaysItemsList = ArrayList<TodoItem>()
    var pastItemsList = ArrayList<TodoItem>()
    private lateinit var todaysItemsButton:Button
    private lateinit var pastItemsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor){
            while(moveToNext()){
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))
                val isUrgent = if(itemUrgency==0)false else true
                var newItem = TodoItem(itemName,isUrgent)
                newItem.dateString = itemDate
                todoItemsList.add(newItem)
                if(itemDate == getDateAsString()){
                    todaysItemsList.add(newItem)
                }else{
                    pastItemsList.add(newItem)
                }
            }
        }

        //todoItemsList.add(TodoItem("Buy groceris"))
        //todoItemsList.add(TodoItem("Do laundry",true))
        //todoItemsList.add(TodoItem("Play guitar",false))
        todaysItemsButton = findViewById(R.id.items_today_button)
        pastItemsButton = findViewById(R.id.items_past_button)
        todoListRecyclerView = findViewById(R.id.todolist_recycler_view)
        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdaptor = TodoListAdaptor(todoItemsList,this)

        todoListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdaptor
        }
    }
    public fun addItemAction(View: View){
        val intent:Intent = Intent(this,AddItemActivity::class.java)
        startActivity(intent)
    }
    public fun displayTodaysItems(view: View){
        recyclerAdaptor = TodoListAdaptor(todaysItemsList,this)

        todoListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdaptor
        }
        todaysItemsButton.background=getDrawable(R.color.pureBlack)
        todaysItemsButton.setTextColor(getColor(R.color.pureWhite))
        pastItemsButton.background=getDrawable(R.color.pureWhite)
        pastItemsButton.setTextColor(getColor(R.color.pureBlack))
    }
    public fun displayPastItems(view: View){
        recyclerAdaptor = TodoListAdaptor(pastItemsList,this)

        todoListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdaptor
        }
        todaysItemsButton.background=getDrawable(R.color.pureWhite)
        todaysItemsButton.setTextColor(getColor(R.color.pureBlack))
        pastItemsButton.background=getDrawable(R.color.pureBlack)
        pastItemsButton.setTextColor(getColor(R.color.pureWhite))
    }
    fun getDateAsString():String{
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$year/$month/$day"
    }
}