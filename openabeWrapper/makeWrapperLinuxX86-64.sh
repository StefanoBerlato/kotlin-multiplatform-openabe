#!/bin/bash

echo "Start of make wrapper Linux x86_64 script"

./configureWrapperLinuxX86-64.sh

# Enter in the wrapper folder and delete old files
cd wrapper
rm *.so *.a *.o

# Compile the wrapper to object file, no linking
gcc -Wall -c wrapper.cpp -I../openabe/deps/root/include -I../openabe/root/include

# Create a static library out of the object file
ar rvs libwrapperNoDeps.a wrapper.o

# Create a static library including object files from OpenABE (and necessary deps)
ar -M <script.mri

# Create a shared library out of the object file
gcc -Wall -shared -o libwrapper.so wrapper.o

echo "End of make wrapper Linux x86_64 script"











