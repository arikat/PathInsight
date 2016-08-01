package org.cytoscape.PModel.internal;

import java.lang.annotation.Annotation;
import java.net.URLEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cytoscape.model.CyNetwork;
import org.cytoscape.view.model.CyNetworkView;

public class StringNetwork {
	final CustomGraphicsManager manager;
	CyNetwork network;
	Map<String, List<String>> resolvedIdMap = null;
	Map<String, List<Annotation>> annotations = null;

	public StringNetwork(CustomGraphicsManager manager) {
		this.manager = manager;
		resolvedIdMap = null;
		annotations = null;
	}

	public void reset() {
		resolvedIdMap = null;
		annotations = null;
	}

	public CustomGraphicsManager getManager() { return manager; }

	public CyNetwork getNetwork() { return network; }

	public void setNetwork(CyNetwork network) {
		this.network = network;
	}

	public Map<String, List<Annotation>> getAnnotations() { return annotations; }

	public Map<String, List<Annotation>> getAnnotations(int taxon, final String terms) {
		String encTerms;
		try {
			encTerms = URLEncoder.encode(terms.trim(), "UTF-8");
		} catch (Exception e) {
			return new HashMap<String, List<Annotation>>();
		}

		String url = manager.getResolveURL()+"json/resolveList";
		Map<String, String> args = new HashMap<>();
		args.put("species", Integer.toString(taxon));
		args.put("identifiers", encTerms);
		args.put("caller_identity", CustomGraphicsManager.CallerIdentity);
		System.out.println("URL: "+url+"?species="+Integer.toString(taxon)+"&caller_identity="+CustomGraphicsManager.CallerIdentity+"&identifiers="+encTerms);
		// Get the results
		return annotations;
	}

	
	/*
	 * Maintenance of the resolveIdMap
	 */
	public boolean resolveAnnotations() {
		if (resolvedIdMap == null) resolvedIdMap = new HashMap<>();
		boolean noAmbiguity = true;
		for (String key: annotations.keySet()) {
			if (annotations.get(key).size() > 1) {
				noAmbiguity = false;
				break;
			} else {
				List<String> ids = new ArrayList<String>();
				resolvedIdMap.put(key, ids);
			}
		}

		// Now trim the key set
		if (resolvedIdMap.size() > 0) {
			for (String key: resolvedIdMap.keySet()) {
				if (annotations.containsKey(key))
					annotations.remove(key);
			}
		}
		return noAmbiguity;
	}

	public void addResolvedStringID(String term, String id) {
		if (!resolvedIdMap.containsKey(term))
			resolvedIdMap.put(term, new ArrayList<String>());
		resolvedIdMap.get(term).add(id);
	}

	public void removeResolvedStringID(String term, String id) {
		if (!resolvedIdMap.containsKey(term))
			return;
		List<String> ids = resolvedIdMap.get(term);
		ids.remove(id);
		if (ids.size() == 0) 
			resolvedIdMap.remove(term);
	}

	public boolean haveResolvedNames() {
		if (resolvedIdMap == null)
			return true;

		for (String key: annotations.keySet()) {
			if (!resolvedIdMap.containsKey(key) || resolvedIdMap.get(key).size() == 0) {
				return false;
			}
		}
		return true;
	}

	public int getResolvedTerms() { return resolvedIdMap.size(); }

	public List<String> combineIds(Map<String, String> reverseMap) {
		List<String> ids = new ArrayList<>();
		for (String term: resolvedIdMap.keySet()) {
			for (String id: resolvedIdMap.get(term)) {
				ids.add(id);
				reverseMap.put(id, term);
			}
		}
		return ids;
	}
}