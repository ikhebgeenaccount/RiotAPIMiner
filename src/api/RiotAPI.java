package api;

import exception.UnknownRegionException;
import exception.UnkownEndpointException;

import java.util.ArrayList;

/**
 * The type Riot api.
 */
public class RiotAPI {

	private String key;
	private String region;

	/**
	 * Instantiates a new Riot api with the specified key and region. If the region is a region that does not exist a UnknownRegionException is thrown.
	 *
	 * A Riot API can be used to get the correct URLs for the several endpoints in the Riot API.
	 *
	 * @param key    the key
	 * @param region the region
	 * @throws UnknownRegionException if the region is a region that does not exist a UnknownRegionException is thrown
	 */
	public RiotAPI(String key, String region) {
		setKey(key);
		setRegion(region);
	}

	/**
	 * Returns the URL of the specified endpoint with the arguments.
	 *
	 * @param endpoint the endpoint
	 * @param args     the args
	 * @return the endpoint url
	 */
	public String getEndpointUrl(String endpoint, ArrayList<Integer> args) {
		switch (endpoint.toLowerCase()) {
			case "match":
				return getMatchEndpointURL(args.get(0));
			case "champion":
				return getChampionEndPointURL(args.get(0));
			default:
				throw new UnkownEndpointException("The endpoint " + endpoint + " is not recognized.");
		}
	}

	/**
	 * Returns the Base URL with the region in this Riot API without a / at the end.
	 * @return the base url
	 */
	private String getBaseURL() {
		return "https://" + getRegion() + "api.pvp.net";
	}

	private String getKeyQuery() {
		return "api_key=" + getKey();
	}

	/**
	 * Gets champion end point url.
	 *
	 * @param championId the champion id
	 * @return the champion end point url
	 */
	private String getChampionEndPointURL(int championId) {
		return getBaseURL() + "/api/lol/" + getRegion() + "/v1.2/champion/" + championId + "&" + getKeyQuery();
	}

	/**
	 * Gets match endpoint url.
	 *
	 * @param matchId the match id
	 * @return the match endpoint url
	 */
	private String getMatchEndpointURL(int matchId) {
		return getBaseURL() + "/api/lol/" + getRegion() + "/v2.2/match/" + matchId + "?" + getKeyQuery();
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets key.
	 *
	 * @param key the key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets region.
	 *
	 * @param region the region
	 */
	public void setRegion(String region) {
		switch (region.toLowerCase()) {
			case "br":
			case "eune":
			case "euw":
			case "jp":
			case "kr":
			case "lan":
			case "las":
			case "na":
			case "oce":
			case "ru":
			case "tr":
				this.region = region;
				break;
			default: throw new UnknownRegionException(region.toLowerCase() + " is not an existing region.");
		}
	}
}
