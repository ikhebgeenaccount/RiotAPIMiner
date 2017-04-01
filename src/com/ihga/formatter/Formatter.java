package com.ihga.formatter;

import org.json.simple.JSONObject;

/**
 * The interface Formatter.
 */
public interface Formatter {

	/**
	 * Add an APIDataObject.
	 *
	 * @param obj the obj
	 */
	void add(JSONObject obj);

	String formatJSONObject(JSONObject obj);

	/**
	 * Sets file the data is written to.
	 *
	 * @param fileName the file name
	 */
	void setFile(String fileName);

	/**
	 * Sets the amount of data the Formatter will store before it will write the data to the file.
	 *
	 * @param writeAmount the write amount
	 */
	void setWriteAmount(int writeAmount);
}
