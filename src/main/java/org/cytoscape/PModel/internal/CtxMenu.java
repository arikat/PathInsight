package org.cytoscape.PModel.internal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.values.ArrowShape;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import org.cytoscape.view.model.View;

import java.util.EventListener;
import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.cytoscape.application.swing.CyMenuItem;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.model.CyNode;

public class CtxMenu implements CyNodeViewContextMenuFactory, ActionListener {
	public CyMenuItem createMenuItem(CyNetworkView netView, View<CyNode> nodeView) {        
        JMenuItem jMenu = new JMenuItem("Node Modification");
        jMenu.addActionListener(this);
        CyMenuItem newMenu = new CyMenuItem(jMenu, 1);
        return newMenu;        
    }

    public void actionPerformed(ActionEvent e) {
        // Write your own function here.
        JOptionPane.showMessageDialog(null, "Work in Progress.");

}
	}