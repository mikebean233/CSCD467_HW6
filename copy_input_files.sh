#! /bin/bash
set -x
hadoop fs -rm -r "$1"/input/
hadoop fs -mkdir  "$1"/input/
hadoop fs -copyFromLocal ./input/* "$1"/input