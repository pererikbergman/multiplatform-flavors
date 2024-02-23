# Building Flavors in Kotlin Multiplatform Project (KMP)

This README outlines the steps required to implement and utilize build flavors in a Kotlin Multiplatform Project (KMP). By following these instructions, you can configure different settings for development and production environments for both Android and iOS platforms.

## Prerequisites

Ensure you have the latest Kotlin Multiplatform project setup and that you have separate configurations for your desired flavors (e.g., development and production).

## Configuration Steps

### Step 1: Define Flavor in Gradle

In your `shared/build.gradle.kts` file within the `kotlin` block, define your flavor. This step allows you to dynamically select the build flavor during the build process.

```kotlin
val flavor: String = (project.findProperty("flavor") as? String) ?: "development"
```

### Step 2: Configure Source Sets

Still in the `shared/build.gradle.kts` file, configure the source sets to include the dependencies specific to the selected flavor. This configuration is crucial for ensuring the correct dependencies are used for each build flavor.

```kotlin
sourceSets {
    commonMain.dependencies {
        // Put your multiplatform dependencies here.
        implementation(project(":config:$flavor"))
    }
    commonTest.dependencies {
        implementation(libs.kotlin.test)
    }
}
```

### Step 3: Build and Install for Android

To build and install the app for Android, use the following Gradle commands. Replace `development` or `production` with your desired flavor.

- For development:

  ```shell
  ./gradlew clean installDebug -Pflavor=development
  ```

- For production:

  ```shell
  ./gradlew clean installDebug -Pflavor=production
  ```

### Step 4: Build and Install for iOS

For iOS, you need to create separate targets and schemes in Xcode for `development` and `production`.

1. **Create Targets and Schemas**: In Xcode, create a target and schema for each of your flavors: `development` and `production`.

2. **Configure Build Phases**: For each target, navigate to the 'Build Phases' tab and find the 'Run Script' section. Modify the script to include the `-Pflavor` argument corresponding to your target.

    - For the development target:

      ```shell
      ./gradlew :shared:embedAndSignAppleFrameworkForXcode -Pflavor=development
      ```

    - For the production target:

      ```shell
      ./gradlew :shared:embedAndSignAppleFrameworkForXcode -Pflavor=production
      ```
## Conclusion

By following these steps, you can successfully configure build flavors for your Kotlin Multiplatform project, allowing for separate development and production builds for both Android and iOS platforms. This setup is invaluable for managing different configurations, such as API endpoints or keys, between your development and production environments.

# Building Flavors in Kotlin Multiplatform Project (KMP) with Firebase Configuration

## Adding Firebase to Your Flavors

### Configuring Firebase Files

In your flavor-specific modules (`:config:development` and `:config:production`), include the Firebase configuration files:

- For Android: Place the `google-services.json` file under the `resources` directory.
- For iOS: Place the `GoogleService-Info.plist` file under the `resources` directory.

These files contain necessary Firebase project settings and credentials specific to each environment.

### Parsing Firebase Configuration in Shared Module

In the `:shared` module, you'll need to parse these configuration files within the platform-specific source sets (`androidMain` and `iosMain`) to create a `FirebaseOptions` object. This object will be used to manually instantiate the Firebase app with the correct environment settings.

#### Android Configuration

In `androidMain`, parse the `google-services.json` file to create a `FirebaseOptions` object. You can do this by accessing the file from the resources and parsing its contents.

#### iOS Configuration

In `iosMain`, parse the `GoogleService-Info.plist` file similarly and create a `FirebaseOptions` object. Use the appropriate method to read plist files in iOS and extract the necessary information to instantiate `FirebaseOptions`.

### Instantiating Firebase with FirebaseOptions

Once you have the `FirebaseOptions` object, you can use it to manually initialize the Firebase app in both Android and iOS platforms. This step is crucial to ensure that your application uses the correct Firebase environment settings.

#### Initialization

```kotlin
expect fun Firebase.initialize(context: Any? = null, options: FirebaseOptions): FirebaseApp
```

Replace `context` with the appropriate context on Android, and for iOS, ensure you're calling this in an appropriate part of your app lifecycle.

## Conclusion

By including Firebase configuration files in your flavor-specific modules and parsing these in the `:shared` module, you can effectively manage different Firebase environments in your Kotlin Multiplatform project. This setup allows for seamless Firebase integration across both development and production builds for Android and iOS, enabling a more structured and environment-specific configuration management.