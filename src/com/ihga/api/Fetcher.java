package com.ihga.api;

import com.ihga.calculator.Calculator;
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

	private Sequencer sequencer;
	private ArrayList<Filter> filters;
	private ArrayList<Formatter> formatters;
	private ArrayList<Calculator> calculators;

	private long startTime, endTime;
	private int numberOfResults = 0, resultCap = 0;

	private boolean statusUpdates = false;
	private int updateInterval = 1000;

	/**
	 * Instantiates a new Fetcher.
	 *
	 * @param sequencer the sequencer
	 * @param resultCap the result cap
	 */
	public Fetcher(Sequencer sequencer, int resultCap) {
		this.sequencer = sequencer;
		this.formatters = new ArrayList<>();
		this.filters = new ArrayList<>();
		this.resultCap = resultCap;
	}

	/**
	 * Runs the Fetcher.
	 */
	public void run() {
		startTime = System.currentTimeMillis();

		// While not the wanted number of results yet
		while (numberOfResults < resultCap) {
			try {
				// Request data from Riot API
				JSONObject obj = sequencer.next();

				// If null is returned, something went wrong; break
				if (obj == null)
					break;

				boolean ok = true;
				// Check if this obj passes all Filters
				for (Filter filter : filters) {
					if (!filter.filter(obj))
						ok = false;
				}

				// If they all pass
				if (ok) {
					// Add to Formatters
					for (Formatter formatter : formatters) {
						formatter.add(obj);
					}
					// Add to Calculators
					for (Calculator calculator : calculators) {
						calculator.add(obj);
					}
					// Increment number of results
					numberOfResults++;
				}

				if (statusUpdates && numberOfResults % updateInterval == 0)
					System.out.println("Fetcher has fetched " + numberOfResults + "/" + resultCap + " results (" + Math.round((double) numberOfResults / (double) resultCap * 100) + "%)");

			} catch (HTTPStatusException e) {
				int code = e.getCode();
				switch (code) {
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
			System.out.println("Fetcher has fetched " + numberOfResults + "/" + resultCap + " results (" + Math.round((double) numberOfResults / (double) resultCap * 100) + "%)");

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
	 * Register a Formatter.
	 *
	 * @param formatter the formatter
	 */
	public void registerFormatter(Formatter formatter) {
		formatters.add(formatter);
	}

	/**
	 * Register calculator.
	 *
	 * @param calculator the calculator
	 */
	public void registerCalculator(Calculator calculator) {
		calculators.add(calculator);
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
	 * Gets formatters.
	 *
	 * @return the formatters
	 */
	public ArrayList<Formatter> getFormatters() {
		return formatters;
	}

	/**
	 * Sets formatters.
	 *
	 * @param formatters the formatters
	 */
	public void setFormatters(ArrayList<Formatter> formatters) {
		this.formatters = formatters;
	}

	/**
	 * Gets calculators.
	 *
	 * @return the calculators
	 */
	public ArrayList<Calculator> getCalculators() {
		return calculators;
	}

	/**
	 * Sets calculators.
	 *
	 * @param calculators the calculators
	 */
	public void setCalculators(ArrayList<Calculator> calculators) {
		this.calculators = calculators;
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
