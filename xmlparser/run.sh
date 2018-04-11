#!/bin/bash

if [ "$#" -ne 3 ]
then echo "Syntax Error: XMLParser.sh <XSLFile> <XMLFile> <OutFile>"
	exit 1
fi

java -cp "saxon9he.jar:." XMLParser $1 $2 $3

