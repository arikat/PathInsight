package org.cytoscape.PModel.internal;
import org.cytoscape.model.CyEdge;
import org.cytoscape.task.AbstractEdgeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskIterator;

//Class creates a task factory for the task ArrowShapeBypass to change an edge within a context menu.
public class InhibitionTaskFactory extends AbstractEdgeViewTaskFactory {

	@Override
	public TaskIterator createTaskIterator(View<CyEdge> edgeView, CyNetworkView netView) {
		// TODO Auto-generated method stub
		return new TaskIterator(new Task[] { new InhibitionShapeTask(edgeView, netView) });
	}

} //Build Task Factory for Finboolcol next