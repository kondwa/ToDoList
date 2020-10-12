package com.example.todolist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TodoListAdaptor(private val todoItemsList:ArrayList<TodoItem>,val activity: MainActivity):RecyclerView.Adapter<TodoListAdaptor.ViewHolder>() {
    class ViewHolder(val constraintLayout: ConstraintLayout):RecyclerView.ViewHolder(constraintLayout){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val constraintLayout = LayoutInflater.from(parent.context).inflate(R.layout.todo_item_layout,parent,false) as ConstraintLayout
        constraintLayout.setOnClickListener(View.OnClickListener {
            val listItemText = constraintLayout.getChildAt(0) as TextView
            val urgentTextView = constraintLayout.getChildAt(1) as TextView
            val item = listItemText.text
            val urgency = urgentTextView.text
            val isUrgent = if(urgency == "!!") true else false
            val intent: Intent = Intent(parent.context,AddItemActivity::class.java)
            intent.putExtra("ITEM",item)
            intent.putExtra("URGENCY",isUrgent)
            activity.startActivity(intent)
        })
        constraintLayout.setOnLongClickListener(View.OnLongClickListener {
            val position = parent.indexOfChild(it)
            activity.todoItemsList.removeAt(position)
            notifyItemRemoved(position)
            true
        })
        return ViewHolder(constraintLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val constraintLayout = holder.constraintLayout
        val listItemText = constraintLayout.getChildAt(0) as TextView
        val urgentTextView = constraintLayout.getChildAt(1) as TextView
        listItemText.text = todoItemsList[position].name
        urgentTextView.text = if(todoItemsList[position].isUrgent) "!!" else ""
    }

    override fun getItemCount(): Int {
        return todoItemsList.size
    }
}