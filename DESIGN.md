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
  * `configureWrapper${arch}.sh`: install dependencies to create the wrapper for the OpenABE library for `<arch>`;
  * `makeOpenABE${arch}.sh`: build the static and shared libraries of OpenABE (in `openabe/root/lib/`) and the static and shared libraries of the dependencies (in `openabe/deps/root/lib/`) with the Konan toolchain of Kotlin for `<arch>`;
  * `makeWrapper${arch}.sh.sh`: build the static and shared libraries of the wrapper (in `wrapper/`) for `<arch>`. The wrapper static library includes the needed dependencies, while the wrapper shared library relies on other shared libraries. The wrapper exposes `C` functions only in the header;
* `linuxBuildLinuxX86-64WithCacheNoTests.sh`: builds the bindings for OpenABE for Linux x86-64 assuming that OpenABE was already built and excluding test tasks;
* `linuxBuildLinuxX86-64.sh`: builds the bindings for OpenABE for Linux x86-64;
* `publishToMavenLocal.sh`: publish to Maven local;
* `publishLinuxToMaven.sh`: publish to the Maven repository.


## Build Process:
* For Linux x86-64, run `linuxBuildLinuxX86-64.sh`. This script: 
  * invokes the gradle build on `multiplatform-crypto-api`;
  * runs `openabeWrapper/makeOpenABELinuxX86-64.sh`;
  * runs `openabeWrapper/makeWrapperLinuxX86-64.sh`;
  * invokes the gradle build on `multiplatform-crypto-libopenabe-bindings`;
  * moves `libwrapper.so` (and other shared libraries) into the JVM resources,

Except for the Kotlin JVM (where we need to use dynamically linked libraries), we build native libraries to be statically linked (so they don't require a system library to be present).


## Changes in the build process of OpenABE:
The OpenABE library is build from sources at [this forked repository](https://github.com/StefanoBerlato/openabe).  The following changes were made: 
* the OpenSSL dependency was outdated and may have presented vulnerabilities and bugs. Moreover, the Konan toolchain in Kotlin builds in x86_64 with glibc v2.19. However, the `getrandom` function (previously a syscall) was added to glibc in version v2.25. The outdated dependency of OpenSSL was trying to find the `getrandom` function but glibc v2.19 does not provide it. Therefore, we updated the OpenSSL dependency to v1.1.1 latest stable (04/04/22), commit 3e8f70c30d84861fcd257a6e280dc49e104eb145;
* the OpenABE make script builds Relic with the `-DSEED="ZERO"` option. However, as explained in the [Relic documentation](https://github.com/relic-toolkit/relic/blob/83de89f714202f9b227a2138e4fe784ee6e202f5/cmake/rand.cmake), using a zero seed is "horribly insecure". Therefore, we changed to `-DSEED="ZERO"` `-DSEED="UDEV"`.
* the Relic dependency was outdated and may have presented vulnerabilities and bugs. Therefore, we updated the Relic dependency to v0.6.0 stable (09/01/2019), commit 3e8f70c30d84861fcd257a6e280dc49e104eb145 (this will soon be replaced with more recent versions). We also updated the names of functions and symbols in OpenABE (see below);
  ```
  changes in OpenABE source code
  - "CMP_EQ => "RLC_EQ"
  - "BN_NEG" => "RLC_NEG"
  - "BN_POS" => "RLC_POS"
  - "CMP_LT" => "RLC_LT"
  - "CMP_GT" => "RLC_GT"
  - "STS_OK" => "RLC_OK"
  - "CMP_NE" => "RLC_NE"
  - "EP_DTYPE" => "RLC_EP_DTYPE"
  - "FP_DIGS" => "RLC_FP_DIGS"
  - "CAT" => "RLC_CAT"
  - "G1_LOWER" => "RLC_G1_LOWER"
  - "G2_LOWER" => "RLC_G2_LOWER"
  - "GT_LOWER" => "RLC_GT_LOWER"
  - "ep_sub_projc(z, x, z);" => "ep_sub(z, x, z);"
  - "ec_ep_is_valid(p)" => ec_ep_on_curve(p)
  - "r->norm = p->norm;" => "r->coord = p->coord;"
  
  changes in platforms/android.sh:
  - "-DFP_METHD="BASIC;COMBA;COMBA;MONTY;LOWER;SLIDE"" => "-DFP_METHD="BASIC;COMBA;COMBA;MONTY;MONTY;JMPDS;SLIDE""
  
  changes in deps/relic/Makefile:
  - "VERSION=toolkit-0.5.0" => "VERSION=toolkit-0.6.0"
  - "-DFP_METHD="BASIC;COMBA;COMBA;MONTY;LOWER;SLIDE"" => "-DFP_METHD="BASIC;COMBA;COMBA;MONTY;MONTY;JMPDS;SLIDE""
  ```


## Open Questions:
* the `multiplatform-crypto-api` module should be needed just "to get kotlin to download appropriate konan tools". In what sense? How is it used?
* what is the purpose of the `multiplatform-crypto-libopenabe-bindings/src/jvmSpecific` folder and the empty `JvmOpenabeWrapper.kt` (that originally was `JvmSodiumWrapper.kt`) file?


## TODOs:
* Need to revise (the version of) Relic
* Complete bindings and exposed APIs;
* Add support for other targets (native, Android, JS);


## Open Issues:
1. the bindings for the linuxX64 native target present incorrect behaviour for some inputs (e.g., decrypting a ciphertext previously encrypted with two attributes with an AND gate fails).