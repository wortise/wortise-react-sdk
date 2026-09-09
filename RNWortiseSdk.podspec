require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |spec|
  spec.name         = "RNWortiseSdk"
  spec.summary      = "Wortise SDK for React Native"
  spec.version      = package['version']

  spec.authors      = package['author']
  spec.homepage     = "https://wortise.com"
  spec.license      = package['license']
  spec.platforms    = { :ios => "13.0" }
  spec.swift_versions = ['5.0']

  spec.source       = { :git => 'https://github.com/wortise/wortise-react-sdk.git' }
  spec.source_files = "ios/**/*.{h,m,swift}"
  spec.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES' }

  if respond_to?(:install_modules_dependencies, true)
    install_modules_dependencies(spec)
  else
    spec.dependency "React-Core"
  end

  spec.dependency   "WortiseSDK", '1.8.0'
end
