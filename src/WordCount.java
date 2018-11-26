// Authors: Kaleb Bello and Alexis Lozano
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

    // ********************** HEAP SORT *************************

    private static <E> void Heapsort(DataCount<E>[] dataCounts, Comparator<DataCount<E>> comparator)
    {
        int n = dataCounts.length;

        // Build heap (rearrange array)
        for (int i = (n / 2) - 1; i >= 0; i--)
            heapify(dataCounts, n, i, comparator);

        // One by one extract an element from heap
        for (int i=n-1; i>=0; i--)
        {
            // Move current root to end

            DataCount<E> temp = dataCounts[0];
            dataCounts[0] = dataCounts[i];
            dataCounts[i] = temp;

            // call max heapify on the reduced heap
            heapify(dataCounts, i, 0, comparator);
        }
    }

    // To heapify a subtree rooted with node i
    private static <E> void heapify(DataCount<E>[] arr, int n, int i, Comparator<DataCount<E>> comparator)
    {
        int largest = i; // Initialize largest as root
        int l = 2*i + 1;
        int r = 2*i + 2;

        // Check if left is larger than root
        if (l < n && comparator.compare(arr[l], arr[largest]) > 0 ) {
            largest = l;
        }

        // Check if right is larger than root
        if (r < n && comparator.compare(arr[r], arr[largest]) > 0)
            largest = r;

        // If largest is not root
        if (largest != i)
        {
            DataCount<E> swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify necessary sub-trees
            heapify(arr, n, largest ,comparator);
        }
    }

}