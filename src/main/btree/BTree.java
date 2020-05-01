package main.btree;


import main.record.Record;

import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BTree {

    private static final int T = 4;

    private BTreeNode root;

    public BTree() {
        root = new BTreeNode();
        root.setLeaf(true);
    }

    private static class BTreeNode {

        private List<Record> keys;
        private List<BTreeNode> children;
        private boolean leaf;

        public BTreeNode() {
            keys = new ArrayList<>();
            children = new ArrayList<>();
        }

        public BTreeNode(List<Record> keys, List<BTreeNode> children, boolean leaf) {
            this.keys = keys;
            this.children = children;
            this.leaf = leaf;
        }

        public List<Record> getKeys() {
            return keys;
        }

        public void setKeys(List<Record> keys) {
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
    }

    public static int getT() {
        return T;
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode root) {
        this.root = root;
    }

    public String search(Integer key) {
        return search(root, key);
    }

    private String search(BTreeNode node, Integer key) {
        int i = 0;
        while (i < node.size() && key > node.getKeys().get(i).getId())
            i++;
        if (i < node.size() && key == node.getKeys().get(i).getId())
            return node.getKeys().get(i).getInfo();
        else if (node.isLeaf())
            return null;
        else return search(node.getChildren().get(i), key);
    }

    public void splitChild(BTreeNode node, Integer i) {
        BTreeNode childNode = node.getChildren().get(i);
        List<Record> newNodeKeys = childNode.getKeys().subList(T , 2 * T - 1);
        List<BTreeNode> newNodeChildren = childNode.getChildren().subList(childNode.nodeSize() / 2, childNode.nodeSize());
        BTreeNode newNode = new BTreeNode(newNodeKeys, newNodeChildren, childNode.leaf);
        Record middle = childNode.getKeys().get(T - 1);
        node.getKeys().add(i, middle);
        node.getChildren().add(i + 1, newNode);
        //一定要加new Arraylist！！！否则分裂后再插入报错ConcurrentModificationException找了巨久
        childNode.setKeys(new ArrayList<>(childNode.getKeys().subList(0, T - 1)));
        childNode.setChildren(childNode.getChildren().subList(0, childNode.nodeSize() / 2));
    }

    public void insert(Record key) {
        if (root.getKeys().size() == 2 * T - 1) {
            BTreeNode newRoot = new BTreeNode();
            newRoot.getChildren().add(root);
            root = newRoot;
            splitChild(root, 0);
        }
        insertNotFull(root, key);
    }

    private void insertNotFull(BTreeNode node, Record key) {
        int i = node.getKeys().size();
        if (node.isLeaf()) {
            while (i > 0 && key.getId() < node.getKeys().get(i-1).getId())
                i--;
            node.getKeys().add(i, key);
        } else {
            while (i > 0 && key.getId() < node.getKeys().get(i-1).getId())
                i--;
            if (node.getChildren().get(i).size() == 2 * T - 1) {
                splitChild(node, i);
                if (key.getId() > node.getKeys().get(i).getId()) {
                    i++;
                }
            }
            insertNotFull(node.getChildren().get(i), key);
        }
    }

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

    public void test() {
        System.out.println("test: " + root.getChildren().get(0).getKeys());
        System.out.println("test: " + root.getChildren().get(1).getKeys());
    }
}