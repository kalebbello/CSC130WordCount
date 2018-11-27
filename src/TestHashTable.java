// Authors: Kaleb Bello and Alexis Lozano
/**
 * Unit tests for the hashtable {@code DataCounter}.
 */
public class TestHashTable {
    public static void main(String[] args) {
        HashTable hash = new HashTable(10);

        String[] data = {"tom", "hello there", "tom", "tom", "hello there", "javadoc", "data structures", "hello there",
                "javadoc", "dumbo", "computer science", "project3", "computer science", "project3", "kaleb", "sky", "kaleb", "dumbo"};

        DataCount<String> s = new DataCount<>("hello there", 3);
        DataCount<String> s1 = new DataCount<>("tom", 3);
        DataCount<String> s2 = new DataCount<>("sky", 1);
        DataCount<String> s3 = new DataCount<>("computer science", 2);
        DataCount<String> s4 = new DataCount<>("project3", 2);
        DataCount<String> s5 = new DataCount<>("javadoc", 2);
        DataCount<String> s6 = new DataCount<>("data structures", 1);
        DataCount<String> s7 = new DataCount<>("dumbo", 2);
        DataCount<String> s8 = new DataCount<>("kaleb", 2);


        DataCount[] expected = {s, s1, s2, s3, s4, s5, s6, s7, s8};

        boolean error = false;

        for(String d : data) {
            hash.incCount(d);
        }

        DataCount<String>[] dataCounts = hash.getCounts();
        if(dataCounts.length != expected.length) {
            error = true;
        } else {
            int k = 0;
            for(DataCount<String> c : dataCounts) {
                if(!c.data.equals(expected[k].data) || c.count != expected[k].count) {
                    error = true;
                    break;
                }
                k++;
            }
        }

        if(error) {
            System.out.println("Test failed");
        } else {
            System.out.println("Test passed");
        }

    }
}
