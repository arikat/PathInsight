package org.cytoscape.PModel.internal;

import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;

public class TutorialFactory extends AbstractTaskFactory {

	public TaskIterator createTaskIterator() {
		// TODO Auto-generated method stub
		return new TaskIterator(new Task[] { new finboolcol() });
	}

}
