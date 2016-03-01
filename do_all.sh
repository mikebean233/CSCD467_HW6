#! /bin/bash

hdfsProgramRootPath=$1

echo;echo
echo "----------------- BUILDING PROGRAM -------------------------"
sh build.sh $hdfsProgramRootPath


# ======================= WORLD ================================ #

echo;echo
echo "---------- REMOVING OLD OUTPUT FILES FROM HDFS-------------"
sh remove_output_files.sh $hdfsProgramRootPath

echo;echo
echo "----------- COPYING NEW INPUT FILES TO HDFS ---------------"
sh copy_input_files.sh $hdfsProgramRootPath

echo;echo
echo "--------- EXECUTING PROGRAM ("\"world\"" as pattern) ------------"
sh run.sh $hdfsProgramRootPath "world"

echo;echo
echo "-------- COPYING OUTPUT FILES FROM HDFS TO LOCAL FS --------"
sh copy_output_files.sh $hdfsProgramRootPath

echo;echo
echo "-------------- PRINTING CONTENTS OF OUTPUT FILES -----------"
cat ./output/new/*


# ======================= LUCK ================================== #

echo;echo;
echo "---------- REMOVING OLD OUTPUT FILES FROM HDFS-------------"
sh remove_output_files.sh $hdfsProgramRootPath

echo;echo
echo "----------- COPYING NEW INPUT FILES TO HDFS ---------------"
sh copy_input_files.sh $hdfsProgramRootPath

echo;echo
echo "--------- EXECUTING PROGRAM ("\"luck\"" as pattern) ------------"
sh run.sh $hdfsProgramRootPath "luck"

echo;echo
echo "-------- COPYING OUTPUT FILES FROM HDFS TO LOCAL FS --------"
sh copy_output_files.sh $hdfsProgramRootPath

echo;echo
echo "-------------- PRINTING CONTENTS OF OUTPUT FILES -----------"
cat ./output/new/*