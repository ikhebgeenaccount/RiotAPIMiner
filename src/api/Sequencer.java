package api;

import api.data.APIDataObject;
import exception.HTTPStatusException;
import exception.RateLimitExceededException;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * The type Sequencer.
 */
public class Sequencer {

	private Requester requester;
	private String endpoint;
	private ArrayList<Long> args;

	private int requestCap = 0;
	private int amountOfRequests = 0;
	private int nextCounter = 0;

	/**
	 * Instantiates a new Sequencer.
	 * On default there is not cap to the amount of requests a sequencer can do.
	 *
	 * @param requester the requester
	 * @param endpoint  the endpoint
	 * @param startArgs the start args
	 */
	public Sequencer(Requester requester, String endpoint, ArrayList<Long> startArgs) {
		setRequester(requester);
		setEndpoint(endpoint);
		setArgs(startArgs);
	}

	/**
	 * Returns the next APIDataObject in this Sequencer.
	 * Returns null if the Sequencer has ended.
	 *
	 * @return the api data object
	 * @throws HTTPStatusException the http status exception
	 */
	public APIDataObject next() throws ParseException, HTTPStatusException, MalformedURLException, RateLimitExceededException {
		nextCounter++;
		// If a requestcap is set and they are reached, return null to indicate end of Sequencer.
		if ((requestCap != 0 && amountOfRequests > requestCap))
			return null;

		for (int i = 0; i < args.size(); i++) {
			args.set(i, args.get(i) + 1);
		}

		amountOfRequests++;
		return requester.request(endpoint, args);
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
	public ArrayList<Long> getArgs() {
		return args;
	}

	/**
	 * Sets args.
	 *
	 * @param args the args
	 */
	public void setArgs(ArrayList<Long> args) {
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

	/**
	 * Gets next counter.
	 *
	 * @return the next counter
	 */
	public int getNextCounter() {
		return nextCounter;
	}
}
