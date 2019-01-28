package routing;

public class Vertex {

    private int x;
    private int y;
    private int hightestX, lowestX;
    private int hightestY, lowestY;
    private int weight;
    private Integer name;
    private Vertex nameProcedor;
    private static int count = 0;
    private Object position;

    public static void setCount(int count) {
        Vertex.count = count;
    }

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.hightestX = x + 50;
        this.lowestX = x - 20;
        this.hightestY = y + 50;
        this.lowestY = y - 20;
        this.name = ++count;
        this.weight = 0;
        this.nameProcedor = null;
    }

    public Object getPosition() {
        return position;
    }

    public int getHightestX() {
        return hightestX;
    }

    public int getLowestX() {
        return lowestX;
    }

    public int getHightestY() {
        return hightestY;
    }

    public int getLowestY() {
        return lowestY;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getName() {
        return name;
    }

    public Vertex getProcedor() {
        return nameProcedor;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setX(int x) {
        this.x = x;
        this.hightestX = x + 50;
        this.lowestX = x - 20;
    }

    public void setY(int y) {
        this.y = y;
        this.hightestY = y + 50;
        this.lowestY = y - 20;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public void setProcedor(Vertex nameProcedor) {
        this.nameProcedor = nameProcedor;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setPosition(Object position) {
        this.position = position;
    }
}
