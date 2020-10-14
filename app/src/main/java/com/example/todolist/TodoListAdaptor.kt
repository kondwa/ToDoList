package com.example.todolist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListAdaptor(private val todoItemsList:ArrayList<TodoItem>,val activity: MainActivity):RecyclerView.Adapter<TodoListAdaptor.ViewHolder>() {
    class ViewHolder(val constraintLayout: ConstraintLayout):RecyclerView.ViewHolder(constraintLayout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val constraintLayout = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_layout,parent,false) as ConstraintLayout
        constraintLayout.setOnClickListener(View.OnClickListener {
            val listItemText = constraintLayout.linearLayout.getChildAt(0) as TextView
            val urgentTextView = constraintLayout.linearLayout.getChildAt(1) as TextView
            val itemName = listItemText.text
            val urgency = urgentTextView.text
            val isUrgent = if(urgency == "!!") true else false
            val intent: Intent = Intent(parent.context,AddItemActivity::class.java)
            intent.putExtra("ITEM_NAME",itemName)
            intent.putExtra("ITEM_URGENCY",isUrgent)
            activity.startActivity(intent)
        })
        constraintLayout.setOnLongClickListener(View.OnLongClickListener {
            val position = parent.indexOfChild(it)
            val item = this.todoItemsList[position]
            val dbo = DatabaseOperations(parent.context)
            dbo.deleteItem(dbo,item)
            this.todoItemsList.removeAt(position)
            notifyItemRemoved(position)
            true
        })
        return ViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val constraintLayout = holder.constraintLayout
        val listItemText = constraintLayout.linearLayout.getChildAt(0) as TextView
        val urgentTextView = constraintLayout.linearLayout.getChildAt(1) as TextView
        val dateTextView = constraintLayout.getChildAt(0) as TextView
        listItemText.text = todoItemsList[position].name
        urgentTextView.text = if(todoItemsList[position].isUrgent) "!!" else ""
        dateTextView.text = todoItemsList[position].dateString
    }

    override fun getItemCount(): Int {
        return todoItemsList.size
    }
}