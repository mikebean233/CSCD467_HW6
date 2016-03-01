#! /bin/bash

hdfsUserPath=$1

echo "path: $hdfsUserPath"

echo;echo
echo "---------- REMOVING OLD OUTPUT FILES FROM HDFS-------------"
sh remove_output_files.sh $hdfsUserPath
echo;echo
echo "----------- COPYING NEW INPUT FILES TO HDFD ---------------"
sh copy_input_files.sh $hdfsUserPath
echo;echo
echo "----------------- BUILDING PROGRAM -------------------------"
sh build.sh $hdfsUserPath
echo;echo

echo "--------- EXECUTING PROGRAM ("world" as pattern) ------------"
sh run.sh $hdfsUserPath "world"
echo;echo
echo "-------- COPYING OUTPUT FILES FROM HDFS TO LOCAL FS --------"
sh copy_output_files.sh
echo;echo
echo "-------------- PRINTING CONTENTS OF OUTPUT FILES -----------"
cat ./output/new/*

echo "--------- EXECUTING PROGRAM ("luck" as pattern) ------------"
sh run.sh $hdfsUserPath "luck"
echo;echo
echo "-------- COPYING OUTPUT FILES FROM HDFS TO LOCAL FS --------"
sh copy_output_files.sh
echo;echo
echo "-------------- PRINTING CONTENTS OF OUTPUT FILES -----------"
cat ./output/new/*