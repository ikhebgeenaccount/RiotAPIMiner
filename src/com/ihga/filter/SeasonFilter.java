package com.ihga.filter;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * The type Season com.ihga.filter.
 */
public class SeasonFilter implements Filter {

	private ArrayList<String> seasons;

	/**
	 * Instantiates a new Gamemode com.ihga.filter.
	 */
	public SeasonFilter() {
		this.seasons = new ArrayList<>();
	}

	/**
	 * Instantiates a new Gamemode com.ihga.filter.
	 *
	 * @param season the season
	 */
	public SeasonFilter(String season) {
		this();
		seasons.add(season);
	}

	/**
	 * Instantiates a new Gamemode com.ihga.filter.
	 *
	 * @param seasons the seasons
	 */
	public SeasonFilter(ArrayList<String> seasons) {
		this.seasons = seasons;
	}

	@Override
	public boolean filter(JSONObject obj) {
		boolean found = false;

		for (String season : seasons) {
			if (obj.get("season").equals(season)) {
				found = true;
				break;
			}
		}

		return found;
	}

	/**
	 * Add gamemode.
	 *
	 * @param season the season
	 * @return the season com.ihga.filter
	 */
	public SeasonFilter addSeason(String season) {
		seasons.add(season);
		return this;
	}
}
