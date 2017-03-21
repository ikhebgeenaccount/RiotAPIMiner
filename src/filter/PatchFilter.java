package filter;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * The type Patch filter.
 */
public class PatchFilter implements Filter {

	private ArrayList<String> patches;

	/**
	 * Instantiates a new Gamemode filter.
	 */
	public PatchFilter() {
		this.patches = new ArrayList<>();
	}

	/**
	 * Instantiates a new Gamemode filter.
	 *
	 * @param patch the patch
	 */
	public PatchFilter(String patch) {
		this();
		patches.add(patch);
	}

	/**
	 * Instantiates a new Gamemode filter.
	 *
	 * @param patches the patches
	 */
	public PatchFilter(ArrayList<String> patches) {
		this.patches = patches;
	}

	@Override
	public boolean filter(JSONObject obj) {
		boolean found = false;

		for (String patch : patches) {
			if (((String)obj.get("season")).contains(patch)) {
				found = true;
				break;
			}
		}

		return found;
	}

	/**
	 * Add gamemode.
	 *
	 * @param patch the patch
	 * @return the season filter
	 */
	public PatchFilter addPatch(String patch) {
		patches.add(patch);
		return this;
	}
}
