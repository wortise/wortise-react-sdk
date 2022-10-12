package com.wortise.ads.react;

import android.app.Activity;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.wortise.ads.data.DataManager;
import com.wortise.ads.user.UserGender;

import java.lang.Integer;
import java.util.List;

public class RNWortiseDataManagerModule extends ReactContextBaseJavaModule {

  public RNWortiseDataManagerModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNWortiseDataManager";
  }

  @ReactMethod
  public void addEmail(String email) {
    DataManager.addEmail(getReactApplicationContext(), email);
  }

  @ReactMethod
  public void getAge(Promise promise) {
    promise.resolve(DataManager.getAge(getReactApplicationContext()));
  }

  @ReactMethod
  public void getEmails(Promise promise) {
    promise.resolve(DataManager.getEmails(getReactApplicationContext()));
  }

  @ReactMethod
  public void getGender(Promise promise) {
    UserGender gender = DataManager.getGender(getReactApplicationContext());

    if (gender == null) {
      promise.resolve(null);
      return;
    }

    promise.resolve(gender.name());
  }

  @ReactMethod
  public void setAge(int age) {
    DataManager.setAge(getReactApplicationContext(), age);
  }

  @ReactMethod
  public void setEmails(List<String> list) {
    DataManager.setEmails(getReactApplicationContext(), list);
  }

  @ReactMethod
  public void setGender(String gender) {
    UserGender value;

    try {
      value = UserGender.valueOf(gender);
    } catch (Throwable t) {
      value = null;
    }

    DataManager.setGender(getReactApplicationContext(), value);
  }
}
