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

public class InhibitionEdge extends AbstractTask {
	public String columnName = "bool";
	private CyNetwork network;

	public InhibitionEdge(CyNetwork network) {
		this.network = network;
	}

	public void run(TaskMonitor monitor) {
		if (network == null) {
			System.out.println("There is no network.");
			return;
		}
		monitor.setTitle("Adding inhibition edge values");

		CyTable edgeTable = network.getDefaultEdgeTable();

		if (edgeTable.getColumn(columnName) == null) {
			edgeTable.createColumn(columnName, Integer.class, true);
			//insert continue or something here to get it to go to the next if statements
			List<CyEdge> Edges = CyTableUtil.getEdgesInState(network, "selected", true);
			monitor.setStatusMessage("Warning: null column - creating column and setting values");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(-1));
			}
			
		}
		
		if (edgeTable.getColumn(columnName) != null) {
			List<CyEdge> Edges = CyTableUtil.getEdgesInState(network, "selected", true);
			monitor.setStatusMessage("Setting values");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(-1));
			}

		}
	}

	public void cancel() {
		cancelled = true;
	}
}