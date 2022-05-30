# Design

This document contains notes and information on the design of the Kotlin multiplatform bindings for OpenABE.


## The C Wrapper

As explained in the Kotlin Native [documentation](https://kotlinlang.org/docs/native-overview.html#interoperability), and as discussed in some issues as well (e.g., [1](https://discuss.kotlinlang.org/t/cinterop-with-c/18810), [2](https://youtrack.jetbrains.com/issue/KT-39144)), Kotlin can interop with `C` functions only. Thus, the library for which you are creating the bindings should expose `C` (not `C++`) functions only. 

As the header of OpenABE exposes also `C++` functions, we created a `C++` wrapper for OpenABE exposing `C` functions only in the header. The `C++` wrapper is located at `openabeWrapper/wrapper/`.


## Modules
This project contains the following modules. Please refer to the READMEs of each module for more details:
* **buildSrc**: utilities and constant values;
* **multiplatform-crypto-api**: allow kotlin to downloads Konan toolchains;
* **multiplatform-crypto-libopenabe-bindings**: the multiplatform bindings for OpenABE.


## Scripts
* The following are configuration and build scripts located in `openabeWrapper`, where `<arch>` is one among the set of architectures {`LinuxX86-64`}. Note that some commands in these scripts require `sudo` privileges:
  * `configureOpenABE${arch}.sh`: install dependencies to build the OpenABE library for `<arch>`;
  * `configureGMP${arch}.sh`: install dependencies to build the GPM library (a dependency of OpenABE) for `<arch>`;
  * `configureWrapper${arch}.sh`: install dependencies to create the wrapper for the OpenABE library for `<arch>`;
  * `makeOpenABE${arch}.sh`: build the static and shared libraries of OpenABE (in `openabe/root/lib/`) and the static and shared libraries of the dependencies (in `openabe/deps/root/lib/`) with the Konan toolchain of Kotlin for `<arch>`;
  * `makeGMP${arch}.sh.sh`: build the GMP library with the Konan toolchain of Kotlin for `<arch>`;
  * `makeWrapper${arch}.sh.sh`: build the static and shared libraries of the wrapper (in `wrapper/`) for `<arch>`. The wrapper static library includes the needed dependencies, while the wrapper shared library relies on other shared libraries. The wrapper exposes `C` functions only in the header;
* `linuxBuildLinuxX86-64WithCacheNoTests.sh`: builds the bindings for OpenABE for Linux x86-64 assuming that OpenABE and gmp libraries were already built and excluding test tasks;
* `linuxBuildLinuxX86-64.sh`: builds the bindings for OpenABE for Linux x86-64;
* `publishToMavenLocal.sh`: publish to Maven local;
* `publishLinuxToMaven.sh`: publish to the Maven repository.


## Build Process:
* For Linux x86-64, run `linuxBuildLinuxX86-64.sh`. This script: 
  * invokes the gradle build on `multiplatform-crypto-api`;
  * runs `openabeWrapper/makeOpenABELinuxX86-64.sh`;
  * runs `openabeWrapper/makeWrapperLinuxX86-64.sh`;
  * runs `openabeWrapper/makeGMPLinuxX86-64.sh`;
  * invokes the gradle build on `multiplatform-crypto-libopenabe-bindings`;
  * moves `libwrapper.so` (and other shared libraries) into the JVM resources,

Except for the Kotlin JVM (where we need to use dynamically linked libraries), we build native libraries to be statically linked (so they don't require a system library to be present).


## Changes in the build process of OpenABE:
The OpenABE library is build from sources at [my forked repository](https://github.com/StefanoBerlato/openabe).  The following changes were made: 
* the OpenSSL dependency was outdated and may have presented vulnerabilities and bugs. Moreover, the Konan toolchain in Kotlin builds in x86_64 with glibc v2.19. However, the `getrandom` function (previously a syscall) was added to glibc in version v2.25. The outdated dependency of OpenSSL was trying to find the `getrandom` function but glibc v2.19 does not provide it. Therefore, we updated OpenSSL dependency to v1.1.1 latest stable (4/4/22), commit 3e8f70c30d84861fcd257a6e280dc49e104eb145;
* the OpenABE make script builds Relic with the `-DSEED="ZERO"` option. However, as explained in the [Relic documentation](https://github.com/relic-toolkit/relic/blob/83de89f714202f9b227a2138e4fe784ee6e202f5/cmake/rand.cmake), using a zero seed is "horribly insecure". Therefore, we changed to `-DSEED="ZERO"` `-DSEED="UDEV"`.


## Open Questions:
* the `multiplatform-crypto-api` module should be needed just "to get kotlin to download appropriate konan tools". In what sense? How is it used?
* what is the purpose of the `multiplatform-crypto-libopenabe-bindings/src/jvmSpecific` folder and the empty `JvmOpenabeWrapper.kt` (that originally was `JvmSodiumWrapper.kt`) file?


## TODOs:
* Need to revise (the version of) other dependencies of OpenABE besides OpenSSL;
* Complete bindings and exposed APIs;
* Add support for other targets (native, Android, JS);


## Open Issues:
1. [WORKAROUND build Relic with system toolchain (the whole build will fail but Relic libraries will be available), then re-launch the build but first switch to Konan's toolchain to build OpenABE] When building OpenABE, we use Konan toolchain (otherwise, the build process will mess up with libraries versions when interoping). However, if we set the Konan toolchain for `CC` only (i.e., for `gcc`, no `g++`), Relic will not build and it will return an error like the following:
    ```bash
    /usr/include/x86_64-linux-gnu/sys/cdef.h:467:79: error :missing binary operator before token "(" 
    #if __GNUC_PREREQ (4,8) || __glibc_clang_prereq (3,5)
    ```
    If we do not set neither `CC` nor `CXX` (i.e., no `gcc` and no `g++`) Relic will build, but Kotlin interoping will mess up. Finally, if we set both `CC` and `CXX` (i.e., both `gcc` and `g++`), Relic will build but OpenABE will not, as the sysroot of `g++` in the Konan toolchain misses some dependencies (in detail, the `gmp` library for both `gmp.h` and `gmpxx.h` and another header named `FlexLexer.h`);
2. the Relic dependency is outdated and may present vulnerabilities and bugs. Moreover, OpenSSL v1.1.1 defines the `bn_init` symbol (in the `bn_lib.c` file), which conflicts with Relic's `bn_init` call that is made by bls-signatures (in the `relic_bn_mem.c` file). See [this issue](https://github.com/relic-toolkit/relic/issues/196) for more details. The problem was solved in Relic with an update which we, unfortunately, cannot use since it comes after a [major update of Relic](https://github.com/relic-toolkit/relic/commit/5518ed1a8c9039ac29c72b4f1b170eae9936f57c) breaking OpenABE;
3. the bindings for the linuxX64 native target present incorrect behaviour for some inputs (e.g., decrypting a ciphertext previously encrypted with two attributes with an AND gate fails).