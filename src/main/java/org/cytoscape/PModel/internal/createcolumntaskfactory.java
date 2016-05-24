package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class createcolumntaskfactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public createcolumntaskfactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new Createcolumnattempt(this.appMgr.getCurrentNetwork()));
	}
}