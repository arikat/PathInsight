package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ClearEdgeBoolTaskFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public ClearEdgeBoolTaskFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new ClearEdgeBool(this.appMgr.getCurrentNetwork()));
	}
}
