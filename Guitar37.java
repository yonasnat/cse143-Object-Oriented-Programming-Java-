// implements guitar interface which models a guitar with 37 strings

public class Guitar37 implements Guitar { 
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  
   private GuitarString[] strings;
   private int numberTics = 0; 
   
   //creates array of guitarstring
   public Guitar37() {
      strings = new GuitarString[KEYBOARD.length()];
      for (int i = 0; i < KEYBOARD.length(); i++) {
      strings[i] = new GuitarString(440.0 * Math.pow(2, ((i-24.0)/12.0)));
      }
   }
     
   //plays the note
   public void playNote(int pitch) {      
    	if(pitch >= -24 && pitch <= 12) {
         int index = pitch + 24;
    	   strings[index].pluck();	
      }
    }
    
    //figures out if input is on keyboard
    public boolean hasString(char string) {
      return KEYBOARD.indexOf(string) != -1;
    }
    
    //plucks the key unless its not one of the 37
    //then it throws IllegalArgumentException
    public void pluck(char string) {
      if (!this.hasString(string)) {
         throw new IllegalArgumentException();
      }
      strings[KEYBOARD.indexOf(string)].pluck();
    }
    
   //returns the sound by returning all the strings
    public double sample() {
      double sum = 0.0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         sum = sum + strings[i].sample();
      }
      return sum;
   }
   
   //goes forward the number of tics
    public void tic() {
      for (GuitarString i: strings) {
         i.tic();
         numberTics++;
      }
    }

   //returns tics
    public int time() {
        return numberTics;
    }
}
