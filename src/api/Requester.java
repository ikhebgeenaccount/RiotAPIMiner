package api;

import api.data.APIDataObject;
import api.data.APIDataObjectFactory;
import exception.HTTPStatusException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Requester.
 * A Requester sends requests to the Riot API and translates those requestes into APIDataObjects.
 */
public class Requester {

	private APIDataObjectFactory factory;
	private RiotAPI api;

	/**
	 * Instantiates a new Requester.
	 */
	public Requester(RiotAPI api) {
		this.factory = new APIDataObjectFactory();
		this.api = api;
	}

	/**
	 * Send a request to the Riot API. Returns the generic type APIDataObject, which can be cast to the correct type.
	 *
	 * @param endpoint the endpoint
	 * @param args     the args
	 * @return the api data object
	 * @throws IOException         the io exception
	 * @throws ParseException      the parse exception
	 * @throws HTTPStatusException the http riot api status exception
	 */
	public APIDataObject request(String endpoint, ArrayList<Integer> args) throws IOException, ParseException, HTTPStatusException {
		String url = api.getEndpointUrl(endpoint, args);

		JSONObject obj = request(new URL(url));

		return factory.createAPIDataObject(obj, endpoint);
	}

	private JSONObject request(URL url) throws IOException, ParseException {
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return (JSONObject) new JSONParser().parse(reader.readLine());
	}
}
