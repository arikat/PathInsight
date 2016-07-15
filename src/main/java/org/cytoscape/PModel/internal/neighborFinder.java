package org.cytoscape.PModel.internal;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.model.CyRow;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.Tunable;
import org.cytoscape.model.CyTable;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.task.AbstractEdgeViewTask;
import org.cytoscape.task.AbstractNetworkViewTask;
import org.cytoscape.task.AbstractNodeViewTask;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.application.swing.CySwingApplication;

public class neighborFinder extends AbstractCyAction { // prev abstractTask

	private View<CyNode> nodeView;
	private CyNetworkView netView;
	private CyApplicationManager applicationManager;
	private static final long serialVersionUID = 1L;
	private VisualLexicon lexicon;
	private CySwingApplication desktopApp;
	
//wrap neighborfinder around Taskmonitor
	public neighborFinder(CySwingApplication desktopApp, CyNetworkView network) { // CyApplicationManager View<CyNode> nodeView, CyNetworkView netView
					
		
		super("Green Arrow");
		setPreferredMenu("Apps.Aarya");

		ImageIcon icon = new ImageIcon(getClass().getResource("/Green_Arrow_small.png"));
		ImageIcon smallIcon = new ImageIcon(getClass().getResource("/Green_Arrow_small.png"));

		// Add image icons on tool-bar and menu item
		putValue(LARGE_ICON_KEY, icon);
		putValue(SMALL_ICON, smallIcon);
		
		this.desktopApp = desktopApp;// appManager // VisualLexicon												// lexicon
		this.nodeView = nodeView;
		this.netView = netView;

		// super(view);
		// super("Draw Images on Nodes");
		// setPreferredMenu("Apps.Aarya"); //Check if correct

		// CyApplicationManager appManager;
		// this.applicationManager = appManager;
		this.lexicon = lexicon;
	}

	public String columnName = "bool";
	// public neighborFinder(CyNetwork network) {
	// this.network = network;
	// }
//wrap this around application manager
	public void actionPerformed(ActionEvent e) { // change back to Task Monitor if needed
		
		JOptionPane.showMessageDialog(this.desktopApp.getJFrame(), "green", "Insert Green Arrow", 
				JOptionPane.INFORMATION_MESSAGE);
		CyNetwork network = applicationManager.getCurrentNetwork();
		CyNetworkView networkView = applicationManager.getCurrentNetworkView(); //NOTE CALL THE NETWORK VIEW

		if (network == null) {
			System.out.println("There is no network.");
			JOptionPane.showMessageDialog(this.desktopApp.getJFrame(), "no network detected", "Insert Green Arrow", 
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		//VisualProperty Green = lexicon.lookup(CyNode.class, "Green_Arrow_Up.png");
		CyTable edgeTable = network.getDefaultEdgeTable();
		CyTable nodeTable = network.getDefaultNodeTable();

		if (edgeTable.getColumn(columnName) == null) {
			System.out.println("There is no column bool");
			JOptionPane.showMessageDialog(this.desktopApp.getJFrame(), "no column detected", "Insert Green Arrow", 
					JOptionPane.INFORMATION_MESSAGE);
		} else if (edgeTable.getColumn(columnName) != null) {
			List<CyNode> Nodes = CyTableUtil.getNodesInState(network, "selected", true); // change to all
			JOptionPane.showMessageDialog(this.desktopApp.getJFrame(), "Activated Node", "Insert Green Arrow", 
					JOptionPane.INFORMATION_MESSAGE);
			CyEdge.Type Incoming = null;
			for (CyNode node : Nodes) {
				List<CyEdge> Edges = network.getAdjacentEdgeList(node, Incoming);
				for (CyEdge edge : Edges) {
					if (network.getRow(edge).get(columnName, int.class) == 1) {
						//SwingUtilities.invokeLater(new Runnable() {
							//public void run() {
								//final CySwingAppAdapter adapter = null;
								//VisualMappingManager visualMappingManager = adapter.getVisualMappingManager();
								//VisualStyle style = visualMappingManager.getCurrentVisualStyle();
								// View<CyNode> nodeView = null;
								// ImageIcon icons = new
								// ImageIcon(getClass().getResource("Green_Arrow_Up.png"));
								//nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.green);
								// nodeView.setVisualProperty(BasicVisualLexicon.NODE_FILL_COLOR,
								// Color.green);
								//style.apply(netView);
								//netView.updateView();
								//System.out.println("Green Arrow on Node.");
								// nodeView.setLockedValue();
							}
						//});
					
					}
				}
			}

		}
	public boolean isInToolBar() {
		return true;
	}
	public boolean isInMenuBar() {
		return true;
	}
}
