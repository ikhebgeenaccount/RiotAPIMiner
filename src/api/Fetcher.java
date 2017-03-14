package api;

import api.data.APIDataObject;
import filter.Filter;
import formatter.Formatter;

import java.util.ArrayList;

/**
 * The type Fetcher.
 * A Fetcher uses a set of Sequencers (or just one) to get APIDataObjects from the Riot API.
 */
public class Fetcher extends Thread {

	private ArrayList<Filter> filters;
	private Sequencer sequencer;
	private Formatter formatter;

	private long startTime, endTime;
	private int numberOfResults = 0, resultCap = 0;

	/**
	 * Instantiates a new Fetcher.
	 */
	public Fetcher(Sequencer sequencer, Formatter formatter) {
		this.sequencer = sequencer;
		this.formatter = formatter;
		this.filters = new ArrayList<>();
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

}
