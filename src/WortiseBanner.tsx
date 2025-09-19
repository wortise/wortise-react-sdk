import React, { createRef } from 'react';
import { findNodeHandle, requireNativeComponent, NativeMethods, UIManager } from 'react-native';
import { SizeChangeEvent, WortiseBannerProps } from './WortiseBannerProps';
import AdSize from './RNWortiseAdSize';

type WortiseBannerStyle = {
  width?: number | string;
  height?: number | string;
};

type WortiseBannerState = {
  style: WortiseBannerStyle;
};

type WortiseBannerView = React.Component<WortiseBannerProps> & NativeMethods;

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

  private adSize: AdSize;

  ref = createRef<WortiseBannerView>();

  constructor(props: WortiseBannerProps) {
    super(props);

    this.adSize = props.adSize || AdSize.HEIGHT_50;

    this.handleSizeChange = this.handleSizeChange.bind(this);

    const style: WortiseBannerStyle = {};

    if (this.adSize.height > 0) {
      style.height = this.adSize.height;
    }

    style.width = this.adSize.width > 0 ? this.adSize.width : '100%';

    this.state = {
      style,
    };
  }

  handleSizeChange(event: { nativeEvent: SizeChangeEvent }) {
    const { height, width } = event.nativeEvent;

    this.setState({ style: { height, width } });

    if (this.props.onSizeChange) {
      this.props.onSizeChange(event);
    }
  }

  loadAd() {
    UIManager.dispatchViewManagerCommand(findNodeHandle(this.ref.current), 'loadAd', []);
  }

  render() {
    return (
      <RNWortiseBanner
        {...this.props}
        adSize={this.adSize}
        onSizeChange={this.handleSizeChange}
        ref={this.ref}
        style={[this.props.style, this.state.style]}
      />
    );
  }
}

const RNWortiseBanner = requireNativeComponent<WortiseBannerProps>('RNWortiseBanner');

export default WortiseBanner;
