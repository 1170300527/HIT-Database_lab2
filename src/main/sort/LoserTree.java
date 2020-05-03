package main.sort;

import java.util.ArrayList;

public class LoserTree<T extends Comparable<T>> {

    //内部节点
    private Integer[] tree;
    private int size;
    private final ArrayList<T> leaves;
    private static final Integer MIN_KEY = Integer.MIN_VALUE;

    /**
     * 败者树构造函数
     * @param initValues 初始化叶子节点的数组，即各个归并段的首元素组成的数组
     */
    public LoserTree(ArrayList<T> initValues) {
        this.leaves = initValues;
        this.size = initValues.size();
        this.tree = new Integer[size];

        //初始化时，树中各个节点值设为可能的最小值
        for (int i = 0; i < size; i++) {
            tree[i] = MIN_KEY;
        }

        //从最后一个节点开始调整
        for (int i = size - 1; i >= 0; i--) {
            adjust(i);
        }

    }

    /**
     * 从底向上调整树结构
     * @param s 叶子节点数组的下标
     */
    private void adjust(int s) {
        // tree[Integer] 是 leaves[s] 的父节点
        int t = (s + size) / 2;
        while (t > 0) {
            if (s >= 0 && (tree[t].equals(MIN_KEY) || leaves.get(s).compareTo(leaves.get(tree[t])) > 0)) {
                // 当前节点比它的父节点大，则把当前节点记录为败者，
                //并把父节点作为胜者进入下一轮比较
                int temp = s;
                s = tree[t];
                tree[t] = temp;
            }
            // tree[Integer/2] 是 tree[Integer] 的父节点
            t /= 2;
        }
        tree[0] = s;
    }

    /**
     * 添加叶子节点
     * @param leaf 叶子节点值
     * @param s    叶子节点的下标
     */
    public void add(T leaf, int s) {
        leaves.set(s, leaf);
        adjust(s);
    }

    /**
     * 删除叶子节点
     * @param s 叶子节点的下标
     */
    public void del(int s) {
        //删除叶子节点
        leaves.remove(s);
        this.size--;
        this.tree = new Integer[size];

        //重新初始化并调整败者树
        for (int i = 0; i < size; i++) {
            tree[i] = MIN_KEY;
        }
        for (int i = size - 1; i >= 0; i--) {
            adjust(i);
        }

    }

    /**
     * 获取叶子节点
     * @param s 叶子节点下标
     * @return 叶子节点
     */
    public T getLeaf(int s) {
        return leaves.get(s);
    }

    /**
     * 获得胜者(值为最终胜出的叶子节点的下标)
     * @return 胜利者
     */
    public Integer getWinner() {
        return tree.length > 0 ? tree[0] : null;
    }

}

