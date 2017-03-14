package api.data;

import exception.HTTPStatusException;
import exception.UnkownAPIDataObjectTypeException;
import org.json.simple.JSONObject;

/**
 * The type APIDataObjectFactory.
 */
public class APIDataObjectFactory {

	/**
	 * Creates an APIDataObject based on the type specified.
	 * Throws an HTTPStatusException if the HTTP status code is anything else than 200.
	 * Throws an UnkownAPIDataObjectTypeException if the type is not recognized.
	 *
	 * @param obj  the obj
	 * @param type the type
	 * @return the api data object
	 * @throws HTTPStatusException the http riot api status exception
	 */
	public APIDataObject createAPIDataObject(JSONObject obj, String type) throws HTTPStatusException {
		if (((JSONObject)obj.get("status")).get("status_code").equals("200")) {
			switch (type) {
				case "match":
					return new Match(obj);
				default:
					throw new UnkownAPIDataObjectTypeException("The type " + type + " is not recognized as an APIDataObject subclass.");
			}
		} else {
			throw new HTTPStatusException("Riot API gave status code " + ((JSONObject)obj.get("status")).get("status_code") + "with message: " + ((JSONObject)obj.get("status")).get("message"));
		}
	}
}
