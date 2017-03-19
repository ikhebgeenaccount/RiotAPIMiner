package formatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;

public class MatchListFormatter extends DefaultFormatter {

	public MatchListFormatter(String s) {
		super(s);
	}

	@Override
	public String formatJSONObject(JSONObject obj) {
		JSONArray matches = (JSONArray)obj.get("matches");
		Iterator<JSONObject> iter = matches.iterator();

		String res = "";

		while(iter.hasNext()) {
			JSONObject j = iter.next();
			res += j.get("champion") + "," + j.get("season") + "," + j.get("matchId") + "," + j.get("queue") + "," + j.get("lane") + "," + j.get("role") + "\n";
		}

		return res;
	}
}
