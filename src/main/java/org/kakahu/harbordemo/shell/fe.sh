#!/bin/bash
rm -rf /data/build/$2/*dist*
rm -rf /data/build/$2/*.zip
mv /data/upload/$1 /data/build/$2
cd /data/build/$2
unzip *.zip
