package org.cytoscape.PModel.internal.Tasks;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class ClearEdgeBool extends AbstractTask {

	public String columnName = "setBool";
	private CyNetwork network;

	public ClearEdgeBool(CyNetwork network) {
		this.network = network;
	}
	
	@Tunable(description = "Are you sure you want to clear the edge values?")
		public boolean isyes;
	
	@Override
	public void run(TaskMonitor tasky) throws Exception {
		if (network == null) {
			tasky.setStatusMessage("There is no network.");
			return;
		}
		
		CyTable edgeTable = network.getDefaultEdgeTable();
		
		List<CyEdge> edges = network.getEdgeList();
		
		if (edgeTable.getColumn(columnName) == null) {
			edgeTable.createColumn(columnName, Integer.class, false);
			tasky.setStatusMessage("Warning: 'bool' column does not exist - creating this column and setting all edges to zero.");
		
		for (CyEdge edge : edges) {
			edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(0));
			
		}
	}
		
		if (edgeTable.getColumn(columnName) != null) {
			tasky.setStatusMessage("Setting all edges to zero");
		
		for (CyEdge edge : edges) {
			edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(0));
			
		}
	}

		
	}

}
