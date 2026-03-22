import React from 'react';
import { requireNativeComponent, ViewProps } from 'react-native';
import WortiseNativeAd from './WortiseNativeAd';

export interface WortiseNativeAdViewProps extends ViewProps {
  factoryId: string;
  nativeAd: WortiseNativeAd;
}

const RNWortiseNativeAdView = requireNativeComponent<
  ViewProps & { factoryId: string; responseId: string }
>('RNWortiseNativeAdView');

const WortiseNativeAdView: React.FC<WortiseNativeAdViewProps> = ({
  factoryId,
  nativeAd,
  ...rest
}) => {
  return <RNWortiseNativeAdView factoryId={factoryId} responseId={nativeAd.responseId} {...rest} />;
};

export default WortiseNativeAdView;
