#!/bin/bash

echo "Start of configure OpenABE Linux x86_64 script"

# Install dependencies for building OpenABE
sudo apt -y update
sudo apt -y install bison lsb-release git sudo python3-pip nano libgtest-dev curl autopoint gettext -y

# Manually install gtest (see https://github.com/zeutro/openabe/issues/61#issuecomment-868751392)
cd ~
g++ -I /usr/include/gtest -I /usr/src/gtest/ -c /usr/src/gtest/src/gtest-all.cc
ar -rv libgtest.a gtest-all.o
sudo mv libgtest.a /usr/local/lib/

echo "End of configure OpenABE Linux x86_64 script"
