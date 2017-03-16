package filter;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * The type Gamemode filter.
 */
public class GamemodeFilter implements Filter {

	private ArrayList<String> gamemodes;

	/**
	 * Instantiates a new Gamemode filter.
	 */
	public GamemodeFilter() {
		this.gamemodes = new ArrayList<>();
	}

	/**
	 * Instantiates a new Gamemode filter.
	 *
	 * @param gamemode the gamemode
	 */
	public GamemodeFilter(String gamemode) {
		this();
		gamemodes.add(gamemode);
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
	public boolean filter(JSONObject obj) {
		boolean found = false;

		for (String gamemode : gamemodes) {
			if (obj.get("queueType").equals(gamemode)) {
				found = true;
				break;
			}
		}

		return found;
	}

	/**
	 * Add gamemode.
	 *
	 * @param gamemode the gamemode
	 */
	public void addGamemode(String gamemode) {
		gamemodes.add(gamemode);
	}
}
