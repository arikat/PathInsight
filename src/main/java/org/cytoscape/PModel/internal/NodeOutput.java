package org.cytoscape.PModel.internal;

import java.util.List;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyEdge.Type;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class NodeOutput extends AbstractTask {
	private View<CyNode> nodeView;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "greeny";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";
	
	public NodeOutput(CyNetwork network) { //View<CyNode> nodeView, CyNetworkView netView, 
		//this.netView = netView;
		//this.nodeView = nodeView;
		//this.registrar = registrar;
		this.network = network;
		// this.edgeView = edgeView;
	}
	

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (network == null) {
			System.out.println("There is no network.");
			return;
		}
		
		taskMonitor.setTitle("Calculating Output");
		taskMonitor.setStatusMessage("Measuring each node...");
		
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
			//insert continue or something here to get it to go to the next if statements		
		}
		
		for (CyNode node : nodes) {
			List<CyEdge> Edges = network.getAdjacentEdgeList(node, CyEdge.Type.OUTGOING);
			List<CyNode> neighbors = network.getNeighborList(node, Type.ANY); //SOMETHING SCREWY IS GOING ON HERE?
			for (CyEdge edge : Edges) { //consider switching order of edges -- look up for loop without for loop!!!!
				for (CyNode nodely : neighbors) {
					List<CyEdge> Edges2 = network.getAdjacentEdgeList(nodely, CyEdge.Type.OUTGOING);
				//this is where I identify CYEDGE and write the algorithm
				// if node >= edge and node > 0 = 1
				// if edge <= node and node < 0 = -1
				// if node = 0 = -1
				//make sure to create a system that allows you to click each time and perturb the system from there
				//finally make sure to create a counter for each node using each edge	
					
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(node).get(columnName, Integer.class) >= 0)) {
					//set nodely to 1, okay?
					
					if (nodeTable.getRow(nodely.getSUID()).get(columnName, Integer.class) != null) {
						int attempt = nodeTable.getRow(nodely.getSUID()).get(columnName, Integer.class);
						nodeTable.getRow(nodely.getSUID()).set(columnName, (attempt+1)); //instead of 2, a = 4
						break; //attempt break statement, lets see what happens? -- HALLELUJAH IT WORKED!!
					}
					
					else { //if (nodeTable.getRow(nodely.getSUID()).get(columnName, Integer.class) == null) {  //put != null before == null to prevent recursive loop
						nodeTable.getRow(nodely.getSUID()).set(columnName, Integer.valueOf(1));
						break;
					}
					
				}
				
				
				/*
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(node).get(columnName, Integer.class) >= 0)) {
					//set nodely to -1, okay?
					nodeTable.getRow(nodely.getSUID()).set(columnName, Integer.valueOf(-1));	
					}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(node).get(columnName, Integer.class) <= 0)) {
					//set nodely to -1, okay?
					nodeTable.getRow(nodely.getSUID()).set(columnName, Integer.valueOf(-1));
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(node).get(columnName, Integer.class) <= 0)) {
					//set nodely to 1, okay?
					nodeTable.getRow(nodely.getSUID()).set(columnName, Integer.valueOf(1));
				}
				
				if (network.getRow(node).get(columnName, Integer.class) == 0) {
					//set nodely to 0, okay?
					nodeTable.getRow(nodely.getSUID()).set(columnName, Integer.valueOf(0));
				}
				
				List<CyNode> nodes3 = network.getNeighborList(nodely, Type.ANY);
					for (CyEdge edgy : Edges2) {
						for (CyNode noddie : nodes3) {
							//Insert all network get row nodes, etc
							if ((network.getRow(edgy).get(columnName, Integer.class) == 1) && (network.getRow(nodely).get(columnName, Integer.class) >= 0)) {
								//set nodely to 1, okay?
								nodeTable.getRow(noddie.getSUID()).set(columnName, Integer.valueOf(1));
							}
							
							if ((network.getRow(edgy).get(columnName, Integer.class) == -1) && (network.getRow(nodely).get(columnName, Integer.class) >= 0)) {
								//set nodely to -1, okay?
								nodeTable.getRow(noddie.getSUID()).set(columnName, Integer.valueOf(-1));
							}
							
							if ((network.getRow(edgy).get(columnName, Integer.class) == 1) && (network.getRow(nodely).get(columnName, Integer.class) <= 0)) {
								//set nodely to -1, okay?
								nodeTable.getRow(noddie.getSUID()).set(columnName, Integer.valueOf(-1));
							}
							
							if ((network.getRow(edgy).get(columnName, Integer.class) == -1) && (network.getRow(nodely).get(columnName, Integer.class) <= 0)) {
								//set nodely to 1, okay?
								nodeTable.getRow(noddie.getSUID()).set(columnName, Integer.valueOf(1));
							}
							
							if (network.getRow(nodely).get(columnName, Integer.class) == 0) {
								//set nodely to 0, okay?
								nodeTable.getRow(noddie.getSUID()).set(columnName, Integer.valueOf(0));
							}
							
							
						}
					}*/
					}
				}
				}
			}
			
		}