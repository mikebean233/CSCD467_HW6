#! /bin/bash

hadoop com.sun.tools.javac.Main ./src/WordCount.java -d ./bin
jar -cvf ./bin/wc.jar -C ./bin/ .