# RiotAPIMiner

Riot API Miner aims to offer an easy-to-use library to get lots of data from the Riot API. 

## Example

Below a simple example that fetches 15000 games from the Riot API.

    // This example is NOT compatible with any released builds, it only
	// represents the current status of branch dev
	
	// Create a new RiotAPI that will get its data from euw
	RiotAPI api = new RiotAPI(API_KEY, "euw");
	// Instantiate a Requester on the api
	Requester requester = new Requester(api);

    // Create a HashMap with the necessary arguments and add optional arguments
    HashMap<String, Object> arguments = new HashMap<>();
    // For the Match endpoint there is one argument that must be provided, the matchId. Since
    // we are going to get lots of matches, we need a matchId to start from.
    arguments.put("matchId", 3103888982L);

	// Create a Sequencer to request the data with
	Sequencer sequencer = new Sequencer(requester, "match", arguments);

	// Next we create a Fetcher that will fetch our games with a MatchFormatter, which
	// will put our data in the desired format, and we specify how many games we want
	Fetcher fetcher = new Fetcher(sequencer, new MatchFormatter("result.csv"), 15000);

	// Enable status updates to see the progress of the Fetcher
	fetcher.setStatusUpdates(true);

	// Set the interval at which we get updates. In this case, since we will fetch 15000 games,
	// we put it on 500 to get an update every 3%.
	fetcher.setUpdateInterval(500);

	// Then, we run our created Fetcher
	fetcher.run();
	// After completion, there will be file called "result.csv" with 15000 games stored in it
	
### Filtering

It is also possible to filter the data. You can filter for example certain champions or gamemodes. It is also possible to easily create your own filters.

Expanding above example to only return ARAM games:

	// Before we run our Fetcher, add a Filter to it
	fetcher.registerFilter(new GamemodeFilter("ARAM_5x5"));
	// Then, run the Fetcher
	fetcher.run();
	// Now, in our "result.csv" file we will find 15000 ARAMs
	
#### Creating your own Filters

To create your own Filter you can create a class that implements the Filter interface.
	
### Formatting

Several default Formatters are included.

#### Creating your own Formatters

To create your own Formatter you can create a class that implements the Formatter interface.

## Disclaimer
Riot API Miner isn’t endorsed by Riot Games and doesn’t reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.

## json-simple
This project uses the [json-simple library](https://code.google.com/archive/p/json-simple/).