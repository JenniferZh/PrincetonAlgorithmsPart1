# PrincetonAlgorithmsPart1 Notes

## Java对象内存大小估计

一个有n个元素的stack, 其中每个元素:
- **class overhead**：16bytes
- inner class(inner class Node): 8bytes
- inner class(inner class Iterator): 8bytes
- data member: 8bytes value + 8bytes next pointer

共计：48bytes

## Java Iterator
1.如果一个类Implements **Iterable**

那么这个类需要有iterator()函数，返回这个类的Itertor

2.如果一个类Implements **Iterator**

那么这个类需要有
- boolean hasNext():当前是不是最后一个
- Item next()：返回当前的，并把指针向后移动一个

3.为什么需要有Iterator

代表这个类表示拥有一系列元素的集合，可以被便历的。使用Iterator，就可以使用Java中的foreach loop
```
for(Item item: stack)
{
    
}
```

4.自己实现一个Iterator的关键点

- 把这个Iterator设置为你要Iterate的类的**Inner Class**
- 由于内部类可以访问外部类的内部变量，在iterator中设置一个**current**，一般指向外部类需要iterate的的集合的头指针。


## 为什么Java的数组不支持Generic泛型
主要是因为数组是covariant的，但是泛型是Invariant的。也就是String[]是Object[]的子类，但是Stack<String>不是Stack<Object>的子类。

Java在编译时的类型检查很严格，所以不支持。具体例子见 http://www.tothenew.com/blog/why-is-generic-array-creation-not-allowed-in-java/

## 堆排序的优缺点
1. 优点：一个In-place**不需要额外空间**，且**最差时间复杂度**也是nlgn的算法；相比快速排序，虽然inplacee，但是最差时n方的复杂度；相比归并，虽然最差是nlgn，但是需要额外空间
2. 缺点：不稳定排序；且对与**cache利用不好**，因为交换比较的元素经常距离很远，利用不上cache。。相比快排，对于cache的利用就很棒。

## 2-3 tree
1. 2-3tree的两种node
- 2-Node 这种节点里面只有一个Key
- 3-node 这种节点里面有两个key

2. 2-3tree的重要性质

从根节点到每一个叶子结点都是**一样高**的

3. 2-3tree的查找

和想象的一样

4. 2-3tree的插入

先插入到最底下一层，如果出现了4-node，把中间的key上浮，一直浮到根节点，如果根节点还是4-node,就分裂根节点

5. 时间复杂度
查找，插入，删除时间复杂度均为**logn**

## Left-leaning Red-Black Tree
1. 核心思想

2-3的二叉树表示！转化流程是把3-node拆分为left-leaning red-black tree，
[![iOcIN4.jpg](https://s1.ax1x.com/2018/11/13/iOcIN4.jpg)](https://imgchr.com/i/iOcIN4)

2. 主要性质
- 没有一个节点有两个红色边连在上面
- 每一个从根部到叶子节点的路径上-**黑色**边的数量相同，这条性质显而易见，因为它就是从2-3树演化而来的，我们可以把红色的边当做是internal的，不计算在内，所以红黑树的黑色边满足2-3树的定理
- 红色边在**左边**

3. 数据结构设计

对于如何表达一个红色边，我们规定如果一个节点和它的父亲之间是红色边，那这个节点的红色属性为true, 这样设计的原因是一个节点和自己的父节点可以唯一确定一条边。

```
private class Node {
    Key key;
    Value value;
    Node left, right;
    boolean color;
}
```

4. 基本操作

- 左旋
- 右旋
- Color flip
```
当一个节点左右都是红色边时，这两条红色边上浮到这个节点的父亲边上。
```

5. 查找的worst case
红黑树的高度**小于等于2logN**，原因很简单
- 黑链是等高的，全黑链的高度是logN
- 红链不能相连，因此在一条path上最多和黑链一样多，因此最多**2lgN**

6. 插入节点

插入的节点默认带一条红色链，再通过三种基本操作来转化成合法红黑树

## kd tree
1. 从1d key到2d key

键值从一维变成了二维，可以联想我们在一个平面上插入，寻找点的信息；具体规定的操作如下：
- insert 2d key
- search 2d key
- **range search**: 给定方形中的点
- **range count**: 给定方形中的点个数
