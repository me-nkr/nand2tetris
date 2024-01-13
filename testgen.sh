#!/usr/bin/bash

# script to generate digital test script
# from nand2tetris compare file and
# copy it to windows clipboard

sed -E -e "s/\|//g" -e "s/[01]{2,}/0b&/g" $1 | clip.exe
