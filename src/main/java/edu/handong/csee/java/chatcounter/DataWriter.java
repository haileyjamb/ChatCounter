package edu.handong.csee.java.chatcounter;

import java.io.File;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.IOException;

/**
 * DataWriter is a class that writes a file
 */
public class DataWriter {
	/**
	 * writeAFile method writes a file when it gets one
	 */
	public void writeAFile(ArrayList<String> lines, String targetFileName) {
		try {
			File files = new File(targetFileName);
			FileOutputStream fos = new FileOutputStream(files);
			DataOutputStream dos = new DataOutputStream(fos);

			for(String line:lines) {
				dos.write((line+"\n").getBytes());
			}
			dos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
