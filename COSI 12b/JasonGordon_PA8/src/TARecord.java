/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * May 9 2021
 * PA8
 * Explanation of the class: This is the storage of each TA's information, and it will hold two arrays,
 * of the line number and of the values, which will have corresponding indicies.
 * Known bugs: N/A
 */

import java.util.*;

public class TARecord {
	ArrayList<String> values;
	ArrayList<Integer> lines;
	String name;
	
	/**
	 * This initializes the TARecord object, more or less adding the first value.
	 */
	
	public TARecord (String name, String value, int line) {
		this.lines = new ArrayList<Integer>();
		this.values = new ArrayList<String>();
		this.name=name;
		addtoRecord(value, line);
	}
	
	/**
	 * This updates the TARecord object, adding values to both arrays.
	 */
	
	public void addtoRecord (String value, int line) {
		lines.add(line);
		values.add(value);
	}
}
	
	


	


