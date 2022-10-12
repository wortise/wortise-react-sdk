import { NativeModules } from "react-native";

const { RNWortiseAdSettings } = NativeModules;

export default {
  get assetKey() {
    return RNWortiseAdSettings.getAssetKey();
  },

  get isChildDirected() {
    return RNWortiseAdSettings.isChildDirected();
  },

  get maxAdContentRating() {
    return RNWortiseAdSettings.getMaxAdContentRating();
  },

  get userId() {
    return RNWortiseAdSettings.getUserId();
  },

  setChildDirected(enabled) {
    RNWortiseAdSettings.setChildDirected(enabled);
  },

  setMaxAdContentRating(rating) {
    RNWortiseAdSettings.setMaxAdContentRating(rating);
  },

  setUserId(userId) {
    RNWortiseAdSettings.setUserId(userId);
  },
};
