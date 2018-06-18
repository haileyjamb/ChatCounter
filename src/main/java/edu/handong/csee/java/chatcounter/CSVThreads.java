package edu.handong.csee.java.chatcounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;	//CSV apache.commons.csv
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * Class to read CSV files using threads
 *
 */
public class CSVThreads extends DataReader implements Runnable{

	private File filepath = null;
	public HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();
	private ArrayList<String> name = new ArrayList<String>();

	/**
	 *get file and read 
	 */
	public CSVThreads(File file) {
		this.filepath = file;
	}

	public void run() {	//override
		messages = getData(filepath);
	}

	/**
	 * read the file and put it in HashMap
	 */
	public HashMap<String, ArrayList<Message>> getData(File file) {

		try { 

			Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

			for(CSVRecord record : records) {
				
				String time = record.get(0).substring(11, 16);
				String user = record.get(1).trim();
				String messageString = record.get(2).trim();

				if(!messages.containsKey(user)) {
					
					messages.put(user, new ArrayList<Message>());
					messages.get(user).add(new Message(user, time, messageString));
					name.add(user);
				}
				else
					
					messages.get(user).add(new Message(user, time, messageString));
			}
		}catch (Exception e) {
			e.printStackTrace();					
		}

		return messages;
	}

	/**
	 * Method to get the data from "Message"
	 */
	public HashMap<String, ArrayList<Message>> getData(){
		return messages;
	}
}