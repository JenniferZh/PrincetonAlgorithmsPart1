# 序
渗透问题，如下图，可以装水的空心格子是open的（白色+蓝色），想象从上向下倒水，水可以流通的格子是full的(蓝色)，如果水可以直接流下去（如图）那么这个容器是可渗透的。

设计算法：时刻知道现在的容器是不是可渗透的

![image](http://coursera.cs.princeton.edu/algs4/assignments/percolation-204.png)

# 方案
当然是并查集啦

# 几个关键点

## 并查集的哈希方法

并查集应用的关键点是如何把现实问题映射到0-(n-1)上，比如这个问题的二维坐标点的映射方法，可以是row*n+col

## 虚拟点的设计
开始判断是否可渗透时，我的想法是两个for循环判断最上面一行的点和最下面一行的点是否存在两个相通的点。然而这种方法的时间复杂度很高，根据题目提示，采用上下各一个虚拟点，在点的添加时，如果是第一行，就和上虚拟点相连；如果是最后一行，就和下虚拟点相连。

## backwash倒流问题

当采取上述的上下虚拟点方案后，可能出现倒流的问题。出现原因如下：当系统可渗透时，上虚拟点和下虚拟点已经处于一个集合内，而和下虚拟点相连的点不一定是full的，却会因为上下虚拟点的合二为一而被误认为是full的（标成蓝色点），因此产生了错误的蓝色标注。

解决方案是，采用两个并查集，一个绑定上虚拟点，一个绑定下虚拟点。

![image](http://coursera.cs.princeton.edu/algs4/checklists/percolation-backwash.png)

# 结果

本次实验得分99，通过了所有正确性测试。