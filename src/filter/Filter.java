package filter;

import org.json.simple.JSONObject;

/**
 * The interface Filter.
 */
public interface Filter {

	/**
	 * Returns true if obj passes the Filter, false otherwise.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
	boolean filter(JSONObject obj);

//	/**
//	 * Filters the ArrayList, returns an ArrayList with just the items that pass the Filter.
//	 *
//	 * @param objs the objs
//	 * @return the array list
//	 */
//	ArrayList<JSONObject> filter(ArrayList<JSONObject> objs);
}
