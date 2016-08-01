package org.cytoscape.PModel.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.events.NetworkAddedEvent;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.TaskObserver;

public class CustomGraphicsManager implements NetworkAddedListener {
	final CyServiceRegistrar registrar;
	final CyEventHelper cyEventHelper;
	final TaskManager<?,?> dialogTaskManager;
	final SynchronousTaskManager<?> synchronousTaskManager;
	private boolean showImage = true;
	private boolean ignore = false;
	private Map<CyNetwork, StringNetwork> stringNetworkMap;

	public static String ResolveURI = "http://string-db.org/api/";
	public static String URI = "http://api.jensenlab.org/";
	public static String CallerIdentity = "string_app";

	public CustomGraphicsManager(CyServiceRegistrar registrar) {
		this.registrar = registrar;
		// Get our task managers
		dialogTaskManager = registrar.getService(TaskManager.class);
		synchronousTaskManager = registrar.getService(SynchronousTaskManager.class);
		cyEventHelper = registrar.getService(CyEventHelper.class);
		stringNetworkMap = new HashMap<>();
	}

	public String getNetworkName(String ids) {
		String name = "String Network";

		// If we have a single query term, use that as the network name
		if (ids != null) {
			if (ids.split("\n").length == 1) {
				return name+" - "+ids;
			}
		}

		// Use simple name, but make sure we're unique
		Set<CyNetwork> allNetworks = registrar.getService(CyNetworkManager.class).getNetworkSet();
		if (allNetworks == null || allNetworks.size() == 0)
			return name;

		int max = 0;
		for (CyNetwork network: allNetworks) {
			String netName = getNetworkName(network);
			if (netName.startsWith("String Network")) {
				String [] parts = netName.split("_");
				if (parts.length == 1)
					max = 1;
				else {
					max = Integer.parseInt(parts[1].trim());
				}
			}
		}

		if (max > 0)
			return name+"_"+max;

		return name;
	}

	public CyNetwork createNetwork(String name) {
		CyNetwork network = registrar.getService(CyNetworkFactory.class).createNetwork();
		network.getRow(network).set(CyNetwork.NAME, name);
		return network;
	}

	public CyNetwork createStringNetwork(String name, StringNetwork stringNet) {
		CyNetwork network = createNetwork(name);
		addStringNetwork(stringNet, network);
		return network;
	}

	public void addStringNetwork(StringNetwork stringNet, CyNetwork network) {
		stringNetworkMap.put(network, stringNet);
		stringNet.setNetwork(network);
	}

	public StringNetwork getStringNetwork(CyNetwork network) {
		if (stringNetworkMap.containsKey(network))
			return stringNetworkMap.get(network);
		return null;
	}

	public String getNetworkName(CyNetwork net) {
		return net.getRow(net).get(CyNetwork.NAME, String.class);
	}

	public CyNetworkView createNetworkView(CyNetwork network) {
		CyNetworkView view = registrar.getService(CyNetworkViewFactory.class).createNetworkView(network);
		return view;
	}

	public void addNetwork(CyNetwork network) {
		registrar.getService(CyNetworkManager.class).addNetwork(network);
		registrar.getService(CyApplicationManager.class).setCurrentNetwork(network);
	}
	
	public CyNetwork getCurrentNetwork() {
		return registrar.getService(CyApplicationManager.class).getCurrentNetwork();
	}

	public boolean showImage() { return showImage; }
	public void setShowImage(boolean set) { showImage = set; }

	public void flushEvents() {
		cyEventHelper.flushPayloadEvents();
	}

	public void execute(TaskIterator iterator) {
		execute(iterator, false);
	}

	public void execute(TaskIterator iterator, TaskObserver observer) {
		execute(iterator, observer, false);
	}

	public void execute(TaskIterator iterator, boolean synchronous) {
		if (synchronous) {
			synchronousTaskManager.execute(iterator);
		} else {
			dialogTaskManager.execute(iterator);
		}
	}

	public void execute(TaskIterator iterator, TaskObserver observer, boolean synchronous) {
		if (synchronous) {
			synchronousTaskManager.execute(iterator, observer);
		} else {
			dialogTaskManager.execute(iterator, observer);
		}
	}

	public String getNetworkURL() {
		return URI+"network";
	}

	public String getTextMiningURL() {
		return URI+"Textmining";
	}

	public String getEntityQueryURL() {
		return URI+"EntityQuery";
	}

	public String getIntegrationURL() {
		return URI+"Integration";
	}

	public String getResolveURL() {
		return ResolveURI;
	}

	public void info(String info) {
	}

	public void error(String error) {
	}

	public void warn(String warn) {
	}

	public void ignoreAdd() {
		ignore = true;
	}

	public <T> T getService(Class<? extends T> clazz) {
		return registrar.getService(clazz);
	}

	public <T> T getService(Class<? extends T> clazz, String filter) {
		return registrar.getService(clazz, filter);
	}

	public void registerService(Object service, Class<?> clazz, Properties props) {
		registrar.registerService(service, clazz, props);
	}

	public void setVersion(String version) {
		String v = version.replace('.', '_');
		CustomGraphicsManager.CallerIdentity = "string_app_v"+v;
	}

	@Override
	public void handleEvent(NetworkAddedEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}







//*************//

/*
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.work.TaskMonitor;


public interface CustomGraphicsManager {

	//void addCustomGraphicsFactory(CyCustomGraphicsFactory factory, Map props);
	//void removeCustomGraphicsFactory(CyCustomGraphicsFactory factory, Map props);
	CyCustomGraphicsFactory getCustomGraphicsFactory(Class<? extends CyCustomGraphics> cls);
	CyCustomGraphicsFactory getCustomGraphicsFactory(String className);
	Collection<CyCustomGraphicsFactory> getAllCustomGraphicsFactories();
	
	//void addCustomGraphics(CyCustomGraphics cg, URL source);
	
	//Collection<CyCustomGraphics> getAllCustomGraphics();
	//Collection<CyCustomGraphics> getAllCustomGraphics(boolean sorted);
	//Collection<CyCustomGraphics> getAllPersistantCustomGraphics();
	
	CyCustomGraphics getCustomGraphicsByID(Long id);
	CyCustomGraphics getCustomGraphicsBySourceURL(URL source);
	
	//SortedSet<Long> getIDSet();
	
	//Properties getMetadata();
	
	//boolean isUsedInCurrentSession(final CyCustomGraphics graphics);
	//void setUsedInCurrentSession(final CyCustomGraphics graphics, final Boolean isUsed);
	
	//void removeAllCustomGraphics();
	//void removeCustomGraphics(Long id);
	
	/**
	 * Provides id available ID;
	 * @return 
	 
	//Long getNextAvailableID();
	
	void addMissingImageCustomGraphics(UpArrowAppearance cg);
	Collection<UpArrowAppearance> reloadMissingImageCustomGraphics();
	void run(TaskMonitor taskMonitor) throws Exception;
}*/