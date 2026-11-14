import fs from 'fs';
import path from 'path';

import {
  AndroidConfig,
  ConfigPlugin,
  withAndroidManifest,
  withDangerousMod,
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
  'https://artifact.bytedance.com/repository/pangle',
  'https://artifactory.bidmachine.io/bidmachine',
];

// WortiseSDK pre-releases, and finals once CocoaPods trunk is read-only, are only in the Wortise
// specs repo. Declaring any source disables the implicit trunk one, so declare both.
const POD_SOURCES = [
  'https://cdn.cocoapods.org/',
  'https://github.com/wortise/cocoapods-specs.git',
];

function addPodSources(podfile: string): string {
  const missing = POD_SOURCES.filter(url => !podfile.includes(url));

  if (missing.length === 0) return podfile;

  return missing.map(url => `source '${url}'`).join('\n') + '\n' + podfile;
}

function addMavenRepository(gradle: string, url: string): string {
  if (gradle.includes(url)) return gradle;

  const allprojectsRegex = /allprojects\s*{[^]*?repositories\s*{/;
  const dependencyResolutionRegex = /dependencyResolutionManagement\s*{[^]*?repositories\s*{/;

  if (allprojectsRegex.test(gradle)) {
    return gradle.replace(allprojectsRegex, match => `${match}\n        maven { url "${url}" }`);
  }

  if (dependencyResolutionRegex.test(gradle)) {
    return gradle.replace(
      dependencyResolutionRegex,
      match => `${match}\n        maven { url "${url}" }`,
    );
  }

  console.warn(
    `Could not inject maven repository '${url}' into build.gradle. ` +
      'Add it manually under allprojects { repositories { ... } }.',
  );

  return gradle;
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

const withPodSources: ConfigPlugin = config => {
  return withDangerousMod(config, [
    'ios',
    async config => {
      const podfilePath = path.join(config.modRequest.platformProjectRoot, 'Podfile');
      const podfile = await fs.promises.readFile(podfilePath, 'utf8');

      await fs.promises.writeFile(podfilePath, addPodSources(podfile));

      return config;
    },
  ]);
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
    withPodSources,
  ]);
};

export default withReactNativeWortise;
