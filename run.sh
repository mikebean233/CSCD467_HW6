#! /bin/bash
set -x
hadoop jar ./bin/wc.jar WordCount world "$1"/input "$1"/output

