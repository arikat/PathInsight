package org.cytoscape.PModel.internal.TaskFactories;

import org.cytoscape.PModel.internal.Tasks.ClearImages;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;

public class ClearImagesTaskFactory extends AbstractTaskFactory {

	private CyApplicationManager appMgr;
	private CyServiceRegistrar registrar;
	private CyNetworkView netView;
	
	public ClearImagesTaskFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	public TaskIterator createTaskIterator(){
		return new TaskIterator(new ClearImages(this.appMgr.getCurrentNetwork()));
	
	}
}