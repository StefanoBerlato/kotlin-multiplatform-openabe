# multiplatform-crypto-libopenabe-bindings

This module contains the Kotlin bindings for OpenABE:
* `commonMain`: it contains `expect` classes and method;
* `commonTest`: it contains tests to ensure that the bindings are correctly generated;
* `jvmMain`: it contains the `actual` for JVM target with JNA. In the resources' folder, there are the `.so` libraries;
* `jvmTest`: it contains initializer code to load the `.so` libraries before running the tests in `commonTest`;
* `linuxX64Main`: placeholder (code for native target is in `nativeMain`);
* `linuxX64Test`: placeholder (code for native target is in `nativeTest`);
* `nativeInterop`: it contains the `.def` file for interoping with (the wrapper of) OpenABE;
* `nativeMain`: it contains `expect` for Native target. Libraries are statically linked;
* `nativeTest`: it contains setup code before running the tests in `commonTest`;
* `jvmSpecific`: **TODO** check open questions to understand whether this is actually needed;
