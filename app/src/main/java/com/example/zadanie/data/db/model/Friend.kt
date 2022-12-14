package com.example.zadanie.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
class Friend (
    @PrimaryKey val id: String,
    val name: String,
    val barName: String?,
    val barId: String?
){

    override fun toString(): String {
        return "Friend(id='$id', name='$name', bar:'$barName')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Friend) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (barName != other.barName) return false
        if (barId != other.barId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + barName.hashCode()
        result = 31 * result + barId.hashCode()

        return result
    }
}