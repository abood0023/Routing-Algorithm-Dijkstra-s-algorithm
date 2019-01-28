package routing;

import java.util.Random;
import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Visualization extends JFrame implements WindowListener, ActionListener {

    private static final long serialVersionUID = -2707712944901661771L;
    private Vertex vertices[];
    private int AdjisentMatrix[][];
    private static MyGraph graph = new MyGraph();
    private JList listNumberOfVertices, listNameVerticesFrom, listNameVerticesTo;
    private JPanel panel;
    private JScrollPane scrollForList, scrollForLabel, scrollForListFrom, scrollForListTo;
    private JLabel labelForResult, labelFrom, labelTo, NumberOfSC;
    private JButton drawBestPath, drawGraph;
    private Object lListData[];
    private boolean graphDrawed, bestEdgeDrawed;

    public Visualization() {
        super("Visualization of the routing algorithm");
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);//disable adding edges
        getContentPane().add(graphComponent);
        graph.setDropEnabled(false);//disable drop vertex on the edge
        graph.setAllowDanglingEdges(false);
        graph.setCellsResizable(false);

        graphDrawed = false;
        bestEdgeDrawed = false;

        drawGraph = new JButton("Draw Random Graph");
        drawGraph.addActionListener(this);

        panel = new JPanel();

        Object listData[] = {10, 20, 30, 40, 50};
        listNumberOfVertices = new JList(listData);
        scrollForList = new JScrollPane(listNumberOfVertices);
        scrollForList.setPreferredSize(new Dimension(50, 50));

        listNameVerticesFrom = new JList();
        listNumberOfVertices.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && listNumberOfVertices.getSelectedIndex() >= 0) {
                    graphDrawed = false;
                    lListData = new Object[(Integer) listNumberOfVertices.getSelectedValue()];
                    for (int i = 0; i < lListData.length; i++) {
                        lListData[i] = i + 1;
                    }
                    listNameVerticesFrom.setListData(lListData);
                }
            }
        });
        scrollForListFrom = new JScrollPane(listNameVerticesFrom);
        scrollForListFrom.setPreferredSize(new Dimension(50, 50));

        listNameVerticesTo = new JList();
        listNameVerticesFrom.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if (!arg0.getValueIsAdjusting() && listNameVerticesFrom.getSelectedIndex() >= 0 && graphDrawed) {
                    RoutingAlgorithm.routingAlgorithm(vertices, AdjisentMatrix, vertices[(Integer) listNameVerticesFrom.getSelectedValue() - 1]);
                    labelForResult.setText("The results after apply Routing Algorthem");
                    String com = "<html>" + labelForResult.getText();
                    for (int i = 0; i < vertices.length; i++) {
                        if ((Integer) listNameVerticesFrom.getSelectedValue() != (i + 1)) {
                            com = com + "<br>" + "Bandwidth from " + listNameVerticesFrom.getSelectedValue() + " to " + (i + 1) + " = " + vertices[i].getWeight();
                        }
                    }
                    labelForResult.setText(com + "</html>");
                    Object listData[] = new Object[lListData.length - 1];
                    for (int i = 0, j = 0; i < lListData.length; i++) {
                        if (!listNameVerticesFrom.getSelectedValue().equals(i + 1)) {
                            listData[j] = i + 1;
                            j++;
                        }
                    }
                    listNameVerticesTo.setListData(listData);

                }
            }
        });
        scrollForListTo = new JScrollPane(listNameVerticesTo);
        scrollForListTo.setPreferredSize(new Dimension(50, 50));

        NumberOfSC = new JLabel("<html>Choose the<br>number of<br>switch center : </html>");
        labelForResult = new JLabel("The results after apply Routing Algorthem");
        labelFrom = new JLabel("<html>Choose<br>source<br>name : </html>");
        labelTo = new JLabel("To");

        scrollForLabel = new JScrollPane(labelForResult);
        scrollForLabel.setPreferredSize(new Dimension(270, 50));

        drawBestPath = new JButton("Draw Best Path");
        drawBestPath.addActionListener(this);

        add(panel, BorderLayout.SOUTH);
        panel.add(NumberOfSC);
        panel.add(scrollForList);
        panel.add(drawGraph);
        panel.add(labelFrom);
        panel.add(scrollForListFrom);
        panel.add(scrollForLabel);
        panel.add(labelTo);
        panel.add(scrollForListTo);
        panel.add(drawBestPath);
    }

    public int[][] genarateGraph(int namberOfVertex) {
        int size = namberOfVertex;
        AdjisentMatrix = new int[size][size];
        HashTable table;
        Random randomWieghted = new Random();
        Random randomVertex = new Random();
        int x, y;

        for (int i = 0; i < size; i++) {
            table = new HashTable(13);
            table.addToTable(i);
            for (int j = 0; j < size / 4; j++) {
                x = randomVertex.nextInt(size);

                while (table.isInTable(x)) {
                    x = randomVertex.nextInt(size);
                }
                AdjisentMatrix[i][x] = randomWieghted.nextInt(12) + 1;

                table.addToTable(x);
            }
        }
        return AdjisentMatrix;
    }

    public boolean isInForbiddenAreas(int x, int y) {
        for (int i = 0; vertices[i].getX() != 0; i++) {
            if ((vertices[i].getLowestX() < x && x < vertices[i].getHightestX() && vertices[i].getLowestY() < y && y < vertices[i].getHightestY())
                    || (vertices[i].getLowestX() < (x + 50) && (x + 50) < vertices[i].getHightestX() && vertices[i].getLowestY() < (y + 50) && (y + 50) < vertices[i].getHightestY())
                    || (vertices[i].getLowestX() < (x + 50) && (x + 50) < vertices[i].getHightestX() && vertices[i].getLowestY() < y && y < vertices[i].getHightestY())
                    || (vertices[i].getLowestX() < x && x < vertices[i].getHightestX() && vertices[i].getLowestY() < (y + 50) && (y + 50) < vertices[i].getHightestY())) {
                return true;
            }
        }
        return false;
    }

    public void beforDraw() {
        Random x = new Random();
        vertices[0].setX(x.nextInt(950));
        vertices[0].setY(x.nextInt(650));
        int tempX, tempY;
        for (int i = 1; i < vertices.length; i++) {
            tempX = x.nextInt(950);
            tempY = x.nextInt(650);
            while (isInForbiddenAreas(tempX, tempY)) {
                tempX = x.nextInt(950);
                tempY = x.nextInt(650);
            }
            vertices[i].setX(tempX);
            vertices[i].setY(tempY);
        }

    }

    public void drawVertices() {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setPosition(graph.insertVertex(graph.getDefaultParent(), null, vertices[i].getName(), vertices[i].getX(), vertices[i].getY(), 30, 30, "shape=ellipse;strokeColor=8A98F5;fillColor=8A98F5"));

        }

    }
