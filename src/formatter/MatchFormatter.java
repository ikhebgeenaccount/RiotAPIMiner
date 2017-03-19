package formatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * The type Match formatter.
 */
public class MatchFormatter extends DefaultFormatter {

	/**
	 * Instantiates a new Match formatter.
	 *
	 * @param fileName the file name
	 */
	public MatchFormatter(String fileName) {
		super(fileName);
	}

	/**
	 * Instantiates a new Match formatter.
	 *
	 * @param fileName    the file name
	 * @param writeAmount the write amount
	 */
	public MatchFormatter(String fileName, int writeAmount) {
		super(fileName, writeAmount);
	}

	@Override
	public String formatJSONObject(JSONObject obj) {
		String result = "";

		result += obj.get("matchId") + ",";
		result += obj.get("matchVersion") + ",";
		result += obj.get("region") + ",";
		result += obj.get("queueType") + ",";
		result += obj.get("mapId") + ",";
		result += obj.get("matchMode") + ",";
		result += obj.get("matchDuration") + ",";

		HashMap<Integer, String> participantsumm = new HashMap<>();

		JSONArray participantIds = (JSONArray)obj.get("participantIdentities");

		for (int i = 0; i < participantIds.size(); i++) {
			int id = (Integer)((JSONObject)participantIds.get(i)).get("participantId");
			String pid = (String)((JSONObject)((JSONObject)participantIds.get(i)).get("player")).get("summonerId");

			participantsumm.put(id, pid);
		}

		JSONArray participants = (JSONArray)obj.get("participants");

		String allps = "[";

		for (int i = 0; i < participants.size(); i++) {
			JSONObject participant = (JSONObject)participants.get(i);
			String ps = "{";

			// Summoner id
			ps += participantsumm.get(participant.get("participantId")) + ",";


			allps += ps + (i < participants.size() - 1 ? "," : "");
		}

		allps += "]";

		result += allps;

		return result;
	}
}
