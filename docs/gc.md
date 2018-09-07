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

## [G1 garbage collector](https://www.youtube.com/watch?v=Gee7QfoY8ys)

### G1 Young GC phases

1. Root Scanning
   > static and local objects are scanned
2. Update RememberSet(RS)
   > Drains the dirty card queue to update the RS
3. Process RS
   > Detect the Eden objects pointed by the Old objects (to check if they are pointed by the Old objects)
4. Object copy
   > the object graph is traversed
   > live objects copied to Survivor/Old regions
5. Reference Processing
   > Soft, Weak, Phantom, Final, JNI weak references
   > Always enable -XX:+ParallelRefProcEnabled
   > More details with -XX:+PrintReferenceGC

### G1 Young GC phases with respect to phase times

* G1 tracks phase times to autotune

* Phase timing used to change the # of the regions

* By updating the # of regions
    * Respect the max pause target

* Typically, the shorter the pause target, the smaller the # of Eden regions

### G1 Old GC

* G1 schedules an Old GC based on heap usage
    * By default when the entire heap is 45% full
        * checked after a Young GC or a humongous allocation
    * Tunable via -XX:InitiatingHeapOccupancyPercent=<>
* The Old GC consists of old region marking
    * Finds all the live objects in the old regions (concurrent marking) (use black-grey mark, wipe out all the white node)
    * Old region marking is concurrent

### G1 Old GC mark miss issues (during mark, a reference become nil)

* G1 uses a write barrier to detect: B.c = null;
    * More precisely that a pointer to C has been deleted

* G1 now knows about object C

* Snapshot-At-The-Beginning (SATB)
    * Preserves the object graph that was live at the marking start
    * C is queued and processed during remark
    * May retain floating garbage, collected the next cycle

### G1 Old GC phases normal procedures

* G1 Stops The World

* Performs a Young GC
    * Piggybacks Old region roots detection (initial-mark)

* G1 resumes application threads

* Concurrent Old Region marking proceeds
    * Keep tracks of reference (soft, weak, etc..)
    * Computes per-region liveness information

* G1 stops the world

* Remark phase
    * STAB queue processing
    * Reference processing

* Cleanup phase
    * Empty old regions are immediately recycled

* Application threads are resumed

### G1 Mixed GC

* Mixed GC - piggybacked on Young GCs
    * By default G1 perform 8 mixed GC
    * -XX:G1MixedGCCountTarget=<>

* The collection set includes
    * Part(1/8) of the remaining Old regions to collect
    * Eden regions
    * Survivor regions

* Algorithm is identical to Young GC
    * Stop-the-world, Parallel, Copying

* Old regions with most garbage are chosen first
    * -XX:G1MixedGCLiveThresholdPercent=<>
    * Defaults to 85%

* G1 wastes some heap space (waste threshold)
    * -XX:G1HeapWastePercent=<>
    * Defaults to 5%

* Mixed GCs are stopped
    * when old region garbage <= waste threshold
    * therefore, mixed GC count may be less than 8

### G1 General advices

* Avoid at all costs full GC
    * The full GC is single threaded and REALLY slow
    * Also because G1 likes BIG heaps
* Grep the GC logs for "Full GC"
    * Use -XX:+PrintAdaptiveSizePolicy to know what caused it
* Avoid lengthy reference processing
    * Always enable -XX:+ParallelRefProcEnabled
    * More details with: -XX:+PrintReferenceGC
* Find the cause for WeakReferences
    * ThreadLocals
    * RMI
    * Third party libraries

## resources

1. cheat sheet of tune jvm --- [click](https://raw.githubusercontent.com/aragozin/sketchbook/download/Java%208%20-%20GC%20cheatsheet.pdf)

2. garbage collector [types](https://javapapers.com/java/types-of-java-garbage-collectors/)