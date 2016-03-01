#! /bin/bash
set -x
hadoop jar ./bin/wc.jar WordCount world /user/mpeterson10/wc/input /user/mpeterson10/wc/output

