package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class InhibitionNodeFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public InhibitionNodeFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new InhibitionNode(this.appMgr.getCurrentNetwork()));
	}
}