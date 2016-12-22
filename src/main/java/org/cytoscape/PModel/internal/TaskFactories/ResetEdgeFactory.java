package org.cytoscape.PModel.internal.TaskFactories;

import org.cytoscape.PModel.internal.Tasks.ResetEdge;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ResetEdgeFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;
	
	public ResetEdgeFactory(CyNetworkView netView, CyServiceRegistrar registrar, final CyApplicationManager appMgr){
		this.appMgr = appMgr;
		this.netView = netView;
		this.registrar = registrar;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new ResetEdge(appMgr, netView, registrar, this.appMgr.getCurrentNetwork()));
	}
}