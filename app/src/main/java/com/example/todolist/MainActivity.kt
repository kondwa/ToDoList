package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var todoListRecyclerView: RecyclerView
    private lateinit var recyclerAdaptor: TodoListAdaptor
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    var todoItemsList = ArrayList<TodoItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor){
            while(moveToNext()){
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val isUrgent = if (itemUrgency==0)false else true
                todoItemsList.add(TodoItem(itemName,isUrgent))
            }
        }

        //todoItemsList.add(TodoItem("Buy groceris"))
        //todoItemsList.add(TodoItem("Do laundry",true))
        //todoItemsList.add(TodoItem("Play guitar",false))

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
}