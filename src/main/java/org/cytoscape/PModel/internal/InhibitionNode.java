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

public class InhibitionNode extends AbstractTask {
	public String columnName = "bool";
	private CyNetwork network;

	public InhibitionNode(CyNetwork network) {
		this.network = network;
	}

	public void run(TaskMonitor monitor) {
	
		/*if (network == null) {
			System.out.println("There is no network.");
			return;
		}*/
		
		monitor.setTitle("Adding Inhibition Node values");

		CyTable nodeTable = network.getDefaultNodeTable();

		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
			
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true);
			monitor.setStatusMessage("Warning: null column - creating column and setting values");

			for (CyNode node : Nodes) {
				nodeTable.getRow(node.getSUID()).set(columnName, Integer.valueOf(-1));
			}
		} 
		
		if (nodeTable.getColumn(columnName) != null) {
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true);
			monitor.setStatusMessage("Setting values");

			for (CyNode node : Nodes) {
				nodeTable.getRow(node.getSUID()).set(columnName, Integer.valueOf(-1));
			}

		}
	}
}