#!/bin/bash

echo "Start of make OpenABE Linux x86_64 script"

./configureOpenABELinuxX86-64.sh

# Setup the environment for building OpenABE
cd openabe
sudo -E ./deps/install_pkgs.sh
. ./env

# This is to use Kotlin toolchain, otherwise make process may mess up with libraries versions when interoping
export CC=$HOME/.konan/dependencies/x86_64-unknown-linux-gnu-gcc-8.3.0-glibc-2.19-kernel-4.9-2/bin/x86_64-unknown-linux-gnu-gcc
#export CXX=$HOME/.konan/dependencies/x86_64-unknown-linux-gnu-gcc-8.3.0-glibc-2.19-kernel-4.9-2/bin/x86_64-unknown-linux-gnu-g++

# Build OpenABE
make

echo "End of make OpenABE Linux x86_64 script"