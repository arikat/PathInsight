package org.cytoscape.PModel.internal.TaskFactories;

import org.cytoscape.PModel.internal.Tasks.InhibitionEdge;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class InhibitionEdgeFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	
	public InhibitionEdgeFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new InhibitionEdge(this.appMgr.getCurrentNetwork()));
	}
}