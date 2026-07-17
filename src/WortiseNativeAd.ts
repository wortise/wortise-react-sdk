import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import {
  WortiseNativeAdData,
  WortiseNativeAdImage,
  WortiseNativeMediaContent,
} from './WortiseNativeAdData';
import { WortiseRequestParameters } from './WortiseRequestParameters';
import { WortiseRevenueData } from './WortiseRevenueData';

const { RNWortiseNativeAdLoader } = NativeModules;

const emitter = new NativeEventEmitter(RNWortiseNativeAdLoader);

type NativeAdEventMap = {
  onNativeClicked: null;
  onNativeImpression: null;
  onNativeRevenuePaid: WortiseRevenueData;
};

export type NativeAdEvent = keyof NativeAdEventMap;

export default class WortiseNativeAd {
  private _data: WortiseNativeAdData;
  private _subscriptions: EmitterSubscription[] = [];

  private constructor(data: WortiseNativeAdData) {
    this._data = data;
  }

  static createForAdRequest(
    adUnitId: string,
    requestParameters?: WortiseRequestParameters,
  ): Promise<WortiseNativeAd> {
    return RNWortiseNativeAdLoader.loadAd(adUnitId, requestParameters).then(
      (data: WortiseNativeAdData) => new WortiseNativeAd(data),
    );
  }

  get advertiser(): string | undefined {
    return this._data.advertiser;
  }

  get body(): string | undefined {
    return this._data.body;
  }

  get callToAction(): string | undefined {
    return this._data.callToAction;
  }

  get headline(): string | undefined {
    return this._data.headline;
  }

  get icon(): WortiseNativeAdImage | undefined {
    return this._data.icon;
  }

  get images(): WortiseNativeAdImage[] | undefined {
    return this._data.images;
  }

  get mediaContent(): WortiseNativeMediaContent | undefined {
    return this._data.mediaContent;
  }

  get price(): string | undefined {
    return this._data.price;
  }

  get store(): string | undefined {
    return this._data.store;
  }

  get rating(): number | undefined {
    return this._data.rating;
  }

  get responseId(): string {
    return this._data.responseId;
  }

  addEventListener<K extends NativeAdEvent>(
    eventType: K,
    handler: (event: NativeAdEventMap[K]) => void,
  ): EmitterSubscription {
    const responseId = this._data.responseId;

    const subscription = emitter.addListener(
      eventType,
      (event: { responseId: string; data?: NativeAdEventMap[K] }) => {
        if (event.responseId === responseId) {
          handler(event.data as NativeAdEventMap[K]);
        }
      },
    );

    this._subscriptions.push(subscription);

    return subscription;
  }

  destroy(): void {
    this.removeAllEventListeners();

    RNWortiseNativeAdLoader.destroyAd(this._data.responseId);
  }

  removeAllEventListeners(): void {
    this._subscriptions.forEach(sub => sub.remove());
    this._subscriptions = [];
  }
}
