
package routing;

public class RoutingAlgorithm {
    
    public static void routingAlgorithm(Vertex vertices[], int adjacentMatrix [][], Vertex s){
        for (int i = 0; i < vertices.length; i++) {
            vertices[i].setWeight(0);
            vertices[i].setProcedor(null);
        }
        s.setWeight(Integer.MAX_VALUE);
        
        HeapStructure h = new HeapStructure(vertices.length);
        for (int i = 0; i < vertices.length; i++) {
            h.insert(vertices[i]);            
        }
        h.findMax();
        
        
        Vertex tempSwitchCenter ;
        int tempWeight ;
        Vertex tempVertex ;
        
        while(!h.isEmpty()){
            tempSwitchCenter = h.deleteMax(1);
            for (int i = 0 ; i<vertices.length ; i++) {
                tempWeight = adjacentMatrix[tempSwitchCenter.getName()-1][i] ;
                if (tempWeight != 0 && h.isInHeap(i+1)){//+1
                    tempVertex = vertices[i];//+1
                    
                    if(Integer.min(tempSwitchCenter.getWeight(), tempWeight) > tempVertex.getWeight()){
                        tempVertex.setWeight(Integer.min(tempSwitchCenter.getWeight(), tempWeight));
                        tempVertex.setProcedor(tempSwitchCenter);
                        h.findMax();
                    }
                }
            }
        }        
    }    
}
