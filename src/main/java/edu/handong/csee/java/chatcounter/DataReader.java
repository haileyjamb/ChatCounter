package edu.handong.csee.java.chatcounter;

import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DataReader class gets parameters and read files, then according to file name(txt,csv)
 * cut the string --> put in the MESSAGE list, 
 * if it's not redundant, then put it in. --> then, return all the messages
 */
public class DataReader {

	HashMap<String, ArrayList<Message>> messages = new HashMap<String, ArrayList<Message>>();
	HashMap<String, Integer> idxcounter = new HashMap<String,Integer>();
	/**
	 * Make a directory and get and read files 
	 */
	public HashMap<String, ArrayList<Message>> getData(String strDir){

		File myDirectory = getDirectory(strDir);
		File[] files = getListOfFilesFromDirectory(myDirectory);
		for(File f:files)			//
		{
			System.out.println("-------"+f.getName()+"-------");
			if(f.getName().endsWith(".txt")) 
				messages = readFilesTXT(files);
			else if(f.getName().endsWith(".csv")){
				messages = readFilesCSV(files);	
			}
		}	//wanna make this to thread reading csv

		return messages;
	}

	private File getDirectory(String Directory) {

		File myDirectory = new File(Directory);
		return myDirectory;
	}

	private File[] getListOfFilesFromDirectory(File dataDir) {

		for(File file:dataDir.listFiles()) {
			//System.out.println(file.getAbsolutePath());
		}
		return dataDir.listFiles();
	}	//only read txt files

	private HashMap<String, ArrayList<Message>> readFilesTXT(File[] dataDir){

		for(int i=0;i<dataDir.length;i++)
		{
			String fileName = dataDir[i].getAbsolutePath();
			Scanner inputStream = null;
			try {
				inputStream = new Scanner(new File(fileName),"UTF-8");
			}  catch (FileNotFoundException e) {
				System.out.println ("Error opening the file " + fileName);
				System.exit (0);
			}

			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				String pattern ="\\[(.+)\\]\\s\\[..\\s([0-9]+:[0-9]+)\\]\\s(.+)";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);

				String name = "";
				String time = "";
				String strMessage = "";

				if(m.find()) {
					name = m.group(1);
					time = m.group(2);
					strMessage = m.group(3);

					Message msg = new Message(name,time,strMessage);
					ArrayList<Message> msgList = messages.get(name);
					//System.out.println("name: "+msg.name+", time: "+msg.time+", \n\""+msg.line+"\"");
					if(msgList == null)
					{
						msgList = new ArrayList<Message>();
						msgList.add(msg);
						messages.put(name,msgList);
						idxcounter.put(name, 0);
					}
					else
					{
						if(!msgList.contains(msg)) {
							System.out.println("Previous: "+msgList.get(idxcounter.get(name)).name+", "+ msgList.get(idxcounter.get(name)).time+", "+msgList.get(idxcounter.get(name)).line);
							System.out.println("Current: "+msg.name+", "+ msg.time+", "+msg.line);
							if((msgList.get(idxcounter.get(name)).time.equals(msg.time))&&(msgList.get(idxcounter.get(name)).line.equals(msg.line))){
								System.out.println("Has the same contents :"+msg.name+", "+msg.time+", "+msg.line);
							}
							else {
								msgList.add(msg); 
								idxcounter.put(name, idxcounter.get(name)+1);
								System.out.println("confirmed, "+name+idxcounter.get(name));
							}
						}
					}
				}
			}
			inputStream.close ();
			System.out.println("End of the file");
		}
		return messages;
	}
	private HashMap<String, ArrayList<Message>> readFilesCSV(File[] dataDir){

		for(int i=0;i<dataDir.length;i++)
		{
			String fileName = dataDir[i].getAbsolutePath();
			Scanner inputStream = null;
			try {
				inputStream = new Scanner(new File(fileName),"UTF-8");
			}  catch (FileNotFoundException e) {
				System.out.println ("Error opening the file " + fileName);
				System.exit (0);
			}

			while (inputStream.hasNextLine()) {
				String line = inputStream.nextLine();
				String pattern ="([0-9]+)\\-([0-9]+)\\-([0-9]+)\\s([0-9]+)\\:([0-9]+)\\:([0-9]+)\\,\\\"(.+)\\\"\\,\\\"(.+)\\\"";
				Pattern r = Pattern.compile(pattern);
				Matcher m = r.matcher(line);

				String name = "";
				String time = "";
				String strMessage = "";

				if(m.find()) {
					name = m.group(7);
					time = m.group(4)+":"+m.group(5)+":"+m.group(6);
					strMessage = m.group(8);

					Message msg = new Message(name,time,strMessage);
					ArrayList<Message> msgList = messages.get(name);
					System.out.println("name: "+msg.name+", time: "+msg.time+", \n\""+msg.line+"\"");
					if(msgList == null)
					{
						System.out.println("new msgList");
						msgList = new ArrayList<Message>();
						msgList.add(msg);
						messages.put(name,msgList);
						idxcounter.put(name, 0);
					}
					else
					{
						if(!msgList.contains(msg)) {
							System.out.println("Previous: "+msgList.get(
									idxcounter.get(name)).name+", "+ msgList.get(idxcounter.get(name)).time+", "+ msgList.get(idxcounter.get(name)).line);
							System.out.println("Current: "+msg.name+", "+ msg.time+", "+msg.line);
							if((msgList.get(idxcounter.get(name)).time.equals(msg.time))&&(msgList.get(idxcounter.get(name)).line.equals(msg.line))){
								System.out.println("Has the same contents :"+msg.name+", "+msg.time+", "+msg.line);
							}
							else {
								msgList.add(msg); 
								idxcounter.put(name, idxcounter.get(name)+1);
								System.out.println("confirmed, "+name+idxcounter.get(name));
							}
						}
					}
				}
			}
			System.out.println("End of the file");
			inputStream.close ();
		}
		return messages;

	}

}
