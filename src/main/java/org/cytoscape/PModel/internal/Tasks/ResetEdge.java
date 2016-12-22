package org.cytoscape.PModel.internal.Tasks;

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
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.application.CyApplicationManager;

public class ResetEdge extends AbstractTask {
	public String columnName = "bool";
	private CyNetwork network;
	private CyApplicationManager appMgr;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;

	public ResetEdge(CyApplicationManager appMgr, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
		this.network = network;
		this.netView = netView;
		this.registrar = registrar;
		this.appMgr = appMgr;
	}

	public void run(TaskMonitor monitor) {
	
		/*if (network == null) {
			System.out.println("There is no network.");
			return;
		}*/
		
		monitor.setTitle("Adding activation edge values");

		CyTable edgeTable = network.getDefaultEdgeTable();
		
		CyNetworkView netoView = appMgr.getCurrentNetworkView();

		if (edgeTable.getColumn(columnName) == null) {
			edgeTable.createColumn(columnName, Integer.class, true);
			
			List<CyEdge> Edges = CyTableUtil.getEdgesInState(network, "selected", true);
			monitor.setStatusMessage("Warning: null column - creating column and setting values");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(0));
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DIAMOND);
			}
		} 
		
		if (edgeTable.getColumn(columnName) != null) {
			List<CyEdge> Edges = CyTableUtil.getEdgesInState(network, "selected", true);
			monitor.setStatusMessage("Setting values");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(0));
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DIAMOND);
			}

		}
	}

	public void cancel() {
		cancelled = true;
	}
}