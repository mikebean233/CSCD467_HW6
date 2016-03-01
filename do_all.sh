#! /bin/bash

sh remove_output_files.sh
sh copy_input_files.sh
sh build.sh
sh run.sh
sh copy_output_files.sh

