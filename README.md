# RiotAPIMiner

Riot API Miner aims to offer an easy-to-use library to get lots of data from the Riot API. 

## Example

Below a simple example that fetches 15000 games from the Riot API.

    // Create a new RiotAPI that will get its data from euw
    RiotAPI api = new RiotAPI(API_KEY, "euw");
	// Instantiate a Requester on the api
	Requester requester = new Requester(api);
	
	// Create a list with enough arguments corresponding to the used endpoint
	// In the case of the match endpoint, 1 argument
	ArrayList<Integer> args = new ArrayList<>();
	// Since we are going to fetch matches, we need a match id to start from
	args.add(3101990172);
	
	// Create a Sequencer to request the data with
	Sequencer sequencer = new Sequencer(requester, "match");
	
    // Next we create a Fetcher that will fetch our games with a MatchFormatter, 
	// that will put our data in the right format and we specify how many games we want
	Fetcher fetcher = new Fetcher(sequencer, new MatchFormatter("result.csv"), 15000);
	
	// Then, we run our created Fetcher
	fetcher.run();
	// After completion, there will be file called "result.csv" with our data
	
### Filtering

It is also possible to filter the data. You can filter for example certain champions or gamemodes. It is also possible to easily create your own filters.

Expanding above example to only return ARAM games:

	// Before we run our Fetcher, add a Filter to its
	filter.registerFilter(new GamemodeFilter("ARAM_5x5"));
	// Then, run the Fetcher
	fetcher.run();
	// Now, in our "result.csv" file we will only find ARAMs
	
	
	
Riot API Miner isn’t endorsed by Riot Games and doesn’t reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.