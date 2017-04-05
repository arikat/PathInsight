package org.cytoscape.PModel.internal.Tasks;

import java.awt.Color;
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
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

public class NodeOutputStageI extends AbstractTask {
	private View<CyNode> nodeView;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "setBool";
	String question = "qMark";
	String shape = "setShape";
	
	public NodeOutputStageI(CyApplicationManager applicationManager, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) { 
		this.netView = netView;
		this.registrar = registrar;
		this.network = network;
		this.applicationManager = applicationManager;
	}
	
	String two = "http://i.imgur.com/0gmeOHH.png";
	String one = "http://i.imgur.com/tdPe5At.png";
	String zero = "http://i.imgur.com/tu2XzsP.png";
	String negone = "http://i.imgur.com/y0845Hl.png";
	String negtwo = "http://i.imgur.com/R7PVjVw.png";
	String plusplus = "http://i.imgur.com/S7RykhX.png";
	String negneg = "http://i.imgur.com/Y3j8Jl0.png";
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (network == null) {
			System.out.println("There is no network.");
			taskMonitor.setTitle("There is no network.");
			return;
		}
		
		//check if visible
		
		CyNetworkView netoView = applicationManager.getCurrentNetworkView();
		
		taskMonitor.setTitle("Calculating Output");
		taskMonitor.setStatusMessage("Measuring each node...");
		
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();
		
