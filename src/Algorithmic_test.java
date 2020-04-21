import java.util.ArrayList;
import java.util.Random;

public class Algorithmic_test {
	private final int NUMBER_OF_ENTRIES = 100;
	private MyHashTable<String, Integer> myTable = new MyHashTable<String, Integer>(1);
	private Random rand = new Random();
	private Twitter twitter;

	public void runTests() throws Exception {
		System.out.println("=========================================================");
		System.out.println("=                  Testing MyHashTable                  =");
		System.out.println("=========================================================\n");
		testPut();
		testGet();
		testRemove();
		testRehash();
		testKeys();
		testValues();
		testFastSort();

		System.out.println("=========================================================");
		System.out.println("=                    Testing Twitter                    =");
		System.out.println("=========================================================\n");
		testAddTweet();
		testLatestTweetByAuthor();
		testTweetsByDate();
		testTrendingTopics();
	}

	public Algorithmic_test() {
	}

	// MyHashTableTests
	private void testPut() throws AssertEqualsException {
		fillTable();
		System.out.print("-> Checking MyHashTable.put()");
		// Test 1: enter a key that is null
		System.out.print(".");
		assertNull(myTable.put(null, 1));
		// Test 2: enter a value that is null
		System.out.print(".");
		assertNull(myTable.put("test2", null));
		// Test 3: enter a normal pair of key,value
		System.out.print(".");
		assertNull(myTable.put("Test3", 3));
		// Test 4: adding a repeated pair of key
		System.out.print(".");
		assertNull(myTable.put("Test4", 4));
		int initialSize = myTable.size();
		assertEquals(4, myTable.put("Test4", 5));
		// Test 5: check that size did not change for repeated key
		System.out.print(".");
		assertEquals(initialSize, myTable.size());
		// Test 6: test that the number of entries is correct
		System.out.print(".");
		fillTable();
		initialSize = myTable.size();
		myTable.put("TEST5", 5);
		assertEquals(initialSize + 1, myTable.size());

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.put()\n");
	}

	private void testGet() throws AssertEqualsException {
		fillTable();
		System.out.print("-> Checking MyHashTable.get()");
		// Test 1: get a null key
		System.out.print(".");
		assertNull(myTable.get(null));
		// Test 2: get a non-existing key
		System.out.print(".");
		assertNull(myTable.get("TEST2"));
		// Test 3: get a normal key
		System.out.print(".");
		myTable.put("TEST3", 3);
		assertEquals(3, myTable.get("TEST3"));

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.get()\n");
	}

	private void testRemove() throws AssertEqualsException {
		fillTable();
		System.out.print("-> Checking MyHashTable.remove()");
		// Test 1: remove a null key
		System.out.print(".");
		assertNull(myTable.remove(null));
		// Test 2: remove a non-existing key
		System.out.print(".");
		assertNull(myTable.remove("TEST2"));
		// Test 3: remove a normal key
		System.out.print(".");
		myTable.put("TEST3", 3);
		assertEquals(3, myTable.remove("TEST3"));
		// Test 4: check if size is correct
		System.out.print(".");
		fillTable();
		myTable.put("TEST4", 4);
		int initialSize = myTable.size();
		myTable.remove("TEST4");
		assertEquals(initialSize - 1, myTable.size());

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.remove()\n");
	}

	private void testRehash() throws AssertEqualsException {
		fillTable();
		System.out.print("-> Checking MyHashTable.rehash()");
		// Test 1: check if rehash doubles the bucket size
		int initialBuckets = myTable.numBuckets();
		myTable.rehash();
		assertEquals(initialBuckets * 2, myTable.numBuckets());

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.rehash()\n");
	}

	private void testKeys() throws Exception {
		int size = 5;
		String keys[] = new String[size];

		myTable = new MyHashTable<String, Integer>(1);

		System.out.print("-> Checking MyHashTable.keys()");
		// Test 1: no keys
		System.out.print(".");
		assertEquals(0, myTable.keys().size());

		// Populate the table
		myTable = new MyHashTable<String, Integer>(1);
		for (int i = 0; i < size; i++) {
			String key = "SAMPLE" + i;
			Integer value = i;
			myTable.put(key, value);
			keys[i] = key;
		}

		// Test 2: make sure that all keys are present in the
		System.out.print(".");
		for (int i = 0; i < size; i++) {
			if (!myTable.keys().contains(keys[i])) {
				throw new Exception("Key: " + keys[i] + " is not present");
			}
		}

		// Test 3: adding the same key twice
		System.out.print(".");
		assertEquals(0, myTable.put("SAMPLE0", 5));
		for (int i = 0; i < size; i++) {
			if (!myTable.keys().contains(keys[i])) {
				throw new Exception("Key: " + keys[i] + " is not present");
			}
		}

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.keys()\n");
	}

