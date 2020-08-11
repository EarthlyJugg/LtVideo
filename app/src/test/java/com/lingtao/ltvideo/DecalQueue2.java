package com.lingtao.ltvideo;

import com.lingtao.ltvideo.struct.DecalQueue;

public class DecalQueue2<T> {

    private Node<T> first;//双向链表的头
    private int size = 0;

    //这个变量没用了
    private int indexs = 0;//这里代表每个添加进来的下标，调整队列后，每个节点对应的index 是不会变的

    public void put(T t) {
        if (first == null) {
            Node<T> node = new Node<>(indexs, null, null, t);
            this.first = node;
            indexs++;
            size++;
            return;
        }
        Node<T> preNode = first;
        Node<T> node = preNode.next;
        while (node != null) {
            preNode = node;
            node = node.next;
        }
        Node<T> lastNode = new Node<>(indexs, preNode, null, t);
        indexs++;
        size++;
        preNode.next = lastNode;

        //添加数据也要调整
        addSwap(t);

    }


    private void addSwap(T t) {// 2
        if (first.next == null) {//如果队列中只有一个数据，就不进行交换了
            System.out.println("队列中只有一个数据，就不进行交换");
            return;
        }

        if (first.next.next == null) {
            Node<T> tmpFirst = getFirst();
            Node<T> second = tmpFirst.next;

            second.pre = null;
            second.next = tmpFirst;
            tmpFirst.pre = second;
            tmpFirst.next = null;

            first = second;
            return;
        } else {
            Node<T> tmpFirst = getFirst();
            Node<T> curNode = getNode(t);


            curNode.pre.next = null;
            curNode.pre = null;
            curNode.next = tmpFirst;

            tmpFirst.pre = curNode;

            first = curNode;

        }


    }

    /**
     * 置顶
     *
     * @param bean
     */
    public void stickTop(T bean) {
        if (getNode(bean) == null) {
            throw new RuntimeException("bean 不存在");
        }

        //如果队列中只有一个数据，就不进行交换了
        //first.index == keyIndex  调整的节点已在表头，也不需要调整
        if (first == null || size == 1 || first.t == bean) {
            return;
        }


        if (size == 2) {
            Node<T> tmpFirst = getFirst();
            Node<T> second = tmpFirst.next;

            second.pre = null;
            second.next = tmpFirst;
            tmpFirst.pre = second;
            tmpFirst.next = null;

            first = second;
        } else {

            Node<T> curNode = getNode(bean);
            Node<T> tmpFirst = getFirst();
            if (curNode.next == null) {//说明操作的节点在队尾

                curNode.pre.next = null;
                curNode.pre = null;
                curNode.next = tmpFirst;

                tmpFirst.pre = curNode;
                first = curNode;

            } else {//操作的节点在队中
                curNode.pre.next = curNode.next;
                curNode.next.pre = curNode.pre;
                curNode.pre = null;
                curNode.next = tmpFirst;
                tmpFirst.pre = curNode;
                first = curNode;
            }

        }
    }

    public void remove(T t) {
        Node<T> node = getNode(t);
        if (node == null) {
            return;
        }
        if (node == first) {//如果删除的头节点
            Node<T> tmpFirst = getFirst();
            Node<T> next = tmpFirst.next;
            if (next != null) {
                next.pre = null;
            }
            tmpFirst.next = null;
            first = next;
            size--;
        } else {
            Node<T> preNode = node.pre;//如果不是头节点，这个preNode 绝对不可能为空
            Node<T> nextNode = node.next;

            preNode.next = nextNode;
            if (nextNode != null) {
                nextNode.pre = preNode;
            }
            node.pre = null;
            node.next = null;
            size--;

        }


    }

    public Node<T> getFirst() {
        return first;
    }

    private Node<T> getNode(T t) {
        Node<T> node = this.first;
        do {
            if (node.t == t) {
                return node;
            }
            node = node.next;
        } while (node != null);
        return null;
    }


    public void printf() {

        Node<T> node = first;
        while (node != null) {
            System.out.println(node.t.toString());
            node = node.next;
        }

    }


    public void printfFirst() {
        if (first != null) {
            System.out.println(first.t.toString());
        }
    }


    public int getSize() {
        return size;
    }


    public T next(int index) {
        if (index >= size) {
            throw new RuntimeException("长度越界");
        }
        int nextIndex = 0;
        Node<T> node = getFirst();
        do {
            if (nextIndex == index) {
                return node.t;
            }
            nextIndex++;
            node = node.next;
        } while (node != null);

        return null;
    }


    public static class Node<T> {
        T t;
        int index;
        Node<T> pre;
        Node<T> next;

        Node(int index, Node<T> pre, Node<T> next, T data) {
            this.t = data;
            this.index = index;
            this.next = next;
            this.pre = pre;
        }


    }

}
