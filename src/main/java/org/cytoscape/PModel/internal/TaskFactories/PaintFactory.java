package org.cytoscape.PModel.internal.TaskFactories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cytoscape.PModel.internal.Tasks.Painter;
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
import org.cytoscape.view.vizmap.VisualMappingManager;

public class PaintFactory extends AbstractTaskFactory {

	private CyServiceRegistrar registrar;
	private CyApplicationManager manny;
	private CyNetworkView netView;
	private final VisualMappingManager vmm;

	public PaintFactory(CyNetworkView netView, CyServiceRegistrar registrar, final CyApplicationManager manny, final VisualMappingManager vmm) {
		this.registrar = registrar;
		this.manny = manny;
		this.netView = netView;
		this.vmm = vmm;
	}

	public TaskIterator createTaskIterator() {
		return new TaskIterator(new Painter(manny, netView, registrar, manny.getCurrentNetwork(), vmm));
	}
}