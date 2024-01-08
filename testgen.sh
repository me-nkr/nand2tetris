sed -E -e "s/\|//g" -e "s/[01]{2,}/0b&/g" $1 | clip.exe
