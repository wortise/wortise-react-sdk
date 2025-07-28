import {
  AndroidConfig,
  ConfigPlugin,
  withAndroidManifest,
  withInfoPlist,
  withPlugins,
  withProjectBuildGradle,
} from '@expo/config-plugins';

type PluginParameters = {
  androidGoogleAppId?: string;
  iosGoogleAppId?: string;
};

const MAVEN_URLS = [
  'https://maven.wortise.com/artifactory/public',
  'https://android-sdk.is.com/',
  'https://artifact.bytedance.com/repository/pangle',
  'https://cboost.jfrog.io/artifactory/chartboost-ads/',
];

function addMavenRepository(gradle: string, url: string): string {
  if (gradle.includes(url)) return gradle;

  return gradle.replace(
    /allprojects\s*{[^]*?repositories\s*{/,
    match => `${match}\n        maven { url "${url}" }`,
  );
}

function addMavenRepositories(gradle: string): string {
  return MAVEN_URLS.reduce(addMavenRepository, gradle);
}

function addReplacingMainApplicationMetaDataItem(
  manifest: AndroidConfig.Manifest.AndroidManifest,
  itemName: string,
  itemValue: string,
): AndroidConfig.Manifest.AndroidManifest {
  AndroidConfig.Manifest.ensureToolsAvailable(manifest);

  const newItem = {
    $: {
      'android:name': itemName,
      'android:value': itemValue,
      'tools:replace': 'android:value',
    },
  } as AndroidConfig.Manifest.ManifestMetaData;

  const mainApplication = AndroidConfig.Manifest.getMainApplicationOrThrow(manifest);
  mainApplication['meta-data'] = mainApplication['meta-data'] ?? [];

  const existingItem = mainApplication['meta-data'].find(
    item => item.$['android:name'] === itemName,
  );

  if (existingItem) {
    existingItem.$['android:value'] = itemValue;
    existingItem.$['tools:replace' as keyof AndroidConfig.Manifest.ManifestMetaData['$']] =
      'android:value';
  } else {
    mainApplication['meta-data'].push(newItem);
  }

  return manifest;
}

const withAndroidGoogleAppId: ConfigPlugin<PluginParameters['androidGoogleAppId']> = (
  config,
  appId,
) => {
  if (appId === undefined) return config;

  return withAndroidManifest(config, config => {
    addReplacingMainApplicationMetaDataItem(
      config.modResults,
      'com.google.android.gms.ads.APPLICATION_ID',
      appId,
    );

    return config;
  });
};

const withIosGoogleAppId: ConfigPlugin<PluginParameters['iosGoogleAppId']> = (config, appId) => {
  if (appId === undefined) return config;

  return withInfoPlist(config, config => {
    config.modResults.GADApplicationIdentifier = appId;
    return config;
  });
};

const withMavenRepositories: ConfigPlugin = config => {
  return withProjectBuildGradle(config, config => {
    config.modResults.contents = addMavenRepositories(config.modResults.contents);
    return config;
  });
};

const withReactNativeWortise: ConfigPlugin<PluginParameters> = (
  config,
  { androidGoogleAppId, iosGoogleAppId } = {},
) => {
  if (androidGoogleAppId === undefined) {
    console.warn(
      "No 'androidGoogleAppId' was provided. The native Google Mobile Ads SDK will crash on Android without it.",
    );
  }

  if (iosGoogleAppId === undefined) {
    console.warn(
      "No 'iosGoogleAppId' was provided. The native Google Mobile Ads SDK will crash on iOS without it.",
    );
  }

  return withPlugins(config, [
    // Android
    [withAndroidGoogleAppId, androidGoogleAppId],
    withMavenRepositories,
    // iOS
    [withIosGoogleAppId, iosGoogleAppId],
  ]);
};

export default withReactNativeWortise;
