package com.wortise.ads.react;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import com.wortise.ads.AdError;
import com.wortise.ads.AdSize;
import com.wortise.ads.banner.BannerAd;

public class RNWortiseBanner extends BannerAd implements BannerAd.Listener, LifecycleEventListener {

  public static final String EVENT_CLICKED     = "onClicked";
  public static final String EVENT_FAILED      = "onFailed";
  public static final String EVENT_IMPRESSION  = "onImpression";
  public static final String EVENT_LOADED      = "onLoaded";
  public static final String EVENT_SIZE_CHANGE = "onSizeChange";


  public RNWortiseBanner(ReactContext context) {
    super(context);

    setListener(this);

    context.addLifecycleEventListener(this);
  }


  private void sendEvent(String eventName, WritableMap params) {
    ReactContext reactContext = (ReactContext) getContext();
    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(getId(), eventName, params);
  }


  @Override
  public void onHostDestroy() {
    destroy();
  }

  @Override
  public void onHostPause() {
    pause();
  }

  @Override
  public void onHostResume() {
    resume();
  }

  @Override
  public void onBannerClicked(BannerAd ad) {
    sendEvent(EVENT_CLICKED, null);
  }

  @Override
  public void onBannerFailed(BannerAd ad, AdError error) {
    WritableMap event = Arguments.createMap();

    event.putString("message", error.toString());
    event.putString("name",    error.name());

    sendEvent(EVENT_FAILED, event);
  }

  @Override
  public void onBannerImpression(BannerAd ad) {
    sendEvent(EVENT_IMPRESSION, null);
  }

  @Override
  public void onBannerLoaded(BannerAd ad) {
    int height = getAdHeightPx();
    int width  = getAdWidthPx ();

    int left = getLeft();
    int top  = getTop ();

    measure(width, height);

    layout(left, top, left + width, top + height);

    sendOnSizeChangeEvent();

    sendEvent(EVENT_LOADED, null);
  }


  private void sendOnSizeChangeEvent() {
    WritableMap event = Arguments.createMap();

    event.putDouble("height", getAdHeight());
    event.putDouble("width",  getAdWidth());

    sendEvent(EVENT_SIZE_CHANGE, event);
  }
}
