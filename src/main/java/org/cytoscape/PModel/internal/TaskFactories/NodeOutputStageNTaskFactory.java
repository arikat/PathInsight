package org.cytoscape.PModel.internal.TaskFactories;

import org.cytoscape.PModel.internal.Tasks.NodeOutputStageI;
import org.cytoscape.PModel.internal.Tasks.NodeOutputStageN;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class NodeOutputStageNTaskFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	private CyServiceRegistrar registrar;
	private CyNetworkView netView;
	
	public NodeOutputStageNTaskFactory(CyNetworkView netView, CyServiceRegistrar registrar, CyApplicationManager appMgr){
		this.registrar = registrar;
		this.appMgr = appMgr;
		this.netView = netView;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new NodeOutputStageN(appMgr, netView, registrar, this.appMgr.getCurrentNetwork()));
	}
}
