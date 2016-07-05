package org.cytoscape.PModel.internal;

import java.util.Properties;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		//change CyActivator for neighborFinderFactory
		VisualLexicon currentLexicon = getService(context, RenderingEngineManager.class).getDefaultVisualLexicon();
		CyApplicationManager cyApplicationManagerService = getService(context, CyApplicationManager.class);
		// Add right click menu item to the edge view
		ArrowShapeBypassTaskFactory ArrowShapeBypass = new ArrowShapeBypassTaskFactory();
		String menu = "Activation";
		addMenus(context, ArrowShapeBypass, menu, "8.1", true); //take out all menus, put in taskfactory task
		InhibitionTaskFactory InhibitionShapeTask = new InhibitionTaskFactory();
		String menus = "Inhibition";
		addMenu(context, InhibitionShapeTask, menus, "8.2", true);
		ActivationEdgeFactory createcolumn = new ActivationEdgeFactory(cyApplicationManagerService);
		String menus2 = "Activation";
		aMenu(context, createcolumn, menus2, "8.3", true);
		//NeighborFinderFactory neighbor = new NeighborFinderFactory(cyApplicationManagerService);
		//String menus3 = "Activation";
		//aMensu(context, neighbor, menus3, "8.3", true);
		
		neighborFinder neighbor = new neighborFinder(cyApplicationManagerService, currentLexicon);
		registerService(context, neighbor, CyAction.class, new Properties());
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

	private void addMenu(BundleContext context, InhibitionTaskFactory InhibitionShapeTask, String menus, String gravity,
			boolean exclusive) {
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

	private void aMenu(BundleContext context, ActivationEdgeFactory createcolumn, String menus2, String gravity,
			boolean exclusive) {
		String baseMenu = "Apps.Aarya";
		Properties props = new Properties();
		if (menus2 != null) {
			props.setProperty("preferredMenu", baseMenu + "." + menus2);
			props.setProperty("inMenuBar", "true");
			props.setProperty("menuGravity", gravity);
		}
		if (exclusive) {
			props = new Properties();
			if (menus2 != null) {
				props.setProperty("inMenuBar", "true");
				props.setProperty("menuGravity", gravity);
				props.setProperty("preferredMenu", baseMenu);
			}

		}
		registerService(context, createcolumn, TaskFactory.class, props);

	}
	/*private void aMensu(BundleContext context, NeighborFinderFactory neighbor, String menus3, String gravity,
			boolean exclusive) {
		String baseMenu = "Apps.Aarya";
		Properties props = new Properties();
		if (menus3 != null) {
			props.setProperty("preferredMenu", baseMenu);
			props.setProperty("inMenuBar", "true");
			props.setProperty("menuGravity", gravity);
		}
		if (exclusive) {
			props = new Properties();
			if (menus3 != null) {
				props.setProperty("inMenuBar", "true");
				props.setProperty("menuGravity", gravity);
				props.setProperty("preferredMenu", baseMenu);
			}

		}
		registerService(context, neighbor, TaskFactory.class, props);

	}*/
}