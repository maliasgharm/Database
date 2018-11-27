package org.noandish.library.databaseexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.noandish.library.database.Database
import org.noandish.library.database.ReadResponseAll

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val db = Database(this,Ranges.getTable())

        db.readAll(object : ReadResponseAll{
            override fun read(items: ArrayList<HashMap<String, Any>>) {

            }
        })
    }
}
