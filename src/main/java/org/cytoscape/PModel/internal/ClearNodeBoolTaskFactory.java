package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ClearNodeBoolTaskFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public ClearNodeBoolTaskFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new ClearNodeBool(this.appMgr.getCurrentNetwork()));
	}
}
