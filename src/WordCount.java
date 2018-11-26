import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
/**
 * An executable that counts the words in a files and prints out the counts in
 * descending order. You will need to modify this file.
 */
public class WordCount {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Usage: [-b | -a | -h] [-frequency | -num_unique] <filename>\n");
            System.out.println("-b - Use an Unbalanced BST");
            System.out.println("-a - Use an AVL Tree");
            System.out.println("-h - Use a Hashtable\n");
            System.out.println("-frequency - Print all the word/frequency pairs, " +
                               "ordered by frequency, and then by the words in" +
                               "lexicographic order.\n");
            System.out.println("-num_unique - Print the number of unique words in the document. " +
                               "This is the total number of distinct (different) words in the document. \n" +
                               "Words that appear more than once are only counted as a single word for " +
                               "this statistic");
        }

        System.out.println(args[0]);
        try {
            switch(args[1]) {
                case "-frequency":
                    countWordFrequencies(countWords(args[0], args[2]));
                    break;
                case "-num_unique":
                    countUniqueWords(countWords(args[0], args[2]));
                    break;
                default:
                    System.out.println("Argument 2 invalid");
                    break;
            }
        } catch(IOException e) {
            System.out.println("ERROR: could not read file");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Counts the words in a document.
     *
     * @return An array of data couns
     * @throws IOException Thrown if there's an exception wile reading
     */
    public static DataCount<String>[] countWords(String dataStructure, String filename) throws IOException {
        FileWordReader fileWordReader = new FileWordReader(filename);
        DataCounter<String> wordCounter;

        switch(dataStructure) {
            case "-b": wordCounter = new BinarySearchTree<>();
                break;
            case "-a": wordCounter = new AVLTree<>();
                break;
            case "-h": wordCounter = new HashTable();
                break;
            default:
                wordCounter = new BinarySearchTree<>();
                System.out.println("Invalid data structure. BST Default");
                break;
        }

        String word;
        while((word = fileWordReader.nextWord()) != null) {
            wordCounter.incCount(word);
        }

        return wordCounter.getCounts();
    }

    /**
     * Prints the word counts given an array of data counts.
     *
     * @param dataCounts Data counts
     * @param <E>        Some type
     */
    private static <E> void printWordCounts(DataCount<E>[] dataCounts) {
        Arrays.stream(dataCounts)
              .forEach(count -> {
                  System.out.format("%d %s\n", count.count, count.data);
              });
    }

    /**
     * Counts the word frequencies in a document and lists them
     * first by frequency, then lexicographically.
     */
    private static void countWordFrequencies(DataCount<String>[] dataCounts) {
        Heapsort(dataCounts, (count1, count2) -> count2.count - count1.count);

        System.out.println("Ordered by Frequency:");
        printWordCounts(dataCounts);

        Heapsort(dataCounts, (count1, count2) -> count1.data.compareTo(count2.data));

        System.out.println("\nOrdered Lexicographically:");
        printWordCounts(dataCounts);
    }

    /**
     * Prints the number of unique words in a document.
     */
    private static void countUniqueWords(DataCount<String>[] dataCounts) {
        System.out.println("Unique words: " + dataCounts.length);
    }

    /**
     * Implementation of merge sort algorithm. Sorts an array of data counts
     * using a comparator.
     *
     * @param dataCounts The array of data counts
     * @param comparator The comparator to compare data counts
     * @param <E>        Some type
     */
   /* private static <E> void sort(DataCount<E>[] dataCounts, Comparator<DataCount<E>> comparator) {
        if(dataCounts.length > 1) {
            int mid = dataCounts.length / 2;
            DataCount<E>[] left = Arrays.copyOfRange(dataCounts, 0, mid);
            DataCount<E>[] right = Arrays.copyOfRange(dataCounts, mid, dataCounts.length);

            sort(left, comparator);
            sort(right, comparator);
            merge(dataCounts, left, right, comparator);
        }
    }*/

    /**
     * Merges the left and right data counts back into the original array.
     * It's assumed that {@code left + right = dataCounts}.
     *
     * @param dataCounts The original array
     * @param left       The left side of the original array
     * @param right      The right side of the original array
     * @param comparator The comparator to compare data counts
     * @param <E>        Some type
     */
   /* private static <E> void merge(DataCount<E>[] dataCounts,
                                  DataCount<E>[] left, DataCount<E>[] right,
                                  Comparator<DataCount<E>> comparator) {
        int i = 0, j = 0;
        for(int k = 0; k < dataCounts.length; k++) {
            if(i < left.length && j < right.length) {
                if(comparator.compare(left[i], right[j]) <= 0) {
                    dataCounts[k] = left[i++];
                } else {
                    dataCounts[k] = right[j++];
                }
            } else if(i < left.length) {
                dataCounts[k] = left[i++];
            } else if(j < right.length) {
                dataCounts[k] = right[j++];
            }
        }
    }*/

    // ********************** HEAP SORT *************************

    private static <E> void Heapsort(DataCount<E>[] dataCounts, Comparator<DataCount<E>> comparator)
    {

        int n = dataCounts.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(dataCounts, n, i);

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end

            DataCount<E> temp = dataCounts[0];
            dataCounts[0] = dataCounts[i];
            dataCounts[i] = temp;

            // call max heapify on the reduced heap
            heapify(dataCounts, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private static <E> void heapify(DataCount<E>[] arr, int n, int i)
    {
        int smallest = i; // Initialize smallest as root
        int l = 2*i + 1; // left = 2*i + 1
        int r = 2*i + 2; // right = 2*i + 2

        // If left child is smaller than root
        if (l < n && arr[l] < arr[smallest]) {
            smallest = l;
        }

        // If right child is smaller than smallest so far
        if (r < n && arr[r] < arr[smallest])
            smallest = r;

        // If smallest is not root
        if (smallest != i)
        {
            DataCount<E> swap = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, smallest);
        }
    }

}