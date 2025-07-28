import { ViewProps } from 'react-native';
import { WortiseRevenueData } from './WortiseRevenueData';

export type SizeChangeEvent = {
  width: number;
  height: number;
};

export interface WortiseBannerProps extends ViewProps {
  adSize?: object;

  adUnitId: string;

  autoRefreshTime?: number;

  onClicked: () => void;

  onFailed: (error: { message: string; name: string }) => void;

  onImpression: () => void;

  onLoaded: () => void;

  onRevenuePaid: (data: WortiseRevenueData) => void;

  onSizeChange: (event: { nativeEvent: SizeChangeEvent }) => void;
}
