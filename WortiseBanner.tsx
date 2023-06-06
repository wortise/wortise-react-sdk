import React, { createRef } from 'react';
import { findNodeHandle, requireNativeComponent, NativeMethods, UIManager } from 'react-native';
import { SizeChangeEvent, WortiseBannerProps } from './WortiseBannerProps';

type WortiseBannerState = {
  style: {
    width?: number;
    height?: number;
  };
};

type WortiseBannerView = React.Component<WortiseBannerProps> & NativeMethods;

class WortiseBanner extends React.Component<WortiseBannerProps, WortiseBannerState> {
  ref = createRef<WortiseBannerView>();

  constructor(props: WortiseBannerProps) {
    super(props);

    this.handleSizeChange = this.handleSizeChange.bind(this);

    this.state = {
      style: {},
    };
  }

  componentDidMount() {
    this.loadAd();
  }

  handleSizeChange(event: { nativeEvent: SizeChangeEvent }) {
    const { height, width } = event.nativeEvent;

    this.setState({ style: { height, width } });

    if (this.props.onSizeChange) {
      this.props.onSizeChange(event);
    }
  }

  loadAd() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.ref.current),
      UIManager.getViewManagerConfig('RNWortiseBanner').Commands.loadAd,
      undefined,
    );
  }

  render() {
    return (
      <RNWortiseBanner
        {...this.props}
        onSizeChange={this.handleSizeChange}
        ref={this.ref}
        style={[this.props.style, this.state.style]}
      />
    );
  }
}

const RNWortiseBanner = requireNativeComponent<WortiseBannerProps>('RNWortiseBanner');

export default WortiseBanner;
