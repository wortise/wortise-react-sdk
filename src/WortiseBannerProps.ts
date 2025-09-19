import { ViewProps } from 'react-native';
import { WortiseRevenueData } from './WortiseRevenueData';
import AdSize from './RNWortiseAdSize';

export type SizeChangeEvent = {
  width: number;
  height: number;
};

export interface WortiseBannerProps extends ViewProps {
  adSize?: AdSize;

  adUnitId: string;

  autoRefreshTime?: number;

  onClicked?: () => void;

  onFailedToLoad?: (error: { message: string; name: string }) => void;

  onImpression?: () => void;

  onLoaded?: () => void;

  onRevenuePaid?: (data: WortiseRevenueData) => void;

  onSizeChange?: (event: { nativeEvent: SizeChangeEvent }) => void;
}
