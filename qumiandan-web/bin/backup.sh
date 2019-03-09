#!/bin/sh
cd /opt/deploy/app

files=$(ls qumiandan-web-*.jar 2> /dev/null | wc -l);
if [ $files != "0" ];
then
    echo "开始备份" $files;
    cp  qumiandan-web-*.jar  qumiandan-web-back.jar  
else
    echo "不存在 *.jar 无法备份！";
fi
