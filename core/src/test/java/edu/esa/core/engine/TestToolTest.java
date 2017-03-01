package edu.esa.core.engine;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestToolTest {
    private static TestTool testTool = new TestTool();

    @Test
    public void testChainsEqual1(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));

        assertTrue("Chains should be equal", testTool.checkChainsEqual(chain1, chain2));
    }

    @Test
    public void testChainsEqual2(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new LinkedList<>(Arrays.asList("r1", "r2", "r3"));

        assertTrue("Chains should be equal", testTool.checkChainsEqual(chain1, chain2));
    }

    @Test
    public void testChainsNotEqual1(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r2", "r3", "r1"));

        assertFalse("Chains should not be equal", testTool.checkChainsEqual(chain1, chain2));
    }

    @Test
    public void testChainsNotEqual2(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r4", "r5", "r1"));

        assertFalse("Chains should not be equal", testTool.checkChainsEqual(chain1, chain2));
    }

    @Test
    public void testChainsNotEqual3(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r3", "r2", "r1"));

        assertFalse("Chains should not be equal", testTool.checkChainsEqual(chain1, chain2));
    }

    @Test
    public void testChainsEqualForCycle1(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));

        assertTrue("Chains should be equal", testTool.checkChainsEqualForCycle(chain1, chain2));
    }

    @Test
    public void testChainsEqualForCycle2(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new LinkedList<>(Arrays.asList("r1", "r2", "r3"));

        assertTrue("Chains should be equal", testTool.checkChainsEqualForCycle(chain1, chain2));
    }

    @Test
    public void testChainsEqualForCycle3(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r3", "r1", "r2"));

        assertTrue("Chains should be equal", testTool.checkChainsEqualForCycle(chain1, chain2));
    }

    @Test
    public void testChainsNotEqualForCycle1(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r4", "r5", "r1"));

        assertFalse("Chains should not be equal", testTool.checkChainsEqualForCycle(chain1, chain2));
    }

    @Test
    public void testChainsNotEqualForCycle2(){
        List<String> chain1 = new ArrayList<>(Arrays.asList("r1", "r2", "r3"));
        List<String> chain2 = new ArrayList<>(Arrays.asList("r3", "r2", "r1"));

        assertFalse("Chains should not be equal", testTool.checkChainsEqualForCycle(chain1, chain2));
    }
}
