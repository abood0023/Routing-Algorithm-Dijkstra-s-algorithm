package routing;

class intNodeSLL {

    private int data;         
    public intNodeSLL next;   
    public intNodeSLL prev;   

    public intNodeSLL(int i) {
        this(i, null, null);
    }

    public intNodeSLL(int i, intNodeSLL n, intNodeSLL p) {
        data = i;
        next = n;
        prev = p;
    }

    public void setData(int i) {
        data = i;
    }

    public int getData() {
        return data;
    }
}
