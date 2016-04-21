package org.cytoscape.PModel.internal;

import java.util.Properties;

import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {

		// Add right click menu item to the edge view
		ArrowShapeBypassTaskFactory ArrowShapeBypass = new ArrowShapeBypassTaskFactory();
		String menu = "Activation";
		addMenus(context, ArrowShapeBypass, menu, "8.1", true);
		InhibitionTaskFactory InhibitionShapeTask = new InhibitionTaskFactory();
		String menus = "Inhibition";
		addMenu(context, InhibitionShapeTask, menus, "8.2", true);
	}

	private void addMenus(BundleContext context, ArrowShapeBypassTaskFactory arrowShapeBypass, String menu,
			String gravity, boolean exclusive) {
		String baseMenu = "Apps.Aarya.Activation";
		Properties props = new Properties();
		if (menu != null) {
			props.setProperty("preferredMenu", baseMenu + "." + menu);
			props.setProperty("inMenuBar", "true");
			props.setProperty("menuGravity", gravity);
		}
		if (exclusive) {
			props = new Properties();
			if (menu != null) {
				props.setProperty("inMenuBar", "true");
				props.setProperty("menuGravity", gravity);
				props.setProperty("preferredMenu", baseMenu);
			}

		}
		registerService(context, arrowShapeBypass, EdgeViewTaskFactory.class, props);
	}
	private void addMenu(BundleContext context, InhibitionTaskFactory InhibitionShapeTask, String menus,
			String gravity, boolean exclusive) {
		String baseMenu = "Apps.Aarya.Inhibition";
		Properties props = new Properties();
		if (menus != null) {
			props.setProperty("preferredMenu", baseMenu + "." + menus);
			props.setProperty("inMenuBar", "true");
			props.setProperty("menuGravity", gravity);
		}
		if (exclusive) {
			props = new Properties();
			if (menus != null) {
				props.setProperty("inMenuBar", "true");
				props.setProperty("menuGravity", gravity);
				props.setProperty("preferredMenu", baseMenu);
			}

		}
		registerService(context, InhibitionShapeTask, EdgeViewTaskFactory.class, props);
	}
}