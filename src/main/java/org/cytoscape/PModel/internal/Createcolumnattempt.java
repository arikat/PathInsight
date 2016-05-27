package org.cytoscape.PModel.internal;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyRow;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.application.CyApplicationManager;

public class Createcolumnattempt extends AbstractTask {

	public String columnName = "bool";
	private CyNetwork network;
	
	public Createcolumnattempt(CyNetwork network){
		this.network = network;
	}
	
	//CyNetwork network = CyActivator.cyapplicationManagerService.getCurrentNetwork();
	//List<CyNode> Nodes = CyTableUtil.getNodesInState(network,"selected",true); //del if not work
	//final CyNode node1 =  Nodes.get(0);
	//final List<CyNode> nodes = network.getNodeList();
	//final CyNode edge1 =  nodes.get(0);
	//private static int numImports = 0;
	//CyRow row = network.getRow(node1);
	

	
	
	public void run(TaskMonitor monitor) {
		if (network == null){
			System.out.println("There is no network.");
			return;
		}
		
		CyTable nodeTable = network.getDefaultNodeTable();
		
		if(nodeTable.getColumn(columnName)== null){
			nodeTable.createColumn(columnName, Integer.class, true);
		}
		else if(nodeTable.getColumn(columnName) != null) {
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network,"selected",true); //del if not work
			final CyNode node1 =  Nodes.get(0);
			nodeTable.getRow(node1.getSUID()).set(columnName, Integer.valueOf(1)); //new
			
			//row.set(columnName, 1, integer.class);
			//nodeTable.getRow(edge1).set(columnName, "-1"); //if this doesn't work, create get row separately in diff task.
		}
	} 
	
	
	public void cancel() {
		cancelled = true;
	}
}