//    public static void drawVertices() {
//            graph.insertVertex(graph.getDefaultParent(), null, 1, 0, 0, 30, 30, "shape=ellipse;strokeColor=8A98F5;fillColor=8A98F5");
//
//    }

    public void drawEdges() {
        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                if (AdjisentMatrix[i][j] != 0) {
                    graph.insertEdge(graph.getDefaultParent(), null, AdjisentMatrix[i][j], vertices[i].getPosition(), vertices[j].getPosition(), "LINE;strokeColor=#FF00DA");
                }
            }
        }
    }

    public static void deleteEdges(Vertex v1, Vertex v2) {
        Object n[] = graph.getEdgesBetween(v1.getPosition(), v2.getPosition());
        for (int i = 0; i < n.length; i++) {
            graph.getModel().remove(n[i]);
        }
    }

    public void deleteAllEdges() {

        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                if (AdjisentMatrix[i][j] != 0) {
                    Object n[] = graph.getEdgesBetween(vertices[i].getPosition(), vertices[j].getPosition());
                    for (Object n1 : n) {
                        graph.getModel().remove(n1);
                    }
                }
            }

        }
    }

    public void drawEdge(Vertex v1, Vertex v2) {
        graph.insertEdge(graph.getDefaultParent(), null, AdjisentMatrix[v1.getName() - 1][v2.getName() - 1], v1.getPosition(), v2.getPosition(), "LINE;strokeColor=#00FF2A");
    }

    public static void deleteVertex(Vertex v1) {
        graph.getModel().remove(v1.getPosition());
    }

    public void deleteGraph() {
        if (vertices != null) {
            for (int i = 0; i < vertices.length; i++) {
                for (int j = 0; j < vertices.length; j++) {
                    if (AdjisentMatrix[i][j] != 0) {
                        deleteEdges(vertices[i], vertices[j]);
                    }
                }
                deleteVertex(vertices[i]);
            }
            Vertex.setCount(0);
            vertices = null;
            AdjisentMatrix = null;
        }
    }

    public void drawGraph(int numberOfVertices) {
        this.vertices = new Vertex[numberOfVertices];
        genarateGraph(numberOfVertices);
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(0, 0);
        }
        beforDraw();
        drawVertices();
        drawEdges();
    }

    public static void main(String[] args) {
        Visualization frame = new Visualization();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        graph.getModel().beginUpdate();
//        drawVertices();
        graph.getModel().endUpdate();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listNumberOfVertices.getSelectedIndex() >= 0 && e.getSource() == drawGraph) {
            graphDrawed = true;
            deleteGraph();
            drawGraph((int) listNumberOfVertices.getSelectedValue());            
        } else if (listNameVerticesTo.getSelectedIndex() >= 0 && e.getSource() == drawBestPath) {
            if (bestEdgeDrawed) {
                deleteAllEdges();
                drawEdges();
            }
            bestEdgeDrawed = true;
            Vertex temp = vertices[(Integer) listNameVerticesTo.getSelectedValue() - 1];
            while (temp.getProcedor() != null) {
                deleteEdges(temp.getProcedor(), temp);
                drawEdge(temp.getProcedor(), temp);
                temp = temp.getProcedor();
            }

        } else if (listNumberOfVertices.getSelectedIndex() < 0 && e.getSource() == drawGraph) {
            JOptionPane.showMessageDialog(this, "Please choose number of switch center.", "", JOptionPane.INFORMATION_MESSAGE);
        } else if (listNameVerticesFrom.getSelectedIndex() < 0 && e.getSource() == drawBestPath) {
            JOptionPane.showMessageDialog(this, "Please choose source and distnation switch center.", "", JOptionPane.INFORMATION_MESSAGE);
        } else if (listNameVerticesTo.getSelectedIndex() < 0 && e.getSource() == drawBestPath) {
            JOptionPane.showMessageDialog(this, "Please choose distnation switch center.", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
