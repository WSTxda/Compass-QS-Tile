package com.wstxda.toolkit.utils.counter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit

object CounterValue {
    private const val PREFS_NAME = "CounterPrefs"
    private const val KEY_COUNTER = "counterValue"
    const val ACTION_COUNTER_UPDATED = "com.wstxda.toolkit.COUNTER_UPDATED"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getValue(context: Context): Int {
        return getPrefs(context).getInt(KEY_COUNTER, 0)
    }

    fun setValue(context: Context, value: Int) {
        getPrefs(context).edit { putInt(KEY_COUNTER, value) }
        val intent = Intent(ACTION_COUNTER_UPDATED)
        context.sendBroadcast(intent)
    }

    fun add(context: Context): Int {
        val newValue = getValue(context) + 1
        setValue(context, newValue)
        return newValue
    }

    fun remove(context: Context): Int {
        val newValue = getValue(context) - 1
        setValue(context, newValue)
        return newValue
    }

    fun reset(context: Context) {
        setValue(context, 0)
    }
}