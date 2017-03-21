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
		result += obj.get("season") + ",";
		result += obj.get("queueType") + ",";
		result += obj.get("mapId") + ",";
		result += obj.get("matchMode") + ",";
		result += obj.get("matchType") + ",";
		result += obj.get("matchDuration") + ",";

		HashMap<Long, Long> participantsumm = new HashMap<>();

		JSONArray participantIds = (JSONArray)obj.get("participantIdentities");

		boolean sumIds = true;

		// Only ranked games provide summoner ids, so check if they exist and set sumIds to false if they don't
		for (int i = 0; i < participantIds.size(); i++) {
			long id = (Long)((JSONObject)participantIds.get(i)).get("participantId");
			try {
				Long pid = (Long) ((JSONObject) ((JSONObject) participantIds.get(i)).get("player")).get("summonerId");

				participantsumm.put(id, pid);
			} catch(NullPointerException e) {
				sumIds = false;
				break;
			}
		}

		JSONArray participants = (JSONArray)obj.get("participants");

		String allps = "[";

		for (int i = 0; i < participants.size(); i++) {
			JSONObject participant = (JSONObject)participants.get(i);
			String ps = "{";

			// Summoner id, if found add them
			if (sumIds)
				ps += participantsumm.get(participant.get("participantId")) + ",";
			// Champion id
			ps += participant.get("championId") + ",";
			// Team id
			ps += participant.get("teamId") + ",";
			// Winner
			ps += ((JSONObject)participant.get("stats")).get("winner") + ",";
			// Summoner spells id
			ps += participant.get("spell1Id") + "," + participant.get("spell2Id") + ",";
			// Kills, deaths, assists
			ps += ((JSONObject)participant.get("stats")).get("kills") + "," + ((JSONObject)participant.get("stats")).get("deaths") + "," + ((JSONObject)participant.get("stats")).get("assists") + ",";
			// Total cs number
			ps += (((Long)((JSONObject)participant.get("stats")).get("minionsKilled")) + (Long)((JSONObject)participant.get("stats")).get("neutralMinionsKilled"));

			allps += ps + "}" + (i < participants.size() - 1 ? "," : "");
		}

		allps += "]";

		result += allps;

		return result;
	}
}
