package org.noandish.library.databaseexample

import org.json.JSONObject
import org.noandish.library.database.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

//"range" : {"order" : Int , "lat":double , "lng" : double , "alt": double }
class Ranges(val order: Int, val latLngRange: Double, val alt: Double, var group_id: Int) {

    var id: Int? = null
    fun tableItemClass(): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        hashMap[KEY_ORDER] = order
        hashMap[KEY_LAT] = latLngRange
        hashMap[KEY_LNG] = latLngRange
        hashMap[KEY_ALT] = alt

        hashMap[KEY_GROUP_ID] = group_id
        hashMap[KEY_DATE] = Date().time
        return hashMap
    }

    companion object {
        private val KEY_ORDER = "\"order\""
        private val KEY_LAT = "lat"
        private val KEY_LNG = "lng"
        private val KEY_ALT = "alt"
        private val KEY_GROUP_ID = "id_group"
        private val KEY_DATE = "date"
        private val NAME_TABLE = "range_table"

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
            rows.add(Row(KEY_ORDER, Row.TYPE_INTEGER))
            rows.add(Row(KEY_LAT, Row.TYPE_DOUBLE))
            rows.add(Row(KEY_LNG, Row.TYPE_DOUBLE))
            rows.add(Row(KEY_ALT, Row.TYPE_DOUBLE))
            rows.add(Row(KEY_GROUP_ID, Row.TYPE_INTEGER))
            rows.add(Row(KEY_DATE, Row.TYPE_LONG))

            return Table(NAME_TABLE, rows)
        }

        class RangesList(val list: ArrayList<Double>, val id_range: Int)

        fun hashMapToEventItem(items: ArrayList<HashMap<String, Any>>): ArrayList<RangesList> {
            val allIdGroups = ArrayList<Int>()

            val arrayListRanges = ArrayList<RangesList>()

            val listEventItem = ArrayList<Ranges>()
            for (item in items) {
                listEventItem.add(hashMapToEventItem(item))
                if (!allIdGroups.contains(item[KEY_GROUP_ID] as Int))
                    allIdGroups.add(item[KEY_GROUP_ID] as Int)
            }
            val sortedList = listEventItem.sortedWith(compareBy(Ranges::group_id))

            for (id in allIdGroups) {
                val list = ArrayList<Double>()
                for (item in sortedList) {
                    if (item.group_id == id) {
                        list.add(item.latLngRange)
                    }
                }
                arrayListRanges.add(RangesList(list, id))
            }

            return arrayListRanges
        }

        fun hashMapToEventItem(items: HashMap<String, Any>): Ranges {
            return Ranges(items[KEY_ORDER] as Int, 0.0, items[KEY_LAT] as Double, items[KEY_LAT] as Int)
        }
    }
}