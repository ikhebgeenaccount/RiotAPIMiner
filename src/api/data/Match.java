package api.data;

import org.json.simple.JSONObject;

public class Match extends APIDataObject{

	private long matchId;

	public Match(JSONObject matchJSON) {
		this.matchId = (long)matchJSON.get("matchId");
	}

	public long getMatchId() {
		return matchId;
	}
}
