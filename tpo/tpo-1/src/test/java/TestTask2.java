import org.example.Task2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestTask2 {

    private Task2 hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new Task2(5);
    }

    @Test
    void testSimpleInsert() {
        hashTable.put(1, "A");

        List<String> expected = Arrays.asList("START", "INSERT");
        assertEquals(expected, hashTable.trace);
    }

    @Test
    void testSimpleCollision() {
        hashTable.put(1, "A");
        hashTable.clearTrace();

        hashTable.put(6, "B");

        List<String> expected = Arrays.asList("START", "COLLISION", "INSERT");
        assertEquals(expected, hashTable.trace);
    }

    @Test
    void testKeyUpdate() {
        hashTable.put(1, "A");
        hashTable.clearTrace();

        hashTable.put(1, "New A");

        List<String> expected = Arrays.asList("START", "MATCH");
        assertEquals(expected, hashTable.trace);
    }

    @Test
    void testComplexChain() {
        hashTable.put(1, "A");
        hashTable.put(2, "B");
        hashTable.clearTrace();

        hashTable.put(11, "C");

        List<String> expected = Arrays.asList("START", "COLLISION", "COLLISION", "INSERT");
        assertEquals(expected, hashTable.trace);
    }

    @Test
    void testTableFull() {
        hashTable.put(0, "0");
        hashTable.put(1, "1");
        hashTable.put(2, "2");
        hashTable.put(3, "3");
        hashTable.put(4, "4");
        hashTable.clearTrace();

        hashTable.put(5, "FAIL");

        List<String> expected = Arrays.asList(
                "START",
                "COLLISION", "COLLISION", "COLLISION", "COLLISION", "COLLISION",
                "FULL"
        );
        assertEquals(expected, hashTable.trace);
    }
}