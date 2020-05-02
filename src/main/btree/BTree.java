package main.btree;


import main.record.Record;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BTree<K extends Comparable<K>, V> {

    private static final int T = 4;

    private BTreeNode root;

    public BTree() {
        root = new BTreeNode();
        root.setLeaf(true);
    }

    private class BTreeNode {

        private List<Record<K, V>> keys;
        private List<BTreeNode> children;
        private boolean leaf;


        public BTreeNode() {
            keys = new ArrayList<>();
            children = new ArrayList<>();
        }

        public BTreeNode(List<Record<K, V>> keys, List<BTreeNode> children, boolean leaf) {
            this.keys = keys;
            this.children = children;
            this.leaf = leaf;
        }

        public List<Record<K, V>> getKeys() {
            return keys;
        }

        public void setKeys(List<Record<K, V>> keys) {
            this.keys = keys;
        }

        public List<BTreeNode> getChildren() {
            return children;
        }

        public void setChildren(List<BTreeNode> children) {
            this.children = children;
        }

        public int size() {
            return keys.size();
        }

        public int nodeSize() {
            return children.size();
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        /**
         * 根据id删除记录
         * @param id 要删除的key值
         */
        public void removeKey(K id) {
            Iterator<Record<K, V>> iterator = keys.iterator();
            while (iterator.hasNext()) {
                Record<K, V> record = iterator.next();
                if (id.compareTo(record.getId()) == 0) {
                    iterator.remove();
                    break;
                }
            }
        }

        /**
         * 寻找key为id的下标
         * @param id 寻找的关键字
         * @return 下标，未找到返回-1
         */
        public int position(K id) {
            for (int i = 0; i < keys.size(); i++) {
                if (id.compareTo(keys.get(i).getId()) == 0)
                    return i;
            }
            return -1;
        }
    }

    /**
     * 供外部使用的search，通过调用private的search
     * @param key 寻找的key值
     * @return key对应的value值
     */
    public V search(K key) {
        return search(root, key);
    }

    /**
     * 寻找key值返回value，递归调用
     * @param node 搜寻的节点
     * @param key 要寻找的key
     * @return key对应的value
     */
    private @Nullable V search(BTreeNode node, K key) {
        int i = 0;
        while (i < node.size() && key.compareTo(node.getKeys().get(i).getId()) > 0)
            i++;
        if (i < node.size() && key.compareTo(node.getKeys().get(i).getId()) == 0)
            return node.getKeys().get(i).getInfo();
        else if (node.isLeaf())
            return null;
        else return search(node.getChildren().get(i), key);
    }

    /**
     * 分裂已满的子节点
     * @param node 父节点
     * @param i 已满子节点下标
     */
    public void splitChild(@NotNull BTreeNode node, Integer i) {
        BTreeNode childNode = node.getChildren().get(i);
        List<Record<K, V>> newNodeKeys = childNode.getKeys().subList(T , 2 * T - 1);
        List<BTreeNode> newNodeChildren = childNode.getChildren().subList(childNode.nodeSize() / 2, childNode.nodeSize());
        BTreeNode newNode = new BTreeNode(newNodeKeys, newNodeChildren, childNode.leaf);
        Record<K, V> middle = childNode.getKeys().get(T - 1);
        node.getKeys().add(i, middle);
        node.getChildren().add(i + 1, newNode);
        //一定要加new Arraylist！！！否则分裂后再插入报错ConcurrentModificationException找了巨久
        childNode.setKeys(new ArrayList<>(childNode.getKeys().subList(0, T - 1)));
        childNode.setChildren(new ArrayList<>(childNode.getChildren().subList(0, childNode.nodeSize() / 2)));
    }

    /**
     * 从根节点开始插入
     * @param key 插入的key
     */
    public void insert(Record<K, V> key) {
        if (root.getKeys().size() == 2 * T - 1) {
            BTreeNode newRoot = new BTreeNode();
            newRoot.getChildren().add(root);
            root = newRoot;
            splitChild(root, 0);
        }
        insertNotFull(root, key);
    }

    /**
     * 向未满的节点插入key，递归调用
     * @param node 节点
     * @param key 插入的记录
     */
    private void insertNotFull(@NotNull BTreeNode node, Record<K, V> key) {
        int i = node.getKeys().size();
        if (node.isLeaf()) {
            while (i > 0 && key.getId().compareTo(node.getKeys().get(i-1).getId()) < 0)
                i--;
            node.getKeys().add(i, key);
        } else {
            while (i > 0 && key.getId().compareTo(node.getKeys().get(i-1).getId()) < 0)
                i--;
            if (node.getChildren().get(i).size() == 2 * T - 1) {
                splitChild(node, i);
                if (key.getId().compareTo(node.getKeys().get(i).getId()) > 0) {
                    i++;
                }
            }
            insertNotFull(node.getChildren().get(i), key);
        }
    }

    /**
     * 供外部调用的delete方法，调用内部的delete方法从root开始删除
     * @param key 删除记录对应的key值
     */
    public void delete(K key) {
        delete(root, key);
    }

    /**
     * 递归删除方法
     * @param node 当前搜寻的节点
     * @param key 删除记录的key值
     */
    private void delete(@NotNull BTreeNode node, K key) {
        int position = node.position(key);
        //1.关键字在叶子节点直接删除,如果未查找到执行后无影响
        if (node.isLeaf()) {
            node.removeKey(key);
        } else if (position != -1) { //2.关键字在内部节点
            BTreeNode leftChild = node.getChildren().get(position);
            BTreeNode rightChild = node.getChildren().get(position + 1);
            if (leftChild.getKeys().size() > T - 1) { //a.前子节点至少包含t个关键字
                Record<K, V> maxRecord = predecessor(leftChild);
                node.getKeys().set(position, maxRecord);
                delete(leftChild, maxRecord.getId());
            } else if (rightChild.getKeys().size() > T - 1) { //b.后子节点至少包含t个关键字
                Record<K, V> minRecord = successor(rightChild);
                node.getKeys().set(position, minRecord);
                delete(rightChild, minRecord.getId());
            } else { //c.前后都只有t-1个关键字
                leftChild.getKeys().add(node.getKeys().get(position));
                leftChild.getKeys().addAll(rightChild.getKeys());
                leftChild.getChildren().addAll(rightChild.getChildren());
                node.getKeys().remove(position);
                node.getChildren().remove(position + 1);
                delete(leftChild, key);
            }
        } else { //3.关键字不在内部节点
            int index = 0;
            while (index < node.size() && key.compareTo(node.getKeys().get(index).getId()) > 0)
                index++;
            BTreeNode childNode = node.getChildren().get(index);
            if (childNode.getKeys().size() < T) { //若节点只有t-1个关键字需要降至至少t个关键字的节点
                BTreeNode leftChild = null;
                BTreeNode rightChild = null;
                if (index > 0 && (leftChild = node.getChildren().get(index - 1)).getKeys().size() > T - 1) {     //a1存在左兄弟且至少包含t个关键字
                    Record<K, V> maxRecord = leftChild.getKeys().get(leftChild.getKeys().size() - 1);
                    leftChild.getKeys().remove(leftChild.getKeys().size() - 1);
                    childNode.getKeys().add(0, node.getKeys().get(index - 1));
                    node.getKeys().set(index - 1, maxRecord);
                    if (!leftChild.getChildren().isEmpty()) {
                        BTreeNode newNode = leftChild.getChildren().get(leftChild.getChildren().size() - 1);
                        leftChild.getChildren().remove(leftChild.getChildren().size() - 1);
                        childNode.getChildren().add(0, newNode);
                    }
                } else if (index < node.getKeys().size() && (rightChild = node.getChildren().get(index + 1)).getKeys().size() > T - 1) {  //a2存在右兄弟且至少包含t个关键字
                    Record<K, V> minRecord = rightChild.getKeys().get(0);
                    rightChild.getKeys().remove(0);
                    childNode.getKeys().add(node.getKeys().get(index));
                    node.getKeys().set(index, minRecord);
                    if (!rightChild.getChildren().isEmpty()) {
                        BTreeNode newNode = rightChild.getChildren().get(0);
                        rightChild.getChildren().remove(0);
                        childNode.getChildren().add(newNode);
                    }
                } else {    //b都只有t-1个关键字，需要合并
                    if (leftChild != null) {    //与左兄弟合并
                        childNode.getKeys().add(0, node.getKeys().get(index - 1));
                        childNode.getKeys().addAll(0, leftChild.getKeys());
                        childNode.getChildren().addAll(0, leftChild.getChildren());
                        node.getKeys().remove(index - 1);
                        node.getChildren().remove(index - 1);
                    } else if (rightChild != null) {    //与右兄弟合并
                        childNode.getKeys().add(node.getKeys().get(index));
                        childNode.getKeys().addAll(rightChild.getKeys());
                        childNode.getChildren().addAll(rightChild.getChildren());
                        node.getKeys().remove(index);
                        node.getChildren().remove(index + 1);
                    }
                    if (node == root && node.getKeys().size() == 0) {
                        root = childNode;
                    }
                }
            }
            delete(childNode, key);
        }
    }

    /**
     * 寻找前驱节点
     * @param node 要寻找前驱节点的节点前子节点
     * @return 前驱节点key值
     */
    private Record<K, V> predecessor(BTreeNode node) {
        while (!node.isLeaf()) {
            node = node.getChildren().get(node.getChildren().size() - 1);
        }
        return node.getKeys().get(node.getKeys().size() - 1);
    }

    /**
     * 寻找后继节点
     * @param node 要寻找后继节点的节点的后子节点
     * @return 前驱节点key值
     */
    private Record<K, V> successor(BTreeNode node) {
        while (!node.isLeaf()) {
            node = node.getChildren().get(0);
        }
        return node.getKeys().get(0);
    }

    /**
     * 输出整棵树
     */
    public void output() {
        Queue<BTreeNode> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(null); //分层标记
        int level = 0;
        while (!queue.isEmpty()) {
            BTreeNode node = queue.poll();
            if (node == null) {
                level++;
                System.out.println("--------------------第" + level + "层------------------");
                queue.offer(null);
                node = queue.poll();
                if (node == null) {
                    break;
                }
            }
            System.out.println(node.getKeys());
            if (!node.isLeaf()) {
                for (int i = 0; i <= node.size(); ++i)
                    queue.offer(node.getChildren().get(i));
            }
        }
    }

}