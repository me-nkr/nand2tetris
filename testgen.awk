#!/usr/bin/awk -f

# script to generate digital test script
# from nand2tetris compare file and
# copy it to windows clipboard
#
# Usage: testgen.awk <header-format> <filename>
# 
# <header-format> : string representing types of columns
#       t - time
#       b - bit
#       B - binary number
#       i - integer

BEGIN {
    FS = "|"
    OFS = " "

    if (ARGC != 3) {
        print "invalid number of arguments"
        exited = 1
        exit 1
    }

    format = ARGV[1]
    if (format !~ /^[tbBi]+$/) {
        print "invalid header format"
        exited = 1
        exit 1
    }
    split(format, headers, "");

    ARGV[1] = ARGV[2]
    ARGV[2] = ""

    system("touch temp.cmp")
}

NR == 1 {
    if (NF - 2 != length(headers)) {
        print "header numbers mismatch"
        exit 1
    }
    $1 = $1
    print $0 >> "temp.cmp"
}

NR != 1 {
    for (field = 2; field < NF; field++) {
        $field = handleColumn(headers[field - 1], $field)
    }
    print $0 >> "temp.cmp"
}

END {
    if (!exited) {
        system("clip.exe < temp.cmp")
        system("rm temp.cmp")
    }
}

function handleTime(time) {
    if (time ~ /^\s*[0-9]+\+\s*$/) return "  1   "
    if (time ~ /^\s*[0-9]+\s*$/) return "  0   "
    print "invalid time value "NR" "NF
    exit 1
}

function handleBinary(binary) {
    if (binary ~ /^\s*[01]+\s*$/) return gensub(/^(\s*)\s\s[^01]/, "\\1 0b", 1, binary)
    print "invalid binary value "NR" "NF
    exit 1
}

function handleBit(bit) {
    if (bit ~ /^\s*[01]\s*$/) return bit
    print "invalid bit value "NR" "NF
    exit 1
}

function handleInt(integer) {
    if (integer ~ /^\s*-?[0-9]+\s*$/) return integer
    print "invalid integer value "NR" "NF
    exit 1
}

function handleColumn(type, value) {
    switch (type) {
        case "t": return handleTime(value)
        case "B": return handleBinary(value)
        case "b": return handleBit(value)
        case "i": return handleInt(value)
        default:
            print "invalid column type"
            exit 1
    }
}
