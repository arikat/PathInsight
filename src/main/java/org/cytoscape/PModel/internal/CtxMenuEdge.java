package org.cytoscape.PModel.internal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.model.CyEdge;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.values.ArrowShape;

public class CtxMenuEdge implements CyEdgeViewContextMenuFactory, ActionListener  {

	@Override
	public CyMenuItem createMenuItem(CyNetworkView netView,View<CyEdge> edgeView) {
		JMenuItem MenuItem = new JMenuItem("Edge Modification");
		MenuItem.addActionListener(this); //Note when changing NetArrow, change this as well
		CyMenuItem edgeMenu = new CyMenuItem(MenuItem, 1);
		return edgeMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Change to change network arrow.
		JOptionPane.showMessageDialog(null, "Work in Progress.");
		
	}
	
}