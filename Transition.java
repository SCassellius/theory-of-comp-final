public class Transition<start, end, number> { 
    public final String start; 
    public final String end;
    public final int number; 
     
    public Transition(String start, String end, int number) { 
      this.start = start;
      this.end = end;
      this.number = number;
    } 
  } 