package org.noandish.library.database

/**
 * Created by AliasgharMirzazade on 10/11/2018 AD.
 */
interface InsertAllResponse {
    fun insertedAll(response: ArrayList<InsertResponseItem>)
}