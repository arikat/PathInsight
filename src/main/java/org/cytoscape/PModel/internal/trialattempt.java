package org.cytoscape.PModel.internal;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class trialattempt extends AbstractTask {
	private View<CyNode> nodeView;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "greeny";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";

	public trialattempt(View<CyNode> nodeView, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
		this.netView = netView;
		this.nodeView = nodeView;
		this.registrar = registrar;
		this.network = network;
		// this.edgeView = edgeView;
	}

	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle("Painting Image");
		taskMonitor.setStatusMessage("Painting image ...");

		// Convert the file to a URL
		String imageString;
		URL urlLocation = (getClass().getResource("/Green_Arrow_small.png"));
		try {
			// imageString = "file://"+urlLocation;
			// imageString = "file://"+imageFile.toURI().getPath();
			imageString = "https://cdn0.iconfinder.com/data/icons/super-mono-reflection/green/arrow-up_green.png";
		} catch (Exception e) {
			return;
		}

		// getRow(cyNode).set(IMAGE_COLUMN, imageString)
		CyTable edgeTable = netView.getModel().getDefaultEdgeTable(); // netView.getModel()
		CyTable nodeTable = netView.getModel().getDefaultNodeTable();
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, false);
		}

		// urlLocation =
		// applicationManager.getCurrentNetwork().getRow(cyNode).get(imageString,
		// String.class);

		nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); // prev
																						// imageString
																						// nodeView.getModel().getSUID()

		VisualMappingManager vmm = registrar.getService(VisualMappingManager.class);

		Set<VisualLexicon> lexSet = vmm.getAllVisualLexicon();
		VisualProperty<?> cgProp = null;
		for (VisualLexicon vl : lexSet) {
			cgProp = vl.lookup(CyNode.class, "NODE_CUSTOMGRAPHICS_1");
			if (cgProp != null)
				break;
		}
		if (cgProp == null) {
			System.out.println("Can't find the CUSTOMGRAPHICS visual property!!!!");
			return;
		}

		// Create a passthrough mapping to that column
		VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
				"(mapping.type=passthrough)");
		PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
				cgProp);

		// Get the current visual style
		// VisualStyle style = vmm.getCurrentVisualStyle();
		VisualStyle style = vmm.getVisualStyle(netView);

		String columnName = "bool";

		List<CyNode> nodes = CyTableUtil.getNodesInState(netView.getModel(), "selected", true); // netView.getModel()

		// CyEdge.Type Incoming = null;

		// for (CyNode node : nodes) {
		// List<CyEdge> Edges = netView.getModel().getAdjacentEdgeList(node,
		// CyEdge.Type.INCOMING); //not calling the network, what do I do?
		// List<CyEdge> Edges = network.getAdjacentEdgeList(node, Incoming);
		// for (CyEdge edge : Edges) {
		// if (edgeTable.getRow(edge).get(columnName, int.class) == 1) {

		if (edgeTable.getColumn(columnName) != null) {
			for (CyNode node : nodes) {
				List<CyEdge> Edges = network.getAdjacentEdgeList(node, CyEdge.Type.INCOMING);
																							
				for (CyEdge edge : Edges) {
					if (edgeTable.getRow(edge).get(columnName, int.class) == 1) { // THIS IS THE ISSUE? HOW DO I CHANGE THIS TO MAKE IT WORK?

						// attempt adding edgeview

						style.addVisualMappingFunction(map);
						style.apply(netView);
						// nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR,
						// Color.RED);
						// netView.updateView();
					}
				} // if
					// (edgeTable.getRow(edgeView.getModel().getSUID()).get(columnName,
					// int.class) == 1) {
			}
		}
	}
}
// Add our map
// }

// style.apply(nodeTable.getRow(nodeView.getModel().getSUID()), nodeView);
// nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.RED);
// netView.updateView();
// }

// }