package org.cytoscape.PModel.internal.Tasks;

import java.awt.Color;
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
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.application.CyApplicationManager;

public class DePhosEdge extends AbstractTask {
	public String columnName = "setBool";
	private CyNetwork network;
	private CyApplicationManager appMgr;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;
	String phosLab = "RelationValue2";

	public DePhosEdge(CyApplicationManager appMgr, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
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
		
		CharSequence act = "+p";
		CharSequence ing = "-p";
		
		monitor.setTitle("Adding phosphorylation edge values");

		CyTable edgeTable = network.getDefaultEdgeTable();
		
		CyNetworkView netoView = appMgr.getCurrentNetworkView();

		if (edgeTable.getColumn(phosLab) == null) {
			edgeTable.createColumn(phosLab, String.class, false);
			
			List<CyEdge> Edges = CyTableUtil.getEdgesInState(network, "selected", true);
			monitor.setStatusMessage("Warning: null column - creating column and setting values");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(phosLab, ing);
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_PAINT, Color.PINK);
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.DASH_DOT);
				monitor.setStatusMessage("changing phosphorylated edges");
			}
		} 
		
		if (edgeTable.getColumn(phosLab) != null) {
			List<CyEdge> Edges = CyTableUtil.getEdgesInState(network, "selected", true);
			monitor.setStatusMessage("Setting values");

			for (CyEdge edge : Edges) {
				edgeTable.getRow(edge.getSUID()).set(phosLab, ing);
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_PAINT, Color.PINK);
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.DASH_DOT);
				monitor.setStatusMessage("changing phosphorylated edges");
				}
		}
	}

	public void cancel() {
		cancelled = true;
	}
}