	private void testValues() throws Exception {
		int size = 5;
		Integer values[] = new Integer[size];

		myTable = new MyHashTable<String, Integer>(1);

		System.out.print("-> Checking MyHashTable.values()");
		// Test 1: no values
		System.out.print(".");
		assertEquals(0, myTable.values().size());

		// Populate the table
		myTable = new MyHashTable<String, Integer>(1);
		for (int i = 0; i < size; i++) {
			String key = "SAMPLE" + i;
			Integer value = i;
			myTable.put(key, value);
			values[i] = value;
		}

		// Test 2: make sure that all values are present in the
		System.out.print(".");
		for (int i = 0; i < size; i++) {
			if (!myTable.values().contains(values[i])) {
				throw new Exception("Value: " + values[i] + " is not present");
			}
		}

		// Test 3: adding the same key twice
		System.out.print(".");
		assertEquals(0, myTable.put("SAMPLE0", 5));
		values[0] = 5;
		for (int i = 0; i < size; i++) {
			if (!myTable.values().contains(values[i])) {
				throw new Exception("Value: " + values[i] + " is not present");
			}
		}

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.values()\n");
	}

	private void testFastSort() throws AssertEqualsException {

		System.out.print("-> Checking MyHashTable.fastSort()");
		// Test 1: sort an empty table
		System.out.print(".");
		myTable = new MyHashTable<String, Integer>(1);
		assertEquals(0, MyHashTable.fastSort(myTable).size());
		// Test 2: check if all is sorted
		fillTable();
		ArrayList<String> slowList = MyHashTable.slowSort(myTable);
		ArrayList<String> fastList = MyHashTable.fastSort(myTable);
		for (int i = 0; i < fastList.size(); i++) {
			assertEquals(slowList.get(i), fastList.get(i));
		}

		System.out.println("Done!");
		System.out.println("No errors with MyHashTable.fastSort()\n");
	}

	// Twitter tests
	private void testAddTweet() throws AssertEqualsException {
		fillTwitter();

		System.out.print("-> Checking Twitter.addTweet()");
		// Test 1: add a null tweet
		System.out.print(".");
		twitter.addTweet(null);
		// Test 2: add a normal tweet
		System.out.print(".");
		Tweet tweet = new Tweet("Adam", "2017-03-03 03:35:38", "I love 250!");
		twitter.addTweet(tweet);
		assertEquals(tweet, twitter.latestTweetByAuthor("Adam"));

		System.out.println("Done!");
		System.out.println("* Make sure that you cannot add a null Tweet in Twitter.addTweet()!");
		System.out.println("No errors with Twitter.addTweet()\n");
	}
	
	private void testLatestTweetByAuthor() throws AssertEqualsException {

		System.out.print("-> Checking Twitter.latestTweetByAuthor()");
		//Test 1: no tweets
		System.out.print(".");
		twitter = new Twitter( new ArrayList<Tweet>(), new ArrayList<String>());
		assertNull(twitter.latestTweetByAuthor("Adam"));
		//Test 2: null author
		fillTwitter();
		System.out.print(".");
		assertNull(twitter.latestTweetByAuthor(null));
		//Test 3: not an author
		System.out.print(".");
		assertNull(twitter.latestTweetByAuthor("Adam"));
		//Test 4: correct author
		System.out.print(".");
		Tweet tweet = new Tweet("Adam", "2017-03-03 03:35:38", "I love 250!");
		twitter.addTweet(tweet);
		assertEquals(tweet, twitter.latestTweetByAuthor("Adam"));
		
		System.out.println("Done!");
		System.out.println("No errors with Twitter.latestTweetByAuthor()\n");
	}
	
