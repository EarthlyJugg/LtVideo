package com.lingtao.ltvideo;

public class DecalQueue2<T> {

    private Node<T> first;//双向链表的头
    private int size = 0;
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
        addSwap(size - 1);

    }


    private void addSwap(int index) {// 2
        if (first.next == null) {//如果队列中只有一个数据，就不进行交换了
            System.out.println("队列中只有一个数据，就不进行交换");
            return;
        }

        if (first.next.next == null) {
            System.out.println("队列中只有二个数据");
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
            Node<T> curNode = getNode(index);


            curNode.pre.next = null;
            curNode.pre = null;
            curNode.next = tmpFirst;

            tmpFirst.pre = curNode;

            first = curNode;

        }


    }

    public void getSwap(T bean) {
        if (getNode(bean) == null) {
            throw new RuntimeException("bean 不存在");
        }

        //如果队列中只有一个数据，就不进行交换了
        //first.index == keyIndex  调整的节点已在表头，也不需要调整
        if (first == null || size == 1 || first.t.equals(bean)) {
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

    private void getSwap(int keyIndex) {
        if (getNode(keyIndex) == null) {
            throw new RuntimeException("下标越界");
        }
        //如果队列中只有一个数据，就不进行交换了
        //first.index == keyIndex  调整的节点已在表头，也不需要调整
        if (first == null || size == 1 || first.index == keyIndex) {
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

            Node<T> curNode = getNode(keyIndex);
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

    public Node<T> getFirst() {
        return first;
    }


    private Node<T> getNode(int index) {

        Node<T> node = this.first;
        do {
            if (node.index == index) {
                return node;
            }
            node = node.next;
        } while (node != null);
        return null;
    }

    private Node<T> getNode(T t) {
        Node<T> node = this.first;
        do {
            if (node.t.equals(t)) {
                System.out.println("比较相等");
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
