package org.cytoscape.PModel.internal.Tasks;

import java.util.List;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class ClearImages extends AbstractTask {

	public String images = "Image URL";
	private CyNetwork network;

	public ClearImages(CyNetwork network) {
		this.network = network;
	}
	
	@Tunable(description = "Are you sure you want to clear all the images?")
		public boolean isyes;
	
	@Override
	public void run(TaskMonitor tasky) throws Exception {
		if (network == null) {
			tasky.setStatusMessage("There is no network.");
			return;
		}
		
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> nodes = network.getNodeList();
		
		if (nodeTable.getColumn(images) == null) {
			nodeTable.createColumn(images, String.class, true);
			tasky.setStatusMessage("Warning: 'Image URL' column does not exist - creating this column.");
	}
		
		if (nodeTable.getColumn(images) != null) {
			tasky.setStatusMessage("Setting all images to null");
		
		for (CyNode node : nodes) {
			nodeTable.getRow(node.getSUID()).set(images, String.valueOf("null"));
			
		}
	}

		
	}

}
