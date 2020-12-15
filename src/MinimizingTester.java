import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MinimizingTester {

    @Test
    public void testBasicDFACreation(){
        String[] statesArray = new String[] {"a", "b", "b", "c", "d", "e"};
        List badStates = Arrays.asList(statesArray);

        String badStart = "a";

        String[] acceptsArray = new String[] {"d", "e"};
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

}
