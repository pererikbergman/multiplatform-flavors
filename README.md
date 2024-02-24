# Guide to Building Flavors in Kotlin Multiplatform Projects (KMP)

This guide outlines the process of implementing and utilizing build flavors in a Kotlin Multiplatform Project (KMP), focusing on configuring different settings for development and production environments across Android and iOS platforms.

## Initial Setup for Config Modules

To utilize build flavors effectively, it's crucial to structure your project to support environment-specific configurations.

### Configuring Project Structure for Flavors

1. **Create Config Modules Directory**: In the root folder of your project, create a directory named `config`. This directory will host the separate modules for each environment.

2. **Create Environment-Specific Shared Modules**: Within the `config` folder, create a shared module for each environment you plan to support, such as `development`, `production`. Ensure the names of these modules correspond to the names of your environments, facilitating dynamic linkage of the correct environment configurations during the build process.

## Configuration Steps for Build Flavors

### Step 1: Define Flavor in Gradle

In your `shared/build.gradle.kts` file, within the `kotlin` block, add the following to dynamically select the build flavor during the build process:

```kotlin
val flavor: String = (project.findProperty("flavor") as? String) ?: "development"
```

### Step 2: Configure Source Sets

Adjust the source sets in the `shared/build.gradle.kts` file to include dependencies specific to the selected build flavor:

```kotlin
sourceSets {
    commonMain.dependencies {
        implementation(project(":config:$flavor"))
    }
    commonTest.dependencies {
        implementation(libs.kotlin.test)
    }
}
```

### Step 3: Build and Install for Android

Execute Gradle commands to build and install the app for Android, adjusting for your chosen flavor:

- **Development**:

  ```shell
  ./gradlew clean installDebug -Pflavor=development
  ```

- **Production**:

  ```shell
  ./gradlew clean installDebug -Pflavor=production
  ```

### Step 4: Build and Install for iOS

Set up separate targets and schemes for each flavor in Xcode:

1. **Create Targets and Schemas**: Define a target and schema for each flavor in Xcode.
2. **Configure Build Phases**: For each target, go to the 'Build Phases' tab and modify the 'Run Script' section to include the `-Pflavor` argument:

    - **Development target**:

      ```shell
      ./gradlew :shared:embedAndSignAppleFrameworkForXcode -Pflavor=development
      ```

    - **Production target**:

      ```shell
      ./gradlew :shared:embedAndSignAppleFrameworkForXcode -Pflavor=production
      ```

## Conclusion

By carefully structuring your project to include config modules for each environment and following these steps, you can successfully configure build flavors for your Kotlin Multiplatform project. This approach allows for the management of separate development and production builds on both Android and iOS platforms, streamlining the development process and ensuring that the correct configurations are used across different environments.