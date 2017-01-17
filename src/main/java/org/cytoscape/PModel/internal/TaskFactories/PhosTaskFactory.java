package org.cytoscape.PModel.internal.TaskFactories;
import org.cytoscape.PModel.internal.Tasks.EdgeShapeTask;
import org.cytoscape.PModel.internal.Tasks.PhosEdge;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.AbstractEdgeViewTaskFactory;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;


public class PhosTaskFactory extends AbstractTaskFactory {
	
	private CyApplicationManager appMgr;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;
	//private View<CyEdge> edgeView;
	
	public PhosTaskFactory(CyNetworkView netView, CyServiceRegistrar registrar, final CyApplicationManager appMgr){
		this.appMgr = appMgr;
		this.netView = netView;
		this.registrar = registrar;
	}
	
	public TaskIterator createTaskIterator() {
		// TODO Auto-generated method stub
		return new TaskIterator(new Task[] { new PhosEdge(appMgr, netView, registrar, appMgr.getCurrentNetwork()) });
	}

}