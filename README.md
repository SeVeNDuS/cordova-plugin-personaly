# Cordova Persona.ly Plugin

A free, no ad-sharing version of Personaly plugin for Cordova.

## Features

* **No Ad-Sharing**

  This plugin does not share your advertising revenue by randomly display developer's owned ads.

* **Fully Open Sourced**

  Every line of code are on Github

* **No Remote Control**

  Do not to control whether ad could be displayed. Therefore, you don't lose revenue because some server bugs

## Installation

```sh
cordova plugin add cordova-plugin-personaly --save
```

Note that `cordova plugin add [GIT_URL]` is not supported.

## Usage

### 1. Create Ad Unit ID for your banner and interstitial.

Go to the [Personaly portal](http://persona.ly/) and add your app (if you haven't done so already), once your app is added to your Persona.ly account, create a new ad unit for it.

### 2. Display advertisements

#### [RewardedVideo Ad](https://sevendus.github.io/cordova-plugin-personaly/variable/index.html#static-variable-rewardedvideo)

#### [Interstitial Ad](https://sevendus.github.io/cordova-plugin-personaly/variable/index.html#static-variable-interstitial)

#### [PopUp Offer Ad](https://sevendus.github.io/cordova-plugin-personaly/variable/index.html#static-variable-popupoffer)

#### [OfferWall Ad](https://sevendus.github.io/cordova-plugin-personaly/variable/index.html#static-variable-offerwall)

#### [AppWall Ad](https://sevendus.github.io/cordova-plugin-personaly/variable/index.html#static-variable-appwall)

### 3. Profit

If you find this plugin useful, please [star it on Github](https://github.com/sevendus/cordova-plugin-personaly).

## Screenshots

TODO

## API

See [documentation page](https://sevendus.github.io/cordova-plugin-personaly/identifiers.html).

## Customize Google Play Services versions (Android only)

The default `PLAY_SERVICES_VERSION` is set to `11.0.2`.
If you need a different version, edit `config.xml` with following,

```xml
  <framework src="com.google.android.gms:play-services-basement:11.0.2" />
```

## Status

This plugin is inspired from [cordova-plugin-admob-free](https://github.com/ratson/cordova-plugin-admob-free) and modified to adapt to Persona.ly SDK.

## Contributing

You can use this Cordova plugin for free. You can contribute to this project in many ways:

* [Reporting issues](https://github.com/sevendus/cordova-plugin-personaly/issues).
* Patching and bug fixing, especially when submitted with test code. [Open a pull request](https://github.com/sevendus/cordova-plugin-personaly/pulls).
* Other enhancements.

Help with documentation is always appreciated and can be done via pull requests.

Read [Contributing Guide](https://sevendus.github.io/cordova-plugin-personaly/manual/tutorial.html#contributing-guide) to learn how to contribute.

### Ionic Support

While the Ionic community have provided [an Ionic Native Plugin](https://ionicframework.com/docs/native/personaly/), plugin users need more examples and tutorials.

As I ([@sevendus](https://github.com/sevendus)) don't use Ionic myself, it would be great if some experienced Ionic developers could help answering questions or come up with more examples. HELP WANTED HERE.

## Credits

Thanks for the [cordova-plugin-admob-free](https://github.com/ratson/cordova-plugin-admob-free) author

## Disclaimer

This is NOT an official Personaly product. It is just a community-driven project, which use the Personaly SDKs.

## License

[MIT](LICENSE)
