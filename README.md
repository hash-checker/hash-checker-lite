<img src="media/logo/ic_app.png" height="100px" />

# Hash Checker Lite

<a href="https://github.com/fartem/hash-checker-lite/releases">
  <img src="media/banners/bn_github.png" height="75px" />
</a>
<a href="https://play.google.com/store/apps/details?id=com.smlnskgmail.jaman.hashcheckerlite">
  <img src="media/banners/bn_google_play.png" height="75px" />
</a>
<a href="https://labs.xda-developers.com/store/app/com.smlnskgmail.jaman.hashcheckerlite">
  <img src="media/banners/bn_xda_labs.png" height="75px" />
</a>
<a href="https://www.androidfilehost.com/?w=files&flid=316844">
  <img src="media/banners/bn_android_file_host.png" height="75px" />
</a>

[![Travis CI](https://travis-ci.org/fartem/hash-checker-lite.svg?branch=master)](https://travis-ci.org/fartem/hash-checker-lite)
[![Codebeat](https://codebeat.co/badges/82ba496d-c878-46c3-a013-8274e4836fad)](https://codebeat.co/projects/github-com-fartem-hash-checker-lite-master)
[![Codecov](https://codecov.io/gh/fartem/hash-checker-lite/branch/master/graph/badge.svg)](https://codecov.io/gh/fartem/hash-checker-lite)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Hash%20Checker%20Lite-green.svg?style=flat)](https://android-arsenal.com/details/1/8155)

## About application

Fast and simple application for generating and comparison hashes from files or text. Lite version of [Hash Checker](https://github.com/fartem/hash-checker).

## Original app and lite version comparison

| Feature | [Hash Checker](https://github.com/fartem/hash-checker) | Hash Checker Lite |
| --- | --- | --- |
| MD5 support | + | + |
| SHA-1 support | + | + |
| SHA-224 support | + | + |
| SHA-256 support | + | + |
| SHA-384 support | + | + |
| SHA-512 support | + | + |
| CRC-32 support | + | + |
| Language switch | + | + |
| Inner File Manager | + | |
| History | + | |
| User data export | + | |
| Vibration | + | |

## Screenshots (Light theme)

<br/>
<p align="center">
  <img src="media/screenshots/screenshot_01.png" width="130" />
  <img src="media/screenshots/screenshot_02.png" width="130" />
  <img src="media/screenshots/screenshot_03.png" width="130" />
  <img src="media/screenshots/screenshot_04.png" width="130" />
  <img src="media/screenshots/screenshot_05.png" width="130" />
</p>

## Screenshots (Dark theme)

<br/>
<p align="center">
  <img src="media/screenshots/screenshot_06.png" width="130" />
  <img src="media/screenshots/screenshot_07.png" width="130" />
  <img src="media/screenshots/screenshot_08.png" width="130" />
  <img src="media/screenshots/screenshot_09.png" width="130" />
  <img src="media/screenshots/screenshot_10.png" width="130" />
</p>

## How to use

* [H2S Media](https://www.how2shout.com/how-to/how-to-calculate-the-hash-of-a-file-or-create-custom-hash-on-android.html)

## Privacy Policy

* [Web version](https://fartem.github.io/hash-checker-privacy-policy.io/)

## Feedback

If you have any question or feature idea for app, you can open issue on [this page](https://github.com/fartem/hash-checker-lite/issues) or contact me by email jaman.smlnsk@gmail.com.

## How to build unsigned .apk from command line without IDE

From project directory run:

```shell
$ ./gradlew clean
$ ./gradlew assembleDebug
```

Go to `app` -> `build` -> `outputs` -> `apk` -> `debug` and find `hash-checker-lite_VERSION.apk` where 'VERSION' is number of app version.

## How to contribute

Read [Commit Convention](https://github.com/fartem/repository-rules/blob/master/commit-convention/COMMIT_CONVENTION.md). Make sure your build is green before you contribute your pull request. Then:

```shell
$ ./gradlew clean
$ ./gradlew build
$ ./gradlew connectedCheck
```

If you don't see any error messages, submit your pull request.

## Contributors

* [@fartem](https://github.com/fartem) as Artem Fomchenkov
* [@Marwa-Eltayeb](https://github.com/Marwa-Eltayeb) as Marwa Said
