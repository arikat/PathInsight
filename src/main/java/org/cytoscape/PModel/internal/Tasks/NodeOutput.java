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

public class NodeOutput extends AbstractTask {
	private View<CyNode> nodeView;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";
	
	public NodeOutput(CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) { 
		this.netView = netView;
		this.registrar = registrar;
		this.network = network;
	}
	
	String two = "http://i.imgur.com/sq4WXnR.png";
	String one = "http://i.imgur.com/nMKj77P.png";
	String zero = "http://i.imgur.com/kh1IMGe.png";
	String negone = "http://i.imgur.com/F0FdgSx.png";
	String negtwo = "http://i.imgur.com/sHkP8rX.png";
	String plusplus = "http://i.imgur.com/qk64sKc.png";
	String negneg = "http://i.imgur.com/o2gB6mm.png";
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (network == null) {
			System.out.println("There is no network.");
			taskMonitor.setTitle("There is no network.");
			return;
		}
		
		taskMonitor.setTitle("Calculating Output");
		taskMonitor.setStatusMessage("Measuring each node...");
		
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
		//List<CyNode> Nodes = network.getNodeList();
		//create HASHSET
		//final Set<CyEdge> Edges = new HashSet<CyEdge>();
		//final Set<CyNode> neighbors = new HashSet<CyNode>();
		HashSet<CyNode> neighbors = new HashSet<CyNode>();
		HashSet<CyNode> nodes3 = new HashSet<CyNode>();
		HashSet<CyNode> adjnodes = new HashSet<CyNode>();
		
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
		
