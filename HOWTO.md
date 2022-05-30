# How To

This document describes the steps to create Kotlin multiplatform bindings for a native library, but there might be (perhaps better) alternatives.

> Thanks to [**Ionspin**](https://github.com/ionspin) for his guidance on this.


## Setup

The only (external) tool I used is IntelliJ Idea, as Kotlin/Native bindings are created with [`cinterop`](https://kotlinlang.org/docs/native-c-interop.html) while I manually wrote Kotlin/JVM bindings (unfortunately, it looks like JNAerator is not maintained anymore).

First, you need to obtain the `.h` files of the library for which you are creating the bindings. As discussed in the [design document](./DESIGN.md), Kotlin can interop with `C` functions only. Thus, if the library exposes `C++` functions, you need to create a first (Native) wrapper exposing `C` functions only. The wrapper can contain `C++` code, but exposed functions should be `C` only (e.g., no namespaces, overloading, default arguments).

> It may seem that cinterop accepts `C++` header files, but the produced Kotlin/Native bindings will not work!



## Build the Library

The objective of this step is to have the library built for all platforms you are planning to support. For instance, for Kotlin/Native bindings you need to build the `.a` static library, while for Kotlin/JVM you need the `.so` shared library.

Usually, popular open-source libraries have a well-documented build process. Ideally, you should be able to download the source code (e.g., clone or fork the repository of the library) to build the library yourself. However, the library build process must use the **Kotlin build toolchain**, e.g., because Kotlin may have a specific version of glibc (like 2.19) while the default library build process may use a different version (like 2.25) which has different symbols; see, for instance, [this issue](https://youtrack.jetbrains.com/issue/KT-43501)

It is recommended to build the library for Kotlin/Native bindings as static (`.a`), so to avoid dependencies requirements. However, for Kotlin/JVM you need a dynamically linked library (`.so`). As for Kotlin/JS, a tool for building the library is [`emscripten`](https://emscripten.org/).



## Create the Bindings

Now we are ready to create the Kotlin multiplatform bindings. In the `commonMain` sourceset, write the common Kotlin APIs that you want to expose (i.e., the `expect` classes). Then, in the `commonTest` sourceset, write common tests to verify that the bindings work. Finally, create the `actual` classes for each target.

### Kotlin/Native

In general, follow the [official tutorial](https://kotlinlang.org/docs/native-c-interop.html) on how to interop Kotlin/Native with native libraries. Note that, in this project, there is a `nativeMain` (and `nativeTest`) sourceset that is common to all native build targets (i.e., iOS, Mac, Linux and MinGW) to avoid implementing actuals for each build target.

After having run `cinterop`, write the Kotlin/Native actual code for the native platform.


### Kotlin/JVM

Implement the Kotlin/JVM JNA interface manually. Remember to create an initializer to load the shared library.
