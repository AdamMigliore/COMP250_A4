import java.util.ArrayList;

public class Twitter {

	// ADD YOUR CODE BELOW HERE
	private MyHashTable<String, Tweet> twitterTable;
	private final double MAX_LOAD_FACTOR = 0.75;
	// ADD CODE ABOVE HERE

	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		// ADD YOUR CODE BELOW HERE
		
		//Create a HashTable for the twitter by using the date and time as a key
		int numbuckets = (int) (tweets.size() / MAX_LOAD_FACTOR);
		
		twitterTable = new MyHashTable<String, Tweet>(numbuckets);

		for (Tweet t : tweets) {
			twitterTable.put(t.getDateAndTime(), t);
		}
		
		//Stop words can be in a hashTable as well not sure if useful

		// ADD CODE ABOVE HERE
	}

	/**
	 * Add Tweet t to this Twitter O(1)
	 */
	public void addTweet(Tweet t) {
		// ADD CODE BELOW HERE
		
		twitterTable.put(t.getDateAndTime(), t);
		// ADD CODE ABOVE HERE
	}

	/**
	 * Search this Twitter for the latest Tweet of a given author. If there are no
	 * tweets from the given author, then the method returns null. O(1)
	 */
	public Tweet latestTweetByAuthor(String author) {
		// ADD CODE BELOW HERE
		
		
		return null;

		// ADD CODE ABOVE HERE
	}

	/**
	 * Search this Twitter for Tweets by `date' and return an ArrayList of all such
	 * Tweets. If there are no tweets on the given date, then the method returns
	 * null. O(1)
	 */
	public ArrayList<Tweet> tweetsByDate(String date) {
		// ADD CODE BELOW HERE
		
		return null;

		// ADD CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of words (that are not stop words!) that appear in the
	 * tweets. The words should be ordered from most frequent to least frequent by
	 * counting in how many tweet messages the words appear. Note that if a word
	 * appears more than once in the same tweet, it should be counted only once.
	 */
	public ArrayList<String> trendingTopics() {
		// ADD CODE BELOW HERE

		return null;

		// ADD CODE ABOVE HERE
	}

	/**
	 * An helper method you can use to obtain an ArrayList of words from a String,
	 * separating them based on apostrophes and space characters. All character that
	 * are not letters from the English alphabet are ignored.
	 */
	private static ArrayList<String> getWords(String msg) {
		msg = msg.replace('\'', ' ');
		String[] words = msg.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>(words.length);
		for (int i = 0; i < words.length; i++) {
			String w = "";
			for (int j = 0; j < words[i].length(); j++) {
				char c = words[i].charAt(j);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
					w += c;

			}
			wordsList.add(w);
		}
		return wordsList;
	}

}
