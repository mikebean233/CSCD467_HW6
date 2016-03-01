#! /bin/bash

hadoop fs -rm -r /user/mpeterson10/wc/input/
hadoop fs -mkdir  /user/mpeterson10/wc/input/
hadoop fs -copyFromLocal ./input/* /user/mpeterson10/wc/input