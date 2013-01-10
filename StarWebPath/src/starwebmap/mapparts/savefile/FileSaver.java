/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts.savefile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Cherubi
 */

public class FileSaver {
	private JFrame responsibleWindow;
	
	public FileSaver(JFrame responsibleWindow) {
		this.responsibleWindow = responsibleWindow;
	}
	
	public void saveFile(String fileName, String data) {
		if (data.length() == 0) {
			JOptionPane.showMessageDialog(responsibleWindow, "There was no data to save in file: " + fileName + ".");
			return;
		}
		
		File file = new File(fileName);
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(data);
			writer.close();
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(responsibleWindow, "There was a problem saving the file: " + fileName + ".");
		}
	}
}
