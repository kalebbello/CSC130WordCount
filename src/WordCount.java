import java.io.IOException;

/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {

    private static void countWords(String file) {
        DataCounter<String> counter = new BinarySearchTree<String>();

        try {
            FileWordReader reader = new FileWordReader(file);
            String word = reader.nextWord();
            while (word != null) {
                counter.incCount(word);
                word = reader.nextWord();
            }
        } catch (IOException e) {
            System.err.println("Error processing " + file + e);
            System.exit(1);
        }

        DataCount<String>[] counts = counter.getCounts();
        sortByDescendingCount(counts);
        for (DataCount<String> c : counts)
            System.out.println(c.count + " \t" + c.data);
    }

    /**
     * TODO Replace this comment with your own.
     * 
     * Sort the count array in descending order of count. If two elements have
     * the same count, they should be in alphabetical order (for Strings, that
     * is. In general, use the compareTo method for the DataCount.data field).
     * 
     * This code uses insertion sort. You should modify it to use a heap sort
     * sorting algorithm. NOTE: the current code assumes the array starts in
     * alphabetical order! You'll need to make your code deal with unsorted
     * arrays.
     * 
     * The generic parameter syntax here is new, but it just defines E as a
     * generic parameter for this method, and constrains E to be Comparable. You
     * shouldn't have to change it.
     * 
     * @param counts array to be sorted.
     */
    private static <E extends Comparable<? super E>> void sortByDescendingCount(
            DataCount<E>[] counts) {
        for (int i = 1; i < counts.length; i++) {
            DataCount<E> x = counts[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                if (counts[j].count >= x.count) {
                    break;
                }
                counts[j + 1] = counts[j];
            }
            counts[j + 1] = x;
        }
    }

    /**
     * TODO add more options 
     * 
     * The commandline structure should be:
	 *	Usage: java Correlator [ -b | -a | -h ] <filename1> <filename2>
	 *		• -b Use an Unbalanced BST in the backend
	 *		• -a Use an AVL Tree in the backend
	 *		• -h Use a Hashtable in the backend
	 *
	 *@param args Files to utilize the word count program (Hamet, New Atlantis)
     */
    public static void main(String[] args) {
    	if (args.length = 1) {
    		countWords(args[1])
    		System.exit(1);
    	}
        if (args[0] != -b | args[0] != -a | args[0] != -h) {
            System.err.println("Usage: java Correlator [ -b | -a | -h ] <filename1> <filename2>");
            System.exit(1);
        }
   
        switch (args[0])) {
        	case '-b': countWords(args[1]);
        		break;
        	case '-a':
        		break;
        	case '-h':
        		break;
        		
        		
        }
    }
}
