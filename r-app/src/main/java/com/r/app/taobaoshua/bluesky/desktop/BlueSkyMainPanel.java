/**
 * 
 */
package com.r.app.taobaoshua.bluesky.desktop;

import com.r.app.taobaoshua.bluesky.BlueSky;
import com.r.core.desktop.ctrl.HBasePanel;
import com.r.core.log.Logger;
import com.r.core.log.LoggerFactory;

/**
 * @author oky
 *
 */
public class BlueSkyMainPanel extends HBasePanel {
	private static final long serialVersionUID = -2648127087894579928L;
	private static final Logger logger = LoggerFactory.getLogger(BlueSkyMainPanel.class);
	private static final BlueSky blueSky = BlueSky.getInstance();
	
	public BlueSkyMainPanel() {
		super();
		logger.debug("BlueSkyMainPanel newInstance..........");
		initStyle();
		initComponents();
		initListeners();
	}

	private void initStyle() {
		
	}

	private void initComponents() {
		
	}

	private void initListeners() {
		
	}
}
