#! /bin/bash
echo;echo
echo "---------- REMOVING OLD OUTPUT FILES FROM HDFS-------------"
sh remove_output_files.sh
echo;echo
echo "----------- COPYING NEW INPUT FILES TO HDFD ---------------"
sh copy_input_files.sh
echo;echo
echo "----------------- BUILDING PROGRAM -------------------------"
sh build.sh
echo;echo
echo "-------------------- EXECUTING PROGRAM ---------------------"
sh run.sh
echo;echo
echo "-------- COPYING OUTPUT FILES FROM HDFS TO LOCAL FS --------"
sh copy_output_files.sh
echo;echo
echo "-------------- PRINTING CONTENTS OF OUTPUT FILES -----------"
cat ./output/new/*