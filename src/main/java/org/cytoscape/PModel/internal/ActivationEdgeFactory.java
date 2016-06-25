package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ActivationEdgeFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public ActivationEdgeFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new ActivationEdge(this.appMgr.getCurrentNetwork()));
	}
}