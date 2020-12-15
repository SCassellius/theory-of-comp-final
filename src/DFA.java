import java.util.*;

public class DFA{
    public Set<String> listAllStates;
    public String startState;
    public Set<String> listAcceptStates;
    public Set<Transition> listTransitions;

    /**
     * Models a DFA so that I have something to minimize
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

//    private DFA minimize(DFA dfa){
//
//        return ;
//    }

}