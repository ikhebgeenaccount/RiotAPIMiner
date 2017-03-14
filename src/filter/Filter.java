package filter;

import api.data.APIDataObject;

import java.util.ArrayList;

/**
 * The interface Filter.
 *
 * @param <T> the type parameter
 */
public interface Filter<T extends APIDataObject> {

	/**
	 * True if obj passes the Filter, false otherwise.
	 *
	 * @param obj the obj
	 * @return the boolean
	 */
	boolean filter(T obj);

	/**
	 * Filters the ArrayList, returns an ArrayList with just the items that pass the Filter.
	 *
	 * @param objs the objs
	 * @return the array list
	 */
	ArrayList<T> filter(ArrayList<T> objs);
}
