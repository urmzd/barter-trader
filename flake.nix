{
  description = "Barter Trader - Android dev environment";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-24.11";
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
          buildToolsVersions = [ "30.0.2" "30.0.3" ];
          platformVersions = [ "30" ];
          includeEmulator = true;
          includeSystemImages = true;
          systemImageTypes = [ "google_apis" ];
          abiVersions = [ "x86_64" "arm64-v8a" ];
          includeExtras = [ "extras;google;gcm" ];
        };

        androidSdk = androidComposition.androidsdk;

        # Resolve the real JAVA_HOME (handles macOS .jdk bundle structure)
        javaHome = if pkgs.stdenv.isDarwin
          then "${pkgs.jdk11}/zulu-11.jdk/Contents/Home"
          else "${pkgs.jdk11}";
      in
      {
        devShells.default = pkgs.mkShell {
          buildInputs = [
            pkgs.jdk11
            androidSdk
            pkgs.gradle
            pkgs.nodejs_20
            pkgs.nodePackages.firebase-tools
          ];

          JAVA_HOME = javaHome;
          ANDROID_HOME = "${androidSdk}/libexec/android-sdk";
          ANDROID_SDK_ROOT = "${androidSdk}/libexec/android-sdk";

          shellHook = ''
            export PATH="${androidSdk}/libexec/android-sdk/platform-tools:$PATH"
            export GRADLE_OPTS="-Dorg.gradle.java.home=${javaHome}"
            echo "Barter Trader dev shell"
            echo "  Java:     $(java -version 2>&1 | head -1)"
            echo "  Gradle:   $(gradle --version 2>/dev/null | grep '^Gradle' || echo 'using wrapper')"
            echo "  Node:     $(node --version)"
            echo "  Firebase: $(firebase --version 2>/dev/null || echo 'available')"
            echo "  Android:  $ANDROID_HOME"
          '';
        };
      });
}
