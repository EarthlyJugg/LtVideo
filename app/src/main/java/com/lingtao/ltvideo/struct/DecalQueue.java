package com.lingtao.ltvideo.struct;

/**
 * 双向链表结构，每新添加一个数据都会放到表头，对于某项数据也可以置顶
 * 不可用于数据量大的数据存储，因为查询使用了顺序查询，添加数据的时候是先添加到尾部，然后在调整到头部
 */
public class DecalQueue<T> {
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

        if (first.next.next == null) {//列表中只有有两个数据，也可以用size=2
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
    private void stickTop(T bean) {
        if (getNode(bean) == null) {
            throw new RuntimeException("bean 不存在");
        }

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

    public T remove(T t) {
        Node<T> node = getNode(t);
        if (node == null) {
            return null;
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

            return tmpFirst.t;
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

            return node.t;

        }

    }

    public T remove(int index) {
        return remove(get(index));
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


    public int size() {
        return size;
    }

    /**
     * 循环使用这个，使用get 会调整队列
     * @param index
     * @return
     */
    public T next(int index) {
        T t = node(index).t;
        return t;
    }

    public T get(int index) {
        T t = node(index).t;
        stickTop(t);
        return t;
    }


    private Node<T> node(int index) {

        Node<T> x = first;
        for (int i = 0; i < index; i++) {
            x = x.next;
        }
        return x;
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
