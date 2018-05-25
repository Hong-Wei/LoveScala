#!/bin/bash
#http://www.runoob.com/linux/linux-shell.html

echo "Hello World"

your_name="hongwei" 
echo $your_name

your_name="hongwei1" 
echo ${your_name}

for skill in Ada Coffe Action Java; do
   echo "I am good at ${skill}Script"
   echo "I am good at $skillScript"
done

myUrl="http://www.w3cschool.cc"
myUrl="http://www.runoob.com"
readonly myUrl

unset your_name
echo ${your_name}

your_name="qinjx"
greeting="hello, "$your_name" !"
greeting_1="hello, ${your_name} !"
str="Hello, I know your are \"$your_name\"! \n" #双引号里可以有变量, 可以有转义字符
str1='Hello, I know your are \"$your_name\"! \n' #单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
echo $greeting 
echo $greeting_1 
echo $str 
echo $str1 
echo ${#str1} 
echo ${str1:1:4} # 输出 ello

string="runoob is a great company"
#echo `expr index "$string" is`  # 输出 8 TODO ? not clear??

array_name=(value0 value1 value2 value3)
array_name[0]=value0
array_name[1]=value1
array_name[n]=valueN

valuen=${array_name[n]}
echo $valuen
echo ${array_name[@]} # @get all the items in the array
# 取得数组元素的个数
length=${#array_name[@]}
# 或者
length=${#array_name[*]}
# 取得数组单个元素的长度
lengthn=${#array_name[n]}



echo "Shell 传递参数实例！";
echo "执行的文件名：$0";
echo "第一个参数为：$1";
echo "第二个参数为：$2";
echo "第三个参数为：$3";


val=`expr 2 + 2`
echo "两数之和为 : $val"

a=10
b=20

val=`expr $a + $b`
echo "a + b : $val"

val=`expr $a - $b`
echo "a - b : $val"

val=`expr $a \* $b`
echo "a * b : $val"

val=`expr $b / $a`
echo "b / a : $val"

val=`expr $b % $a`
echo "b % a : $val"

if [ $a == $b ]
then
   echo "a 等于 b"
fi
if [ $a != $b ]
then
   echo "a 不等于 b"
fi

if [ $a -eq $b ]
then
   echo "$a -eq $b : a 等于 b"
else
   echo "$a -eq $b: a 不等于 b"
fi
if [ $a -ne $b ]
then
   echo "$a -ne $b: a 不等于 b"
else
   echo "$a -ne $b : a 等于 b"
fi
if [ $a -gt $b ]
then
   echo "$a -gt $b: a 大于 b"
else
   echo "$a -gt $b: a 不大于 b"
fi
if [ $a -lt $b ]
then
   echo "$a -lt $b: a 小于 b"
else
   echo "$a -lt $b: a 不小于 b"
fi
if [ $a -ge $b ]
then
   echo "$a -ge $b: a 大于或等于 b"
else
   echo "$a -ge $b: a 小于 b"
fi
if [ $a -le $b ]
then
   echo "$a -le $b: a 小于或等于 b"
else
   echo "$a -le $b: a 大于 b"
fi

if [ $a != $b ]
then
   echo "$a != $b : a 不等于 b"
else
   echo "$a != $b: a 等于 b"
fi
if [ $a -lt 100 -a $b -gt 15 ]
then
   echo "$a 小于 100 且 $b 大于 15 : 返回 true"
else
   echo "$a 小于 100 且 $b 大于 15 : 返回 false"
fi
if [ $a -lt 100 -o $b -gt 100 ]
then
   echo "$a 小于 100 或 $b 大于 100 : 返回 true"
else
   echo "$a 小于 100 或 $b 大于 100 : 返回 false"
fi
if [ $a -lt 5 -o $b -gt 100 ]
then
   echo "$a 小于 5 或 $b 大于 100 : 返回 true"
else
   echo "$a 小于 5 或 $b 大于 100 : 返回 false"
fi


if [[ $a -lt 100 && $b -gt 100 ]]
then
   echo "返回 true"
else
   echo "返回 false"
fi

if [[ $a -lt 100 || $b -gt 100 ]]
then
   echo "返回 true"
else
   echo "返回 false"
fi

file="/Users/zhanghongwei/Documents/workspaceScala/LoveScala/src/main/scala/learnShell/s1.sh"
if [ -r $file ]
then
   echo "文件可读"
else
   echo "文件不可读"
fi
if [ -w $file ]
then
   echo "文件可写"
else
   echo "文件不可写"
fi
if [ -x $file ]
then
   echo "文件可执行"
else
   echo "文件不可执行"
fi
if [ -f $file ]
then
   echo "文件为普通文件"
else
   echo "文件为特殊文件"
fi
if [ -d $file ]
then
   echo "文件是个目录"
else
   echo "文件不是个目录"
fi
if [ -s $file ]
then
   echo "文件不为空"
else
   echo "文件为空"
fi
if [ -e $file ]
then
   echo "文件存在"
else
   echo "文件不存在"
fi

echo -e "OK! \n" 
echo "OK! \n" 
echo "OK! \n" 
echo -e "OK! \c" # -e 开启转义 \c 不换行

echo "It is a test" > myfile.log
echo `date`


if [ $(ps -ef | grep -c "ssh") -gt 1 ]; then echo "true"; fi


#!/bin/bash
# author:菜鸟教程
# url:www.runoob.com

demoFun(){
    echo "这是我的第一个 shell 函数!"
}
echo "-----函数开始执行-----"
demoFun
echo "-----函数执行完毕-----"


#funWithReturn(){
#    echo "这个函数会对输入的两个数字进行相加运算..."
#    echo "输入第一个数字: "
#    read aNum
#    echo "输入第二个数字: "
#    read anotherNum
#    echo "两个数字分别为 $aNum 和 $anotherNum !"
#    return $(($aNum+$anotherNum))
#}
#funWithReturn
#echo "输入的两个数字之和为 $? !" #函数返回值在调用该函数后通过 $? 来获得。



funWithParam(){
    echo "第一个参数为 $1 !"
    echo "第二个参数为 $2 !"
    echo "第十个参数为 $10 !"
    echo "第十个参数为 ${10} !"
    echo "第十一个参数为 ${11} !"
    echo "参数总数有 $# 个!"
    echo "脚本运行的当前进程ID号: $$ "
    echo "作为一个字符串输出所有参数 $* !"
}
funWithParam 1 2 3 4 5 6 7 8 9 34 73

