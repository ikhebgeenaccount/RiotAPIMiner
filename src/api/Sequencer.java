package api;

import api.data.APIDataObject;
import exception.HTTPStatusException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Sequencer.
 */
public class Sequencer {

	private Requester requester;
	private String endpoint;
	private ArrayList<Integer> args;

	private int requestCap = 0;
	private int amountOfRequests = 0;

	/**
	 * Instantiates a new Sequencer.
	 * On default there is not cap to the amount of requests a sequencer can do.
	 *
	 * @param requester the requester
	 * @param endpoint  the endpoint
	 * @param startArgs the start args
	 */
	public Sequencer(Requester requester, String endpoint, ArrayList<Integer> startArgs) {
		setRequester(requester);
		setEndpoint(endpoint);
		setArgs(startArgs);
	}

	/**
	 * Returns the next APIDataObject in this Sequencer.
	 * Returns null if the Sequencer has ended.
	 *
	 * @return the api data object
	 */
	public APIDataObject next() throws HTTPStatusException {
		// If a requestcap is set and they are reached, return null to indicate end of Sequencer.
		if ((requestCap != 0 && amountOfRequests > requestCap))
			return null;

		for (int i = 0; i < args.size(); i++) {
			args.set(i, args.get(i) + 1);
		}

		try {
			amountOfRequests++;
			return requester.request(endpoint, args);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets requester.
	 *
	 * @return the requester
	 */
	public Requester getRequester() {
		return requester;
	}

	/**
	 * Sets requester.
	 *
	 * @param requester the requester
	 */
	public void setRequester(Requester requester) {
		this.requester = requester;
	}

	/**
	 * Gets args.
	 *
	 * @return the args
	 */
	public ArrayList<Integer> getArgs() {
		return args;
	}

	/**
	 * Sets args.
	 *
	 * @param args the args
	 */
	public void setArgs(ArrayList<Integer> args) {
		this.args = args;
	}

	/**
	 * Gets request cap.
	 *
	 * @return the request cap
	 */
	public int getRequestCap() {
		return requestCap;
	}

	/**
	 * Sets request cap.
	 * Setting it to 0 disables the request cap.
	 *
	 * @param requestCap the request cap
	 */
	public void setRequestCap(int requestCap) {
		this.requestCap = requestCap;
	}

	/**
	 * Gets amount of requests.
	 *
	 * @return the amount of requests
	 */
	public int getAmountOfRequests() {
		return amountOfRequests;
	}

	/**
	 * Gets endpoint.
	 *
	 * @return the endpoint
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * Sets endpoint.
	 *
	 * @param endpoint the endpoint
	 */
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
