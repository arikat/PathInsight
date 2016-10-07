package org.cytoscape.PModel.internal.Tasks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyEdge.Type;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class NodeOutputStageI extends AbstractTask {
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";
	
	public NodeOutputStageI(CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) { 
		this.network = network;
		this.registrar = registrar;
		this.netView = netView;
		
	}
	

	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (network == null) {
			System.out.println("There is no network.");
			taskMonitor.setTitle("There is no network.");
			return;
		}
		
		taskMonitor.setTitle("Calculating Output");
		taskMonitor.setStatusMessage("Measuring each node...");
		
		String two = "http://i.imgur.com/sq4WXnR.png";
		String one = "http://i.imgur.com/nMKj77P.png";
		String zero = "http://i.imgur.com/kh1IMGe.png";
		String negone = "http://i.imgur.com/F0FdgSx.png";
		String negtwo = "http://i.imgur.com/sHkP8rX.png";
		String plusplus = "http://i.imgur.com/qk64sKc.png";
		String negneg = "http://i.imgur.com/o2gB6mm.png";
		
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
		taskMonitor.setStatusMessage("nodes selected...");
		
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
		
		taskMonitor.setStatusMessage("visual style selected");

		//create HASHSET
		HashSet<CyNode> neighbors = new HashSet<CyNode>();
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
		}
		
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, true);
		}
		
		for (CyNode node : nodes) {
			taskMonitor.setStatusMessage("Number of nodes: " + nodes);
			List<CyNode> neighborinos = network.getNeighborList(node, Type.OUTGOING);
			taskMonitor.setStatusMessage("Number of neighborinos: " + neighborinos);
			
			for (CyNode nebs : neighborinos) {
				if (!neighbors.contains(nebs)) {
					neighbors.add(nebs);
					taskMonitor.setStatusMessage("Number of neighbors: " + neighbors);
				}
			}
		} //This cuts off the for loop to node and nodes - you can eliminate this if it doesn't work.
			
			for (CyNode nodely : neighbors) {
				
			if (network.getRow(nodely).get(columnName, Integer.class) == null) {
					//set nodely to 0, okay?
					network.getRow(nodely).set(columnName, Integer.valueOf(0));
				}
				
				List<CyEdge> Edges = network.getAdjacentEdgeList(nodely, CyEdge.Type.INCOMING); //change back to nodely, incoming if not working
				for (CyEdge edge : Edges) { //consider switching order of edges -- look up for loop without for loop!!!!
				
					CyNode source = edge.getSource();
					CyNode target = edge.getTarget(); //prev nodely
					
				//this is where I identify CYEDGE and write the algorithm
				// if node >= edge and node > 0 = 1
				// if edge <= node and node < 0 = -1
				// if node = 0 = -1			
					
					if (network.getRow(source).get(columnName, Integer.class) == 1) {
						network.getRow(source).set(IMAGE_COLUMN, one);
					}
				
					if (network.getRow(source).get(columnName, Integer.class) > 2) {
						network.getRow(source).set(IMAGE_COLUMN, plusplus);
					}
			
					if (network.getRow(source).get(columnName, Integer.class) < -2) { 
						network.getRow(source).set(IMAGE_COLUMN, negneg);
					}
			
					if (network.getRow(source).get(columnName, Integer.class) == 2) {
						network.getRow(source).set(IMAGE_COLUMN, two);
					}
					
					if (network.getRow(source).get(columnName, Integer.class) == 0) {
						network.getRow(source).set(IMAGE_COLUMN, zero); 
					}
					
					if (network.getRow(source).get(columnName, Integer.class) == -1) { 
						network.getRow(source).set(IMAGE_COLUMN, negone);
					}
					
					if (network.getRow(source).get(columnName, Integer.class) == -2) {
						network.getRow(source).set(IMAGE_COLUMN, negtwo);
					}	
					
					
				int attempt = nodeTable.getRow(target.getSUID()).get(columnName, Integer.class);
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) >= 1)) {
					//set nodely to 1, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, (++attempt));
					
					if (nodeTable.getRow(target.getSUID()).get(columnName, Integer.class) == null) {
						taskMonitor.setStatusMessage("Warning: null node - breaking loop");
						network.getRow(target).set(columnName, Integer.valueOf(0));
					}
					
						if (network.getRow(target).get(columnName, Integer.class) == 1) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, one); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
							
						if (network.getRow(target).get(columnName, Integer.class) > 2) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, plusplus); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
							if (network.getRow(target).get(columnName, Integer.class) == 2) { 
								
								network.getRow(target).set(IMAGE_COLUMN, two);
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
							}
								
								if (network.getRow(target).get(columnName, Integer.class) == 0) {
									network.getRow(target).set(IMAGE_COLUMN, zero); 
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -1) { 
									
									network.getRow(target).set(IMAGE_COLUMN, negone);
									
									VisualMappingFunctionFactory facto = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping mapsy = (PassthroughMapping) facto.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(mapsy);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -2) {
									network.getRow(target).set(IMAGE_COLUMN, negtwo);
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) >= 1)) {
					//set nodely to -1, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, (--attempt));

					if (nodeTable.getRow(target.getSUID()).get(columnName, Integer.class) == null) {
						taskMonitor.setStatusMessage("Warning: null node - breaking loop");
						network.getRow(target).set(columnName, Integer.valueOf(0));
					}
					
						if (network.getRow(target).get(columnName, Integer.class) == 1) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, one); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
							
						if (network.getRow(target).get(columnName, Integer.class) > 2) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, plusplus); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
							if (network.getRow(target).get(columnName, Integer.class) == 2) { 
								
								network.getRow(target).set(IMAGE_COLUMN, two);
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
							}
								
								if (network.getRow(target).get(columnName, Integer.class) == 0) {
									network.getRow(target).set(IMAGE_COLUMN, zero); 
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -1) { 
									
									network.getRow(target).set(IMAGE_COLUMN, negone);
									
									VisualMappingFunctionFactory facto = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping mapsy = (PassthroughMapping) facto.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(mapsy);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -2) {
									network.getRow(target).set(IMAGE_COLUMN, negtwo);
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) <= -1)) {
					//set nodely to -1, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, (--attempt));
					
					if (nodeTable.getRow(target.getSUID()).get(columnName, Integer.class) == null) {
						taskMonitor.setStatusMessage("Warning: null node - breaking loop");
						network.getRow(target).set(columnName, Integer.valueOf(0));
					}
					
						if (network.getRow(target).get(columnName, Integer.class) == 1) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, one); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
							
						if (network.getRow(target).get(columnName, Integer.class) > 2) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, plusplus); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
							if (network.getRow(target).get(columnName, Integer.class) == 2) { 
								
								network.getRow(target).set(IMAGE_COLUMN, two);
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
							}
								
								if (network.getRow(target).get(columnName, Integer.class) == 0) {
									network.getRow(target).set(IMAGE_COLUMN, zero); 
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -1) { 
									
									network.getRow(target).set(IMAGE_COLUMN, negone);
									
									VisualMappingFunctionFactory facto = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping mapsy = (PassthroughMapping) facto.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(mapsy);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -2) {
									network.getRow(target).set(IMAGE_COLUMN, negtwo);
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) <= -1)) {
					//set nodely to 1, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, (++attempt));
					
					if (nodeTable.getRow(target.getSUID()).get(columnName, Integer.class) == null) {
						taskMonitor.setStatusMessage("Warning: null node - breaking loop");
						network.getRow(target).set(columnName, Integer.valueOf(0));
					}
					
						if (network.getRow(target).get(columnName, Integer.class) == 1) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, one); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
							
						if (network.getRow(target).get(columnName, Integer.class) > 2) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, plusplus); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
							if (network.getRow(target).get(columnName, Integer.class) == 2) { 
								
								network.getRow(target).set(IMAGE_COLUMN, two);
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
							}
								
								if (network.getRow(target).get(columnName, Integer.class) == 0) {
									network.getRow(target).set(IMAGE_COLUMN, zero); 
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -1) { 
									
									network.getRow(target).set(IMAGE_COLUMN, negone);
									
									VisualMappingFunctionFactory facto = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping mapsy = (PassthroughMapping) facto.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(mapsy);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -2) {
									network.getRow(target).set(IMAGE_COLUMN, negtwo);
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
				}
					
				if (network.getRow(source).get(columnName, Integer.class) == 0) {
					//set nodely to 0, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, Integer.valueOf(0));
					
					if (nodeTable.getRow(target.getSUID()).get(columnName, Integer.class) == null) {
						taskMonitor.setStatusMessage("Warning: null node - breaking loop");
						network.getRow(target).set(columnName, Integer.valueOf(0));
					}
					
						if (network.getRow(target).get(columnName, Integer.class) == 1) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, one); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
							
						if (network.getRow(target).get(columnName, Integer.class) > 2) { //change to integer if necessary
							network.getRow(target).set(IMAGE_COLUMN, plusplus); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg); 
							
							VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
									"(mapping.type=passthrough)");
							PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
									cgProp);
							
							style.addVisualMappingFunction(map);
						}
						
							if (network.getRow(target).get(columnName, Integer.class) == 2) { 
								
								network.getRow(target).set(IMAGE_COLUMN, two);
								
								VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
										"(mapping.type=passthrough)");
								PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
										cgProp);
								
								style.addVisualMappingFunction(map);
							}
								
								if (network.getRow(target).get(columnName, Integer.class) == 0) {
									network.getRow(target).set(IMAGE_COLUMN, zero); 
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -1) { 
									
									network.getRow(target).set(IMAGE_COLUMN, negone);
									
									VisualMappingFunctionFactory facto = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping mapsy = (PassthroughMapping) facto.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(mapsy);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -2) {
									network.getRow(target).set(IMAGE_COLUMN, negtwo);
									VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
											"(mapping.type=passthrough)");
									PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
											cgProp);
									
									style.addVisualMappingFunction(map);
								}
				}	
			}
		} style.apply(netView);
	}	
}