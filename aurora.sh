#!/bin/bash
#set echo off

echo $0 $1 $2 $3 $4 $5

java -jar --enable-preview "./aurora.jar" $0 $1 $2 $3 $4 $5

#set echo on
