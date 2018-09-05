# How to set JAVA_OPTS in java to tune the JVM?

## base
0. show all the available jvm tuning flags
```cmd
    java -XX:+PrintFlagsFinal -version | findstr /i "HeapSize PermSize ThreadStackSize"
```
```bash
    java -XX:+PrintFlagsFinal -version  | grep -iE 'HeapSize|PermSize|ThreadStackSize'
```
1. This -XX:+PrintCommandLineFlags is used to print out the values that modified by VM only (indicated by this := symbol).
```bash
    java -XX:+PrintCommandLineFlags -version
```
2. check the details of gc using
```bash
    java -XX:+PrintCommandLineFlags -XX:+PrintGC -XX:+PrintGCDetails -version
```
3. use a different gc
```bash
    java -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC -version
```
4. set the heap size and metaspace size (by default, Xms =1/64 total mem, Xmx=1/4 total mem)
```bash
    java -Xms50m -Xmx200m -XX:MetaspaceSize=60m -XX:MaxMetaspaceSize=200m -version
```

## resources

1. cheat sheet of tune jvm --- [click](https://raw.githubusercontent.com/aragozin/sketchbook/download/Java%208%20-%20GC%20cheatsheet.pdf)

