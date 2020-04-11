import java.util.ArrayList;

public class Twitter {

	// ADD YOUR CODE BELOW HERE
	private MyHashTable<String, Tweet> normalTable;
	private MyHashTable<String, ArrayList<Tweet>> twitterTable;
	private MyHashTable<String, ArrayList<Tweet>> authorTable;
	private MyHashTable<String, String> stopWords;
	private final double MAX_LOAD_FACTOR = 0.75;
	// ADD CODE ABOVE HERE

	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		// ADD YOUR CODE BELOW HERE

		int numbuckets = (int) (tweets.size() / MAX_LOAD_FACTOR);
		
		int stopWordBuckets = (int) (stopWords.size() / MAX_LOAD_FACTOR);
		
		if(stopWords.size()==0) {
			stopWordBuckets = 1;
		}
		

		twitterTable = new MyHashTable<String, ArrayList<Tweet>>(numbuckets);
		authorTable = new MyHashTable<String, ArrayList<Tweet>>(numbuckets);
		normalTable = new MyHashTable<String, Tweet>(numbuckets);
		
		this.stopWords = new MyHashTable<String, String>(stopWordBuckets);

		for (Tweet t : tweets) {

			addTweet(t);
		}

		for (String word : stopWords) {
			word = word.toLowerCase();
			this.stopWords.put(word, word);
		}

		// ADD CODE ABOVE HERE
	}

	/**
	 * Add Tweet t to this Twitter O(1)
	 */
	public void addTweet(Tweet t) {
		// ADD CODE BELOW HERE

		String key = t.getDateAndTime().substring(0, 10);
		ArrayList<Tweet> list = twitterTable.get(key);

		if (list == null) {
			list = new ArrayList<Tweet>();
		}
		list.add(t);
		twitterTable.put(key, list);

		String author = t.getAuthor();
		ArrayList<Tweet> authorList = authorTable.get(author);

		if (authorList == null) {
			authorList = new ArrayList<Tweet>();
		}
		authorList.add(t);
		authorTable.put(author, authorList);
		
		normalTable.put(t.getDateAndTime(), t);
		
		// ADD CODE ABOVE HERE
	}

	/**
	 * Search this Twitter for the latest Tweet of a given author. If there are no
	 * tweets from the given author, then the method returns null. O(1)
	 */
	public Tweet latestTweetByAuthor(String author) {
		// ADD CODE BELOW HERE
		if (authorTable.get(author) == null) {
			return null;
		}

		Tweet latest = authorTable.get(author).get(0);

		for (Tweet t : authorTable.get(author)) {
			if (t.compareTo(latest) > 0) {
				latest = t;
			}
		}

		return latest;

		// ADD CODE ABOVE HERE
	}

	/**
	 * Search this Twitter for Tweets by `date' and return an ArrayList of all such
	 * Tweets. If there are no tweets on the given date, then the method returns
	 * null. O(1)
	 */
	public ArrayList<Tweet> tweetsByDate(String date) {
		// ADD CODE BELOW HERE

		return twitterTable.get(date);

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
		MyHashTable<String, Integer> trendingTable = new MyHashTable<String, Integer>(1);

		for (HashPair pair : normalTable) {
			// 1- get the value
			Tweet tweet = (Tweet) pair.getValue();
			// 2- get the words
			ArrayList<String> words = getWords(tweet.getMessage());
			// 3- for each word check if its a stop word
			for (String word : words) {
				// if null - go on and increment
				word = word.toLowerCase();
				if (stopWords.get(word) == null) {
					Integer value = trendingTable.get(word);
					if (value == null) {
						value = 1;
					} else {
						value++;
					}
					trendingTable.put(word, value);
				}
			}
		}
		System.out.println(trendingTable.get("that"));
		return MyHashTable.slowSort(trendingTable);
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
