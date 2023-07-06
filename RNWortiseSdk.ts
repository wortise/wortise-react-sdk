import { NativeModules } from 'react-native';

const { RNWortiseSdk } = NativeModules;

export default {
  get isInitialized() {
    return RNWortiseSdk.isInitialized();
  },

  get isReady() {
    return RNWortiseSdk.isReady();
  },

  get version() {
    return RNWortiseSdk.getVersion();
  },

  initialize(assetKey: string) {
    return RNWortiseSdk.initialize(assetKey);
  },

  wait() {
    return RNWortiseSdk.wait();
  },
};
