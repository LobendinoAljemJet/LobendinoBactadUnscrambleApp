# Implementation Plan - Debugging Build and Basic UI

The goal is to fix the build errors caused by incompatible `compileSdk` version and resolve the basic UI issues in `MainActivity.kt` to make the app interactive and buildable.

## User Review Required
> [!IMPORTANT]
> I am upgrading the project to `compileSdk 37` and `targetSdk 37` as required by the latest `androidx.core` and `androidx.lifecycle` libraries currently in the project.

## Proposed Changes

### Build Configuration
#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/aljem/AndroidStudioProjects/Unscramble/app/build.gradle.kts)
- Update `compileSdk` to 37.
- Update `targetSdk` to 37.

### UI Implementation
#### [MODIFY] [MainActivity.kt](file:///C:/Users/aljem/AndroidStudioProjects/Unscramble/app/src/main/java/com/example/unscramble/MainActivity.kt)
- Add missing `androidx.compose.foundation.layout.padding` import.
- Add `remember` and `mutableStateOf` to handle user input in `GameScreen`.
- Connect the text field and button to the state.
- Implement basic logic to check if the user entered "CAT" for the scrambled word "TAC".

## Verification Plan
### Automated Tests
- Run `./gradlew :app:assembleDebug` to verify the build completes successfully.

### Manual Verification
- Deploy the app to a device/emulator.
- Verify the scrambled word "TAC" is displayed.
- Verify the text field allows typing.
- Verify that clicking "SUBMIT" with "CAT" updates the score.
