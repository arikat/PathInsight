package org.cytoscape.PModel.internal;

import java.net.URL;

import org.cytoscape.view.presentation.customgraphics.CyCustomGraphics;
import org.cytoscape.view.presentation.customgraphics.CyCustomGraphicsFactory;
import org.cytoscape.work.AbstractTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.view.presentation.customgraphics.CustomGraphicLayer;

public class CustomGraphicsFactory extends AbstractTaskFactory implements CyCustomGraphicsFactory {

	private static final Class<? extends CyCustomGraphics> TARGET_CLASS = CustomGraphics.class;
	//"/Green_Arrow_small.png"
	String uparrow = "/Green_Arrow_small.png";
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
	public CyCustomGraphics parseSerializableString(String arg0) {
		// TODO Auto-generated method stub
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

	@Override
	public TaskIterator createTaskIterator() {
		return new TaskIterator(new CustomGraphics(this.appMgr.getCurrentNetwork()));
	}

}
