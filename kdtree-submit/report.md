# 序
编写二维空间中使用暴力方法和kdtree方法解决
- 给定长方形，确定其包围的点集合问题
- 给定其中一点，找此点最近点问题

# kdtree实现要点

1. kdtree的节点插入模板

开始写节点插入时，使用while循环寻找插入位置，最后通过更新路径上的最后一个节点实现插入，这种插入的方法需要写很多判断条件，代码不简洁。关于BST的插入，kdtree的插入，可以使用递归调用的插入模板编写插入函数。

```
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        RectHV rect = new RectHV(0, 0, 1, 1);
        root = put(root, p, true, rect);
    }
    
    //递归插入，代表以node为根节点插入点p，并返回插入之后的根节点返回
    private TreeNode put(TreeNode node, Point2D p, boolean vertical, RectHV rect) {
        if (node == null) {
            size++;
            return new TreeNode(p, rect, vertical);
        }
        if (node.key.compareTo(p) == 0) return node;
        if (node.isVertical) {
            if (p.x() < node.key.x()) {
                //左下不变，右上左移
                RectHV rectleft = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                node.leftdown = put(node.leftdown, p, false, rectleft);
            } else {
                //右上不变，左下右移
                RectHV rectright = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.rightup = put(node.rightup, p, false, rectright);
            }
        } else {
            if (p.y() < node.key.y()) {
                //左下不变，右上下移
                RectHV rectdown = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                node.leftdown = put(node.leftdown, p, true, rectdown);
            } else {
                //右上不变，左下上移
                RectHV rectup = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
                node.rightup = put(node.rightup, p, true, rectup);
            }
        }
        return node;
    }
```

2. kdtree的数据结构设计

注意：需要添加两个变量
- isVertical表示当前的点是横向分割还是纵向分割
- rect是**当前点插入在了哪一个长方形里**，并不是以这个点划分后的某半边。例如如果点确定在1x1大小的方格中，插入的第一个点对应的长方形就是最大的1x1这个长方形。rect的设置会使判断长方形中的包围点问题更加容易。
```
private class TreeNode {
    private final Point2D key;
    private final RectHV rect;
    private TreeNode leftdown;
    private TreeNode rightup;
    private final boolean isVertical;

    TreeNode(Point2D point, RectHV rec, boolean vertical) {
        key = point;
        rect = rec;
        isVertical = vertical;
    }
}
```

3. rect长方形包围的点集问题的剪枝策略

如果某个子树的根节点对应的长方形和当前要查找的长方形没有交集，那么这个子树就会被剪枝。

4. 查找最近点问题的剪枝策略

如果某个子树的根节点对应的长方形到给定点的距离大于当前找到的最小距离，那么这个子树就会被剪枝。

5. 查找最近点的评分遍历问题

你可能会遇到自动评分系统在最近点问题出现错误，这是因为你需要做一个小优化：如果两边都没有被剪枝，应**该先遍历靠近查找点**的那一边。

## 结果

本次作业得分87，正确性全部通过，时间效率得分较低。