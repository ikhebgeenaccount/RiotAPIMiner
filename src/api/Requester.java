package api;

import exception.HTTPStatusException;
import exception.RateLimitExceededException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * The type Requester.
 * A Requester sends requests to the Riot API and translates those requestes into APIDataObjects.
 */
public class Requester {

	private RiotAPI api;

	/**
	 * Instantiates a new Requester.
	 */
	public Requester(RiotAPI api) {
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
	public JSONObject request(String endpoint, ArrayList<Long> args) throws ParseException, HTTPStatusException, MalformedURLException, RateLimitExceededException {
		return request(new URL(api.getEndpointUrl(endpoint, args)));
	}

	private JSONObject request(URL url) throws ParseException, HTTPStatusException, RateLimitExceededException {
		HttpsURLConnection connection = null;
		int code = 200;
		try {
			connection = (HttpsURLConnection) url.openConnection();

			code = connection.getResponseCode();

			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			return (JSONObject) new JSONParser().parse(reader.readLine());
		} catch (IOException e) {
			if (code == 429)
				throw new RateLimitExceededException(e.getMessage(), Integer.parseInt(connection.getHeaderField("Retry-After")));
			else
				throw new HTTPStatusException(e.getMessage(), code);
		}
	}
}
