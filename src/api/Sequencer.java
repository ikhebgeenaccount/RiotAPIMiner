package api;

import exception.HTTPStatusException;
import exception.InternalServerErrorException;
import exception.RateLimitExceededException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * The type Sequencer.
 */
public class Sequencer {

	private Requester requester;
	private String endpoint;
	private HashMap<String, Object> args;

	private int requestCap = 0;
	private int amountOfRequests = 0;
	private int nextCounter = 0;

	/**
	 * Instantiates a new Sequencer.
	 */
	public Sequencer() {

	}

	/**
	 * Instantiates a new Sequencer.
	 * On default there is not cap to the amount of requests a sequencer can do.
	 *
	 * @param requester the requester
	 * @param endpoint  the endpoint
	 * @param startArgs the start args
	 */
	public Sequencer(Requester requester, String endpoint, HashMap<String, Object> startArgs) {
		setRequester(requester);
		setEndpoint(endpoint);
		setArgs(startArgs);
	}

	/**
	 * Instantiates a new Sequencer.
	 *
	 * @param requester the requester
	 * @param endpoint  the endpoint
	 */
	public Sequencer(Requester requester, String endpoint) {
		setRequester(requester);
		setEndpoint(endpoint);
	}

	/**
	 * Returns the next APIDataObject in this Sequencer.
	 * Returns null if the Sequencer has ended.
	 *
	 * @return the api data object
	 * @throws ParseException             the parse exception
	 * @throws HTTPStatusException        the http status exception
	 * @throws MalformedURLException      the malformed url exception
	 * @throws RateLimitExceededException the rate limit exceeded exception
	 */
	public JSONObject next() throws ParseException, HTTPStatusException, MalformedURLException, RateLimitExceededException, InternalServerErrorException {
		nextCounter++;

		// If a requestcap is set and they are reached, return null to indicate end of Sequencer.
		if ((requestCap != 0 && amountOfRequests > requestCap))
			return null;

		JSONObject obj = requester.request(endpoint, args);

		for (String s : args.keySet()) {
			if (args.get(s) instanceof Long)
				args.put(s, (Long)args.get(s) + 1);
		}

		amountOfRequests++;
		return obj;
	}

	/**
	 * Decrement arguments.
	 */
	public void decrementArguments() {
		for (String s : args.keySet()) {
			if (args.get(s) instanceof Long)
				args.put(s, (Long)args.get(s) - 1);
		}
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
	public HashMap<String, Object> getArgs() {
		return args;
	}

	/**
	 * Sets args.
	 *
	 * @param args the args
	 */
	public void setArgs(HashMap<String, Object> args) {
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
	 * Sets amount of requests.
	 *
	 * @param amountOfRequests the amount of requests
	 */
	public void setAmountOfRequests(int amountOfRequests) {
		this.amountOfRequests = amountOfRequests;
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

	/**
	 * Sets next counter.
	 *
	 * @param nextCounter the next counter
	 */
	public void setNextCounter(int nextCounter) {
		this.nextCounter = nextCounter;
	}
}
