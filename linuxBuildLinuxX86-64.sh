#!/bin/bash

# If any command fails, exit
set -e

# Download Konan dependencies used in the build scripts
./gradlew multiplatform-crypto-api:build

# Enter the directory where we build shared (for JVM)
# and static (for all other targets) libraries
cd openabeWrapper

# Build the OpenABE library for Linux x86_64
./makeOpenABELinuxX86-64.sh

# Build the wrapper for Linux x86_64
./makeWrapperLinuxX86-64.sh

# Copy the .so libraries in the JVM resources folder
cp wrapper/libwrapper.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-libwrapper.so
cp openabe/root/lib/libopenabe.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-libopenabe.so
cp openabe/deps/root/lib/librelic.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-librelic.so
cp openabe/deps/root/lib/librelic_ec.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-librelic_ec.so
cp openabe/deps/root/lib/libgmp.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-libgmp.so
cp openabe/deps/root/lib/libgmpxx.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-libgmpxx.so
cp openabe/deps/root/lib/libfl.so ../multiplatform-crypto-libopenabe-bindings/src/jvmMain/resources/dynamic-linux-x86-64-libfl.so

# Build the bindings
cd ..
./gradlew multiplatform-crypto-libopenabe-bindings:build --info

# Reset the flag
set +e





