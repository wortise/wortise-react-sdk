export type WortiseNativeAdImage = {
  height?: number;
  scale?: number;
  uri?: string;
  width?: number;
};

export type WortiseNativeMediaContent = {
  aspectRatio?: number;
};

export type WortiseNativeAdData = {
  advertiser?: string;
  body?: string;
  callToAction?: string;
  headline?: string;
  icon?: WortiseNativeAdImage;
  images?: WortiseNativeAdImage[];
  mediaContent?: WortiseNativeMediaContent;
  price?: string;
  rating?: number;
  responseId: string;
  store?: string;
};
