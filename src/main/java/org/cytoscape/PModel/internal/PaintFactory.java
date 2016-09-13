package org.cytoscape.PModel.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class PaintFactory extends AbstractTaskFactory implements NodeViewTaskFactory {

	private CyServiceRegistrar registrar;
	private CyApplicationManager manny;

	public PaintFactory(CyServiceRegistrar registrar, final CyApplicationManager manny) {
		this.registrar = registrar;
		this.manny = manny;
	}

	public TaskIterator createTaskIterator() {
		return null;
	}

	public boolean isReady(View<CyNode> nodeView, CyNetworkView netView) {
		// This only applies to the target node, so we just use the context
		
		//List<CyIdentifiable> selectedList = new ArrayList<CyIdentifiable>();
	//	selectedList.add(nodeView.getModel());

		// Get the list of open structures for this node
	//	Map<CyIdentifiable, List<String>> stMap = structureManager.getOpenChimObjNames(selectedList);
		//if (stMap != null && stMap.containsKey(nodeView.getModel()))
			return true;
		//return false;
	}

	public TaskIterator createTaskIterator(View<CyNode> nodeView, CyNetworkView netView) {
		return new TaskIterator(new Painter(nodeView, netView, registrar, manny.getCurrentNetwork())); //add manny in a bit
	}
}