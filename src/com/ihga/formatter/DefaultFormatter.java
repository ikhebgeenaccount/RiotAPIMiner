package com.ihga.formatter;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DefaultFormatter implements Formatter {
	private String fileName;
	private File file;
	private FileWriter fileWriter;

	private int writeAmount = 0;

	private String output;
	private int outputCounter;

	/**
	 * Instantiates a new Default com.ihga.formatter.
	 */
	public DefaultFormatter() {

	}

	/**
	 * Instantiates a new Match com.ihga.formatter.
	 *
	 * @param fileName the file name
	 */
	public DefaultFormatter(String fileName) {
		setFile(fileName);
		file = new File(fileName);
		output = "";
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a new Match com.ihga.formatter.
	 *
	 * @param fileName    the file name
	 * @param writeAmount the write amount
	 */
	public DefaultFormatter(String fileName, int writeAmount) {
		this(fileName);
		setWriteAmount(writeAmount);
	}

	@Override
	public void add(JSONObject obj) {
		output += formatJSONObject(obj) + "\n";
		outputCounter++;

		if (outputCounter > writeAmount) {
			try {
				fileWriter.append(output);
				fileWriter.flush();
				output = "";
				outputCounter = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String formatJSONObject(JSONObject obj) {
		return obj.toJSONString();
	}

	@Override
	public void setFile(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void setWriteAmount(int writeAmount) {
		this.writeAmount = writeAmount;
	}
}
