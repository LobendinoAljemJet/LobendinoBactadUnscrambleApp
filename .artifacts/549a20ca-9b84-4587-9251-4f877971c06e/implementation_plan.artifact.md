# Implementation Plan - Downgrade to SDK 36

The user wants to use `compileSdk 36` instead of `37`. Since the latest versions of some libraries require SDK 37, we need to downgrade those dependencies to compatible versions.

## Proposed Changes

### Version Catalog
#### [MODIFY] [libs.versions.toml](file:///C:/Users/aljem/AndroidStudioProjects/Unscramble/gradle/libs.versions.toml)
- Downgrade `coreKtx` from `1.19.0` to `1.15.0`.
- Downgrade `lifecycleRuntimeKtx` from `2.11.0` to `2.8.7`.
- (Optional) If the build still fails due to BOM, we might need to downgrade `composeBom`. For now, we'll start with these two.

### Build Configuration
#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/aljem/AndroidStudioProjects/Unscramble/app/build.gradle.kts)
- Change `compileSdk` to 36.
- Change `targetSdk` to 36.

## Verification Plan
### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify the build completes successfully with SDK 36.
