package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class NodeOutputFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public NodeOutputFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new NodeOutput(this.appMgr.getCurrentNetwork()));
	}

}
