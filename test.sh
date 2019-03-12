#!/bin/bash
clear
rm -rf ./test.log
rm -rf ./db/*.db
mvn package
