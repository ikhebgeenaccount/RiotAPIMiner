package api;

import exception.UnknownRegionException;
import exception.UnkownEndpointException;

import java.util.HashMap;

/**
 * The type Riot api.
 *
 * Endpoint URLS are requested through the Riot API type. Some endpoints allow one or more arguments. This is specified in the Riot API documentation and in the specification of the corresponding method in RiotAPI.
 * A list of arguments is provided in the description of every method that returns an endpoint url. A - (dash) preceding an argument means that this argument is necessary, if it is not provided a NullPointerException will be thrown. An o (oh) preceding the argument means that the argument can be omitted.
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
	public String getEndpointUrl(String endpoint, HashMap<String, Object> args) {
		switch (endpoint.toLowerCase()) {
			case "champion":
				return getChampionEndpointURL(args);
			case "championmastery":
				return getChampionMasteryEndpointURL(args);
			case "currentgame":
				return getCurrentGameEndpointURL(args);
			case "featuredgames":
				return getFeaturedGamesEndpointURL();
			case "game":
				return getGameEndpointURL(args);
			case "league":
				return getLeagueEndpointURL(args);
			case "league-entry":
				return getLeagueEntryEndpointURL(args);
			case "lolstaticdata":
				return getLolStaticDataEndpointURL(args);
			case "lolstatus":
				return getLolStatusEndpointURL(args);
			case "match":
				return getMatchEndpointURL(args);
			case "matchlist":
				return getMatchListEndpointURL(args);
			case "masteries":
				return getMasteriesEndpointURL(args);
			case "runes":
				return getRunesEndpointURL(args);
			case "rankedstats":
				return getRankedStatsEndpointURL(args);
			case "summarystats":
				return getSummaryStatsEndpointURL(args);
			case "summoner":
				return getSummonerEndpointURL(args);
			case "summoner-by-account":
				return getSummonerByAccountEndpointURL(args);
			case "summoner-by-name":
				return getSummonerByNameEndpointURL(args);
			case "summonername":
				return getSummonerNameEndpointURL(args);
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
	 * Allowed arguments:
	 * o championId : int
	 * o freeToPlay : boolean
	 *
	 * @param args the args
	 * @return the champion end point url
	 */
	public String getChampionEndpointURL(HashMap<String, Object> args) {
		int championId = (args.containsKey("championId") ? (Integer)args.get("championId") : -1);
		boolean freeToPlay = (args.containsKey("freeToPlay") ? (Boolean) args.get("freeToPlay") : false);
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.2/champion" + (championId == -1 ? "" : "/" + championId) + "?" + getKeyQuery() + (freeToPlay ? "&freeToPlay=true" : "" );
	}

	/**
	 * Gets champion mastery endpoint url.
	 * Allowed arguments:
	 * - playerId : long
	 * o championId : int
	 * o count : int
	 *
	 * @param args the args
	 * @return the champion mastery endpoint url
	 */
	public String getChampionMasteryEndpointURL(HashMap<String, Object> args) {
		long playerId = (Long)args.get("playerId");
		int championId = (args.containsKey("championId") ? (Integer)args.get("championId") : -1);
		int count = (args.containsKey("count") ? (Integer)args.get("count") : -1);
		if (count == -1)
			return getBaseURL() + "/championmastery/location/" + getRegion().toUpperCase() + "/player/" + playerId + (championId == -1 ? "/champions" : "/champion/" + championId) + "?" + getKeyQuery();
		else
			return getBaseURL() + "/championmastery/location/" + getRegion().toUpperCase() + "/player/" + playerId + "/topchampions" + "?" + getKeyQuery() + "&" + "count=" + count;
	}

	/**
	 * Gets current game endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the current game endpoint url
	 */
	public String getCurrentGameEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/observer-mode/rest/consumer/getSpectatorGameInfo/" + getPlatformId() + "/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets featured games endpoint url.
	 * Allowed arguments:
	 * none
	 *
	 * @return the featured games endpoint url
	 */
	public String getFeaturedGamesEndpointURL() {
		return getBaseURL() + "/observer-mode/rest/featured?" + getKeyQuery();
	}

	/**
	 * Gets game endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the game endpoint url
	 */
	public String getGameEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.3/game/by-summoner/" + summonerId + "/recent?" + getKeyQuery();
	}

	/**
	 * Gets league endpoint url.
	 * Allowed arguments:
	 * - summonerid : long
	 *
	 * @param args the args
	 * @return the league endpoint url
	 */
	public String getLeagueEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.5/league/by-summoner/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets league entry endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the league entry endpoint url
	 */
	public String getLeagueEntryEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.5/league/by-summoner/" + summonerId + "/entry?" + getKeyQuery();
	}

	/**
	 * Returns the LoL-Static-Data endpoint URL.
	 * The type specifies what endpoint tof the LoL-Static-Data is used, legal values are:
	 * champion, item, language-strings, lanuages, map, mastery, realm, rune, summoner-spell, versions
	 * If any other value is provided either a 404 or a 500 HTTPStatusException will be thrown.
	 * Check the Lol-Static-Data endpoint documentation for the most up-to-date endpoints.
	 * Allowed arguments:
	 * - type : String
	 * o id : long
	 * o version : String
	 * o dataById : boolean
	 * o champData : String
	 * o itemData : String
	 * o masteryData : String
	 * o masteryListData : String
	 * o runeData : String
	 * o runeListData : String
	 * o spellData : String
	 * o locale : String
	 *
	 * @param args the args
	 * @return the lol static data endpoint url
	 */
	public String getLolStaticDataEndpointURL(HashMap<String, Object> args) {
		String type = (String)args.get("type");
		long id = (args.containsKey("id") ? (Long)args.get("id") : -1);
		String version = (args.containsKey("version") ? (String)args.get("version") : "");
		boolean dataById = (args.containsKey("dataById") ? (Boolean)args.get("dataById") : false);
		String champData = (args.containsKey("champData") ? (String)args.get("champData") : "");
		String itemData = (args.containsKey("itemData") ? (String)args.get("itemData") : "");
		String masteryData = (args.containsKey("masteryData") ? (String)args.get("masteryData") : "");
		String masteryListData = (args.containsKey("masteryListData") ? (String)args.get("masteryListData") : "");
		String runeData = (args.containsKey("runeData") ? (String)args.get("runeData") : "");
		String runeListData = (args.containsKey("runeListData") ? (String)args.get("runeListData") : "");
		String spellData = (args.containsKey("spellData") ? (String)args.get("spellData") : "");
		String locale = (args.containsKey("locale") ? (String)args.get("locale") : "");
		return "https://global.api.pvp.net/api/lol/static-data/" + getRegion().toUpperCase() + "/v1.2/" + type.toLowerCase() + (id == -1 ? "" : id)
				+ "?" + getKeyQuery()
				+ (version.equals("") ? "" : "&version=" + version)
				+ (champData.equals("") ? "" : "&champData=" + champData)
				+ (itemData.equals("") ? "" : "&itemData=" + itemData)
				+ (masteryData.equals("") ? "" : "&masteryData=" + masteryData)
				+ (masteryListData.equals("") ? "" : "&masteryListData=" + masteryListData)
				+ (runeData.equals("") ? "" : "&runeData=" + runeData)
				+ (runeListData.equals("") ? "" : "&runeListData=" + runeListData)
				+ (spellData.equals("") ? "" : "&spellData=" + spellData)
				+ (locale.equals("") ? "" : "&locale=" + locale)
				+ (dataById ? "&dataById=true" : "");
	}

	/**
	 * Returns the Lol-Status endpoint URL.
	 * Set the isShard argument to true to get the 'shard' URL, set it to false to get the 'shards' URL.
	 * Allowed arguments:
	 * - isShard : boolean
	 *
	 * @param args the args
	 * @return the lol status endpoint url
	 */
	public String getLolStatusEndpointURL(HashMap<String, Object> args) {
		boolean isShard = (Boolean)args.get("isShard");
		return getBaseURL() + "/lol/status/v1/shard" + (isShard ? "" : "s") + "?" + getKeyQuery();
	}

	/**
	 * Gets match endpoint url.
	 * Allowed arguments:
	 * - matchId : long
	 * o includeTimeline : boolean
	 *
	 * @param args the args
	 * @return the match endpoint url
	 */
	public String getMatchEndpointURL(HashMap<String, Object> args) {
		long matchId = (Long)args.get("matchId");
		boolean includeTimeline = (args.containsKey("includeTimeline") ? (Boolean)args.get("includeTimeline") : false);
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.2/match/" + matchId + "?" + getKeyQuery() + (includeTimeline ? "&includeTimeline=true" : "&includeTimeline=false");
	}

	/**
	 * Gets match list endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 * o rankedQueues : String
	 * o beginTime : long
	 * o endTime : long
	 * o beginIndex : int
	 * o endIndex : int
	 * o seasons : String
	 * o championIds : String
	 *
	 * @param args the args
	 * @return the match list endpoint url
	 */
	public String getMatchListEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		String rankedQueues = (args.containsKey("rankedQueues") ? (String)args.get("rankedQueues") : "");
		long endTime = (args.containsKey("endTime") ? (Long)args.get("endTime") : -1);
		int endIndex = (args.containsKey("endIndex") ? (Integer)args.get("endIndex") : -1);
		long beginTime = (args.containsKey("beginTime") ? (Long)args.get("beginTime") : -1);
		int beginIndex = (args.containsKey("beginIndex") ? (Integer)args.get("beginIndex") : -1);
		String seasons = (args.containsKey("seasons") ? (String)args.get("seasons") : "");
		String championIds = (args.containsKey("championIds") ? (String)args.get("championIds") : "");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v2.2/matchlist/by-summoner/" + summonerId
				+ "?" + getKeyQuery()
				+ (rankedQueues.equals("") ? "" : "&rankedQueues=" + rankedQueues)
				+ (endTime == -1 ? "" : "&endTime=" + endTime)
				+ (endIndex == -1 ? "" : "&endIndex=" + endIndex)
				+ (beginTime == -1 ? "" : "&beginTime=" + beginTime)
				+ (beginIndex == -1 ? "" : "&beginIndex=" + beginIndex)
				+ (seasons.equals("") ? "" : "&seasons=" + seasons)
				+ (championIds.equals("") ? "" : "&championIds=" + championIds);
	}

	/**
	 * Gets masteries endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the masteries endpoint url
	 */
	public String getMasteriesEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "/masteries?" + getKeyQuery();
	}

	/**
	 * Gets runes endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the runes endpoint url
	 */
	public String getRunesEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "/runes?" + getKeyQuery();
	}

	/**
	 * Gets ranked stats endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 * o season : String
	 *
	 * @param args the args
	 * @return the ranked stats endpoint url
	 */
	public String getRankedStatsEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		String season = (args.containsKey("season") ? (String)args.get("season") : "");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.3/stats/by-summoner/" + summonerId + "/ranked?" + getKeyQuery() + (season.equals("") ? "" : "&season=" + season);
	}

	/**
	 * Gets summary stats endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 * o season : String
	 *
	 * @param args the args
	 * @return the summary stats endpoint url
	 */
	public String getSummaryStatsEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		String season = (args.containsKey("season") ? (String)args.get("season") : "");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.3/stats/by-summoner/" + summonerId + "/summary?" + getKeyQuery() + (season.equals("") ? "" : "&season=" + season);
	}

	/**
	 * Gets summoner endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the summoner endpoint url
	 */
	public String getSummonerEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/" + summonerId + "?" + getKeyQuery();
	}

	/**
	 * Gets summoner by account endpoint url.
	 * Allowed arguments:
	 * - accountId : String
	 *
	 * @param args the args
	 * @return the summoner by account endpoint url
	 */
	public String getSummonerByAccountEndpointURL(HashMap<String, Object> args) {
		String accountId = (String)args.get("accountId");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/by-account/" + accountId + "?" + getKeyQuery();
	}

	/**
	 * Gets summoner by name endpoint url.
	 * - summonerName : String
	 *
	 * @param args the args
	 * @return the summoner by name endpoint url
	 */
	public String getSummonerByNameEndpointURL(HashMap<String, Object> args) {
		String summonerName = (String)args.get("summonerName");
		return getBaseURL() + "/api/lol/" + getRegion().toUpperCase() + "/v1.4/summoner/by-name/" + summonerName + "?" + getKeyQuery();
	}

	/**
	 * Gets summoner name endpoint url.
	 * Allowed arguments:
	 * - summonerId : long
	 *
	 * @param args the args
	 * @return the summoner name endpoint url
	 */
	public String getSummonerNameEndpointURL(HashMap<String, Object> args) {
		long summonerId = (Long)args.get("summonerId");
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
