{
  description = "Barter Trader - Android dev environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs {
          inherit system;
          config.allowUnfree = true;
          config.android_sdk.accept_license = true;
        };

        androidComposition = pkgs.androidenv.composeAndroidPackages {
          buildToolsVersions = [ "30.0.3" ];
          platformVersions = [ "30" ];
          includeEmulator = false;
          includeSystemImages = true;
          systemImageTypes = [ "google_apis" ];
          abiVersions = [ "arm64-v8a" ];
        };

        androidSdk = androidComposition.androidsdk;

        javaHome = if pkgs.stdenv.isDarwin
          then "${pkgs.jdk11}/Library/Java/JavaVirtualMachines/zulu-11.jdk/Contents/Home"
          else "${pkgs.jdk11}";

      in
      {
        packages.emulator = pkgs.androidenv.emulateApp {
          name = "barter-trader-emulator";
          platformVersion = "30";
          abiVersion = "arm64-v8a";
          systemImageType = "google_apis";
          sdkExtraArgs = {};
        };

        devShells.default = pkgs.mkShell {
          buildInputs = [
            pkgs.jdk11
            androidSdk
            pkgs.gradle
          ];

          JAVA_HOME = javaHome;
          ANDROID_HOME = "${androidSdk}/libexec/android-sdk";
          ANDROID_SDK_ROOT = "${androidSdk}/libexec/android-sdk";

          shellHook = ''
            export PATH="${androidSdk}/libexec/android-sdk/platform-tools:${androidSdk}/libexec/android-sdk/emulator:$PATH"
            export GRADLE_OPTS="-Dorg.gradle.java.home=${javaHome}"

            # Generate local.properties so ./gradlew works without env vars
            echo "sdk.dir=${androidSdk}/libexec/android-sdk" > local.properties
          '';
        };
      });
}
