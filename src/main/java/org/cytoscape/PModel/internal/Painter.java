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

public class Painter extends AbstractTask {
	private View<CyNode> nodeView;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URLs";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";

	public Painter(View<CyNode> nodeView, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
		this.netView = netView;
		this.nodeView = nodeView;
		this.registrar = registrar;
		this.network = network;
		// this.edgeView = edgeView;
	}

	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle("Painting Image");
		taskMonitor.setStatusMessage("Painting image ...");
		
		String two = "http://i.imgur.com/Cj7NDV3.png";
		String one = "http://i.imgur.com/dwt0BWf.png";
		String zero = "http://i.imgur.com/ScITBMD.png";
		String negone = "http://i.imgur.com/d24OeFc.jpg";
		String negtwo = "http://i.imgur.com/6xAePTG.jpg";

		// getRow(cyNode).set(IMAGE_COLUMN, imageString)  // netView.getModel()
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, false);
		}

		if (edgeTable.getColumn(columnName) != null) {
			
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

			VisualStyle style = vmm.getVisualStyle(netView);

			String columnName = "bool";

			List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
			
			for (CyNode node : nodes) {				
				
				///WRITE CODE HERE LOOKING AT NEIGHBOR NODES - THEN GET THOSE ADJACENT EDGE LISTS - THEN REPEAT ONCE MORE FOR TERTIARY INTERACTIONS?
					 
					if (network.getRow(node).get(columnName, Integer.class) == 1) { //change to integer if necessary
						// Create a passthrough mapping to that column
						//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
						network.getRow(node).set(IMAGE_COLUMN, one); //fixed original issue with only one writing - now fix overwriting arrows
						
						
						VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
								"(mapping.type=passthrough)");
						PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
								cgProp);
						
						style.addVisualMappingFunction(map);
						style.apply(netView);
					}
						
						if (network.getRow(node).get(columnName, Integer.class) >= 2) { //change to integer if necessary
							// Create a passthrough mapping to that column
							//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
							network.getRow(node).set(IMAGE_COLUMN, two); //fixed original issue with only one writing - now fix overwriting arrows
							
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
							style.apply(netView);
						}
							
							if (network.getRow(node).get(columnName, Integer.class) == 0) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, zero); //fixed original issue with only one writing - now fix overwriting arrows
								
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
								style.apply(netView);
							}
							
							if (network.getRow(node).get(columnName, Integer.class) == -1) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, negone); //fixed original issue with only one writing - now fix overwriting arrows
								
								
								VisualMappingFunctionFactory facto = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping mapsy = (PassthroughMapping) facto.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(mapsy);
								style.apply(netView);
							}
							
							if (network.getRow(node).get(columnName, Integer.class) <= -2) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, negtwo); //fixed original issue with only one writing - now fix overwriting arrows
								
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
								style.apply(netView);
							}
							
							if (nodeTable.getRow(node.getSUID()).get(columnName, Integer.class) == null) {
								//print something or do something so this doesn't end in an error
								System.out.println("null node");
								network.getRow(node).set(IMAGE_COLUMN, zero); //fixed original issue with only one writing - now fix overwriting arrows
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
								style.apply(netView);
								//nvm paint null on this node so no issues occur
							}
			}
		}
	}
}