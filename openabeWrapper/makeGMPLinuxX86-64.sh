#!/bin/bash

echo "Start of make GMP Linux x86_64 script"

./configureGMPLinuxX86-64.sh

# This is to use Kotlin toolchain, otherwise make process may mess up with libraries versions when interoping
export CC=$HOME/.konan/dependencies/x86_64-unknown-linux-gnu-gcc-8.3.0-glibc-2.19-kernel-4.9-2/bin/x86_64-unknown-linux-gnu-gcc
export CXX=$HOME/.konan/dependencies/x86_64-unknown-linux-gnu-gcc-8.3.0-glibc-2.19-kernel-4.9-2/bin/x86_64-unknown-linux-gnu-g++

cd gmp/gmp-6.2.1/

PREFIX="${PREFIX:-$PWD/linux-x86-64}"

./configure --prefix=$PREFIX "$@"
make
make install

echo "End of make GMP Linux x86_64 script"