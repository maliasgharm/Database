package org.noandish.library.databaseexample

import android.content.Context
import org.json.JSONObject
import org.noandish.library.database.*
import org.noandish.library.database.Database.Companion.KEY_ID
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class User(val fname: String, val lname: String) {

    var id: Int? = null
    fun tableItemClass(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap[KEY_FNAME] = fname
        hashMap[KEY_LNAME] = lname
        return hashMap
    }

    companion object {
        private val KEY_LNAME = "fname"
        private val KEY_FNAME = "lname"

        private val NAME_TABLE = "tb_user"

        private fun convertJsonObjectToHashMap(jsonObject: JSONObject): HashMap<String, Any> {
            val hashMap = HashMap<String, Any>()
            for (key in jsonObject.keys()) {
                hashMap.put(key, jsonObject[key])
            }
            return hashMap
        }


        private fun convertHashMapToJson(hashMap: HashMap<String, Any>): JSONObject {
            val jsonObject = JSONObject()
            for (key in hashMap.keys) {
                jsonObject.put(key, hashMap[key])
            }
            return jsonObject
        }


        fun getTable(): Table {
            val rows = ArrayList<Row>()
            rows.add(Row(KEY_LNAME, Row.TYPE_STRING))
            rows.add(Row(KEY_FNAME, Row.TYPE_STRING))

            return Table(NAME_TABLE, rows)
        }

        fun hashMapToEventItem(items: ArrayList<HashMap<String, Any>>): ArrayList<User> {
            val listEventItem = ArrayList<User>()
            for (item in items) {
                listEventItem.add(hashMapToEventItem(item))
            }
            return listEventItem
        }

        fun hashMapToEventItem(items: HashMap<String, Any>): User {
            return User(items[KEY_FNAME] as String,(items[KEY_LNAME] as String).toDouble())
        }

        fun arrayRangeToArrayHash(items: ArrayList<User>): ArrayList<HashMap<String, Any>> {
            val list = ArrayList<HashMap<String, Any>>()
            for (item in items) {
                list.add(item.tableItemClass())
            }
            return list
        }

        fun findWithId(id: Long, context: Context): User? {
            val db = Database(context, User.getTable())
            db.addFilterItem(KEY_ID, id)
            val values = db.readAll()
            val read = hashMapToEventItem(values)
            return if (read.size > 0) read[0] else null
        }
    }

}