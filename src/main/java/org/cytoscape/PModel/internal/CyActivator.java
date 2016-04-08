package org.cytoscape.PModel.internal;

import java.util.Properties;

import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		
		// Add right click menu item to the edge view
		CyEdgeViewContextMenuFactory EdgeTF = new CtxMenuEdge();
		CyNodeViewContextMenuFactory NodeTF = new CtxMenu();
		Properties Props = new Properties();
		registerAllServices(context, EdgeTF,Props);
		registerAllServices(context,NodeTF,Props);
	}

}
