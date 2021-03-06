import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RangeTest {

    private Range range;

    @BeforeEach
    void setUp() {
        range = new RangeImpl(5L, 15L);
    }

    @Test
    void testThatConstructorThrowsWhenInputBordersInWrongOrder() {
        assertThrows(IllegalArgumentException.class, () -> new RangeImpl(15L, 5L));
    }

    @Test
    void testThatIsBeforeWorksCorrectOnSingleElementRange() {
        RangeImpl testingRange = new RangeImpl(-5L, -5L);
        assertTrue(testingRange.isBefore(range));
        assertFalse(range.isBefore(testingRange));
    }

    @Test
    public void testThatIsBeforeWorksCorrectly() {
        Range testingRange = new RangeImpl(20L, 30L);
        assertTrue(range.isBefore(testingRange));
        assertFalse(testingRange.isBefore(range));
    }

    @Test
    void testThatLowerBoundWorksCorrect() {
        assertThat(range.getLowerBound(), is(5L));
    }

    @Test
    void testThatUpperBoundWorksCorrect() {
        assertThat(range.getUpperBound(), is(15L));
    }

    @Test
    public void testThatIsAfterWorksCorrectly() {
        Range testingRange = new RangeImpl(20L, 30L);
        assertTrue(testingRange.isAfter(range));
        assertFalse(range.isAfter(testingRange));
    }

    @Test
    void testThatIsBeforeWorksCorrectOnSameRange() {
        assertFalse(range.isBefore(range));
    }

    @Test
    void testThatIsAfterWorksCorrectOnSameRange() {
        assertFalse(range.isAfter(range));
    }

    @Test
    void testThatIsConcurrentWorksCorrectOnLowerBound() {
        assertTrue(range.isConcurrent(new RangeImpl(3L, 7L)));
    }

    @Test
    void testThatIsConcurrentWorksCorrectOnUpperBound() {
        assertTrue(range.isConcurrent(new RangeImpl(7L, 20L)));
    }

    @Test
    void testThatIsConcurrentWorksCorrectOnInnerRange() {
        assertTrue(range.isConcurrent(new RangeImpl(7L, 10L)));
    }

    @Test
    void testThatIsConcurrentWorksCorrectOnOuterRange() {
        assertTrue(range.isConcurrent(new RangeImpl(3L, 20L)));
    }

    @Test
    void testThatIsConcurrentWorksCorrectOnUnconcurrentRangeAfter() {
        assertFalse(range.isConcurrent(new RangeImpl(17L, 20L)));
    }

    @Test
    void testThatIsConcurrentWorksCorrectOnUnconcurrentRangeBefore() {
        assertFalse(range.isConcurrent(new RangeImpl(3L, 4L)));
    }

    @Test
    void testThatRangeContainsValue() {
        assertTrue(range.contains(7));
        assertFalse(range.contains(0));
        assertFalse(range.contains(20));
    }

    @Test
    void testThatRangeContainsBorderValues() {
        assertTrue(range.contains(5));
        assertTrue(range.contains(15));
    }

    @Test
    void testThatAsListReturnsCorrectList() {
        List<Long> longs = range.asList();

        assertThat(longs.size(), is(11));
        for (int i = 5; i <= 15; i++) {
            assertThat(longs.get(i - 5), is((long) i));
        }
    }

    @Test
    void testThatIteratorWorksCorrect() {
        Iterator<Long> longIterator = range.asIterator();
        for (int i = 5; i <= 15; i++) {
            assertThat(longIterator.next(), is((long) i));
        }
    }
}
