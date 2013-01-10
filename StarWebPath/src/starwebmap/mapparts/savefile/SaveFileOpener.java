/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts.savefile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Cherubi
 */

public class SaveFileOpener {
	private JFrame responsibleWindow;
	private File file;
	private int amount;
	
	public SaveFileOpener(String fileName, JFrame responsibleWindow) {
		this.file = new File(fileName);
		this.amount = 0;
		this.responsibleWindow = responsibleWindow;
	}
	
	public String readFile() {
		if (!file.exists()) {
			JOptionPane.showMessageDialog(responsibleWindow, "Savefile: " + file.getName() + " did not exist.");
			return "";
		}
		
		String fileContent = "";
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				fileContent += scanner.nextLine() + "\n";
				amount++;
			}
			scanner.close();
		} catch (FileNotFoundException fe) {
			JOptionPane.showMessageDialog(responsibleWindow, "There was a problem opening the file: " + file.getName() + ".");
		}
		
		return fileContent;
	}
	
	public int estimateAmount() {
		return amount;
	}
}
