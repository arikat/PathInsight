package org.cytoscape.PModel.internal;
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


public class InhibitionTaskFactory extends AbstractTaskFactory {
	
	private CyApplicationManager appMgr;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;
	
	public InhibitionTaskFactory(CyNetworkView netView, CyServiceRegistrar registrar, CyApplicationManager appMgr){
		this.appMgr = appMgr;
		this.netView = netView;
		this.registrar = registrar;
	}
	
	
	public TaskIterator createTaskIterator() {
		// TODO Auto-generated method stub
		return new TaskIterator(new Task[] { new InhibitionShapeTask(netView, registrar, this.appMgr.getCurrentNetwork()) });
	}

}








//Class creates a task factory for the task ArrowShapeBypass to change an edge within a context menu.
/*public class InhibitionTaskFactory extends AbstractTaskFactory { //implements EdgeViewTaskFactory {
	
	private CyApplicationManager manny;
	
	public InhibitionTaskFactory(final CyApplicationManager manny) {
		this.manny = manny;
	}
	
	@Override
	public TaskIterator createTaskIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	//public boolean isReady(View<CyEdge> edgeView, CyNetworkView netView) {
		// TODO Auto-generated method stub
		//return true;
	//}	
	
	public TaskIterator createTaskIterator(CyNetworkView netView) { //View<CyEdge> edgeView
		// TODO Auto-generated method stub
		return new TaskIterator(new Task[] { new InhibitionShapeTask(netView, manny.getCurrentNetwork()) }); //edgeView
	}


} //Build Task Factory for Finboolcol next*/