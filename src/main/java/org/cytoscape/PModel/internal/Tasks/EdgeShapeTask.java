package org.cytoscape.PModel.internal.Tasks;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;

import java.awt.Color;
import java.lang.Integer;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;

import java.util.List;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.view.presentation.property.PaintVisualProperty;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

public class EdgeShapeTask extends AbstractTask {
	String columnName = "bool";
	String phosLab = "RelationValue2";
	String subby = "ArrowShape";
	private CyNetwork network;
	private CyApplicationManager appMgr;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;
	
	public EdgeShapeTask(CyApplicationManager appMgr, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
		//super(edgeView, netView);
		this.network = network;
		this.netView = netView;
		this.registrar = registrar;
		this.appMgr = appMgr;
		//this.edgeView = edgeView;
	}
	
	@Override
	public void run(TaskMonitor taskbp) throws Exception {
		
		List<CyEdge> edges = network.getEdgeList();
		CyTable edgeTable = network.getDefaultEdgeTable();
		taskbp.setStatusMessage("getting Edge Table.");
		
		CyNetworkView netoView = appMgr.getCurrentNetworkView();
		
		if (edgeTable.getColumn(columnName) == null) {
			edgeTable.createColumn(columnName, Integer.class, true);
		}
		
		
		for (CyEdge edge : edges) {
			
			if (edgeTable.getColumn(subby) != null) {
				//taskbp.setStatusMessage("Identified Kegg column");
				
				CharSequence hip = "Delta";
				CharSequence po = "T";
				
				String actin = edgeTable.getRow(edge.getSUID()).get(subby, String.class);
				
				if (actin.contains(hip)) {
					//network.getRow(edge).set(columnName, Integer.valueOf(1));
					//taskbp.setStatusMessage("Identified Kegg column activation");
					
					edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(1));
				}
				
				if (actin.contains(po)) {
					//network.getRow(edge).set(columnName, Integer.valueOf(-1));
					taskbp.setStatusMessage("Identified Kegg column inhibition");
					edgeTable.getRow(edge.getSUID()).set(columnName, Integer.valueOf(-1));
				}
			}
			
			CharSequence act = "+p";
			CharSequence ing = "-p";
			
			if (edgeTable.getColumn(phosLab) != null) {
				String phos = network.getRow(edge).get(phosLab, String.class); //something strange going on with edge values - they're not exactly strings
				if (phos.contains(act)) {
					netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_PAINT, Color.YELLOW);
					netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.DASH_DOT);
					taskbp.setStatusMessage("changing phosphorylated edges");
				}
				if (phos.contains(ing)) {
					netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_PAINT, Color.PINK);
					netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.DASH_DOT);
					taskbp.setStatusMessage("changing phosphorylated edges");
				}
			}
			
			if (network.getRow(edge).get(columnName, Integer.class) == null) {
				network.getRow(edge).set(columnName, Integer.valueOf(0));
				taskbp.setStatusMessage("Warning: null edges - setting to zero to prevent errors");
			}
			
			if (network.getRow(edge).get(columnName, Integer.class) == 0) {
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.DIAMOND);			
			}
			
			if (network.getRow(edge).get(columnName, Integer.class) == -1) {
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
			} 
			
			if (network.getRow(edge).get(columnName, Integer.class) == 1) {
				netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.CIRCLE);
			}
		}
	}
}