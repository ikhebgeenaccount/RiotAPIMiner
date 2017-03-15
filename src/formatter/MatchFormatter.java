package formatter;

import api.data.APIDataObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Match formatter.
 */
public class MatchFormatter implements Formatter {

	private String fileName;
	private File file;
	private FileWriter fileWriter;

	private int writeAmount;

	private String output;
	private int outputCounter;

	/**
	 * Instantiates a new Match formatter.
	 *
	 * @param fileName the file name
	 */
	public MatchFormatter(String fileName) {
		setFile(fileName);
		file = new File(fileName);
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a new Match formatter.
	 *
	 * @param fileName    the file name
	 * @param writeAmount the write amount
	 */
	public MatchFormatter(String fileName, int writeAmount) {
		this(fileName);
		setWriteAmount(writeAmount);
	}

	@Override
	public void add(APIDataObject obj) {
		output += formatAPIDataObject(obj);
		outputCounter++;

		if (outputCounter > writeAmount) {
			try {
				fileWriter.append(output);
				output = "";
				outputCounter = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String formatAPIDataObject(APIDataObject obj) {
		return null;
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
