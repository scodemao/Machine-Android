#!/bin/bash
basedir=$(cd "$(dirname "$0")";pwd)
cd $basedir
IFS=$'\n'
###市场,需更改###
markets="蝉游记
小米
AppSolution
Google Play
粉丝通
豌豆荚
豌豆荚推广
360
安卓市场
百度
安智
淘宝
三星
腾讯应用宝
应用汇
联想
OPPO
华为
魅族
机锋
搜狗
贴吧
小恩爱
堆糖"
###内部市场名，需要与市场列表11对应
lettermarkets="chanyouji
xiaomi
appso
google
sinafensi
wandoujia
wandoujiapromo
360
hiapk
baidu
anzhi
taobao
samsung
tencentapp
appchina
lenovo
oppo
huawei
meizu
gfan
sogou
tieba
xiaoenai
duitang"


declare -a lettermarkets=(蝉游记
小米
AppSolution
Google Play
粉丝通
豌豆荚
豌豆荚推广
360
安卓市场
百度
安智
淘宝
三星
腾讯应用宝
应用汇
联想
OPPO
华为
魅族
机锋
搜狗
贴吧
小恩爱
堆糖
)

markets="蝉游记
小米
AppSolution
Google Play
粉丝通
豌豆荚
豌豆荚推广
360
安卓市场
百度
安智
淘宝
三星
腾讯应用宝
应用汇
联想
OPPO
华为
魅族
机锋
搜狗
贴吧
小恩爱
堆糖
"

lettermarkets=(chanyouji
xiaomi
appso
google
sinafensi
wandoujia
wandoujiapromo
360
hiapk
baidu
anzhi
taobao
samsung
tencentapp
appchina
lenovo
oppo
huawei
meizu
gfan
sogou
tieba
xiaoenai
duitang
)

###版本号，需更改###
apkversion="1.1"
internalcode="1.1.0"
###版本的versionCode，需更改##
apkversioncode="10"



###版本发布日志###
log="新导航"

##更改版本号##
sed -i -e "s/name=\"versionName\">.*</name=\"versionName\">$apkversion</g" res/values/config.xml

##更改versioncode和versionname##
sed -i -e "s/versionCode=\".*\"/versionCode=\"$apkversioncode\"/g" AndroidManifest.xml
sed -i -e "s/versionName=\".*\"/versionName=\"$apkversion\"/g" AndroidManifest.xml

declare -i index=0

for market in $markets
do
	echo packaging $market
	sed -i -e "s/name=\"channel_name\">.*</name=\"channel_name\">$market</g" res/values/config.xml
	sed -i -e "s/name=\"channel_letter\">.*</name=\"channel_letter\">${lettermarkets[$((index++))]}</g" res/values/config.xml
	ant
	mv bin/wiki-release.apk release/wiki-$apkversion-$market.apk
done
