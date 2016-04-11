package org.cytoscape.PModel.internal;

import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.TaskMonitor;

public class ArrowShapeBypass extends AbstractEdgeViewTask {

	public ArrowShapeBypass(View<CyEdge> edgeView, CyNetworkView netView) {
		super(edgeView, netView);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(TaskMonitor taskbp) throws Exception {
		edgeView.setLockedValue(BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE,  ArrowShapeVisualProperty.CIRCLE);
	}
}
