package org.cytoscape.PModel.internal;

import java.net.URL;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;

import org.cytoscape.PModel.internal.CustomGraphicsManager;

public class CustomGraphicsFactory implements CyCustomGraphicsFactory<ImageAnno> {
	CustomGraphicsManager manager;

	public CustomGraphicsFactory(CustomGraphicsManager manager) {
		this.manager = manager;
	}

	public CyCustomGraphics<ImageAnno> getInstance(String input) {
		return new CustomGraphics(manager, input);
	}

	public CyCustomGraphics<ImageAnno> getInstance(URL url) { return null; }

	public Class<? extends CyCustomGraphics> getSupportedClass() {return CustomGraphics.class;}

	public CyCustomGraphics<ImageAnno> parseSerializableString(String string) { return null; }

	public boolean supportsMime(String mimeType) { return false; }

	public String getPrefix() { return "string"; }

}












//**************************************************//



/*
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;

public class CustomGraphicsFactory implements CyCustomGraphicsFactory {

	
    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public boolean supportsMime(String mimeType) {
        return false;
    }

    @Override
    public CustomGraphics getInstance(URL url) {
        
    	url =  getClass().getClassLoader().getResource("/Green_Arrow_small.png");
    	CustomGraphics myCustomGraphics = new CustomGraphics(url);
        return myCustomGraphics;
    }

    @Override
    public CyCustomGraphics getInstance(String input) {
        return null;
    }

    @Override
    public CyCustomGraphics parseSerializableString(String string) {
        return null;
    }
    
    @Override
    public Class<? extends CyCustomGraphics> getSupportedClass() {
        return null;
    }
}
	   
	/*
	
	@Override
	    public String getPrefix() {
	        return null;
	    }

	    @Override
	    public boolean supportsMime(String mimeType) {
	        return false;
	    }

	    @Override
	    public CustomGraphics getInstance(URL url) {
	        CustomGraphics myCustomGraphics = new CustomGraphics(url);
	        return myCustomGraphics;
	    }

	    @Override
	    public CyCustomGraphics getInstance(String input) {
	        return null;
	    }

	    @Override
	    public CyCustomGraphics parseSerializableString(String stringy) {
	    	final String imageName = "green";
			final String sourceURL = "/Green_Arrow_small.png";
			
			// Try using the URL first
			if (sourceURL != null) {
				try {
					URL url = new URL(sourceURL);
					//CyCustomGraphics cg = manager.getCustomGraphicsBySourceURL(url);
					//cg.setDisplayName(entry[1]);
					//return cg;
				} catch (Exception e) {
					// This just means that "sourceURL" is malformed.  That may be OK.
				}
			}
	    
	    @Override
	    public Class<? extends CyCustomGraphics> getSupportedClass() {
	        return null;
	    }
	}*/

	
	
	
	
	
	
	/*
	private static final Class<? extends CyCustomGraphics> TARGET_CLASS = (Class<? extends CyCustomGraphics>) CustomGraphics.class;
	private String entry[];

	private final CustomGraphicsManager manager;
	
	public CustomGraphicsFactory(final CustomGraphicsManager manager) {
		this.manager = manager;
	}

	@Override
	public String getPrefix() { return "image"; }

	@Override
	public boolean supportsMime(String mimeType) {
		if (mimeType.equals("image/bmp"))
			return true;
		if (mimeType.equals("image/x-windows-bmp"))
			return true;
		if (mimeType.equals("image/gif"))
			return true;
		if (mimeType.equals("image/jpeg"))
			return true;
		if (mimeType.equals("image/png"))
			return true;
		if (mimeType.equals("image/vnd.wap.wbmp"))
			return true;
		return false;
	}
	
	/**
	 * Generate Custom Graphics object from a string.
	 * 
	 * <p>
	 * There are two types of valid string:
	 * <ul>
	 * <li>Image URL only - This will be used in Passthrough mapper.
	 * <li>Output of toSerializableString method of URLImageCustomGraphics
	 * </ul>
	 * 
	 
	public CyCustomGraphics parseSerializableString(String entryStr) {
		// Check this is URL or not
		if (entryStr == null) return null;
		if (!validate(entryStr)) return null;

		final String imageName = entry[0];
		final String sourceURL = entry[1];
		
		// Try using the URL first
		if (sourceURL != null) {
			try {
				URL url = new URL(sourceURL);
				CyCustomGraphics cg = manager.getCustomGraphicsBySourceURL(url);
				cg.setDisplayName(entry[1]);
				return cg;
			} catch (Exception e) {
				// This just means that "sourceURL" is malformed.  That may be OK.
			}
		}
		
		final Long imageId = Long.parseLong(imageName);
		CyCustomGraphics cg = manager.getCustomGraphicsByID(imageId);
		
		if (cg == null) {
			// Can't find image, maybe because it has not been added to the manager yet,
			// so create a special "missing image" graphics that stores the original raw value.
			// Cytoscape can then try to reload this missing custom graphics later.
			/*try 
			{
				cg = new MissingImageCustomGraphics(entryStr, imageId, sourceURL, this);
				manager.addMissingImageCustomGraphics((MissingImageCustomGraphics)cg);
			} 
			catch (IOException e) 
			{
			}
		}
		
		cg.setDisplayName(entry[1]);
		
		return cg;
	}

	public CyCustomGraphics getInstance(URL url) {
		return getInstance(url.toString());
	}

	public CyCustomGraphics getInstance(String input) {
		try {
			URL url = new URL(input);
			CyCustomGraphics cg = manager.getCustomGraphicsBySourceURL(url);
			if(cg == null) {
				//Long id = manager.getNextAvailableID();
				//cg = new CustomGraphics(id, input);
				//manager.addCustomGraphics(cg, url);
			}
			return cg;
		} catch(IOException e) {
			return null;
		}
	}

	public Class<? extends CyCustomGraphics> getSupportedClass() { return TARGET_CLASS; }

	private boolean validate(final String entryStr) {
		entry = entryStr.split(",");
		if (entry == null || entry.length < 2) {
			return false;
		}
		return true;
	}
}
	
	*/
	
	
	
	
	
	
	
	
	
	/*
	
	private static final Class<? extends CyCustomGraphics> TARGET_CLASS = CustomGraphics.class;
	//"/Green_Arrow_small.png"
	final String uparrow = "/Green_Arrow_small.png";
	private CyApplicationManager appMgr;
	
	public CustomGraphicsFactory(CyApplicationManager appMgr){
		this.appMgr = appMgr;
	}
	
	
	
	@Override
	public CyCustomGraphics<ImageAnnotationNode> getInstance(URL uparrow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CyCustomGraphics<ImageAnnotationNode> getInstance(String uparrow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPrefix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getSupportedClass() {
		// TODO Auto-generated method stub
		return TARGET_CLASS;
	}

	@Override
	public CyCustomGraphics parseSerializableString(String entryStr) {
		final String uparrow = "/Green_Arrow_small.png";
		
		
		// Check this is URL or not
		if (entryStr == null) return null;

		try {
			URL url = new URL(uparrow);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean supportsMime(String mimeType) {
		// TODO Auto-generated method stub
		
		if (mimeType.equals("image/bmp"))
			return true;
		if (mimeType.equals("image/x-windows-bmp"))
			return true;
		if (mimeType.equals("image/gif"))
			return true;
		if (mimeType.equals("image/jpeg"))
			return true;
		if (mimeType.equals("image/png"))
			return true;
		if (mimeType.equals("image/vnd.wap.wbmp"))
			return true;
		return false;
	}

//	@Override
	//public TaskIterator createTaskIterator() {
		//return new TaskIterator(new CustomGraphics(this.appMgr.getCurrentNetwork()));*/
