import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyHashTable<K, V> implements Iterable<HashPair<K, V>> {
	// num of entries to the table
	private int numEntries;
	// num of buckets
	private int numBuckets;
	// load factor needed to check for rehashing
	private static final double MAX_LOAD_FACTOR = 0.75;
	// ArrayList of buckets. Each bucket is a LinkedList of HashPair
	private ArrayList<LinkedList<HashPair<K, V>>> buckets;

	// constructor
	public MyHashTable(int initialCapacity) {
		// ADD YOUR CODE BELOW THIS

		// The capacity is the number of buckets in the hash table, and the initial
		// capacity is simply the capacity at the
		// time the hash table is created
		this.numBuckets = initialCapacity;
		this.numEntries = 0;
		// this.numEntries = (int) (MAX_LOAD_FACTOR * numBuckets);
		buckets = new ArrayList<LinkedList<HashPair<K, V>>>();

		for (int i = 0; i < initialCapacity; i++) {
			buckets.add(new LinkedList<HashPair<K, V>>());
		}

		// ADD YOUR CODE ABOVE THIS
	}

	public int size() {
		return this.numEntries;
	}

	public boolean isEmpty() {
		return this.numEntries == 0;
	}

	public int numBuckets() {
		return this.numBuckets;
	}

	/**
	 * Returns the buckets variable. Useful for testing purposes.
	 */
	public ArrayList<LinkedList<HashPair<K, V>>> getBuckets() {
		return this.buckets;
	}

	/**
	 * Given a key, return the bucket position for the key.
	 */
	public int hashFunction(K key) {
		int hashValue = Math.abs(key.hashCode()) % this.numBuckets;
		return hashValue;
	}

	/**
	 * Takes a key and a value as input and adds the corresponding HashPair to this
	 * HashTable. Expected average run time O(1)
	 */
	public V put(K key, V value) {
		// ADD YOUR CODE BELOW HERE
		if (value == null || key == null) {
			return null;
		}

		// Get the corresponding buckets
		int hashValue = hashFunction(key);
		LinkedList<HashPair<K, V>> bucket = buckets.get(hashValue);
		int sameKeyAt = -1;
		V ourValue = null;

		// look for the same key
		for (int i = 0; i < bucket.size(); i++) {
			if (bucket.get(i).getKey().equals(key)) {
				sameKeyAt = i;
				break;
			}
		}

		// sameKey ? store old value AND replace value : add new HashPair
		if (sameKeyAt != -1) {
			ourValue = bucket.get(sameKeyAt).getValue();
			bucket.get(sameKeyAt).setValue(value);
		} else {
			bucket.add(new HashPair<K, V>(key, value));
			numEntries++;
		}

		// Check for rehashing
		if (((double) numEntries / (double) numBuckets) > MAX_LOAD_FACTOR) {
			rehash();
		}

		return ourValue;
		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Get the value corresponding to key. Expected average runtime O(1)
	 */

	public V get(K key) {
		// ADD YOUR CODE BELOW HERE

		if (key == null) {
			return null;
		}

		int hashValue = hashFunction(key);

		LinkedList<HashPair<K, V>> bucket = buckets.get(hashValue);

		for (int i = 0; i < bucket.size(); i++) {

			if (bucket.get(i).getKey().equals(key)) {
				return bucket.get(i).getValue();
			}
		}

		return null;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Remove the HashPair corresponding to key . Expected average runtime O(1)
	 */
	public V remove(K key) {
		// ADD YOUR CODE BELOW HERE
		if (key == null) {
			return null;
		}

		int hashValue = hashFunction(key);
		LinkedList<HashPair<K, V>> bucket = buckets.get(hashValue);
		V ourValue = null;

		for (int i = 0; i < bucket.size(); i++) {

			if (bucket.get(i).getKey().equals(key)) {
				ourValue = bucket.get(i).getValue();
				bucket.remove(i);
				numEntries--;
				break;
			}
		}

		return ourValue;
		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Method to double the size of the hashtable if load factor increases beyond
	 * MAX_LOAD_FACTOR. Made public for ease of testing. Expected average runtime is
	 * O(m), where m is the number of buckets
	 */
	public void rehash() {
		// ADD YOUR CODE BELOW HERE
		numBuckets = numBuckets * 2;
		numEntries = 0;

		ArrayList<LinkedList<HashPair<K, V>>> newList = buckets;
		buckets = new ArrayList<LinkedList<HashPair<K, V>>>();

		for (int i = 0; i < numBuckets; i++) {
			buckets.add(new LinkedList<HashPair<K, V>>());
		}

		for (int i = 0; i < newList.size(); i++) {
			for (int j = 0; j < newList.get(i).size(); j++) {
				put(newList.get(i).get(j).getKey(), newList.get(i).get(j).getValue());
			}
		}

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Return a list of all the keys present in this hashtable. Expected average
	 * runtime is O(m), where m is the number of buckets
	 */

	public ArrayList<K> keys() {
		// ADD YOUR CODE BELOW HERE

		ArrayList<K> myList = new ArrayList<K>();

		for (int i = 0; i < buckets.size(); i++) {
			for (int j = 0; j < buckets.get(i).size(); j++) {
				myList.add(buckets.get(i).get(j).getKey());
			}
		}

		return myList;

		// ADD YOUR CODE ABOVE HERE
	}

	/**
	 * Returns an ArrayList of unique values present in this hashtable. Expected
	 * average runtime is O(m) where m is the number of buckets
	 */
	public ArrayList<V> values() {
		// ADD CODE BELOW HERE
		ArrayList<V> myList = new ArrayList<V>();

		for (int i = 0; i < buckets.size(); i++) {
			
			for (int j = 0; j < buckets.get(i).size(); j++) {
				//if (!myList.contains(buckets.get(i).get(j).getValue())) {
					myList.add(buckets.get(i).get(j).getValue());
				//}
			}
			
		}

		return myList;

		// ADD CODE ABOVE HERE
	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n^2), where n is the number of pairs
	 * in the map.
	 */
	public static <K, V extends Comparable<V>> ArrayList<K> slowSort(MyHashTable<K, V> results) {
		ArrayList<K> sortedResults = new ArrayList<>();
		for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
			while (i >= 0) {
				toCompare = results.get(sortedResults.get(i));
				if (element.compareTo(toCompare) <= 0)
					break;
				i--;
			}
			sortedResults.add(i + 1, toAdd);
		}
		return sortedResults;
	}

	/**
	 * This method takes as input an object of type MyHashTable with values that are
	 * Comparable. It returns an ArrayList containing all the keys from the map,
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number of
	 * pairs in the map.
	 */

	public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {

		// ADD CODE BELOW HERE
		ArrayList<HashPair<K, V>> resultsArray = new ArrayList<>();
		ArrayList<K> out = new ArrayList<>();

		for (HashPair<K, V> entry : results) {
			resultsArray.add(entry);
		}

		ArrayList<HashPair<K, V>> sorted = mergeSort(resultsArray);

		for (HashPair<K, V> entry : sorted) {
			K toAdd = entry.getKey();
			out.add(toAdd);
		}

		return out;
		// ADD CODE ABOVE HERE
	}

	private static <K, V extends Comparable<V>> ArrayList<HashPair<K, V>> mergeSort(
			ArrayList<HashPair<K, V>> resultsArray) {
		// TODO Auto-generated method stub

		if (resultsArray.size() == 1) {
			return resultsArray;
		} else {
			int mid = (resultsArray.size() - 1) / 2;
			ArrayList<HashPair<K, V>> lower = partition(0, mid, resultsArray);
			ArrayList<HashPair<K, V>> upper = partition(mid + 1, resultsArray.size() - 1, resultsArray);
			lower = mergeSort(lower);
			upper = mergeSort(upper);
			return merge(lower, upper);
		}
	}

	private static <K, V extends Comparable<V>> ArrayList<HashPair<K, V>> merge(ArrayList<HashPair<K, V>> lower,
			ArrayList<HashPair<K, V>> upper) {

		// 1- make a new list
		ArrayList<HashPair<K, V>> outList = new ArrayList<>();
		// 2- while look until a list if empty
		while (!lower.isEmpty() && !upper.isEmpty()) {

			if (lower.get(0).getValue().compareTo(upper.get(0).getValue()) >= 0) {
				outList.add(lower.remove(0));
			} else {
				outList.add(upper.remove(0));
			}

		}

		while (!lower.isEmpty()) {
			outList.add(lower.remove(0));
		}

		while (!upper.isEmpty()) {
			outList.add(upper.remove(0));
		}

		return outList;
	}

	private static <K, V extends Comparable<V>> ArrayList<HashPair<K, V>> partition(int start, int end,
			ArrayList<HashPair<K, V>> resultsArray) {
		ArrayList<HashPair<K, V>> out = new ArrayList<>();

		for (int i = start; i <= end; i++) {
			out.add(resultsArray.get(i));
		}

		return out;
	}

	@Override
	public MyHashIterator iterator() {
		return new MyHashIterator();
	}

	private class MyHashIterator implements Iterator<HashPair<K, V>> {
		// ADD YOUR CODE BELOW HERE

		HashPair<K, V> current;
		//ArrayList<HashPair<K, V>> myList = new ArrayList<HashPair<K, V>>();
		LinkedList<HashPair<K,V>> myList2 = new LinkedList<HashPair<K,V>>();
		// ADD YOUR CODE ABOVE HERE

		/**
		 * Expected average runtime is O(m) where m is the number of buckets
		 */
		private MyHashIterator() {
			// ADD YOUR CODE BELOW HERE

			for (int i = 0; i < buckets.size(); i++) {
				for (int j = 0; j < buckets.get(i).size(); j++) {
					myList2.add(buckets.get(i).get(j));
				}
			}

			// ADD YOUR CODE ABOVE HERE
		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public boolean hasNext() {
			// ADD YOUR CODE BELOW HERE
			return !myList2.isEmpty();

			// ADD YOUR CODE ABOVE HERE
		}

		@Override
		/**
		 * Expected average runtime is O(1)
		 */
		public HashPair<K, V> next() {
			// ADD YOUR CODE BELOW HERE
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			current = myList2.removeFirst();
			return current;
			// ADD YOUR CODE ABOVE HERE
		}

	}
}
