#! /bin/bash
set -x
rm ./output/new/*
mkdir ./output/new
hadoop fs -copyToLocal "$1"/output/* ./output/new