		List<CyNode> nodes = CyTableUtil.getNodesInState(network, "selected", true);
		//List<CyNode> Nodes = network.getNodeList();
		//create HASHSET
		HashSet<CyEdge> Edges = new HashSet<CyEdge>();
		//final Set<CyNode> neighbors = new HashSet<CyNode>();
		HashSet<CyNode> neighbors = new HashSet<CyNode>();
		
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
			nodeTable.createColumn(columnName, Integer.class, false);
			//insert continue or something here to get it to go to the next if statements		
		}
		
		if (nodeTable.getColumn(question) == null) {
			nodeTable.createColumn(question, String.class, false);
		}
		
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, false);
		}
		
		if (nodeTable.getColumn(shape) == null) {
			nodeTable.createColumn(shape, String.class, false);
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
			
			CharSequence hip = "process";	
			CharSequence and = "AND";
			CharSequence or = "OR";
			String lab = network.getRow(nodely).get(shape, String.class);
			
			//if (lab == null) {
			
			//if ((lab.contains(hip) || lab.contains(or) || lab.contains(and)) == false) {	//check if this works
				
			if (network.getRow(nodely).get(columnName, Integer.class) == null) {
					//set nodely to 0, okay?
					network.getRow(nodely).set(columnName, Integer.valueOf(0));
				}
			
				//List<CyEdge> qEdges = network.getAdjacentEdgeList(nodely, CyEdge.Type.OUTGOING); //attempt for question marks
				List<CyEdge> Edgess = network.getAdjacentEdgeList(nodely, CyEdge.Type.INCOMING); //change back to nodely, incoming if not working
				Edges.addAll(Edgess);
				for (CyEdge edge : Edges) { //consider switching order of edges -- look up for loop without for loop!!!!
					
					//consider making these edges incoming and creating a hashset, then clear the hashset at the end for the next round
				
					CyNode source = edge.getSource();
					CyNode target = edge.getTarget(); //prev nodely
					
				List<CyEdge> qEdges = network.getAdjacentEdgeList(source, CyEdge.Type.OUTGOING);
				
				//this is where I identify CYEDGE and write the algorithm
				// if node >= edge and node > 0 = 1
				// if edge <= node and node < 0 = -1
				// if node = 0 = -1	
					
					//if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
						//note del this if it doesn't work
						
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
						
						if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
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
					
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) >= 1)) {
					//set nodely to -1, okaay
						nodeTable.getRow(target.getSUID()).set(columnName, (--attempt));
						
						
						if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
							
						
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

				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) <= -1)) {
					//set nodely to -1, okay?
						nodeTable.getRow(target.getSUID()).set(columnName, (--attempt));
						
						if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
						
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
					}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) <= -1)) {
					//set nodely to 1, okay?
						nodeTable.getRow(target.getSUID()).set(columnName, (++attempt));
						
						//all I did was add q, see if this works.
						
						if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
						
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
					}
				
				if (network.getRow(source).get(columnName, Integer.class) == 0) {
					//set nodely to 0, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, attempt); //may need to insert attempt + 0, instead of just inserting zero - remember to test this.
					
					for (CyEdge qEdge : qEdges) {
						
						CyNode qsource = qEdge.getSource();
						CyNode qtarget = qEdge.getTarget();
					
					if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
					
					if (network.getRow(qtarget).get(columnName, Integer.class) == 1) {
						network.getRow(qtarget).set(IMAGE_COLUMN, one);
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
				
					if (network.getRow(qtarget).get(columnName, Integer.class) > 2) {
						network.getRow(qtarget).set(IMAGE_COLUMN, plusplus);
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
			
					if (network.getRow(qtarget).get(columnName, Integer.class) < -2) { 
						network.getRow(qtarget).set(IMAGE_COLUMN, negneg);
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
			
					if (network.getRow(qtarget).get(columnName, Integer.class) == 2) {
						network.getRow(qtarget).set(IMAGE_COLUMN, two);
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
					
					if (network.getRow(qtarget).get(columnName, Integer.class) == 0) {
						network.getRow(qtarget).set(IMAGE_COLUMN, zero); 
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
					
					if (network.getRow(qtarget).get(columnName, Integer.class) == -1) { 
						network.getRow(qtarget).set(IMAGE_COLUMN, negone);
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
					
					if (network.getRow(qtarget).get(columnName, Integer.class) == -2) {
						network.getRow(qtarget).set(IMAGE_COLUMN, negtwo);
						netoView.getEdgeView(qEdge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?"); //note may need to remove and check
					} 
					
					}
				}
				}
				
				//}
				
			}
				
			Edges.clear();
			
			/*if (lab != null) { // why did you forget to instantiate lab, #sad :(
			
			//if ((lab.contains(hip) || lab.contains(or) || lab.contains(and))) {	 //check if this works
				
			if (network.getRow(nodely).get(columnName, Integer.class) == null) {
					//set nodely to 0, okay?
					network.getRow(nodely).set(columnName, Integer.valueOf(0));
				}
				
				List<CyEdge> Edges = network.getAdjacentEdgeList(nodely, CyEdge.Type.INCOMING); //change back to nodely, incoming if not working
				for (CyEdge edge : Edges) { //consider switching order of edges -- look up for loop without for loop!!!!
				
					CyNode source = edge.getSource();
					CyNode target = edge.getTarget(); //prev nodely		
					
					
				int attempt = nodeTable.getRow(target.getSUID()).get(columnName, Integer.class);
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) == 1)) {
					//set nodely to 1, okay?
						nodeTable.getRow(target.getSUID()).set(columnName, (++attempt)); //instead of 2, a = 4
						
				}
				
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) == -1)) {
					//set nodely to -1, okay?
						nodeTable.getRow(target.getSUID()).set(columnName, (++attempt));
						
					}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) == 1)) {
					nodeTable.getRow(target.getSUID()).set(columnName, --attempt);
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) > 1)) {
					nodeTable.getRow(target.getSUID()).set(columnName, --attempt - 1);
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) < -1)) {
					nodeTable.getRow(target.getSUID()).set(columnName, --attempt - 1);
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) > 1)) {
					nodeTable.getRow(target.getSUID()).set(columnName, ++attempt + 1);
				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == -1) && (network.getRow(source).get(columnName, Integer.class) < -1)) {
					nodeTable.getRow(target.getSUID()).set(columnName, ++attempt + 1);
				}
				
				if (network.getRow(source).get(columnName, Integer.class) == 0) {
					//set nodely to 0, okay?
					nodeTable.getRow(target.getSUID()).set(columnName, attempt);	

				}
				
				if ((network.getRow(edge).get(columnName, Integer.class) == 1) && (network.getRow(source).get(columnName, Integer.class) == -1)) {
					nodeTable.getRow(target.getSUID()).set(columnName, --attempt);
				}
				
				}
				
			}*/
				
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