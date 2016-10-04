package org.cytoscape.PModel.internal;

import java.util.List;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class ClearNodeBool extends AbstractTask {

	public String columnName = "bool";
	private CyNetwork network;

	public ClearNodeBool(CyNetwork network) {
		this.network = network;
	}
	
	@Tunable(description = "Are you sure you want to clear the node values?")
		public boolean isyes;
	
	@Override
	public void run(TaskMonitor tasky) throws Exception {
		if (network == null) {
			tasky.setStatusMessage("There is no network.");
			return;
		}
		
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> Nodes = network.getNodeList();
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
			tasky.setStatusMessage("Warning: 'bool' column does not exist - creating this column and setting all nodes to zero.");
		
		for (CyNode node : Nodes) {
			nodeTable.getRow(node.getSUID()).set(columnName, Integer.valueOf(0));
			
		}
	}
		
		if (nodeTable.getColumn(columnName) != null) {
			tasky.setStatusMessage("Setting all nodes to zero");
		
		for (CyNode node : Nodes) {
			nodeTable.getRow(node.getSUID()).set(columnName, Integer.valueOf(0));
			
		}
	}

		
	}

}
