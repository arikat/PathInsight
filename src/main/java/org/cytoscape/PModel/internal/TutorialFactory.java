package org.cytoscape.PModel.internal;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

public class TutorialFactory extends AbstractTaskFactory {
	
	private CyApplicationManager appMgr;
	
	public TutorialFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	public TaskIterator createTaskIterator() {
		// TODO Auto-generated method stub
		return new TaskIterator(new Task[] { new finboolcol(this.appMgr.getCurrentNetwork()) });
	}

}
