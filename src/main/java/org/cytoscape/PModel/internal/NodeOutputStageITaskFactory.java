package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class NodeOutputStageITaskFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public NodeOutputStageITaskFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new NodeOutputStageI(this.appMgr.getCurrentNetwork()));
	}

}
