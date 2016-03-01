#! /bin/bash
set -x
rm ./output/new/*
mkdir ./output/new
hadoop fs -copyToLocal /user/mpeterson10/wc/output/* ./output/new