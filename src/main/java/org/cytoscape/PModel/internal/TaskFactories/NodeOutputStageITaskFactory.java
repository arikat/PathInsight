package org.cytoscape.PModel.internal.TaskFactories;

import org.cytoscape.PModel.internal.Tasks.NodeOutputStageI;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class NodeOutputStageITaskFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	private CyServiceRegistrar registrar;
	private CyNetworkView netView;
	
	public NodeOutputStageITaskFactory(CyNetworkView netView, CyServiceRegistrar registrar, CyApplicationManager appMgr){
		this.registrar = registrar;
		this.appMgr = appMgr;
		this.netView = netView;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new NodeOutputStageI(appMgr, netView, registrar, this.appMgr.getCurrentNetwork()));
	}
}