	private void testTweetsByDate() throws AssertEqualsException {
		System.out.print("-> Checking Twitter.tweetsByDate()");
		
		//Test 1: null date
		System.out.print(".");
		twitter = new Twitter( new ArrayList<Tweet>(), new ArrayList<String>());
		assertNull(twitter.tweetsByDate(null));
		//Test 2: date is non-existent
		fillTwitter();
		System.out.print(".");
		assertNull(twitter.tweetsByDate("1990-03-03"));
		//Test 3: normal query
		System.out.print(".");
		ArrayList<Tweet> myTweets = new ArrayList<>();
		myTweets.add(new Tweet("Adam", "2017-03-03 03:35:38", "1"));
		myTweets.add(new Tweet("Adam", "2017-03-03 03:36:38", "2"));
		myTweets.add(new Tweet("Adam", "2017-03-03 03:37:38", "3"));
		twitter = new Twitter( myTweets, new ArrayList<String>());
		assertEquals(myTweets, twitter.tweetsByDate("2017-03-03"));
		
		System.out.println("Done!");
		System.out.println("No errors with Twitter.tweetsByDate()\n");
	}
	
	private void testTrendingTopics() throws AssertEqualsException {
		ArrayList<Tweet> myTweets = new ArrayList<>();
		myTweets.add(new Tweet("Adam", "2017-03-03 03:35:38", "Hi, Adam Adam "));
		myTweets.add(new Tweet("Adam", "2017-03-03 03:36:38", "Hi, Felix Adam"));
		myTweets.add(new Tweet("Adam", "2017-03-03 03:37:38", "Hi, Felix Felix Adam"));
		twitter = new Twitter( myTweets, new ArrayList<String>());
		
		ArrayList<String> answerWithoutStop = new ArrayList<>();
		answerWithoutStop.add("hi"); //should be 3
		answerWithoutStop.add("adam"); //should be 3
		answerWithoutStop.add("felix");// should be 2
		
		ArrayList<String> stopWords = new ArrayList<>();
		stopWords.add("adam");
		
		ArrayList<String> answerWithStop = new ArrayList<>();
		answerWithStop.add("hi"); //should be 3
		answerWithStop.add("felix");// should be 2
		
		System.out.print("-> Checking Twitter.trendingTopics()");
		
		//Test 1: without stopwords
		System.out.print(".");
		assertEquals(answerWithoutStop, twitter.trendingTopics());
		
		//Test 2: with stopwords
		System.out.print(".");
		twitter = new Twitter( myTweets, stopWords);
		assertEquals(answerWithStop, twitter.trendingTopics());
		
		System.out.println("Done!");
		System.out.println("* Make sure to ignore case for a word");
		System.out.println("* Make sure that a word in a Tweet is only counted once");
		System.out.println("No errors with Twitter.trendingTopics()\n");
	}
	
	// Helpers
	private void fillTable() {
		myTable = new MyHashTable<String, Integer>(1);
		for (int i = 0; i < NUMBER_OF_ENTRIES; i++) {
			String key = generateRandomString();
			Integer value = rand.nextInt(NUMBER_OF_ENTRIES);
			myTable.put(key, value);
		}
	}

	private void fillTwitter() {
		ArrayList<String> stopWords = new ArrayList<String>();
		twitter = new Twitter(initTweetList(), stopWords);
	}

