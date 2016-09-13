package org.cytoscape.PModel.internal;

import org.cytoscape.model.CyEdge;
import java.lang.Integer;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;

import java.util.List;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

public class InhibitionShapeTask extends AbstractEdgeViewTask {
	String columnName = "bool";
	private CyNetwork network;

	public InhibitionShapeTask(View<CyEdge> edgeView, CyNetworkView netView, CyNetwork network) {
		super(edgeView, netView);
		this.network = network;
	}
	
	@Override
	public void run(TaskMonitor taskbp) throws Exception {
		
		List<CyEdge> edges = network.getEdgeList();
		//List<CyEdge> edges = CyTableUtil.getEdgesInState(network, "selected", true);
		CyTable edgeTable = network.getDefaultEdgeTable();
		
		
		for (CyEdge edge : edges) {
			if (network.getRow(edge).get(columnName, Integer.class) == -1) {
				//edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
				netView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
			} 
			
			if (network.getRow(edge).get(columnName, Integer.class) == 1) {
				//edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE,  ArrowShapeVisualProperty.CIRCLE);
				netView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.CIRCLE);
			}
		}
	}
}

















//see if I can convert to just Abstract Task, no edgeView

/*
public class InhibitionShapeTask extends AbstractTask { //abstractedgeviewtask
	String columnName = "bool";
	private CyNetwork network;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;

	public InhibitionShapeTask(CyNetworkView netView, CyNetwork network) { //View<CyEdge> edgeView
		//super(edgeView, netView);
		this.network = network;
		//this.edgeView = edgeView;
		this.netView = netView;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(TaskMonitor taskbp) throws Exception {
		
		//List<CyEdge> edges = CyTableUtil.getEdgesInState(network, "selected", true);
		
		List<CyEdge> edges = network.getEdgeList();
 		CyTable edgeTable = network.getDefaultEdgeTable();
		
		
		for (CyEdge edge : edges) {
			if (network.getRow(edge).get(columnName, Integer.class) == -1) {
				netView.getEdgeView(edge).setVisualProperty(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
				
				//edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
				break;
			} 
			
			if (network.getRow(edge).get(columnName, Integer.class) == 1) {
				netView.getEdgeView(edge).setVisualProperty(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.CIRCLE);
				//edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE,  ArrowShapeVisualProperty.CIRCLE);
				//consider doing setVisualProperty instead?
				//try resetting to original
				break;
			} //else if (network.getRow(edge).get(columnName, Integer.class) == null) {
				//continue; //think on this if remove necessary
		//	}
		}
	}
	
	// make sure to look at this next!!
	//public void working(CyNetwork network, String idColumn, double affinityCutoff, List<CyEdge> nodes) {
		//EdgeTableTaskFactory.createColumn(network.getDefaultEdgeTable(), BoolVal, List.class, String.class);
	//}

	//void writerow(TaskMonitor monitor, CyTable table, CyEdge edge, String id, int cutoff) {
		// network.getRow(edge).set(BoolVal, -1);
		//table.getRow(edge.getSUID()).set(BoolVal, -1);
	//}
}*/