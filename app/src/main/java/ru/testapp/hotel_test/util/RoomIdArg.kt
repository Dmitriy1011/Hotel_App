package ru.testapp.hotel_test.util

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object RoomIdArg : ReadWriteProperty<Bundle, Int> {
    override fun getValue(thisRef: Bundle, property: KProperty<*>): Int =
        thisRef.getInt(property.name)

    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Int) {
        thisRef.putInt(property.name, value)
    }

}