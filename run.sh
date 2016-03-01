#! /bin/bash
set -x
hadoop jar ./bin/wc.jar WordCount "$2" "$1"/input "$1"/output

