package com.example.data.local

import com.raizlabs.android.dbflow.config.DatabaseDefinition
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.sql.language.SQLCondition
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.raizlabs.android.dbflow.structure.Model

class LocalStorage {

    private val mDataBase: DatabaseDefinition = FlowManager.getDatabase(AppDataBase::class.java)

    fun saveObjectList(clazz: Class<out Model>, list: List<Model>) {
        val adapter = mDataBase.getModelAdapterForTable(clazz)
        adapter.insertAll(list)
    }

    fun deleteAllObjects(clazz: Class<out Model>) {
        val adapter = mDataBase.getModelAdapterForTable(clazz)
        adapter.deleteAll(retrieveAllObjects(clazz))
    }

    fun retrieveAllObjects(clazz: Class<out Model>): List<Model> {
        return SQLite.select().from(clazz).queryList()
    }

    fun retrieveSingleItem(clazz: Class<out Model>, condition: SQLCondition): Model {
        return SQLite.select().from(clazz).
                where(condition).
                queryList().
                first()
    }

}