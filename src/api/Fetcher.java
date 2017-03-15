package api;

import api.data.APIDataObject;
import exception.HTTPStatusException;
import filter.Filter;
import formatter.Formatter;

import java.util.ArrayList;

/**
 * The type Fetcher.
 * A Fetcher uses  a Sequencer to get APIDataObjects from the Riot API.
 */
public class Fetcher extends Thread {

	private ArrayList<Filter> filters;
	private Sequencer sequencer;
	private Formatter formatter;

	private long startTime, endTime;
	private int numberOfResults = 0, resultCap = 0;

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
				e.printStackTrace();
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
}
