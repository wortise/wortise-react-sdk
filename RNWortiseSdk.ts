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

  initialize(assetKey: string, start?: boolean) {
    return RNWortiseSdk.initialize(assetKey, start ?? true);
  },

  start() {
    RNWortiseSdk.start();
  },

  stop() {
    RNWortiseSdk.stop();
  },

  wait() {
    return RNWortiseSdk.wait();
  },
};
