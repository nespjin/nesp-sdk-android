#!/bin/bash
LOCAL_PATH=$(cd `dirname $0`; pwd)
cd $LOCAL_PATH

# java -jar 文件名.jar 基准宽 基准高 额外支持尺寸1的宽,额外支持尺寸1的高_额外支持尺寸2的宽,额外支持尺寸2的高：
# 例如：需要设置的基准是800x1280,额外支持尺寸：735x1152 ；3200x4500；
# java -jar 文件名.jar 800 1280 735,1152_3200,4500
java -jar ./autolayout.jar 480 800 1440,2880