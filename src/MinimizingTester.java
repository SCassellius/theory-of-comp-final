import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MinimizingTester {

    /**
     * This test is to check for basic DFA creation. It check that all states and transitions are capitalized
     * so that there's never an issue in equality checking. It checks for the removal of duplicate states
     * in the list of all states. It checks that duplicate states, accept states, and transition states are removed.
     * It verifies that the start, accept states, and transition states are in the list of all states, and that
     * they're all capitalized. It also verifies that the transitions occur only between states that exist, and
     * that the values are binary as is consistent with this programs alphabet.
     */
    @Test
    public void testBasicDFACreation(){
        String[] statesArray = new String[] {"a", "b", "b", "c", "d", "e"};
        List badStates = Arrays.asList(statesArray);

        String badStart = "a";

        String[] acceptsArray = new String[] {"d", "e", "e"};
        List badAccepts = Arrays.asList(acceptsArray);

        List<Transition> badTransitions = new ArrayList<Transition>();
        badTransitions.add(new Transition("a", "b", 0));
        badTransitions.add(new Transition("a", "b", 1));
        badTransitions.add(new Transition("b", "c", 0));
        badTransitions.add(new Transition("b", "c", 1));
        badTransitions.add(new Transition("c", "d", 0));
        badTransitions.add(new Transition("c", "d", 1));
        badTransitions.add(new Transition("d", "e", 0));
        badTransitions.add(new Transition("d", "e", 1));
        badTransitions.add(new Transition("e", "e", 0));
        badTransitions.add(new Transition("e", "e", 1));

        String[] goodStatesArray = new String[] {"A", "B", "C", "D", "E"};
        List goodStates = Arrays.asList(goodStatesArray);

        String goodStart = "A";

        String[] goodAcceptsArray = new String[] {"D", "E"};
        List goodAccepts = Arrays.asList(goodAcceptsArray);

        List<Transition> goodTransitions = new ArrayList<Transition>();
        List<Transition> transitions = new ArrayList<Transition>();
        goodTransitions.add(new Transition("a", "b", 0));
        goodTransitions.add(new Transition("a", "b", 1));
        goodTransitions.add(new Transition("b", "c", 0));
        goodTransitions.add(new Transition("b", "c", 1));
        goodTransitions.add(new Transition("c", "d", 0));
        goodTransitions.add(new Transition("c", "d", 1));
        goodTransitions.add(new Transition("d", "e", 0));
        goodTransitions.add(new Transition("d", "e", 1));
        goodTransitions.add(new Transition("e", "e", 0));
        goodTransitions.add(new Transition("e", "e", 1));

        DFA badDFA = new DFA(badStates, badStart, badAccepts, badTransitions);
        DFA goodDFA = new DFA(goodStates, goodStart, goodAccepts, goodTransitions);

        assertTrue(badDFA.equals(goodDFA), "DFAs should be equal, check constructor for errors");
    }

    /**
     * This test is to check for illegal transitions. An illegal transition is namely a state
     * that has more than one "0" or more than one "1" transition value.
     */
    @Test
    public void testForIllegalTransitions(){
        String[] statesArray = new String[] {"a", "b", "b", "c", "d", "e"};
        List states = Arrays.asList(statesArray);

        String start = "a";

        String[] acceptsArray = new String[] {"d", "e"};
        List accepts = Arrays.asList(acceptsArray);

        List<Transition> transitions = new ArrayList<Transition>();
        transitions.add(new Transition("a", "b", 0));
        transitions.add(new Transition("a", "b", 1));
        transitions.add(new Transition("b", "c", 0));
        transitions.add(new Transition("a", "e", 0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DFA DFA = new DFA(states, start, accepts, transitions);
        });

        String expectedMessage = "There is an illegal transition in the given list of transitions";
        assertTrue(expectedMessage.equals(exception.getMessage()), "The correct exception was not thrown");
    }

    /**
     * This test is to check for duplicate transitions in the given list of transitions.
     */
    @Test
    public void testForDuplicateTransitions(){
        String[] statesArray = new String[] {"a", "b", "b", "c", "d", "e"};
        List states = Arrays.asList(statesArray);

        String start = "a";

        String[] acceptsArray = new String[] {"d", "e"};
        List accepts = Arrays.asList(acceptsArray);

        List<Transition> transitions = new ArrayList<Transition>();
        transitions.add(new Transition("a", "b", 0));
        transitions.add(new Transition("a", "b", 1));
        transitions.add(new Transition("b", "c", 0));
        transitions.add(new Transition("a", "b", 0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DFA DFA = new DFA(states, start, accepts, transitions);
        });

        String expectedMessage = "There is a duplicate transition in the given list of transitions";
        assertTrue(expectedMessage.equals(exception.getMessage()), "The correct exception was not thrown");
    }

    /**
     * A helper method to determine if two transition tables are equal
     * @param one the first transition table
     * @param two the second transition table
     * @return true if the transition tables are equal, false otherwise
     */
    public boolean transitionTablesAreEqual(Map<String,String[]> one, Map<String,String[]> two){
        ArrayList<String> onesTT = new ArrayList<String>();
        ArrayList<String> twosTT = new ArrayList<String>();

        for (Map.Entry<String,String[]> entry : one.entrySet())
            onesTT.add(entry.getKey() + " " + entry.getValue()[0] + " " + entry.getValue()[1]);

        for (Map.Entry<String,String[]> entry : two.entrySet())
            twosTT.add(entry.getKey() + " " + entry.getValue()[0] + " " + entry.getValue()[1]);

        return onesTT.equals(twosTT);
    }

    /**
     * This test is to check for the creation of a valid transition table.
     */
    @Test
    public void testCreateTransitionTable(){
        String[] statesArray = new String[] {"a", "b", "b", "c", "d", "e"};
        List states = Arrays.asList(statesArray);

        String start = "a";

        String[] acceptsArray = new String[] {"d", "e"};
        List accepts = Arrays.asList(acceptsArray);

        List<Transition> transitions = new ArrayList<Transition>();
        transitions.add(new Transition("a", "b", 0));
        transitions.add(new Transition("a", "b", 1));
        transitions.add(new Transition("b", "c", 0));
        transitions.add(new Transition("b", "c", 1));
        transitions.add(new Transition("c", "d", 0));
        transitions.add(new Transition("c", "d", 1));
        transitions.add(new Transition("d", "e", 0));
        transitions.add(new Transition("d", "e", 1));
        transitions.add(new Transition("e", "e", 0));
        transitions.add(new Transition("e", "e", 1));

        DFA DFA = new DFA(states, start, accepts, transitions);

        Map<String,String[]> correctTransitionTable = new HashMap<String,String[]>();
        correctTransitionTable.put("A",new String[]{"B", "B"});
        correctTransitionTable.put("B",new String[]{"C", "C"});
        correctTransitionTable.put("C",new String[]{"D", "D"});
        correctTransitionTable.put("D",new String[]{"E", "E"});
        correctTransitionTable.put("E",new String[]{"E", "E"});

    assertTrue(transitionTablesAreEqual(DFA.transitionTable, correctTransitionTable));
    }

    /**
     * This test is to check that an error is thrown if the DFA is incomplete;
     * meaning that not every state has both a 0 and a 1 transition
     */
    @Test
    public void testIncompleteDFA(){
        String[] statesArray = new String[] {"a", "b", "b", "c", "d", "e"};
        List states = Arrays.asList(statesArray);

        String start = "a";

        String[] acceptsArray = new String[] {"d", "e"};
        List accepts = Arrays.asList(acceptsArray);

        List<Transition> transitions = new ArrayList<Transition>();
        transitions.add(new Transition("a", "b", 0));
        transitions.add(new Transition("a", "b", 1));
        transitions.add(new Transition("b", "c", 0));
        transitions.add(new Transition("b", "c", 1));
        transitions.add(new Transition("c", "d", 0));
        transitions.add(new Transition("c", "d", 1));
        transitions.add(new Transition("d", "e", 0));
        transitions.add(new Transition("d", "e", 1));
        transitions.add(new Transition("e", "e", 0));
        // this will be the error -> transitions.add(new Transition("e", "e", 1));


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DFA DFA = new DFA(states, start, accepts, transitions);
        });

        String expectedMessage = "A given state does not have either a 0 or a 1 transition, or both";
        assertTrue(expectedMessage.equals(exception.getMessage()), "The correct exception was not thrown");
    }

    /**
     * This test is to check that a list of strings is correctly
     * separated into a list of list of strings to store the levels
     * of equivalence during DFA minimization
     */
    @Test
    public void testOpenEquivalenceTree(){
        final String SEPARATOR = "...";
        String[] toadd = new String[]{"D", "E", SEPARATOR, "A", "B", "S", "C"};
        List<String> complied = Arrays.asList(toadd);
        List<List> opened = DFA.openEquivalenceTree(complied);

        String correctAnswer = "DE...ABSC";
        String answerToTest = "";
        for(List<String> list : opened){
            for(String str: list){
                answerToTest+= str;
            }
            answerToTest += SEPARATOR;
        }
        answerToTest = answerToTest.substring(0, answerToTest.length() - 3);

        assertEquals(correctAnswer, answerToTest);
    }

    /**
     * This is the first minimization test, it is modeled after the in class example
     * from week 3, lecture 6. The example can been seen in its entirety in the README
     */
    @Test
    public void firstDFAMinimizationTest(){
        String[] statesArray = new String[] {"S", "A", "B", "C", "D", "E"};
        List largeStates = Arrays.asList(statesArray);

        String largeStart = "S";

        String[] acceptsArray = new String[] {"D", "E"};
        List largeAccepts = Arrays.asList(acceptsArray);

        List<Transition> largeTransitions = new ArrayList<Transition>();
        largeTransitions.add(new Transition("S", "A", 0));
        largeTransitions.add(new Transition("S", "B", 1));
        largeTransitions.add(new Transition("A", "C", 0));
        largeTransitions.add(new Transition("A", "D", 1));
        largeTransitions.add(new Transition("B", "B", 0));
        largeTransitions.add(new Transition("B", "C", 1));
        largeTransitions.add(new Transition("C", "B", 0));
        largeTransitions.add(new Transition("C", "C", 1));
        largeTransitions.add(new Transition("D", "D", 0));
        largeTransitions.add(new Transition("D", "E", 1));
        largeTransitions.add(new Transition("E", "E", 0));
        largeTransitions.add(new Transition("E", "E", 1));

        DFA largeDFA = new DFA(largeStates, largeStart, largeAccepts, largeTransitions);

        String[] minStatesArray = new String[] {"S", "A", "BC", "DE"};
        List minStates = Arrays.asList(minStatesArray);

        String minStart = "S";

        String[] minAcceptsArray = new String[] {"DE"};
        List minAccepts = Arrays.asList(minAcceptsArray);

        List<Transition> minTransitions = new ArrayList<Transition>();
        minTransitions.add(new Transition("S", "A", 0));
        minTransitions.add(new Transition("S", "BC", 1));
        minTransitions.add(new Transition("A", "BC", 0));
        minTransitions.add(new Transition("A", "DE", 1));
        minTransitions.add(new Transition("BC", "BC", 0));
        minTransitions.add(new Transition("BC", "BC", 1));
        minTransitions.add(new Transition("DE", "DE", 0));
        minTransitions.add(new Transition("DE", "DE", 1));

        DFA minimizedDFA = new DFA(minStates, minStart, minAccepts, minTransitions);

        assertTrue(minimizedDFA.equals(largeDFA.minimizeDFA(largeDFA)));
    }


}
