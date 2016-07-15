/*package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.task.AbstractNetworkViewTaskFactory;
import org.cytoscape.task.AbstractNodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class NeighborFinderFactory extends AbstractNodeViewTaskFactory {
private CyApplicationManager appManager;
private VisualLexicon Lexicon;
	
	//public NeighborFinderFactory(CyApplicationManager appManager) {
		//this.appManager = appManager;
//	}
	
	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView netView){ //CyNetworkView networkView
		return new TaskIterator(new neighborFinder(nodeView, netView));
	}
} //this.appMgr.getCurrentNetwork()*/