package api;

import api.data.APIDataObject;
import exception.HTTPStatusException;
import exception.RateLimitExceededException;
import filter.Filter;
import formatter.Formatter;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * The type Fetcher.
 * A Fetcher uses  a Sequencer to get APIDataObjects from the Riot API.
 */
public class Fetcher extends Thread {

	private ArrayList<Filter> filters;
	private Sequencer sequencer;
	private Formatter formatter;

	private long startTime, endTime, sequenceTime = 0;
	private int numberOfResults = 0, resultCap = 0;

	private boolean statusUpdates = false;
	private int updateInterval = 1000;

	/**
	 * Instantiates a new Fetcher.
	 *
	 * @param sequencer the sequencer
	 * @param formatter the formatter
	 * @param resultCap the result cap
	 */
	public Fetcher(Sequencer sequencer, Formatter formatter, int resultCap) {
		this.sequencer = sequencer;
		this.formatter = formatter;
		this.filters = new ArrayList<>();
		this.resultCap = resultCap;
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis();

		/*
		while not enough results
			get next result r from sequencer

			ok = true
			for each filter f in filters
				if !f.filter(r)
					ok = false
			end for

			if ok
				formatter.add(r)
				numberOfResults++
			end if
		end while
		 */

		while (numberOfResults < resultCap) {
			if (numberOfResults % updateInterval == 0) {
				System.out.println("Fetcher has fetched " + numberOfResults + "/" + resultCap + " results (" + Math.round((double)numberOfResults/(double)resultCap*100) + "%)");
			}
			try {
				APIDataObject obj = sequencer.next();

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
			} catch (HTTPStatusException e) {
				int code = e.getCode();
				switch(code) {
					case 404: // File not found
						break;
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
					this.sleep(e.getSecondsLeftToReset() * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		endTime = System.currentTimeMillis();
	}

	/**
	 * Register a Filter.
	 *
	 * @param filter the filter
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
}
