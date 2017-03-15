package filter;

import api.data.Match;

import java.util.ArrayList;

/**
 * The type Gamemode filter.
 */
public class GamemodeFilter implements Filter<Match>{

	private ArrayList<String> gamemodes;

	/**
	 * Instantiates a new Gamemode filter.
	 *
	 * @param gamemode the gamemode
	 */
	public GamemodeFilter(String gamemode) {
		this.gamemodes = new ArrayList<>();
	}

	/**
	 * Instantiates a new Gamemode filter.
	 *
	 * @param gamemodes the gamemodes
	 */
	public GamemodeFilter(ArrayList<String> gamemodes) {
		this.gamemodes = gamemodes;
	}

	@Override
	public boolean filter(Match obj) {
		return false;
	}

	@Override
	public ArrayList<Match> filter(ArrayList<Match> objs) {
		return null;
	}
}
