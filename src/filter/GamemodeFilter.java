package filter;

import api.data.Match;

import java.util.ArrayList;

public class GamemodeFilter implements Filter<Match>{

	@Override
	public boolean filter(Match obj) {
		return false;
	}

	@Override
	public ArrayList<Match> filter(ArrayList<Match> objs) {
		return null;
	}
}
