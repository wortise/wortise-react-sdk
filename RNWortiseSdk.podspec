require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |spec|
  spec.name         = "RNWortiseSdk"
  spec.summary      = "Wortise SDK for React Native"
  spec.version      = package['version']

  spec.authors      = package['author']
  spec.homepage     = "https://wortise.com"
  spec.license      = package['license']
  spec.platforms    = { :ios => "12.0" }

  spec.source       = { :git => 'https://github.com/wortise/wortise-react-sdk.git' }
  spec.source_files = "ios/**/*.{h,m,swift}"
  spec.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES' }

  spec.dependency   "React-Core"
  spec.dependency   "WortiseSDK", '1.7.0'
end
