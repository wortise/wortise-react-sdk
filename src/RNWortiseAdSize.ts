class AdSize {
  height: number;
  type: string;
  width: number;

  constructor(width?: number, height?: number, type?: string) {
    this.height = height ?? -1;
    this.width = width ?? -1;
    this.type = type ?? 'normal';
  }

  static readonly HEIGHT_50 = new AdSize(-1, 50);

  static readonly HEIGHT_90 = new AdSize(-1, 90);

  static readonly HEIGHT_250 = new AdSize(-1, 250);

  static readonly HEIGHT_280 = new AdSize(-1, 280);

  static readonly MATCH_VIEW = new AdSize(-1, -1);

  static getAnchoredAdaptiveBannerAdSize(width: number) {
    return new AdSize(width, -1, 'anchored');
  }

  static getInlineAdaptiveBannerAdSize(width: number, maxHeight?: number) {
    return new AdSize(width, maxHeight, 'inline');
  }
}

export default AdSize;
