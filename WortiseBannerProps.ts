import { ViewProps } from 'react-native';

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

  onLoaded: () => void;

  onSizeChange: (event: { nativeEvent: SizeChangeEvent }) => void;
}
