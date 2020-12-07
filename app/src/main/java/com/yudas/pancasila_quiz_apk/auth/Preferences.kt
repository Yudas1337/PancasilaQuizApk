package com.yudas.pancasila_quiz_apk.auth

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.android.material.internal.ContextUtils.getActivity


class Preferences(activity: Activity) {

    private val preferences : SharedPreferences = activity.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    private val editor : SharedPreferences.Editor = preferences.edit()

//    Integer
    fun setInt(KEY : String, VALUE : Int){
        editor.putInt(KEY, VALUE)
        editor.apply()
    }

    fun getInt(KEY : String, DEFAULT : Int) : Int {
        return preferences.getInt(KEY, DEFAULT)
    }
    
//    String
    fun setString(KEY : String, VALUE : String?){
        editor.putString(KEY, VALUE)
        editor.apply()
    }

    fun getString(KEY : String, DEFAULT : String) : String? {
        return preferences.getString(KEY, DEFAULT)
    }

    fun clear() {
       return editor.clear().apply()
    }
}