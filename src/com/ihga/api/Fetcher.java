package com.ihga.api;

import com.ihga.exception.HTTPStatusException;
import com.ihga.exception.InternalServerErrorException;
import com.ihga.exception.RateLimitExceededException;
import com.ihga.filter.Filter;
import com.ihga.formatter.Formatter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * The type Fetcher.
 * A Fetcher uses  a Sequencer to get APIDataObjects from the Riot API.
 */
public class Fetcher {

	private ArrayList<Filter> filters;
	private Sequencer sequencer;
	private Formatter formatter;

	private long startTime, endTime;
	private int numberOfResults = 0, resultCap = 0;

	private boolean statusUpdates = false;
	private int updateInterval = 1000;

	/**
	 * Instantiates a new Fetcher.
	 *
	 * @param sequencer the sequencer
	 * @param formatter the com.ihga.formatter
	 * @param resultCap the result cap
	 */
	public Fetcher(Sequencer sequencer, Formatter formatter, int resultCap) {
		this.sequencer = sequencer;
		this.formatter = formatter;
		this.filters = new ArrayList<>();
		this.resultCap = resultCap;
	}

	/**
	 * Runs the Fetcher.
	 */
	public void run() {
		startTime = System.currentTimeMillis();

		/*
		while not enough results
			get next result r from sequencer

			ok = true
			for each com.ihga.filter f in filters
				if !f.com.ihga.filter(r)
					ok = false
			end for

			if ok
				com.ihga.formatter.add(r)
				numberOfResults++
			end if
		end while
		 */

		while (numberOfResults < resultCap) {
			try {
				JSONObject obj = sequencer.next();

				if (obj == null)
					break;

				boolean ok = true;
				for (Filter filter : filters) {
					if (!filter.filter(obj))
						ok = false;
				}

				if (ok) {
					formatter.add(obj);
					numberOfResults++;
				}

				if (statusUpdates && numberOfResults % updateInterval == 0)
					System.out.println("Fetcher has fetched " + numberOfResults + "/" + resultCap + " results (" + Math.round((double)numberOfResults/(double)resultCap*100) + "%)");

			} catch (HTTPStatusException e) {
				int code = e.getCode();
				switch(code) {
//					case 404: // File not found
//						break;
//					case 429: // Rate limit exceeded
//						System.out.println("Rate limit exceeded with " + sequencer.getAmountOfRequests() + " in " + Math.round((System.currentTimeMillis() - startTime)/1000) + "s.\nSlowing down.");
//						sequenceTime = (System.currentTimeMillis() - startTime) / sequencer.getNextCounter();
//						break;
					default:
						System.out.println(e.getMessage());
						break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RateLimitExceededException e) {
//				System.out.println("Rate limit exceeded with " + sequencer.getAmountOfRequests() + " in " + Math.round((System.currentTimeMillis() - startTime)/1000) + "s, sleeping for " + e.getSecondsLeftToReset() + "s.");
				try {
					sequencer.decrementArguments();
					Thread.sleep(e.getSecondsLeftToReset() * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			} catch (InternalServerErrorException e) {
				try {
					sequencer.decrementArguments();
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (statusUpdates)
			System.out.println("Fetcher has fetched " + numberOfResults + "/" + resultCap + " results (" + Math.round((double)numberOfResults/(double)resultCap*100) + "%)");

		endTime = System.currentTimeMillis();
	}

	/**
	 * Register a Filter.
	 *
	 * @param filter the com.ihga.filter
	 */
	public void registerFilter(Filter filter) {
		filters.add(filter);
	}

	/**
	 * Gets start time.
	 *
	 * @return the start time
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Sets start time.
	 *
	 * @param startTime the start time
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets end time.
	 *
	 * @return the end time
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * Sets end time.
	 *
	 * @param endTime the end time
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * Sets status updates.
	 *
	 * @param statusUpdates the status updates
	 */
	public void setStatusUpdates(boolean statusUpdates) {
		this.statusUpdates = statusUpdates;
	}

	/**
	 * Sets update interval.
	 *
	 * @param updateInterval the update interval
	 */
	public void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}

	/**
	 * Gets filters.
	 *
	 * @return the filters
	 */
	public ArrayList<Filter> getFilters() {
		return filters;
	}

	/**
	 * Sets filters.
	 *
	 * @param filters the filters
	 */
	public void setFilters(ArrayList<Filter> filters) {
		this.filters = filters;
	}

	/**
	 * Gets sequencer.
	 *
	 * @return the sequencer
	 */
	public Sequencer getSequencer() {
		return sequencer;
	}

	/**
	 * Sets sequencer.
	 *
	 * @param sequencer the sequencer
	 */
	public void setSequencer(Sequencer sequencer) {
		this.sequencer = sequencer;
	}

	/**
	 * Gets com.ihga.formatter.
	 *
	 * @return the com.ihga.formatter
	 */
	public Formatter getFormatter() {
		return formatter;
	}

	/**
	 * Sets com.ihga.formatter.
	 *
	 * @param formatter the com.ihga.formatter
	 */
	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

	/**
	 * Gets result cap.
	 *
	 * @return the result cap
	 */
	public int getResultCap() {
		return resultCap;
	}

	/**
	 * Sets result cap.
	 *
	 * @param resultCap the result cap
	 */
	public void setResultCap(int resultCap) {
		this.resultCap = resultCap;
	}

	/**
	 * Get status updates boolean.
	 *
	 * @return the boolean
	 */
	public boolean getStatusUpdates() {
		return statusUpdates;
	}

	/**
	 * Gets update interval.
	 *
	 * @return the update interval
	 */
	public int getUpdateInterval() {
		return updateInterval;
	}
}
