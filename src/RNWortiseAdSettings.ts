import { NativeModules } from 'react-native';
import RNWortiseAdContentRating from './RNWortiseAdContentRating';

const { RNWortiseAdSettings } = NativeModules;

export default {
  get assetKey() {
    return RNWortiseAdSettings.getAssetKey();
  },

  get isChildDirected() {
    return RNWortiseAdSettings.isChildDirected();
  },

  get isTestEnabled() {
    return RNWortiseAdSettings.isTestEnabled();
  },

  get maxAdContentRating() {
    return RNWortiseAdSettings.getMaxAdContentRating();
  },

  get userId() {
    return RNWortiseAdSettings.getUserId();
  },

  setChildDirected(enabled: boolean) {
    RNWortiseAdSettings.setChildDirected(enabled);
  },

  setMaxAdContentRating(rating?: RNWortiseAdContentRating) {
    RNWortiseAdSettings.setMaxAdContentRating(rating);
  },

  setTestEnabled(enabled: boolean) {
    RNWortiseAdSettings.setTestEnabled(enabled);
  },

  setUserId(userId?: string) {
    RNWortiseAdSettings.setUserId(userId);
  },
};
