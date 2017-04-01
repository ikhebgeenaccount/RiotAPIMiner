package com.ihga.api;

import com.ihga.exception.HTTPStatusException;
import com.ihga.exception.InternalServerErrorException;
import com.ihga.exception.RateLimitExceededException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

/**
 * The type Fixed Argument Sequencer.
 *
 * A Fixed Argument Sequencer does not start from one point and increments from there, but traverses a List in-order
 */
public class FixedArgumentSequencer extends Sequencer {

	private HashMap<String, List<Object>> fixedArguments;

	/**
	 * Instantiates a new Fixed sequencer.
	 *
	 * @param requester the requester
	 * @param endpoint  the endpoint
	 * @param fixedArgs the fixed args
	 */
	public FixedArgumentSequencer(Requester requester, String endpoint, HashMap<String, List<Object>> fixedArgs) {
		super(requester, endpoint);
		this.fixedArguments = fixedArgs;
	}

	/**
	 * Returns the next APIDataObject in this Sequencer.
	 * Returns null if the Sequencer has ended.
	 *
	 * @return the com.ihga.api data object
	 * @throws HTTPStatusException the http status com.ihga.exception
	 */
	public JSONObject next() throws ParseException, HTTPStatusException, MalformedURLException, RateLimitExceededException, InternalServerErrorException {
		// If a requestcap is set and they are reached, return null to indicate end of Sequencer.
		if ((getRequestCap() != 0 && getAmountOfRequests() > getRequestCap()) || getNextCounter() >= fixedArguments.get(fixedArguments.keySet().iterator().next()).size())
			return null;

		HashMap<String, Object> args = new HashMap<>();

		for (String s : fixedArguments.keySet()) {
			args.put(s, fixedArguments.get(s).get(getNextCounter() % fixedArguments.get(s).size()));
		}

		setNextCounter(getNextCounter() + 1);

		JSONObject obj = this.getRequester().request(this.getEndpoint(), args);

		setAmountOfRequests(getAmountOfRequests() + 1);
		return obj;
	}

	/**
	 * Decrement arguments.
	 */
	public void decrementArguments() {
		setNextCounter(getNextCounter() - 1);
	}

	/**
	 * Gets fixed arguments.
	 *
	 * @return the fixed arguments
	 */
	public HashMap<String, List<Object>> getFixedArguments() {
		return fixedArguments;
	}

	/**
	 * Sets fixed arguments.
	 *
	 * @param fixedArguments the fixed arguments
	 */
	public void setFixedArguments(HashMap<String, List<Object>> fixedArguments) {
		this.fixedArguments = fixedArguments;
	}
}