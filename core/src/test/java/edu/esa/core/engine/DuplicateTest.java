package edu.esa.core.engine;

import edu.esa.core.errors.ErrorsData;
import edu.esa.core.structure.GraphStructureBuilder;
import edu.esa.core.structure.GraphStructureBuilderEngineWrapper;
import edu.esa.core.structure.GraphStructureBuilderImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class DuplicateTest {
    @Test
    public void testEmptyStructure(){
        ErrorsFinder errorsFinder = new DuplicatesFinder();

        prepareBuilder(errorsFinder);

        ErrorsData errorsData = findDuplicates(errorsFinder);

        assertTrue("There should be no inclusive found",
                errorsData.getInclusiveDuplicates().isEmpty());

        assertTrue("There should be no partial found",
                errorsData.getPartialDuplicates().isEmpty());

        assertTrue("There should be no complete found",
                errorsData.getCompleteDuplicates().isEmpty());
    }

    @Test
    public void testInclusiveDuplicatesExist(){
        ErrorsFinder errorsFinder = new DuplicatesFinder();

        prepareBuilder(errorsFinder)
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v3", "v1", "v4")
                .addRule("r3", "v5", "v3")
                .addRule("r4", "v5", "v3", "v4")
                .addRule("r5", "v8", "v1", "v6")
                .addRule("r6", "v8", "v1", "v6")
                .addRule("r7", "v8", "v1", "v6", "v7")
                .addRule("r8", "v8", "v6", "v5", "v7");

        Collection<String> expected1 = Arrays.asList("r3", "r4");
        Collection<String> expected2 = Arrays.asList("r5", "r7");
        Collection<String> expected3 = Arrays.asList("r6", "r7");

        ErrorsData errorsData = findDuplicates(errorsFinder);
        Collection<Collection<String>> actual = errorsData.getInclusiveDuplicates();

        assertTrue("There should be 3 inclusive duplicate pairs found, actual: " + actual, actual.size() == 3);
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected1,
                containsCollection(actual, expected1));
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected2,
                containsCollection(actual, expected2));
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected3,
                containsCollection(actual, expected3));
    }

    @Test
    public void testCompleteDuplicatesExist(){
        ErrorsFinder errorsFinder = new DuplicatesFinder();

        prepareBuilder(errorsFinder)
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v3", "v1", "v2")
                .addRule("r3", "v5", "v3")
                .addRule("r4", "v5", "v1", "v2")
                .addRule("r5", "v8", "v1", "v6", "v7")
                .addRule("r6", "v8", "v1", "v6", "v7")
                .addRule("r7", "v8", "v1", "v3")
                .addRule("r8", "v3", "v1", "v2");

        Collection<String> expected1 = Arrays.asList("r1", "r2", "r8");
        Collection<String> expected2 = Arrays.asList("r5", "r6");

        ErrorsData errorsData = findDuplicates(errorsFinder);
        Collection<Collection<String>> actual = errorsData.getCompleteDuplicates();

        assertTrue("There should be 2 complete duplicate pairs found, actual: " + actual, actual.size() == 2);
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected1,
                containsCollection(actual, expected1));
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected2,
                containsCollection(actual, expected2));
    }

    @Test
    public void testPartialDuplicatesExist(){
        ErrorsFinder errorsFinder = new DuplicatesFinder();

        prepareBuilder(errorsFinder)
                .addVertices("v1", "v2", "v3", "v4", "v5", "v6", "v7", "v8")
                .addRule("r1", "v3", "v1", "v2")
                .addRule("r2", "v3", "v1", "v4")
                .addRule("r3", "v5", "v3")
                .addRule("r4", "v5", "v1", "v4")
                .addRule("r5", "v5", "v3", "v6")
                .addRule("r6", "v8", "v1", "v6", "v7")
                .addRule("r7", "v8", "v1", "v3", "v6")
                .addRule("r8", "v3", "v1", "v2");

        Collection<String> expected1 = Arrays.asList("r1", "r2");
        Collection<String> expected2 = Arrays.asList("r6", "r7");
        Collection<String> expected3 = Arrays.asList("r8", "r2");

        ErrorsData errorsData = findDuplicates(errorsFinder);
        Collection<Collection<String>> actual = errorsData.getPartialDuplicates();

        assertTrue("There should be 3 inclusive duplicate pairs found, actual: " + actual, actual.size() == 3);
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected1,
                containsCollection(actual, expected1));
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected2,
                containsCollection(actual, expected2));
        assertTrue("Wrong duplicate pair found, actual: " + actual +"; expected: " + expected3,
                containsCollection(actual, expected3));
    }

    private GraphStructureBuilder prepareBuilder(ErrorsFinder errorsFinder){
        Collection<ErrorsFinder> finders = Collections.singletonList(errorsFinder);

        GraphStructureBuilder builder = new GraphStructureBuilderImpl();
        return new GraphStructureBuilderEngineWrapper(builder, finders);
    }

    private ErrorsData findDuplicates(ErrorsFinder errorsFinder){
        ErrorsData errorsData = new ErrorsData();
        errorsFinder.fillErrorsData(errorsData);
        return errorsData;
    }

    private boolean containsCollection(Collection<Collection<String>> source, Collection<String> value) {
        for(Collection<String> sourceCollection : source) {
            if(sourceCollection.containsAll(value)) {
                return true;
            }
        }
        return false;
    }
}
