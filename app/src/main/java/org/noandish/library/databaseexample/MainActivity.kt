package org.noandish.library.databaseexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.noandish.library.database.Database
import org.noandish.library.database.InsertAllResponse
import org.noandish.library.database.InsertResponseItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Database(this, User.getTable())

        val list = ArrayList<User>()
        list.add(User("aaa", "dddd"))
        list.add(User("bbb", "eeeee"))
        list.add(User("ccc", "fffff"))



        db.insertAll(User.arrayRangeToArrayHash(list), object : InsertAllResponse {
            override fun insertedAll(response: ArrayList<InsertResponseItem>) {
                val test = User.findWithId(response!![1].id, baseContext)
                Log.w("test", "${response!![1].id}" + test?.fname ?: "null")
            }
        })

    }
}
