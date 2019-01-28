package routing;

public class HashTable {

    intSLL list[];
    int capacity;

    public HashTable(int capacity) {
        this.capacity = capacity;
        list = new intSLL[capacity];
        for (int i = 0; i < capacity; i++) {
            list[i] = new intSLL();
        }
    }

    public int hashFunction(int val) {
        return val % capacity;
    }

    public boolean isInTable(int val) {
        int index = hashFunction(val);
        return list[index].isInList(val);
    }

    public void addToTable(int val) {
        int index = hashFunction(val);
        list[index].insertAtBack(val);
    }

    public void removeFromTable(int val) {
        int index = hashFunction(val);
        if (!list[index].isEmpty()) {
            list[index].removeNode(val);
        }

    }
}
