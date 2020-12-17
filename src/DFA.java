import java.util.*;

public class DFA{
    public Set<String> listAllStates;
    public String startState;
    public Set<String> listAcceptStates;
    public Set<Transition> listTransitions;
    public Map<String,String[]> transitionTable;

    /**
     * Models a DFA so that I have something to minimize.
     * The helper methods insure that a valid, complete, and binary DFA is created
     * @param listStates the set of all states in the DFA, each denoted by a String
     * @param startState the start state for the DFA
     * @param acceptStates the set of accept states for the DFA, each denoted by a string
     * @param transitions the transitions for the DFA, each a tuple called a Transition,
     *                    containing a start state, an end state, and a binary value
     */
    DFA(List<String> listStates, String startState, List<String> acceptStates, List<Transition> transitions) {
        this.listAllStates = cleanStates(listStates);
        this.startState = verifyStartState(startState);
        this.listAcceptStates = verifyAcceptStates(acceptStates);
        this.listTransitions = verifyBasicTransitions(transitions);
        testForIllegalTransitions(listTransitions);
        this.transitionTable = createTransitionTable(this.listAllStates, this.listTransitions);
    }

    /**
     * Removes duplicate states and capitalizes all entries
     * @param listStates the list total list of states
     * @return a cleaned and capitalized set of states
     */
    private Set<String> cleanStates(List<String> listStates){
        listAllStates = new HashSet<String>();
        for(String str: listStates){
            listAllStates.add(str.toUpperCase());
        }
        return listAllStates;
    }

    /**
     * Verifies the start state is in the set of all states, and capitalizes it
     * @param startState a state
     * @return a capitalized, valid state
     */
    private String verifyStartState(String startState){
        startState = startState.toUpperCase();
        if(!this.listAllStates.contains(startState)) throw new IllegalArgumentException("Start state does not exist in the given list of all states");
        return startState;
    }

    /**
     * Verifies all accept states exist in the set of all states, removes duplicates, and capitalizes them
     * @param acceptStates the list of accept states
     * @return a cleaned and capitalized set of accept states
     */
    private Set<String> verifyAcceptStates(List<String> acceptStates){
        listAcceptStates = new HashSet<String>();
        for(String str: acceptStates){
            listAcceptStates.add(str.toUpperCase());
            if(!listAllStates.contains(str.toUpperCase())) throw new IllegalArgumentException("A given accept state does not exist in the given list of all states");
        }
        return listAcceptStates;
    }

    /**
     * Verifies that the transitions occur only between states that exist, that the values are binary,
     * that there are no duplicate transitions, and capitalizes them
     * @param transitions the list of transitions
     * @return a cleaned and capitalized set of transitions
     */
    private Set<Transition> verifyBasicTransitions(List<Transition> transitions){
        Set<Transition> listTransitions = new HashSet<Transition>();
            for(Transition t : transitions){
                if(this.listAllStates.contains(t.start.toUpperCase()) &&
                this.listAllStates.contains(t.end.toUpperCase()) &&
                t.value >= 0 &&
                t.value <= 1) {
                    Transition newT = new Transition(t.start.toUpperCase(), t.end.toUpperCase(), t.value);
                    boolean canAdd = true;
                    for(Transition checkingT : listTransitions){
                        if(checkingT.equals(newT)){
                            canAdd = false;
                            break;
                        }
                    }
                    if(canAdd){
                        listTransitions.add(newT);
                    }else{
                        throw new IllegalArgumentException("There is a duplicate transition in the given list of transitions");
                    }
                }else{
                    throw new IllegalArgumentException("The given list of transitions contains an illegal transition");
                }
            }
        return listTransitions;
    }

