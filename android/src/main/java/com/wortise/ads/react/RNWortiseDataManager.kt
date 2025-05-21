package com.wortise.ads.react

import android.app.Activity
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.wortise.ads.data.DataManager
import com.wortise.ads.extensions.tryOrNull
import com.wortise.ads.user.UserGender
import java.lang.Integer
import java.util.ArrayList
import java.util.List

class RNWortiseDataManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String = "RNWortiseDataManager"

  @ReactMethod
  fun addEmail(email: String) {
    DataManager.addEmail(reactApplicationContext, email)
  }

  @ReactMethod
  fun getAge(promise: Promise) {
    promise.resolve(DataManager.getAge(reactApplicationContext))
  }

  @ReactMethod
  fun getEmails(promise: Promise) {
    promise.resolve(DataManager.getEmails(reactApplicationContext))
  }

  @ReactMethod
  fun getGender(promise: Promise) {
    val gender = DataManager.getGender(reactApplicationContext)

    if (gender == null) {
      promise.resolve(null)
      return
    }

    promise.resolve(gender.name)
  }

  @ReactMethod
  fun setAge(age: Int?) {
    DataManager.setAge(reactApplicationContext, age)
  }

  @ReactMethod
  fun setEmails(array: ReadableArray) {
    val list = mutableListOf<String>()

    for (i in 0 until array.size()) {
      list.add(array.getString(i))
    }

    DataManager.setEmails(reactApplicationContext, list)
  }

  @ReactMethod
  fun setGender(gender: String?) {
    val value = gender?.let {
      tryOrNull { UserGender.valueOf(gender) }
    }

    DataManager.setGender(reactApplicationContext, value)
  }
}
