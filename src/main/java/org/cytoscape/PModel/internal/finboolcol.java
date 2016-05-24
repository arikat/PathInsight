package org.cytoscape.PModel.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.cytoscape.model.CyColumn;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.PModel.internal.InhibitionShapeTask;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;

public class finboolcol extends AbstractTask {
	public CyNetwork network;
	
	//private CyApplicationManager cyApplicationManager;
	//final List<CyNode> Nodes = network.getNodeList();
	
	List<CyNode> Nodes = CyTableUtil.getNodesInState(network,"selected",true);
	
	//final List<CyNode> nodes = CyTableUtil.getNodesInState(cyApplicationManager.getCurrentNetwork(),"selected",true);
	final CyNode node1 =  Nodes.get(0);
	
	
	//final List<Long> node1 = nodes.stream().map(edge -> edge.getSUID()).collect(Collectors.toList());
	//private static int numImports = 0;
	//public InhibitionShapeTask tooty;
	
	@Override
    public void run(TaskMonitor tm) throws IOException {

		CyTable table = network.getDefaultNodeTable();
		String bool = "bool";
		table.getColumn("bool");
		
		
		//CyNode nod1 = network.getNode(arg0);
		
		
		table.getRow(node1.getSUID()).set(bool, Integer.valueOf(1));
		
		
		//table.getRow(node1.getSUID()).set("bool", String.valueOf(1));
		//CyRow row = table.getRow(nodes);
		//row.set(bool, 1, Integer.class);
        //CyColumn column = table.getColumn(bool);
        //if (table.getColumn(bool) != null) {
        //table.getRow(bool).set(bool, "wing");
        //table.getRow(nodes.set(bool, new Integer(2)));
        }      
        //insertTasksAfterCurrentTask(tooty);
        //Class<? extends CyIdentifiable> type = CyEdge.class;

}
