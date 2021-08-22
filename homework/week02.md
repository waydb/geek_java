# week 02

1.（选做）使用 GCLogAnalysis.java 自己演练一遍串行 / 并行 /CMS/G1 的案例。

[GC练习](../01jvm/memory&gc)

```shell
# 获取GCLogAnalysis.java

# 编译GCLogAnalysis 得到.class
javac GCLogAnalysis.java

# 测试不同GC

## G1
java -Xmx1g -Xms1g -XX:+UseG1GC -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:gc_log/g1.log  GCLogAnalysis
## ParNewGC
java -Xmx1g -Xms1g -XX:+UseParNewGC -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:gc_log/par.log  GCLogAnalysis
## CMS
java -Xmx1g -Xms1g -XX:+UseConcMarkSweepGC -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:gc_log/cms.log  GCLogAnalysis
## SerializeGC
java -Xmx1g -Xms1g -XX:+UseSerialGC -verbose:gc -XX:+PrintGCDateStamps -XX:+PrintGCDetails -Xloggc:gc_log/serial.log  GCLogAnalysis
```

[gc日志结果](../01jvm/memory&gc/gc_log)

2.（选做）使用压测工具（wrk 或 sb），演练 gateway-server-0.0.1-SNAPSHOT.jar 示例。

3.（选做）如果自己本地有可以运行的项目，可以按照 2 的方式进行演练。

4.（必做）根据上述自己对于 1 和 2 的演示，写一段对于不同 GC 和堆内存的总结，提交到 GitHub。

[gc类型选择心得](../01jvm/memory&gc/gc_note.md)

5.（选做）运行课上的例子，以及 Netty 的例子，分析相关现象。

[nio项目netty部分](../02nio/nio/src/main/java/netty)

6.（必做）写一段代码，使用 HttpClient 或 OkHttp 访问  http://localhost:8801 ，代码提交到 GitHub

[nio项目okhttp部分](../02nio/nio/src/main/java/okhttp)
