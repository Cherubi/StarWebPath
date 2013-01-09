/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Cherubi
 */

public class MouseMotionTimer extends Timer implements ActionListener {
	private MapPanel mapPanel;
	
	public MouseMotionTimer(int time, MapPanel mapPanel) {
		super(time, null);
		super.addActionListener(this);
		this.mapPanel = mapPanel;
		
		this.setDelay(time);
		this.setRepeats(true);
		this.start();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		mapPanel.updateMouseCoordinates();
	}
}
