package org.cytoscape.PModel.internal.TaskFactories;

import org.cytoscape.PModel.internal.Tasks.DrawAnd;
import org.cytoscape.PModel.internal.Tasks.DrawOr;
import org.cytoscape.PModel.internal.Tasks.DrawProcessNode;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class OrTaskFactory extends AbstractTaskFactory {

	private CyServiceRegistrar registrar;
	private CyApplicationManager manny;
	private CyNetworkView netView;
	private final VisualMappingManager vmm;
	
	public OrTaskFactory(CyNetworkView netView, CyServiceRegistrar registrar, final CyApplicationManager manny, final VisualMappingManager vmm) {
		this.registrar = registrar;
		this.manny = manny;
		this.netView = netView;
		this.vmm = vmm;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new DrawOr(manny, netView, registrar, this.manny.getCurrentNetwork(), vmm));
	}
}