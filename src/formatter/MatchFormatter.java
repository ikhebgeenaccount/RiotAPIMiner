package formatter;

import org.json.simple.JSONObject;

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

	private int writeAmount = 0;

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
		output = "";
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

	private String formatJSONObject(JSONObject obj) {
		String result = "";

		result += obj.get("matchId") + ",";
		result += obj.get("matchVersion") + ",";
		result += obj.get("platformId") + ",";
		result += obj.get("region") + ",";
		result += obj.get("mapId") + ",";
		result += obj.get("matchMode") + ",";
		result += obj.get("matchDuration") + ",";
		result += obj.get("queueType");

		return result;
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
