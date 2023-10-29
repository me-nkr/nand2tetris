#!/usr/bin/bash

root=$(pwd);

if [[ ! -e commit-msg ]]; then
    echo ""commit-msg" file not found"
    exit 1
fi

if [[ -e $root/.git/hooks/commit-msg ]]; then
    echo ".git/hooks/commit-msg: hook already exists, delete it and rerun script to reinstall hook"
    exit 1
else
    ln -s $root/commit-msg .git/hooks/commit-msg
fi
