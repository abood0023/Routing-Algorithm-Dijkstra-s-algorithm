package routing;

import routing.intNodeSLL;

class intSLL {

    public intNodeSLL head, last;   //References to the first and the last nodes in the list
    public int size;  // The number of elements in the list

    public intSLL() {
        head = last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void listSize() {
        System.out.println("List Size = " + size);
    }

    public void insertAtFront(int data) {
        if (isEmpty()) {
            head = last = new intNodeSLL(data);
        } else {
            head = new intNodeSLL(data, head, null);
            head.next.prev = head;
        }
        size++;
    }

    public void insertAtBack(int data) {
        if (isEmpty()) {
            head = last = new intNodeSLL(data);
        } else {
            last.next = new intNodeSLL(data, null, last);
            last = last.next;
        }
        size++;
    }

    public void removeFromFront() {
        if (isEmpty()) {
            System.out.println("Cannot remove from empty list ");
            return;
        } else if (head == last) {
            head = last = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    public void removeFromBack() {
        if (isEmpty()) {
            System.out.println("Cannot remove from empty list ");
            return;
        } else if (head == last) {
            head = last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        size--;
    }

    public boolean isInList(int x) {
        intNodeSLL temp = head;
        while (temp != null) {
            if (temp.getData() == x) {
                return true;
            }
            temp = temp.next;
        }
        return false;
    }

    public void removeNode(int x) {
        intNodeSLL temp1 = head;
        if (head.getData() == x) {
            removeFromFront();
        } else if (last.getData() == x) {
            removeFromBack();
        } else {
            while (temp1.next.getData() != x) {
                temp1 = temp1.next;
            }
            temp1.next = temp1.next.next;
            temp1.next.prev = temp1;
        }
        size--;
    }

}
