# MOEATC
基于最小匹配距离的多目标演化算法停止条件判定算法。

## 依赖库
下列jar可以从[这里下载](https://github.com/MOEAFramework/MOEAFramework/releases/download/v2.11/MOEAFramework-2.11.tar.gz)下载。
+ commons-cli-1.2.jar
+ commons-codec-1.8.jar
+ commons-lang3-3.1.jar
+ commons-math3-3.4.1.jar
+ jcommon-1.0.20.jar
+ jfreechart-1.0.15.jar
+ JMetal-4.3.jar
+ MOEAFramework-2.11.jar
+ rsyntaxtextarea.jar

## 引用的代码
[HungarianAlgorithm.java](https://github.com/KevinStern/software-and-algorithms/blob/master/src/main/java/blogspot/software_and_algorithms/stern_library/optimization/HungarianAlgorithm.java)

## 编译运行
可选两种编译方式：
+ 将依赖的库放在`lib`文件夹下，导入Eclipse进行编译。
+ 添加依赖库之后以其它方式编译。

编译的结果命名为`MOEA.jar`放在`matlab`子文件夹下。然后就可以在matlab中进行调用了。