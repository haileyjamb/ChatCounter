package edu.handong.csee.java.chatcounter;

import java.util.*;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * ChatCounter class has main method. It works with DataReader and DataWriter classes
 */
public class ChatCounter {

	String inputPath;
	String outputPath;
	boolean help;

	/**
	 * main method gets command line parameters and give to DataReader
	 * then, give what is given by DataReader to DataWriter as an arraylist
	 */
	public static void main(String[] args) {

		ChatCounter myChatCounter = new ChatCounter();
		myChatCounter.run(args);
	}

	private void run(String[] args) {
		Options options = createOptions();

		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}

			DataReader dataReader = new DataReader();
			
			HashMap<String, ArrayList<Message>> messages = dataReader.getData(inputPath);
			for(String key:messages.keySet())
			{
				for(Message msg:messages.get(key))
				{
					System.out.println(msg.name+", "+msg.time+", "+msg.line);
				}
			}
			HashMap<String,Integer> counter = countMessgaes(messages);

			List<String> ids = sortByValue(counter);

			ArrayList<String> linesToWrite = new ArrayList<String>();
			
			int i=0;
			for(String key:ids) {
				linesToWrite.add(key+","+counter.get(key));
			}
			DataWriter writer = new DataWriter();
			writer.writeAFile(linesToWrite, outputPath);
			System.out.println("Program is terminated.");

		}
	}

	private boolean parseOptions(Options options, String[] args) {
		
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			outputPath = cmd.getOptionValue("o");
			inputPath = cmd.getOptionValue("i");
			help = cmd.hasOption("h");

		} catch (Exception e) {	//Exception --> '0'
			printHelp(options);
			return false;
		}

		return true;
	}

	private Option createOptions() {
		Option options = new Options();

		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set a path of a directory or a file to display")
				.hasArg()
				.argName("a directory path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("a file path name where the count results are saved")
				.hasArg()    
				.argName("a file path")
				.required() 
				.build());

		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}

	private void printHelp(Options options) {	//has to print out the help statement

		HelpFormatter formatter = new HelpFormatter();
		String header = "CLI test program";
		String footer ="\nPlease report issues at https://github.com/lifove/CLIExample/issues";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}
	private List<String> sortByValue(HashMap<String,Integer> k) {
		List<String> q = new ArrayList();
		q.addAll(k.keySet());

		Collections.sort(q,new Comparator<Object>(){
			public int compare(Object o1, Object o2) {
				Object v1 = k.get(o1);
				Object v2 = k.get(o2);
				return (((Comparable<Object>) v2).compareTo(v1));
			}
		});
		return q;
	}

	private HashMap<String,Integer> countMessgaes(HashMap<String, ArrayList<Message>> messages) {
		HashMap<String, Integer> counter = new HashMap<String, Integer>();
		for(String key:messages.keySet())
		{
			counter.put(key, messages.get(key).size());
		}
		return counter;
	}
}
