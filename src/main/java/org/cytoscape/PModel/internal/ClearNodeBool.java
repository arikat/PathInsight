package org.cytoscape.PModel.internal;

import java.util.List;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;

public class ClearNodeBool extends AbstractTask {

	public String columnName = "bool";
	public static final String IMAGE_COLUMN = "Image URL";
	private CyNetwork network;
	private CyNetworkView netView;
	private CyServiceRegistrar registrar;
	
	String zero = "http://i.imgur.com/kh1IMGe.png";

	public ClearNodeBool(CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
		this.network = network;
		this.registrar = registrar;
		this.netView = netView;
	}
	
	@Tunable(description = "Are you sure you want to clear the node values?")
		public boolean isyes;
	
	@Override
	public void run(TaskMonitor tasky) throws Exception {
		if (network == null) {
			tasky.setStatusMessage("There is no network.");
			return;
		}
		
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> Nodes = network.getNodeList();
		
		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class);

		Set<VisualLexicon> lexSet = vmm.getAllVisualLexicon();
		VisualProperty<?> cgProp = null;
		for (VisualLexicon vl : lexSet) {
			cgProp = vl.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");
			if (cgProp != null)
				break;
		}

		VisualStyle style = vmm.getVisualStyle(netView);
		
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, true);
		}
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
			tasky.setStatusMessage("Warning: 'bool' column does not exist - creating this column and setting all nodes to zero.");
			
		for (CyNode node : Nodes) {
			nodeTable.getRow(node.getSUID()).set(columnName, Integer.valueOf(0));
			network.getRow(node).set(IMAGE_COLUMN, zero); 
			VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
					"(mapping.type=passthrough)");
			PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
					cgProp);
			
			style.addVisualMappingFunction(map);
			
		}
	}
		
		if (nodeTable.getColumn(columnName) != null) {
			tasky.setStatusMessage("Setting all nodes to zero");
		
		for (CyNode node : Nodes) {
			nodeTable.getRow(node.getSUID()).set(columnName, Integer.valueOf(0));
			
			network.getRow(node).set(IMAGE_COLUMN, zero); 
			VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
					"(mapping.type=passthrough)");
			PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
					cgProp);
			
			style.addVisualMappingFunction(map);
			
		}
	}
		style.apply(netView);	
	}

}
