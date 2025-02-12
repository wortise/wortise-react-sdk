package com.wortise.ads.react;

import android.content.Context;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.wortise.ads.AdSize;
import com.wortise.ads.banner.BannerAd;

import java.util.Map;

public class RNWortiseBanner extends SimpleViewManager<RNWortiseBannerView> {

  public static final int COMMAND_LOAD_AD = 1;


  @Override
  protected RNWortiseBannerView createViewInstance(ThemedReactContext reactContext) {
    return new RNWortiseBannerView(reactContext);
  }

  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of("loadAd", COMMAND_LOAD_AD);
  }

  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    String[] events = {
      RNWortiseBannerView.EVENT_CLICKED,
      RNWortiseBannerView.EVENT_FAILED_TO_LOAD,
      RNWortiseBannerView.EVENT_IMPRESSION,
      RNWortiseBannerView.EVENT_LOADED,
      RNWortiseBannerView.EVENT_SIZE_CHANGE
    };

    MapBuilder.Builder<String, Object> builder = MapBuilder.builder();

    for (int i = 0; i < events.length; i++) {
      builder.put(events[i], MapBuilder.of("registrationName", events[i]));
    }

    return builder.build();
  }

  @Override
  public String getName() {
    return "RNWortiseBanner";
  }

  @Override
  public void receiveCommand(RNWortiseBannerView view, int commandId, ReadableArray args) {
    switch (commandId) {
    case COMMAND_LOAD_AD:
      view.loadAd();
      break;
    }
  }


  private AdSize getAdSize(Context context, ReadableMap adSize) {
    int height = adSize.getInt("height");
    int width  = adSize.getInt("width");

    String type = adSize.getString("type");

    switch (type) {
    case "anchored":
      return AdSize.getAnchoredAdaptiveBannerAdSize(context, width);

    case "inline":
      return AdSize.getInlineAdaptiveBannerAdSize(context, width, height);

    default:
      return new AdSize(width, height);
    }
  }

  
  @ReactProp(name = "adSize")
  public void setAdSize(RNWortiseBannerView view, ReadableMap adSize) {
    if (adSize == null) {
      return;
    }

    AdSize size = getAdSize(view.getContext(), adSize);

    view.setAdSize(size);
  }

  @ReactProp(name = "adUnitId")
  public void setAdUnitId(RNWortiseBannerView view, String adUnitId) {
    view.setAdUnitId(adUnitId);
  }

  @ReactProp(name = "autoRefreshTime", defaultInt = 0)
  public void setAutoRefresh(RNWortiseBannerView view, int autoRefreshTime) {
    view.setAutoRefreshTime(autoRefreshTime);
  }
}
