#!/bin/bash
clear
./clean.sh
mvn compile assembly:single
java -cp Norovin-jar-with-dependencies.jar com.knossys.rnd.Norovin
