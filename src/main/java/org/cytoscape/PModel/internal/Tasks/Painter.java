package org.cytoscape.PModel.internal.Tasks;

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
//	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	public static final String label = "Label";
	public static final String name = "name";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";

	public Painter(CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) {
		this.netView = netView;
		//this.nodeView = nodeView;
		this.registrar = registrar;
		this.network = network;
		// this.edgeView = edgeView;
	}

	public void run(TaskMonitor taskMonitor) {
		taskMonitor.setTitle("Painting Image");
		taskMonitor.setStatusMessage("Painting image ...");
		
		String two = "http://i.imgur.com/sq4WXnR.png";
		String one = "http://i.imgur.com/nMKj77P.png";
		String zero = "http://i.imgur.com/kh1IMGe.png";
		String negone = "http://i.imgur.com/F0FdgSx.png";
		String negtwo = "http://i.imgur.com/sHkP8rX.png";
		String plusplus = "http://i.imgur.com/qk64sKc.png";
		String negneg = "http://i.imgur.com/o2gB6mm.png";

		// getRow(cyNode).set(IMAGE_COLUMN, imageString)  // netView.getModel()
		//CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, true);
		}
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
		}

		if (nodeTable.getColumn(columnName) != null) {
			
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

			//List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
			List<CyNode> nodes = network.getNodeList();
			
			for (CyNode node : nodes) {		
				
				if (nodeTable.getColumn(label) != null) {
					String john = network.getRow(node).get(label, String.class);
					network.getRow(node).set(name, john);
				}
				
				///WRITE CODE HERE LOOKING AT NEIGHBOR NODES - THEN GET THOSE ADJACENT EDGE LISTS - THEN REPEAT ONCE MORE FOR TERTIARY INTERACTIONS?
					 
				if (nodeTable.getRow(node.getSUID()).get(columnName, Integer.class) == null) {
					//print something or do something so this doesn't end in an error
					taskMonitor.setStatusMessage("Warning: null node - breaking loop");
					//break;
					network.getRow(node).set(columnName, Integer.valueOf(0));
					/*network.getRow(node).set(IMAGE_COLUMN, zero); //fixed original issue with only one writing - now fix overwriting arrows
					
					VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
							"(mapping.type=passthrough)");
					PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
							cgProp);
					
					style.addVisualMappingFunction(map);
					style.apply(netView);*/
					//nvm paint null on this node so no issues occur
				}
				
				
					if (network.getRow(node).get(columnName, Integer.class) == 1) { //change to integer if necessary
						// Create a passthrough mapping to that column
						//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
						network.getRow(node).set(IMAGE_COLUMN, one); //fixed original issue with only one writing - now fix overwriting arrows
						
						
						VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
								"(mapping.type=passthrough)");
						PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
								cgProp);
						
						style.addVisualMappingFunction(map);
						//style.apply(netView);
					}
						
					if (network.getRow(node).get(columnName, Integer.class) > 2) { //change to integer if necessary
						// Create a passthrough mapping to that column
						//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
						network.getRow(node).set(IMAGE_COLUMN, plusplus); //fixed original issue with only one writing - now fix overwriting arrows
						
						
						VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
								"(mapping.type=passthrough)");
						PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
								cgProp);
						
						style.addVisualMappingFunction(map);
						//style.apply(netView);
					}
					
					if (network.getRow(node).get(columnName, Integer.class) < -2) { //change to integer if necessary
						// Create a passthrough mapping to that column
						//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
						network.getRow(node).set(IMAGE_COLUMN, negneg); //fixed original issue with only one writing - now fix overwriting arrows
						
						
						VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
								"(mapping.type=passthrough)");
						PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
								cgProp);
						
						style.addVisualMappingFunction(map);
						//style.apply(netView);
					}
					
					
					
						if (network.getRow(node).get(columnName, Integer.class) == 2) { //change to integer if necessary
							// Create a passthrough mapping to that column
							//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
							network.getRow(node).set(IMAGE_COLUMN, two); //fixed original issue with only one writing - now fix overwriting arrows
							
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
							//style.apply(netView);
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
								//style.apply(netView);
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
								//style.apply(netView);
							}
							
							if (network.getRow(node).get(columnName, Integer.class) == -2) { //change to integer if necessary
								// Create a passthrough mapping to that column
								//nodeTable.getRow(nodeView.getModel().getSUID()).set(IMAGE_COLUMN, imageString); //easy fix - overwriting arrows, make i++
								network.getRow(node).set(IMAGE_COLUMN, negtwo); //fixed original issue with only one writing - now fix overwriting arrows
								
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
								//style.apply(netView);
							}
							//style.apply(netView);
			}
			style.apply(netView);
			netView.updateView();
		}
		
	}
}