		if (nodeTable.getColumn(columnName) == null) {
			nodeTable.createColumn(columnName, Integer.class, true);
			//insert continue or something here to get it to go to the next if statements		
		}
		
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, true);
		}
		
		for (CyNode node : nodes) {
			taskMonitor.setStatusMessage("Number of nodes: " + nodes);
			//List<CyEdge> Edges = network.getAdjacentEdgeList(node, CyEdge.Type.OUTGOING);
			//Edges.addAll(network.getAdjacentEdgeList(node, CyEdge.Type.OUTGOING));
			//neighbors.addAll(network.getNeighborList(node, Type.OUTGOING));
			List<CyNode> neighborinos = network.getNeighborList(node, Type.OUTGOING); //SOMETHING STRANGE IS GOING ON HERE?
			taskMonitor.setStatusMessage("Number of neighborinos: " + neighborinos);
			
			for (CyNode nebs : neighborinos) {
				if (!neighbors.contains(nebs)) {
					neighbors.add(nebs);
					taskMonitor.setStatusMessage("Number of neighbors: " + neighbors);
				}
			}
		} //This cuts off the for loop to node and nodes - you can eliminate this if it doesn't work.
			//^ may be the issue - iterating twice based on number of edges - need to fix with an array perhaps? Write hash map - read up on deleting duplicate values
			
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
						nodeTable.getRow(target.getSUID()).set(columnName, (++attempt)); //instead of 2, a = 4
						
								if (network.getRow(target).get(columnName, Integer.class) == 1) {
									network.getRow(target).set(IMAGE_COLUMN, one);
								}
							
								if (network.getRow(target).get(columnName, Integer.class) > 2) {
									network.getRow(target).set(IMAGE_COLUMN, plusplus);
								}
						
								if (network.getRow(target).get(columnName, Integer.class) < -2) { 
									network.getRow(target).set(IMAGE_COLUMN, negneg);
								}
						
								if (network.getRow(target).get(columnName, Integer.class) == 2) {
									network.getRow(target).set(IMAGE_COLUMN, two);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == 0) {
									network.getRow(target).set(IMAGE_COLUMN, zero); 
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -1) { 
									network.getRow(target).set(IMAGE_COLUMN, negone);
								}
								
								if (network.getRow(target).get(columnName, Integer.class) == -2) {
									network.getRow(target).set(IMAGE_COLUMN, negtwo);
								}
					
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) >= 1)) {
					//set nodely to -1, okaay
						nodeTable.getRow(target.getSUID()).set(columnName, (--attempt));
						
						if (network.getRow(target).get(columnName, Integer.class) == 1) {
							network.getRow(target).set(IMAGE_COLUMN, one);
						}
					
						if (network.getRow(target).get(columnName, Integer.class) > 2) {
							network.getRow(target).set(IMAGE_COLUMN, plusplus);
						}
				
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg);
						}
				
						if (network.getRow(target).get(columnName, Integer.class) == 2) {
							network.getRow(target).set(IMAGE_COLUMN, two);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == 0) {
							network.getRow(target).set(IMAGE_COLUMN, zero); 
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == -1) { 
							network.getRow(target).set(IMAGE_COLUMN, negone);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == -2) {
							network.getRow(target).set(IMAGE_COLUMN, negtwo);
						}

				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) <= -1)) {
					//set nodely to -1, okay?
						nodeTable.getRow(target.getSUID()).set(columnName, (--attempt));
						
						if (network.getRow(target).get(columnName, Integer.class) == 1) {
							network.getRow(target).set(IMAGE_COLUMN, one);
						}
					
						if (network.getRow(target).get(columnName, Integer.class) > 2) {
							network.getRow(target).set(IMAGE_COLUMN, plusplus);
						}
				
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg);
						}
				
						if (network.getRow(target).get(columnName, Integer.class) == 2) {
							network.getRow(target).set(IMAGE_COLUMN, two);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == 0) {
							network.getRow(target).set(IMAGE_COLUMN, zero); 
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == -1) { 
							network.getRow(target).set(IMAGE_COLUMN, negone);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == -2) {
							network.getRow(target).set(IMAGE_COLUMN, negtwo);
						}
					}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) <= -1)) {
					//set nodely to 1, okay?
						nodeTable.getRow(target.getSUID()).set(columnName, (++attempt));
						
						if (network.getRow(target).get(columnName, Integer.class) == 1) {
							network.getRow(target).set(IMAGE_COLUMN, one);
						}
					
						if (network.getRow(target).get(columnName, Integer.class) > 2) {
							network.getRow(target).set(IMAGE_COLUMN, plusplus);
						}
				
						if (network.getRow(target).get(columnName, Integer.class) < -2) { 
							network.getRow(target).set(IMAGE_COLUMN, negneg);
						}
				
						if (network.getRow(target).get(columnName, Integer.class) == 2) {
							network.getRow(target).set(IMAGE_COLUMN, two);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == 0) {
							network.getRow(target).set(IMAGE_COLUMN, zero); 
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == -1) { 
							network.getRow(target).set(IMAGE_COLUMN, negone);
						}
						
						if (network.getRow(target).get(columnName, Integer.class) == -2) {
							network.getRow(target).set(IMAGE_COLUMN, negtwo);
						}
					}
				
				if (network.getRow(source).get(columnName, Integer.class) == 0) {
					//set nodely to 0, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, Integer.valueOf(0)); //may need to insert attempt + 0, instead of just inserting zero - remember to test this.
					
					if (network.getRow(target).get(columnName, Integer.class) == 1) {
						network.getRow(target).set(IMAGE_COLUMN, one);
					}
				
					if (network.getRow(target).get(columnName, Integer.class) > 2) {
						network.getRow(target).set(IMAGE_COLUMN, plusplus);
					}
			
					if (network.getRow(target).get(columnName, Integer.class) < -2) { 
						network.getRow(target).set(IMAGE_COLUMN, negneg);
					}
			
					if (network.getRow(target).get(columnName, Integer.class) == 2) {
						network.getRow(target).set(IMAGE_COLUMN, two);
					}
					
					if (network.getRow(target).get(columnName, Integer.class) == 0) {
						network.getRow(target).set(IMAGE_COLUMN, zero); 
					}
					
					if (network.getRow(target).get(columnName, Integer.class) == -1) { 
						network.getRow(target).set(IMAGE_COLUMN, negone);
					}
					
					if (network.getRow(target).get(columnName, Integer.class) == -2) {
						network.getRow(target).set(IMAGE_COLUMN, negtwo);
					}

				}
				
				adjnodes.addAll(network.getNeighborList(nodely, Type.OUTGOING));
				
				}
			}
				
				//OKAY - I FIGURED IT OUT - CREATE HASHSETS OF EVERYTHING SO YOU CAN TAKE THIS OUT OF THE NODELY FOR LOOP
				//NECESSARY TO PREVENT THE LOOP FROM REPEATING TWICE PER NODE!
				
				//Forget the next section until you fix current...
				
				//List<CyNode> adjnodes = network.getNeighborList(nodely, Type.OUTGOING);
				//or perhaps just create a hashset of adjnodes put it in nodely and then call it here?
				//THIS MIGHT WORK BETTER!
				
				taskMonitor.setStatusMessage("Number of adjacent nodes: " + adjnodes);
				
				for (CyNode adjs : adjnodes) {
					if (!nodes3.contains(adjs)) {
						nodes3.add(adjs);
						taskMonitor.setStatusMessage("Number of new adjacent nodes: " + nodes3);
					}
				}
				for (CyNode noddie : nodes3) {
					//insert hashmap duplicates deleter
					
					if (network.getRow(noddie).get(columnName, Integer.class) == null) {
						//set nodely to 0, okay?
						network.getRow(noddie).set(columnName, Integer.valueOf(0));
					}
					
					List<CyEdge> Edges2 = network.getAdjacentEdgeList(noddie, CyEdge.Type.INCOMING);
					
					for (CyEdge edgy : Edges2) {
						
						CyNode source2 = edgy.getSource();
						CyNode target2 = edgy.getTarget();
						
						int adjattempt = nodeTable.getRow(target2.getSUID()).get(columnName, Integer.class);
						
							//Insert all network get row nodes, etc
							if ((network.getRow(edgy).get(columnName, Integer.class) == 1) && (network.getRow(source2).get(columnName, Integer.class) >= 0)) {
								//set noddie to 1, okay?
									nodeTable.getRow(target2.getSUID()).set(columnName, (++adjattempt));
									if (network.getRow(target2).get(columnName, Integer.class) == 1) {
										network.getRow(target2).set(IMAGE_COLUMN, one);
									}
								
									if (network.getRow(target2).get(columnName, Integer.class) > 2) {
										network.getRow(target2).set(IMAGE_COLUMN, plusplus);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) < -2) { 
										network.getRow(target2).set(IMAGE_COLUMN, negneg);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) == 2) {
										network.getRow(target2).set(IMAGE_COLUMN, two);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == 0) {
										network.getRow(target2).set(IMAGE_COLUMN, zero); 
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -1) { 
										network.getRow(target2).set(IMAGE_COLUMN, negone);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -2) {
										network.getRow(target2).set(IMAGE_COLUMN, negtwo);
									}
							}
							
							if ((network.getRow(edgy).get(columnName, Integer.class) == -1) && (network.getRow(source2).get(columnName, Integer.class) >= 0)) {
								//set nodely to -1, okay?
									nodeTable.getRow(target2.getSUID()).set(columnName, (--adjattempt));
									if (network.getRow(target2).get(columnName, Integer.class) == 1) {
										network.getRow(target2).set(IMAGE_COLUMN, one);
									}
								
									if (network.getRow(target2).get(columnName, Integer.class) > 2) {
										network.getRow(target2).set(IMAGE_COLUMN, plusplus);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) < -2) { 
										network.getRow(target2).set(IMAGE_COLUMN, negneg);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) == 2) {
										network.getRow(target2).set(IMAGE_COLUMN, two);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == 0) {
										network.getRow(target2).set(IMAGE_COLUMN, zero); 
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -1) { 
										network.getRow(target2).set(IMAGE_COLUMN, negone);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -2) {
										network.getRow(target2).set(IMAGE_COLUMN, negtwo);
									}
							}
							
							if ((network.getRow(edgy).get(columnName, Integer.class) == 1) && (network.getRow(source2).get(columnName, Integer.class) <= 0)) {
								//set nodely to -1, okay?
									nodeTable.getRow(target2.getSUID()).set(columnName, (--adjattempt));
									if (network.getRow(target2).get(columnName, Integer.class) == 1) {
										network.getRow(target2).set(IMAGE_COLUMN, one);
									}
								
									if (network.getRow(target2).get(columnName, Integer.class) > 2) {
										network.getRow(target2).set(IMAGE_COLUMN, plusplus);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) < -2) { 
										network.getRow(target2).set(IMAGE_COLUMN, negneg);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) == 2) {
										network.getRow(target2).set(IMAGE_COLUMN, two);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == 0) {
										network.getRow(target2).set(IMAGE_COLUMN, zero); 
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -1) { 
										network.getRow(target2).set(IMAGE_COLUMN, negone);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -2) {
										network.getRow(target2).set(IMAGE_COLUMN, negtwo);
									}
							}
							
							if ((network.getRow(edgy).get(columnName, Integer.class) == -1) && (network.getRow(source2).get(columnName, Integer.class) <= 0)) {
								//set nodely to 1, okay?
									nodeTable.getRow(target2.getSUID()).set(columnName, (++adjattempt));
									if (network.getRow(target2).get(columnName, Integer.class) == 1) {
										network.getRow(target2).set(IMAGE_COLUMN, one);
									}
								
									if (network.getRow(target2).get(columnName, Integer.class) > 2) {
										network.getRow(target2).set(IMAGE_COLUMN, plusplus);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) < -2) { 
										network.getRow(target2).set(IMAGE_COLUMN, negneg);
									}
							
									if (network.getRow(target2).get(columnName, Integer.class) == 2) {
										network.getRow(target2).set(IMAGE_COLUMN, two);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == 0) {
										network.getRow(target2).set(IMAGE_COLUMN, zero); 
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -1) { 
										network.getRow(target2).set(IMAGE_COLUMN, negone);
									}
									
									if (network.getRow(target2).get(columnName, Integer.class) == -2) {
										network.getRow(target2).set(IMAGE_COLUMN, negtwo);
									}
								}
								
							if (network.getRow(source2).get(columnName, Integer.class) == 0) {
								//set nodely to 0, okay?
								nodeTable.getRow(target2.getSUID()).set(columnName, Integer.valueOf(0)); //perhaps change this to adjattempt + 0
								if (network.getRow(target2).get(columnName, Integer.class) == 1) {
									network.getRow(target2).set(IMAGE_COLUMN, one);
								}
							
								if (network.getRow(target2).get(columnName, Integer.class) > 2) {
									network.getRow(target2).set(IMAGE_COLUMN, plusplus);
								}
						
								if (network.getRow(target2).get(columnName, Integer.class) < -2) { 
									network.getRow(target2).set(IMAGE_COLUMN, negneg);
								}
						
								if (network.getRow(target2).get(columnName, Integer.class) == 2) {
									network.getRow(target2).set(IMAGE_COLUMN, two);
								}
								
								if (network.getRow(target2).get(columnName, Integer.class) == 0) {
									network.getRow(target2).set(IMAGE_COLUMN, zero); 
								}
								
								if (network.getRow(target2).get(columnName, Integer.class) == -1) { 
									network.getRow(target2).set(IMAGE_COLUMN, negone);
								}
								
								if (network.getRow(target2).get(columnName, Integer.class) == -2) {
									network.getRow(target2).set(IMAGE_COLUMN, negtwo);
								}
							}
			} 
					VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
							"(mapping.type=passthrough)");
					PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
							cgProp);
					
					style.addVisualMappingFunction(map);
					style.apply(netView);
					netView.updateView();
		}
	}	
}