	/**
	 * @author COMP250 Teaching staff
	 */
	private static ArrayList<Tweet> initTweetList() {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(new Tweet("USER_989b85bb", "2010-03-04 15:34:46",
				"@USER_6921e61d I can be made into one twitter superstar."));
		tweets.add(new Tweet("USER_a75657c2", "2010-03-03 00:02:54",
				"@USER_13e8a102 They reached a compromise just on time"));
		tweets.add(new Tweet("USER_989b85bb", "2010-03-04 15:34:47", "I can be MADE into a need."));
		tweets.add(new Tweet("USER_a75657c2", "2010-03-07 21:45:48",
				"So SunChips made a bag that is 100% biodegradeable. It is about damn time somebody did."));
		tweets.add(new Tweet("USER_ee551c6c", "2010-03-07 15:40:27",
				"drthema: Do something today that feeds your spirit and empowers you to start the week from a higher place."));
		tweets.add(new Tweet("USER_6c78461b", "2010-03-03 05:13:34",
				"@USER_a3d59856 yes, i watched that foolery done disturbed my spirit. @USER_b1d28f26"));
		tweets.add(new Tweet("USER_92b2293c", "2010-03-04 14:00:11",
				"@USER_5aac9e88: Let no one push u around today! Be at Peace! If u dont have restful spirit, u'll definitely have a stressful spirit"));
		tweets.add(new Tweet("USER_75c62ed9", "2010-03-07 03:35:38",
				"@USER_cb237f7f Congrats on everything I am there in spirit my brother."));
		tweets.add(new Tweet("USER_7f72a368", "2010-03-07 07:18:22",
				"Actions speak louder than words but feelings and spirits speak louder than anything #FACT"));
		tweets.add(new Tweet("USER_b6cc1831", "2010-03-07 04:04:37", "@USER_be777094 urban spirit cafe. On Long st"));
		tweets.add(new Tweet("USER_65006b55", "2010-03-05 00:58:28",
				"RT @USER_86e8d97f: @USER_65006b55's spirit just took a turn for the worst. Lol please."));
		tweets.add(new Tweet("USER_60b9991b", "2010-03-04 22:33:23",
				"Who on my time ever flew on spirit airlines let me kno if there decent"));
		tweets.add(new Tweet("USER_36607a99", "2010-03-03 02:06:01",
				"@USER_561fe280: Nourish your spirit with your own achievement."));
		tweets.add(new Tweet("USER_9506fb5f", "2010-03-04 01:16:34",
				"Great spirits have often encountered violent opposition from weak minds"));
		tweets.add(new Tweet("USER_d3ca457f", "2010-03-03 04:53:06",
				"RT @USER_6d6bfb4d: The things that make a woman beautiful are her character, intellect, and spirituality."));
		tweets.add(new Tweet("USER_14f78255", "2010-03-03 17:07:45",
				"@USER_9afbc367 Oh in spirit. That's all that matters lol"));
		tweets.add(new Tweet("USER_3dfae4fe", "2010-03-05 00:44:33",
				"time for a spiritual cleansing of my facebook friend list"));
		tweets.add(new Tweet("USER_bd852fb7", "2010-03-03 14:19:51",
				"RT @USER_24bd1961:God's spirit is like a Radio station, broadcasting all the time. You just have to learn how to tune in and receive his signal"));
		tweets.add(new Tweet("USER_136c16da", "2010-03-07 19:56:54",
				"RT @USER_11d35e61: @USER_136c16da finally a kindred spirit. *daps* lol thanks"));
		tweets.add(new Tweet("USER_47063e51", "2010-03-04 12:47:54",
				"cathartic - noun - a purification or purgation that brings about spiritual renewal or release from tension"));
		tweets.add(new Tweet("USER_1e4eb302", "2010-03-03 20:13:18",
				"Anything worth having you have to contribute yourself heart, mind, soul and spirit to. It is so rewarding. Have u contributed lately?"));
		tweets.add(new Tweet("USER_5d246e83", "2010-03-04 14:57:01",
				"@USER_8e090edb That's always good to hear. Starting off to a good morning, always puts your spirit in a great place."));
		tweets.add(new Tweet("USER_b7117680", "2010-03-03 06:55:17", "I got a hustlas spirit, period!"));
		tweets.add(new Tweet("USER_25ecff25", "2010-03-05 17:33:20",
				"RT @USER_3a117437: The woman at the rental car spot tried 2 give us a Toyota! No ma'am lk the old spiritual says \"aint got time 2 die!\""));
		tweets.add(new Tweet("USER_f91d8165", "2010-03-03 22:33:24",
				"#RandomThought why do people grab guns or knives when they think theres a ghost? DUMBASS! You can't shoot a spirit, grab some holy water! duh"));
		tweets.add(new Tweet("USER_86c542b8", "2010-03-04 02:52:06",
				"@USER_8cd1512d haha, maybe your right. I use to watch gymnastics all the time. I love the olympics. That's why I have so much spirit lol"));

		return tweets;
	}

	private String generateRandomString() {
		String output = "";

		for (int i = 0; i < rand.nextInt(NUMBER_OF_ENTRIES); i++) {
			output += rand.nextInt(NUMBER_OF_ENTRIES);
		}

		return output;

	}

	private <T> boolean assertEquals(T expected, T actual) throws AssertEqualsException {

		if (expected.equals(actual)) {
			return true;
		}

		throw new AssertEqualsException(" Expected: " + expected + ", but got actual: " + actual);
	}

	private <T> boolean assertNull(T actual) throws AssertEqualsException {

		if (actual == null) {
			return true;
		}

		throw new AssertEqualsException(" Expected: null" + ", but got actual: " + actual);
	}

}

class AssertEqualsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AssertEqualsException(String message) {
		super(message);
	}
}
