import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


// --- Day 3: Binary Diagnostic ---
public class Day03 {

   public static void main( String[] args ) {
      final StringBuilder gammaRate = new StringBuilder();
      final StringBuilder epsilonRate = new StringBuilder();
//      final List<String> input = Util.getAoCTestInput(Day03.class.getName());
      final List<String> input = Util.getAoCInput(Day03.class.getName());
      assert !input.isEmpty();
      int lineLength = input.get(0).length();
      for ( int i = 0; i < lineLength; i++ ) {
         final Map<Character, Integer> charCounter = new HashMap<>();
         for ( final String string : input ) {
            char charAt = string.charAt(i);
            if ( !charCounter.containsKey(charAt) ) {
               charCounter.put(charAt, 0);
            } else {
               charCounter.put(charAt, charCounter.get(charAt) + 1);
            }
         }
         final Integer zeroes = charCounter.get('0');
         final Integer ones = charCounter.get('1');
         gammaRate.append(zeroes > ones ? "0" : "1");
         epsilonRate.append(zeroes > ones ? "1" : "0");
         // 10110
      }

      int gammaDEC = Integer.parseInt(gammaRate.toString(), 2);
      int epsilonDEC = Integer.parseInt(epsilonRate.toString(), 2);
      System.out.println("GammaRate: " + gammaRate + " in decimal: " + gammaDEC);
      System.out.println("EpsilonRate: " + epsilonRate + " in decimal: " + epsilonDEC);
      System.out.println("Result: " + gammaDEC * epsilonDEC);
   }

}
