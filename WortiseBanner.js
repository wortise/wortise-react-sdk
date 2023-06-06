import React, { Component } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
} from "react-native";
import PropTypes from "prop-types";
import { ViewPropTypes } from "deprecated-react-native-prop-types";

class WortiseBanner extends Component {
  constructor() {
    super();

    this.handleSizeChange = this.handleSizeChange.bind(this);

    this.state = {
      style: {},
    };
  }

  componentDidMount() {
    this.loadAd();
  }

  handleSizeChange(event) {
    const { height, width } = event.nativeEvent;

    this.setState({ style: { height, width } });

    if (this.props.onSizeChange) {
      this.props.onSizeChange(event.nativeEvent);
    }
  }

  loadAd() {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this._banner),
      UIManager.getViewManagerConfig("RNWortiseBanner").Commands.loadAd,
      null
    );
  }

  render() {
    return (
      <RNWortiseBanner
        {...this.props}
        onSizeChange={this.handleSizeChange}
        ref={(el) => (this._banner = el)}
        style={[this.props.style, this.state.style]}
      />
    );
  }
}

WortiseBanner.propTypes = {
  ...ViewPropTypes,

  adSize: PropTypes.object,
  adUnitId: PropTypes.string.isRequired,
  autoRefreshTime: PropTypes.number,

  onClicked: PropTypes.func,
  onFailed: PropTypes.func,
  onLoaded: PropTypes.func,
  onSizeChange: PropTypes.func,
};

const RNWortiseBanner = requireNativeComponent(
  "RNWortiseBanner",
  WortiseBanner
);

export default WortiseBanner;
