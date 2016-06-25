package org.cytoscape.PModel.internal;

import org.cytoscape.model.CyEdge;
import java.lang.Integer;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;

import java.util.List;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;

public class InhibitionShapeTask extends AbstractEdgeViewTask {
	public static final String BoolVal = "BoolValue";

	public InhibitionShapeTask(View<CyEdge> edgeView, CyNetworkView netView) {
		super(edgeView, netView);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run(TaskMonitor taskbp) throws Exception {
		edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE, ArrowShapeVisualProperty.T);
		//insertTasksAfterCurrentTask(tooty);
	}
	
	
	// make sure to look at this next!!
	//public void working(CyNetwork network, String idColumn, double affinityCutoff, List<CyEdge> nodes) {
		//EdgeTableTaskFactory.createColumn(network.getDefaultEdgeTable(), BoolVal, List.class, String.class);
	//}

	//void writerow(TaskMonitor monitor, CyTable table, CyEdge edge, String id, int cutoff) {
		// network.getRow(edge).set(BoolVal, -1);
		//table.getRow(edge.getSUID()).set(BoolVal, -1);
	//}
}