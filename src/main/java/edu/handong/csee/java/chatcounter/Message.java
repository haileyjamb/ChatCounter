package edu.handong.csee.java.chatcounter;
/**
 * 
 * Message class worksn as a struct in C programming lang. 
 *
 */
public class Message {
	String name;
	String time;
	String line;
	
	/**
	 * gets parameters "n","t",and "l" and store them as "name", "time", and "line" string variables
	 * @param n
	 * @param t
	 * @param l
	 */
	public Message(String n, String t, String l) {
	
		this.name = n;
		this.time = t;
		this.line = l;
	}
	
	/**
	 * getName method gets "name" string
	 * @return
	 */
	public String getName() {
		
		return name;
	}

	/**
	 * setName method store "name" string
	 * @param name
	 */
	public void setName(String name) {
		
		this.name = name;
	}

	/**
	 * getTime method gets "time" string
	 * @return
	 */
	public String getTime() {
		
		return time;
	}

	/**
	 * setTime method stores "time" string
	 * @return
	 */
	public void setTime(String time) {
		
		this.time = time;
	}

	/**
	 * getLine method gets "line" string
	 * @return
	 */
	public String getLine() {
		
		return line;
	}

	/**
	 * setLine method stores "line" string
	 * @return
	 */
	public void setLine(String line) {
		
		this.line = line;
	}
	
}
