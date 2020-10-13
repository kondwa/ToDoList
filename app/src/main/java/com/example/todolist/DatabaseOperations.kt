package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class DatabaseOperations(context:Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        const val DATABASE_NAME = "TodoItems.db"
        const val DATABASE_VERSION = 1
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DatabaseInfo.SQL_CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DatabaseInfo.SQL_DELETE_TABLE_QUERY)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db,oldVersion,newVersion)
    }
    fun addItem(dbo:DatabaseOperations,todoItem: TodoItem){
        val db = dbo.writableDatabase
        val itemName = todoItem.name
        val itemUrgency = if (todoItem.isUrgent == true) 1 else 0
        val itemDate = todoItem.getDateAsString()
        val values = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,itemName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY,itemUrgency)
            put(DatabaseInfo.TableInfo.COLUMN_DATE,itemDate)
        }
        val rowId = db.insert(DatabaseInfo.TableInfo.TABLE_NAME,null,values)
    }
    fun getAllItems(dbo: DatabaseOperations):Cursor{
         val db = dbo.readableDatabase
        val projection = arrayOf(
            BaseColumns._ID,
            DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,
            DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY,
            DatabaseInfo.TableInfo.COLUMN_DATE
        )
        val selection = ""
        val selectionArgs = null
        val sortOrder = null
        val cursor = db.query(
            DatabaseInfo.TableInfo.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        return cursor
    }
    fun updateItem(dbo: DatabaseOperations,oldItem: TodoItem,newItem: TodoItem){
        val db = dbo.writableDatabase
        val itemName = newItem.name
        val itemUrgency = if(newItem.isUrgent == true) 1 else 0
        val itemDate = newItem.getDateAsString()
        val newValues = ContentValues().apply {
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME,itemName)
            put(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY,itemUrgency)
            put(DatabaseInfo.TableInfo.COLUMN_DATE,itemDate)
        }
        val whereClause = DatabaseInfo.TableInfo.COLUMN_ITEM_NAME +" LIKE ?"
        val whereArgs = arrayOf(oldItem.name)
        val count = db.update(DatabaseInfo.TableInfo.TABLE_NAME, newValues, whereClause, whereArgs)
    }
    fun deleteItem(dbo: DatabaseOperations,todoItem: TodoItem){
        val db =dbo.writableDatabase
        val whereClause = "${DatabaseInfo.TableInfo.COLUMN_ITEM_NAME} LIKE ?"
        val whereArgs = arrayOf(todoItem.name)
        val deletedRows  = db.delete(DatabaseInfo.TableInfo.TABLE_NAME,whereClause,whereArgs)
    }
}