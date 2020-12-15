import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        String[] goodStatesArray = new String[] {"A", "B", "C", "D", "E"};
        List goodStates = Arrays.asList(goodStatesArray);

        String goodStart = "A";

        String[] goodAcceptsArray = new String[] {"D", "E"};
        List goodAccepts = Arrays.asList(goodAcceptsArray);

        List<Transition> goodTransitions = new ArrayList<Transition>();
        goodTransitions.add(new Transition("A", "B", 0));
        goodTransitions.add(new Transition("A", "B", 1));
        goodTransitions.add(new Transition("B", "C", 0));

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

}
