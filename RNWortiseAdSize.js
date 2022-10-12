class AdSize {
  constructor(width, height, type) {
    this.height = height ?? -1;
    this.width = width ?? -1;
    this.type = type ?? "normal";
  }

  static get HEIGHT_50() {
    return new AdSize(-1, 50);
  }

  static get HEIGHT_90() {
    return new AdSize(-1, 90);
  }

  static get HEIGHT_250() {
    return new AdSize(-1, 250);
  }

  static get HEIGHT_280() {
    return new AdSize(-1, 280);
  }

  static get MATCH_VIEW() {
    return new AdSize(-1, -1);
  }

  static getAnchoredAdaptiveBannerAdSize(width) {
    return new AdSize(width, -1, "anchored");
  }

  static getInlineAdaptiveBannerAdSize(width, maxHeight) {
    return new AdSize(width, maxHeight, "inline");
  }
}

export default AdSize;