    /**
     * Test if 'this' DFA is equal to the parameter DFA
     * @param newDFA a DFA to test against
     * @return true, if the DFAs are equal, false otherwise
     */
    public boolean equals(DFA newDFA){
        if(this.listAllStates.equals(newDFA.listAllStates)){
            if(this.startState.equals(newDFA.startState)){
                if(this.listAcceptStates.equals(newDFA.listAcceptStates)){
                    if(this.listTransitions.size()!=newDFA.listTransitions.size()) return false;
                    Iterator<Transition> it1 = this.listTransitions.iterator();
                    while(it1.hasNext()){
                        Transition tToCheck = it1.next();
                        boolean contains = false;
                        for(Transition checkingT : newDFA.listTransitions){
                            if(checkingT.equals(tToCheck)){
                                contains = true;
                                break;
                            }
                        }
                        if(!contains) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * This test is to check for illegal transitions. An illegal transition is namely a state
     * that has more than one "0" or more than one "1" transition value. It will throw an
     * IllegalArgumentException if it detects and illegal transition
     * @param transitions the set of transitions
     */
    private void testForIllegalTransitions(Set<Transition> transitions){
        Iterator it = this.listAllStates.iterator();
        while(it.hasNext()){
            String testState = (String)it.next();
            Set<Integer> values = new HashSet<Integer>();
            for(Transition t: this.listTransitions){
                if(testState.equals(t.start)){
                    if(values.add(t.value) == false) throw new IllegalArgumentException("There is an illegal transition in the given list of transitions");
                }
            }
        }
    }

    /**
     * A k-equivalence DFA minimization method
     * @param dfa the DFA to minimize
     * @return a minimized dfa
     */
    public DFA minimizeDFA(DFA dfa){
        final int MINIMIZATION_LIMIT = 99;
        final String SEPARATOR = "...";

        // Preparing 0 equivalence
        List<List> kEquivalenceTree = new ArrayList<List>();
        kEquivalenceTree.add(new ArrayList<String>());
        List<String> eq0 = kEquivalenceTree.get(0);

        for(String str: dfa.listAcceptStates) eq0.add(str);

        eq0.add(SEPARATOR);

        for(String str: dfa.listAllStates){
            if(!dfa.listAcceptStates.contains(str)) eq0.add(str);
        }

        //Getting 1 equivalence
        for(int i = 1; i < MINIMIZATION_LIMIT; i++) {
            kEquivalenceTree.add(new ArrayList<String>());
            List<String> previousEQ = kEquivalenceTree.get(i - 1);
            List<List> workingEquivalenceTree = openEquivalenceTree(previousEQ);


            for(int j = 0; j < workingEquivalenceTree.size(); j++) {
                List<String> currentTable = workingEquivalenceTree.get(j);

                for(int k = 0; k < currentTable.size(); k++) {
                    if(currentTable.size() <= 2) break;

                    String testState = currentTable.get(k);
                    String[] transitions = dfa.transitionTable.get(testState);

                    if(currentTable.contains(transitions[0]) && currentTable.contains(transitions[1])) {
                        continue;
                    }else{
                        currentTable.remove(testState);
                        List<String> newList = new ArrayList<String>();
                        newList.add(testState);
                        workingEquivalenceTree.add(newList);
                        k--;
                    }
                }
            }

            String answer = "";
            for(List<String> list : workingEquivalenceTree){
                for(String str: list){
                    answer += str;
                }
                answer += SEPARATOR;
            }
            answer = answer.substring(0, answer.length() - 3);

            List<String> thisEQ = closeEquivalenceTree(workingEquivalenceTree);
            kEquivalenceTree.add(thisEQ);
            if(thisEQ.equals(previousEQ)) break;
        }

        //Now we put it all together to make a new, minimized DFA
        List<String> uncompiledStates = kEquivalenceTree.get(kEquivalenceTree.size() - 3);
        uncompiledStates.add(SEPARATOR);
        List<String> states = new ArrayList<>();
        String compiler = "";
        for(String str : uncompiledStates){
            if(!str.equals(SEPARATOR)){
                compiler += str;
            }else{
                states.add(compiler);
                compiler = "";
            }
        }

        String start = dfa.startState;

        List<String> accepts = new ArrayList<>();
        for(String state : states){
            for(String acceptState: dfa.listAcceptStates){
                if(state.contains(acceptState)) accepts.add(state);
            }
        }

        Map<String, String> transitionConversionTable = createTransitionConversionTable(states, dfa.listAllStates);

        List<Transition> transitions = createMinimizedTransitions(states, dfa, transitionConversionTable);

        return new DFA(states, start, accepts, transitions);
    }


    /**
     * The method opens a list of strings and correctly
     * separates them into a list of lists of strings to store the levels
     * of equivalence during DFA minimization. Each list is split
     * on "...".
     * @param list the list of strings to be converted
     * @return a properly converted list of lists of strings
     */
    public static List<List> openEquivalenceTree(List<String> list) {
        final String SEPARATOR = "...";
        List<List> tree = new ArrayList<List>();
        int startingPoint = 0;
        int numberOfTables = Collections.frequency(list, SEPARATOR);

        for (int i = 0; i <= numberOfTables; i++) {
            tree.add(new ArrayList<String>());
            List<String> currentBranch = tree.get(i);

            for (int j = startingPoint; j < list.size(); j++) {
                String str = list.get(j);
                if (!str.equals(SEPARATOR)) {
                    currentBranch.add(str);
                } else {
                    startingPoint = j + 1;
                    break;
                }
            }
        }

        return tree;
    }

    /**
     * This method closes a list of lists of stings into a list
     * of strings that can be used to check against other layers of
     * k-equivalence to see if i have completed the minimization
     * @param list the list of lists of strings to be converted
     * @return a properly converted list of strings
     */
    public static List<String> closeEquivalenceTree(List<List> list) {
        final String SEPARATOR = "...";
        List<String> closedList = new ArrayList<String>();

        for(List<String> listToClose: list){
            for(String str: listToClose){
                closedList.add(str);
            }
            closedList.add(SEPARATOR);
        }
        closedList.remove(closedList.size()-1);

        return closedList;
    }

    /**
     * This creates the transition table for 'this' DFA. It also check to make
     * sure that the DFA is complete, meaning that each state has a 0 and a 1 transition.
     * @param listAllStates the set of all sate in the DFA
     * @param listTransitions the set of all transitions for the DFA
     * @return Key: state in question. Value: Value[0] = state transition to on zero,
     * Value[1] = state transition to on 1
     */
    private Map<String,String[]> createTransitionTable(Set<String> listAllStates, Set<Transition> listTransitions){
        Map<String,String[]> tt = new HashMap<String,String[]>();
        for(String state: listAllStates){
            String stateZero = "";
            String stateOne = "";

            for(Transition t: listTransitions){
                if(t.start.equals(state)){
                    if(t.value == 0){
                        stateZero = t.end;
                    }else{
                        stateOne = t.end;
                    }
                }
            }
            if(stateZero.equals("") || stateOne.equals("")) {
                throw new IllegalArgumentException("A given state does not have either a 0 or a 1 transition, or both");
            }else{
                tt.put(state, new String[]{stateZero, stateOne});
            }

        }
        return tt;
    }

    /**
     * This method reads the new minimized states and maps them forwards and backward to the original states
     * so that a new list of transitions for the minimized table can be made
     * @param newStates the list of minimized states
     * @param oldStates the set of original states
     * @return Key: a state, Value: the correlating state before or after it was minimized
     */
    private Map<String, String> createTransitionConversionTable(List<String> newStates, Set<String> oldStates){
        Map<String, String> conversionTable = new HashMap<>();

        for(String newState: newStates) {
            Iterator it = oldStates.iterator();
            while(it.hasNext()){
                String oldState = (String) it.next();
                if(newState.contains(oldState)){
                    conversionTable.put(newState, oldState);
                    break;
                }
            }
        }

        Iterator it = oldStates.iterator();
        while(it.hasNext()){
            String oldState = (String) it.next();
            for(String newState: newStates) {
                if(newState.contains(oldState)){
                    conversionTable.put(oldState, newState);
                    break;
                }
            }
        }

        return conversionTable;
    }

    /**
     * This method creates the new list of transitions for the the minimized DFA
     * @param states the list of minimized states
     * @param oldDfa the DFA that was given to be minimized
     * @param transitionConversionTable a transitionConversionTable to help maneuver between the new and old states
     * @return a list of transitions for the minimized DFA
     */
    private List<Transition> createMinimizedTransitions(List<String> states, DFA oldDfa, Map<String, String> transitionConversionTable ){
        List<Transition> transitions = new ArrayList<>();
        for(String state: states){
            String stateImLookingFor = transitionConversionTable.get(state);
            for(Transition t: oldDfa.listTransitions){
                if(t.start.equals(stateImLookingFor)){
                    transitions.add(new Transition(state, transitionConversionTable.get(t.end),t.value));
                }
            }
        }
        return transitions;
    }

}