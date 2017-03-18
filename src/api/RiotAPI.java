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
	 * <p>
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
	public String getEndpointUrl(String endpoint, ArrayList<Long> args) {
		switch (endpoint.toLowerCase()) {
			case "match":
				return getMatchEndpointURL(args.get(0));
			case "champion":
				return getChampionEndpointURL(args.get(0));
			default:
				throw new UnkownEndpointException("The endpoint " + endpoint + " is not recognized.");
		}
	}

	/**
	 * Returns the Base URL with the region in this Riot API without a / at the end.
	 * @return the base url
	 */
	private String getBaseURL() {
		return "https://" + getRegion() + ".api.pvp.net";
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
	public String getChampionEndpointURL(long championId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.2/champion/" + championId + "&" + getKeyQuery();
	}

	/**
	 * Gets champion mastery endpoint url.
	 *
	 * @param playerId the player id
	 * @return the champion mastery endpoint url
	 */
	public String getChampionMasteryEndpointURL(long playerId) {
		return getBaseURL() + "/championmastery/location/" + getRegion().toUpperCase() + "player/" + playerId + "/champions&" + getKeyQuery();
	}

	/**
	 * Gets champion mastery endpoint url.
	 *
	 * @param playerId   the player id
	 * @param championId the champion id
	 * @return the champion mastery endpint url
	 */
	public String getChampionMasteryEndpointURL(long playerId, long championId) {
		return getBaseURL() + "/championmastery/location/" + getRegion().toUpperCase() + "player/" + playerId + "/champion/" + championId + "&" + getKeyQuery();
	}

	/**
	 * Gets current game endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the current game endpoint url
	 */
	public String getCurrentGameEndpointURL(long summonerId) {
		return getBaseURL() + "/observer-mode/rest/consumer/getSpectatorGameInfo/" + getPlatformId() + "/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets featured games endpoint url.
	 *
	 * @return the featured games endpoint url
	 */
	public String getFeaturedGamesEndpointURL() {
		return getBaseURL() + "/observer-mode/rest/featured?" + getKeyQuery();
	}

	/**
	 * Gets game endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the game endpoint url
	 */
	public String getGameEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.3/game/by-summoner/" + summonerId + "/recent?" + getKeyQuery();
	}

	/**
	 * Gets league endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the league endpoint url
	 */
	public String getLeagueEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.5/league/by-summoner/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets league entry endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the league entry endpoint url
	 */
	public String getLeagueEntryEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.5/league/by-summoner/" + summonerId + "/entry?" + getKeyQuery();
	}

	/**
	 * Returns the LoL-Static-Data endpoint URL.
	 * The type specifies what endpoin tof the LoL-Static-Data is used, legal values are:
	 * champion, item, language-strings, lanuages, map, mastery, realm, rune, summoner-spell, versions
	 * Setting the id to zero will result in the id being omitted from the URL.
	 *
	 * @param type the type
	 * @param id   the id
	 * @return the lol static data endpoint url
	 */
	public String getLolStaticDataEndpointURL(String type, long id) {
		return getBaseURL() + "/api/lol/static-data/" + getRegion().toUpperCase() + "/v1.2/" + type.toLowerCase() + (id == 0 ? "" : id) + "?" + getKeyQuery();
	}

	/**
	 * Returns the Lol-Status endpoint URL.
	 * Set the isShard argument to true to get the 'shard' URL, set it to false to get the 'shards' URL.
	 *
	 * @param isShard the is shard
	 * @return the lol status endpoint url
	 */
	public String getLolStatusEndpointURL(boolean isShard) {
		return getBaseURL() + "/lol/status/v1/shard" + (isShard ? "" : "s") + "?" + getKeyQuery();
	}

	/**
	 * Gets match endpoint url.
	 *
	 * @param matchId the match id
	 * @return the match endpoint url
	 */
	public String getMatchEndpointURL(long matchId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.2/match/" + matchId + "?" + getKeyQuery();
	}

	/**
	 * Gets match list endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the match list endpoint url
	 */
	public String getMatchListEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.2/matchlist/by-summoner/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets masteries endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the masteries endpoint url
	 */
	public String getMasteriesEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "/masteries?" + getKeyQuery();
	}

	/**
	 * Gets runes endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the runes endpoint url
	 */
	public String getRunesEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "/runes?" + getKeyQuery();
	}

	/**
	 * Gets ranked stats endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the ranked stats endpoint url
	 */
	public String getRankedStatsEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.3/stats/by-summoner/" + summonerId + "/ranked?" + getKeyQuery();
	}

	/**
	 * Gets summary stats endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the summary stats endpoint url
	 */
	public String getSummaryStatsEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.3/stats/by-summoner/" + summonerId + "/summary?" + getKeyQuery();
	}

	/**
	 * Gets summoner endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the summoner endpoint url
	 */
	public String getSummonerEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets summoner by account endpoint url.
	 *
	 * @param accountId the account id
	 * @return the summoner by account endpoint url
	 */
	public String getSummonerByAccountEndpointURL(long accountId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/by-account/" + accountId + "?" + getKeyQuery();
	}

	/**
	 * Gets summoner by name endpoint url.
	 *
	 * @param summName the summ name
	 * @return the summoner by name endpoint url
	 */
	public String getSummonerByNameEndpointURL(String summName) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/by-name/" + summName + "?" + getKeyQuery();
	}

	/**
	 * Gets summoner name endpoint url.
	 *
	 * @param summonerId the summoner id
	 * @return the summoner name endpoint url
	 */
	public String getSummonerNameEndpointURL(long summonerId) {
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "/name?" + getKeyQuery();
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

	public String getPlatformId() {
		switch (getRegion()) {
			case "br":
				return "BR1";
			case "eune":
				return "EUN1";
			case "euw":
				return "EUW1";
			case "jp":
				return "JP1";
			case "kr":
				return "KR";
			case "lan":
				return "LA1";
			case "las":
				return "LA2";
			case "na":
				return "NA1";
			case "oce":
				return "OC1";
			case "ru":
				return "RU";
			case "tr":
				return "TR1";
			default: throw new UnknownRegionException(region.toLowerCase() + " is not an existing region.");
		}
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
