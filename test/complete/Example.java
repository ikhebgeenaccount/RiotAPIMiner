package complete;

import api.Fetcher;
import api.Requester;
import api.RiotAPI;
import api.Sequencer;
import formatter.MatchFormatter;

import java.util.ArrayList;

public class Example {

	public static void main(String[] args) {
		// Create a new RiotAPI that will get its data from euw
		RiotAPI api = new RiotAPI(API_KEY, "euw");
		// Instantiate a Requester on the api
		Requester requester = new Requester(api);

		// Create a list with enough arguments corresponding to the used endpoint
		// In the case of the match endpoint, 1 argument
		ArrayList<Long> arguments = new ArrayList<>();
		// Since we are going to fetch matches, we need a match id to start from
		arguments.add(3103888982L);

		// Create a Sequencer to request the data with
		Sequencer sequencer = new Sequencer(requester, "match", arguments);

		// Next we create a Fetcher that will fetch our games with a MatchFormatter, which
		// will put our data in the desired format, and we specify how many games we want
		Fetcher fetcher = new Fetcher(sequencer, new MatchFormatter("result.csv"), 2000);

		// Enable status updates to see the progress of the Fetcher
		fetcher.setStatusUpdates(true);

		// Set the interval at which we get updates. In this case, since we will fetch 15000 games,
		// we put it on 500 to get an update every 3%.
		fetcher.setUpdateInterval(5);

		// Then, we run our created Fetcher
		fetcher.run();
		// After completion, there will be file called "result.csv" with 15000 games stored in it
	}
}
