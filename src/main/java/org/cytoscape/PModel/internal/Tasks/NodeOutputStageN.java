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
import org.cytoscape.work.Tunable;

public class NodeOutputStageN extends AbstractTask {
	private View<CyNode> nodeView;
	private View<CyEdge> edgeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private CyServiceRegistrar registrar;
	public static final String IMAGE_COLUMN = "Image URL";
	private CyNode cyNode;
	private CyNetwork network;
	String columnName = "bool";
	String question = "qMark";
	String shape = "Shape";
	Integer n = 0;
	Integer x = 0;
	
	public NodeOutputStageN(CyApplicationManager applicationManager, CyNetworkView netView, CyServiceRegistrar registrar, CyNetwork network) { 
		this.netView = netView;
		this.registrar = registrar;
		this.network = network;
		this.applicationManager = applicationManager;
	}
	
	String two = "http://i.imgur.com/gBkmqwX.png";
	String one = "http://i.imgur.com/y1VbITV.png";
	String zero = "http://i.imgur.com/UfTJo4N.png";
	String negone = "http://i.imgur.com/fsEggSs.png";
	String negtwo = "http://i.imgur.com/qQ5JvTy.png";
	String plusplus = "http://i.imgur.com/mWmyPNl.png";
	String negneg = "http://i.imgur.com/MXvZ8rG.png";
	
	@Tunable(description="Enter Number of Steps", groups=("Steps"), params="displayState=collapsed")
	public Integer getSteps() {
		return x;
	}
	
	public void setSteps(Integer newSteps) {
		x = newSteps;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		if (network == null) {
			System.out.println("There is no network.");
			taskMonitor.setTitle("There is no network.");
			return;
		}
		
		
		taskMonitor.setStatusMessage("Number of Steps set to " + x);
				
		CyNetworkView netoView = applicationManager.getCurrentNetworkView();
		
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
		HashSet<CyNode> selective = new HashSet<CyNode>();
		selective.addAll(nodes);
		
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
		
		if (nodeTable.getColumn(question) == null) {
			nodeTable.createColumn(question, String.class, true);
		}
		
		if (nodeTable.getColumn(IMAGE_COLUMN) == null) {
			nodeTable.createColumn(IMAGE_COLUMN, String.class, true);
		}
		
		if (nodeTable.getColumn(shape) == null) {
			nodeTable.createColumn(shape, String.class, true);
		}
		
		
		
		//Introduce the while loop here, if n!=x, may need to introduce above the for loop?
		while (n < x) { //replaced while loop with if -- maybe this works better
			taskMonitor.setStatusMessage("loop begins");
		
		for (CyNode node : selective) { //let's see if this changes anything
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
		
			for (CyNode nodely : neighbors) {
				
			selective.clear(); //introduce this here, by clearing it, you can add to it again at the end. 
			taskMonitor.setStatusMessage("Selected node hashset cleared");
			
			
			CharSequence hip = "process";	
			CharSequence and = "AND";
			CharSequence or = "OR";
			String lab = network.getRow(nodely).get(shape, String.class);
			
			if (lab == null) {	//check if this works
				
			if (network.getRow(nodely).get(columnName, Integer.class) == null) {
					//set nodely to 0, okay?
					network.getRow(nodely).set(columnName, Integer.valueOf(0));
				}
				
				List<CyEdge> Edges = network.getAdjacentEdgeList(nodely, CyEdge.Type.INCOMING); //change back to nodely, incoming if not working
				for (CyEdge edge : Edges) { //consider switching order of edges -- look up for loop without for loop!!!!
				
					CyNode source = edge.getSource();
					CyNode target = edge.getTarget(); //prev nodely
					
					selective.add(target); //Would this work? If I give the targets?
					
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
					
					if (netoView.getNodeView(nodely).getVisualProperty(BasicVisualLexicon.NODE_VISIBLE) == true) {
					
					if (network.getRow(target).get(columnName, Integer.class) == 1) {
						network.getRow(target).set(IMAGE_COLUMN, one);
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
				
					if (network.getRow(target).get(columnName, Integer.class) > 2) {
						network.getRow(target).set(IMAGE_COLUMN, plusplus);
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
			
					if (network.getRow(target).get(columnName, Integer.class) < -2) { 
						network.getRow(target).set(IMAGE_COLUMN, negneg);
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
			
					if (network.getRow(target).get(columnName, Integer.class) == 2) {
						network.getRow(target).set(IMAGE_COLUMN, two);
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
					
					if (network.getRow(target).get(columnName, Integer.class) == 0) {
						network.getRow(target).set(IMAGE_COLUMN, zero); 
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
					
					if (network.getRow(target).get(columnName, Integer.class) == -1) { 
						network.getRow(target).set(IMAGE_COLUMN, negone);
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?");
					}
					
					if (network.getRow(target).get(columnName, Integer.class) == -2) {
						network.getRow(target).set(IMAGE_COLUMN, negtwo);
						netoView.getEdgeView(edge).setLockedValue(BasicVisualLexicon.EDGE_LABEL, "?"); //note may need to remove and check
					} 
					
					}

				}
				
				}
				
			}
			
			
			if (lab != null) {	 //check if this works
				
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
				
			
			
			}
				
					VisualMappingFunctionFactory factory = registrar.getService(VisualMappingFunctionFactory.class,
							"(mapping.type=passthrough)");
					PassthroughMapping map = (PassthroughMapping) factory.createVisualMappingFunction(IMAGE_COLUMN, String.class,
							cgProp);
					
					style.addVisualMappingFunction(map);
					style.apply(netView);
					netView.updateView();
					
					//right
					
					//random null error is occurring here. What is going on?
					
					//selective.clear(); maybe add it here?
					
				/*	neighbors.clear(); //by adding it here, it will reiterate at the very end.
					n = n++;
					taskMonitor.setStatusMessage("Step " + n + " complete!");
					
					taskMonitor.setStatusMessage("hashsets cleared and renewed");*/
					
					
		}
			
			neighbors.clear(); //by adding it here, it will reiterate at the very end.
			n = n+1;
			taskMonitor.setStatusMessage("Step " + n + " complete!");
			
			taskMonitor.setStatusMessage("neighbor hashset cleared and renewed");
			
		}
			
	}	
}