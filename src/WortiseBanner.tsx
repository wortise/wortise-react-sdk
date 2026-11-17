import React, { createRef } from 'react';
import { UIManager, findNodeHandle, requireNativeComponent } from 'react-native';
import AdSize from './RNWortiseAdSize';
import { SizeChangeEvent, WortiseBannerProps } from './WortiseBannerProps';

type WortiseBannerStyle = {
  height?: number | string;
  width?: number | string;
};

type WortiseBannerState = {
  style: WortiseBannerStyle;
};

type WortiseBannerView = React.ComponentRef<typeof RNWortiseBanner>;

const bannerUIManager = UIManager as typeof UIManager & {
  dispatchViewManagerCommand(reactTag: number, command: 'loadAd', args: []): void;
};

class WortiseBanner extends React.Component<WortiseBannerProps, WortiseBannerState> {
  static get AUTO_REFRESH_DEFAULT_TIME() {
    return 60 * 1000;
  }

  static get AUTO_REFRESH_DISABLED() {
    return -1;
  }

  static get AUTO_REFRESH_MAX_TIME() {
    return 120 * 1000;
  }

  static get AUTO_REFRESH_MIN_TIME() {
    return 30 * 1000;
  }

  static get AUTO_REFRESH_UNSPECIFIED() {
    return 0;
  }

  ref: React.RefObject<WortiseBannerView | null> = createRef<WortiseBannerView>();

  constructor(props: WortiseBannerProps) {
    super(props);

    this.handleSizeChange = this.handleSizeChange.bind(this);

    const style = WortiseBanner.computeStyle(props.adSize);

    this.state = { style };
  }

  componentDidUpdate(prevProps: WortiseBannerProps) {
    const prevAdSize = prevProps.adSize || AdSize.HEIGHT_50;
    const nextAdSize = this.props.adSize || AdSize.HEIGHT_50;

    if (
      prevAdSize.width !== nextAdSize.width ||
      prevAdSize.height !== nextAdSize.height ||
      prevAdSize.type !== nextAdSize.type
    ) {
      const style = WortiseBanner.computeStyle(this.props.adSize);

      this.setState({ style });
    }
  }

  handleSizeChange(event: { nativeEvent: SizeChangeEvent }) {
    const { height, width } = event.nativeEvent;

    this.setState({ style: { height, width } });

    if (this.props.onSizeChange) {
      this.props.onSizeChange(event);
    }
  }

  loadAd() {
    const reactTag = findNodeHandle(this.ref.current);

    if (reactTag != null) {
      bannerUIManager.dispatchViewManagerCommand(reactTag, 'loadAd', []);
    }
  }

  render() {
    const adSize = this.props.adSize || AdSize.HEIGHT_50;

    return (
      <RNWortiseBanner
        {...this.props}
        adSize={adSize}
        onSizeChange={this.handleSizeChange}
        ref={this.ref}
        style={[this.props.style, this.state.style]}
      />
    );
  }

  private static computeStyle(adSize?: AdSize): WortiseBannerStyle {
    const size = adSize || AdSize.HEIGHT_50;

    const style: WortiseBannerStyle = {};

    if (size.height > 0) {
      style.height = size.height;
    }

    style.width = size.width > 0 ? size.width : '100%';

    return style;
  }
}

const RNWortiseBanner = requireNativeComponent<WortiseBannerProps>('RNWortiseBanner');

export default WortiseBanner;
