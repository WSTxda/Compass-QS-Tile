# Toolkit Tiles – Useful Quick Settings Tools

Toolkit Tiles integrates useful tools directly into your Android Quick Settings panel, allowing instant access with a single tap—no need to launch separate apps.

[![GitHub Actions Status](https://img.shields.io/github/actions/workflow/status/WSTxda/Toolkit-Tiles/.github%2Fworkflows%2Fandroid.yml?style=for-the-badge&logo=github-actions&labelColor=21262D&color=3FB950)](https://github.com/WSTxda/Toolkit-Tiles/actions)
[![Platform](https://img.shields.io/badge/android-platform?style=for-the-badge&label=platform&labelColor=21262d&color=6e7681)](https://www.android.com)
[![API](https://img.shields.io/badge/26%2B-level?style=for-the-badge&logo=android&logoColor=3cd382&label=API&labelColor=21262d&color=ff663b)](https://developer.android.com/studio/releases/platforms)
[![Release](https://img.shields.io/github/v/release/WSTxda/Toolkit-Tiles?display_name=tag&style=for-the-badge&logo=github&labelColor=21262d&color=1f6feb)](https://github.com/WSTxda/Toolkit-Tiles/releases/latest)
[![Downloads](https://img.shields.io/github/downloads/WSTxda/Toolkit-Tiles/total?style=for-the-badge&labelColor=21262d&color=238636)](https://github.com/WSTxda/Toolkit-Tiles/releases)

![Banner](https://raw.githubusercontent.com/WSTxda/Toolkit-Tiles/main/images/Banner.svg)

Toolkit Tiles enhances your Android experience by adding practical utilities to the Quick Settings panel. These tools leverage your device's built-in sensors for accurate, real-time results, ensuring they're always at your fingertips.

## Features

### Tools Available

**🧭 Compass**  
  Quickly determine your device's orientation and find directions. It displays cardinal directions and degrees for precise navigation.

**📏 Bubble Level**  
  Measure the inclination of surfaces with ease, mimicking a traditional spirit level. Ideal for DIY projects, hanging pictures, or checking alignments.

### Key Benefits

- **Lightweight and Efficient**: Minimal resource usage with no background processes.
- **Seamless Integration**: Fits naturally into Android's Quick Settings for one-tap access.
- **Sensor-Based Accuracy**: Utilizes your device's accelerometer and magnetometer for reliable readings.
- **No Permissions Overreach**: Only requires sensor access; no internet or storage permissions needed.

### Installation and Usage

1. Download and install the latest APK from the [releases page](https://github.com/WSTxda/Toolkit-Tiles/releases/latest).
2. Swipe down twice from the top of your screen to open the full Quick Settings panel.
3. Tap the edit button (pencil icon) to customize the panel.
4. Locate the Toolkit Tiles (Compass and Bubble Level) in the available tiles section.
5. Drag them to your active Quick Settings area.
6. Tap any tile to activate the tool instantly.

> [!NOTE]
> **Required Sensors:**
>
> **🧭 Compass:**
> - [Rotation Vector Sensor](https://developer.android.com/reference/android/hardware/Sensor#TYPE_ROTATION_VECTOR) (gyro + accelerometer + magnetometer)
> - [Geomagnetic Rotation Vector](https://developer.android.com/reference/android/hardware/Sensor#TYPE_GEOMAGNETIC_ROTATION_VECTOR) (accelerometer + magnetometer, fallback)
>
> **📏 Bubble Level:**
> - [Rotation Vector Sensor](https://developer.android.com/reference/android/hardware/Sensor#TYPE_ROTATION_VECTOR)
> - [Accelerometer](https://developer.android.com/reference/android/hardware/Sensor#TYPE_ACCELEROMETER) only (fallback, less accurate)
>
> **Optional:** Gyroscope can improve accuracy for both tiles but is not mandatory.

---

### Download

[<img src="https://raw.githubusercontent.com/WSTxda/WSTxda/main/images/GitHub.svg" alt="Get it on GitHub" height="80">](https://github.com/WSTxda/Toolkit-Tiles/releases/latest)
[<img src="https://raw.githubusercontent.com/WSTxda/WSTxda/main/images/Telegram.svg" alt="Get it on Telegram" height="80">](https://t.me/WSTprojects)

### Donate

[<img src="https://raw.githubusercontent.com/WSTxda/WSTxda/main/images/PayPal.svg" alt="Donate with PayPal" height="80">](https://bit.ly/2lV0E6u)
[<img src="https://raw.githubusercontent.com/WSTxda/WSTxda/main/images/BMC.svg" alt="Donate with BMC" height="80">](https://www.buymeacoffee.com/wstxda)

### Credits

**[Mike Klimek](https://github.com/Tetr4)** for the original [CompassTile](https://github.com/Tetr4/CompassTile) project, inspired by a [CyanogenMod/LineageOS feature](https://review.lineageos.org/c/LineageOS/android_frameworks_base/+/179168).
