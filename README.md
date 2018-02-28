# tree-dbscan
Efforts to speed up DBSCAN to O(n log n) by using trees to index the points.

Running the performance tests for 100 points:
```
mvn clean package
java -Xss1G -jar performance-test/target/benchmarks.jar com.treedbscan.PerformanceTest -p points=100
```