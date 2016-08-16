package org.cytoscape.PModel.internal;

import java.util.Properties;
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.task.NetworkViewTaskFactory;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.VisualLexicon;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.application.swing.CyNodeViewContextMenuFactory;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CyEdgeViewContextMenuFactory;
import org.osgi.framework.BundleContext;

public class CyActivator extends AbstractCyActivator {
	
	@Override
	public void start(BundleContext context) throws Exception {
		//change CyActivator for neighborFinderFactory
		CyServiceRegistrar registrar = getService(context, CyServiceRegistrar.class);
		VisualStyleFactory vsf = getService(context,VisualStyleFactory.class);
		VisualLexicon currentLexicon = getService(context, RenderingEngineManager.class).getDefaultVisualLexicon();
		VisualMappingManager mappy = getService(context, VisualMappingManager.class);
		CyApplicationManager cyApplicationManagerService = getService(context, CyApplicationManager.class);
		CyNetworkView cyNetworkView = getService(context, CyNetworkView.class);
		CySwingApplication cyApp = getService(context, CySwingApplication.class);
		VisualMappingFunctionFactory fact = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");
		//CustomGraphicsManager customGraphicsManager = getService(context, CustomGraphicsManager.class);
		//CustomGraphicsManagerImpl customGraphicsManagerImpl = getService(context, CustomGraphicsManagerImpl.class);
		
		//shape change
		InhibitionTaskFactory InhibitionShapeTask = new InhibitionTaskFactory(cyApplicationManagerService);
		Properties cprops = new Properties();
		cprops.setProperty("preferredMenu", "Apps.Pathway Model");
		cprops.setProperty("title", "Auto Edge Distinction");
		cprops.setProperty("inMenuBar", "true");
		cprops.setProperty("menuGravity", "8.2");
		registerService(context, InhibitionShapeTask, EdgeViewTaskFactory.class, cprops);
		
		//create column activation
		ActivationEdgeFactory createcolumn = new ActivationEdgeFactory(cyApplicationManagerService);
		Properties aprops = new Properties();
		aprops.setProperty("preferredMenu", "Apps.Pathway Model.Edge Label");
		aprops.setProperty("title", "Activation");
		aprops.setProperty("inMenuBar", "true");
		aprops.setProperty("menuGravity", "8.3");
		registerService(context, createcolumn, TaskFactory.class, aprops);

		
		//annotate with image - maybe works
		NeighborFinderFactory cg = new NeighborFinderFactory();
		Properties gprops = new Properties();
		gprops.setProperty("preferredMenu", "Apps.Pathway Model.Draw");
		gprops.setProperty("title", "Drowsy");
		gprops.setProperty("inMenuBar", "true");
		gprops.setProperty("menuGravity", "8.4");
		registerService(context, cg, NodeViewTaskFactory.class, gprops);
		
		//ImageAnnotationNode ian = new ImageAnnotationNode(cyApplicationManagerService, currentLexicon);
		//registerService(context, ian, CyAction.class, new Properties());
		
		//ImageAnnoViz vizan = new ImageAnnoViz(cyApplicationManagerService, mappy, fact, currentLexicon);
		//registerService(context, vizan, CyAction.class, new Properties());
		
		CustomGraphicsManager manager = new CustomGraphicsManager(registrar);
		CyCustomGraphicsFactory<ImageAnno> stringLookFactory = new CustomGraphicsFactory(manager);
		Properties stringProps = new Properties();
		registerService(context, stringLookFactory, CyCustomGraphicsFactory.class, stringProps);
		
		
		//annotate with image -- does not work currently?
		/*CustomGraphicsFactory cg = new CustomGraphicsFactory();
		Properties gprops = new Properties();
		gprops.setProperty("preferredMenu", "Apps.Aarya.Drowsy");
		gprops.setProperty("inMenuBar", "true");
		gprops.setProperty("menuGravity", "8.4");
		registerService(context, cg, CyCustomGraphicsFactory.class, gprops);*/
		
		
		
		TaskFactory paintStructure = new trialattemptfactory(registrar, cyApplicationManagerService);
		Properties paintStructureProps = new Properties();
		paintStructureProps.setProperty("preferredMenu", "Apps.Pathway Model");
		paintStructureProps.setProperty("title", "Painty");
		//paintStructureProps.setProperty(ENABLE_FOR, "networkAndView");
		paintStructureProps.setProperty("inMenuBar", "true");
		paintStructureProps.setProperty("menuGravity", "8.6");
		registerService(context, paintStructure, NodeViewTaskFactory.class, paintStructureProps);
		
		
	/*	VisualMappingManager vmmServiceRef = getService(context,VisualMappingManager.class);
		
		VisualStyleFactory visualStyleFactoryServiceRef = getService(context,VisualStyleFactory.class);
		
		VisualMappingFunctionFactory vmfFactoryC = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory vmfFactoryD = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP = getService(context,VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");

		CreateVisualStyleAction createVisualStyleAction = new CreateVisualStyleAction(cyApplicationManagerService, vmmServiceRef, visualStyleFactoryServiceRef, 
				vmfFactoryC, vmfFactoryD, vmfFactoryP);
		
		registerService(context,createVisualStyleAction,CyAction.class, new Properties());
		*/
		
		
	}
	
}
		
		
		
		
		
		
		
		
		
		
		
		
		// Add right click menu item to the edge view
		/*ArrowShapeBypassTaskFactory ArrowShapeBypass = new ArrowShapeBypassTaskFactory();
		String menu = "Activation";
		addMenus(context, ArrowShapeBypass, menu, "8.1", true); //take out all menus, put in taskfactory task
		InhibitionTaskFactory InhibitionShapeTask = new InhibitionTaskFactory();
		String menus = "Inhibition";
		addMenu(context, InhibitionShapeTask, menus, "8.2", true);
		ActivationEdgeFactory createcolumn = new ActivationEdgeFactory(cyApplicationManagerService);
		String menus2 = "Activation";
		aMenu(context, createcolumn, menus2, "8.3", true);
		
		CustomGraphicsFactory cg = new CustomGraphicsFactory(cyApplicationManagerService);
		String menus3 = "Draw Arrows";
		menus3(context, cg, menus3, "8.5", true);
		
		
		
		//NeighborFinderFactory neighbor = new NeighborFinderFactory();
		//String menus3 = "Draw Arrows";
		//aMensu(context, neighbor, menus3, "8.4", true);
		//neighborFinder neighbor = new neighborFinder(cyApp, null); // replace null with network view
		//Properties propsy = new Properties();
		//propsy.setProperty("preferredMenu", "Apps.Aarya.DrawArrow");
		//propsy.setProperty("title", "Draw Arrows");
		//propsy.setProperty("inMenuBar", "true");
		//registerService(context, neighbor, CyAction.class, new Properties());
		

		
	}

	private void menus3(BundleContext context, CustomGraphicsFactory cg, String menus3, String gravity, boolean exclusive) {
		String baseMenu = "Apps.Aarya.Draw";
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
		registerService(context, cg, TaskFactory.class, props);
		
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
		String baseMenu = "Apps.Aarya.Activation";
		Properties props = new Properties();
		if (menus2 != null) {
			props.setProperty("preferredMenu", baseMenu);
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
		String baseMenu = "Apps.Aarya.Draw";
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
		registerService(context, neighbor, NodeViewTaskFactory.class, props);

	}*/