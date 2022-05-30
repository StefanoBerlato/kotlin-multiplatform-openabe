# openabeWrapper

As the wrapper must expose `C` functions only, it does not have a 1 to 1 mapping to the OpenABE `C++` exposed APIs. For instance, overloeaded functions or functions with default arguments (not supported in `C`) are split into different functions, e.g., a function with a `bool` default argument is split into two functions, one with the value at `true` and one with the value at `false`.

Moreover, some functions have an additional parameter `int * errorCode` to collect errors in OpenABE and handle them in Kotlin. For more details, please check the documentation directly in the [wrapper code](./wrapper/wrapper.cpp).

Other than that, refer to the [original documentation](https://github.com/StefanoBerlato/openabe).