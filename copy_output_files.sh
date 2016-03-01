#! /bin/bash
rm -r ./output/new
hadoop fs -copyToLocal /user/mpeterson10/wc/output/* ./output/new