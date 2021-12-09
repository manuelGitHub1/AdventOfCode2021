import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


// --- Day 3: Binary Diagnostic ---
public class Day03 extends EveryDay {

   public static void main( String[] args ) {
      Day03 day = new Day03();
      day.testInput = false;
      day.firstPart();
      day.secondPart();
   }

   private static Container calculate( List<String> input ) {
      final StringBuilder mostCommonLetters = new StringBuilder();
      final StringBuilder leastCommonLetter = new StringBuilder();
      assert !input.isEmpty();
      int lineLength = input.get(0).length();
      for ( int i = 0; i < lineLength; i++ ) {
         final Map<Character, AtomicInteger> charCounter = new HashMap<>();
         for ( final String string : input ) {
            char charAt = string.charAt(i);
            if ( !charCounter.containsKey(charAt) ) {
               charCounter.put(charAt, new AtomicInteger(0));
            } else {
               charCounter.get(charAt).incrementAndGet();
            }
         }
         final int zeroes = charCounter.get('0').intValue();
         final int ones = charCounter.get('1').intValue();
         if ( zeroes == ones ) {
            mostCommonLetters.append("1");
            leastCommonLetter.append("1");
         } else {
            mostCommonLetters.append(zeroes > ones ? "0" : "1");
            leastCommonLetter.append(zeroes > ones ? "1" : "0");
         }
      }
      final Container container = new Container();
      container.epsilonRate = leastCommonLetter.toString();
      container.gammaRate = mostCommonLetters.toString();
      return container;
   }

   private void calc( List<String> input, int i, Map<Character, List<String>> charCounter ) {
      for ( final String string : input ) {
         char charAt = string.charAt(i);
         if ( !charCounter.containsKey(charAt) ) {
            charCounter.put(charAt, new ArrayList<>());
         }
         charCounter.get(charAt).add(string);
      }
   }

   private void firstPart() {
      final Container container = calculate(input());

      int gammaDEC = Integer.parseInt(container.gammaRate, 2);
      int epsilonDEC = Integer.parseInt(container.epsilonRate, 2);
      System.out.println("GammaRate: " + container.gammaRate + " in decimal: " + gammaDEC);
      System.out.println("EpsilonRate: " + container.epsilonRate + " in decimal: " + epsilonDEC);
      System.out.println("Result: " + gammaDEC * epsilonDEC + " (expected: 198/1997414");

   }

   private String getCO2Rate() {
      final List<String> input = input();
      assert !input.isEmpty();
      int lineLength = input.get(0).length();
      for ( int i = 0; i < lineLength; i++ ) {
         final Map<Character, List<String>> charCounter = new HashMap<>();

         calc(input, i, charCounter);
         input.clear();

         final List<String> zeroNumbers = charCounter.get('0');
         final List<String> oneNumbers = charCounter.get('1');
         final int zeroesCount = zeroNumbers.size();
         final int onesCount = oneNumbers.size();
         if ( onesCount == zeroesCount || zeroesCount < onesCount ) {
            input.addAll(zeroNumbers);
         } else {
            input.addAll(oneNumbers);
         }
         if ( input.size() == 1 ) {
            return input.get(0);
         }
      }
      return null;
   }

   private String getOxygenRate() {
      final List<String> input = input();
      assert !input.isEmpty();
      int lineLength = input.get(0).length();
      for ( int i = 0; i < lineLength; i++ ) {
         final Map<Character, List<String>> charCounter = new HashMap<>();
         calc(input, i, charCounter);
         input.clear();

         final List<String> zeroNumbers = charCounter.get('0');
         final List<String> oneNumbers = charCounter.get('1');
         final int zeroesCount = zeroNumbers.size();
         final int onesCount = oneNumbers != null ? oneNumbers.size() : 0;
         if ( onesCount == zeroesCount || onesCount > zeroesCount ) {
            assert oneNumbers != null;
            input.addAll(oneNumbers);
         } else {
            input.addAll(zeroNumbers);
         }
         if ( input.size() == 1 ) {
            return input.get(0);
         }
      }
      return null;
   }

   private void secondPart() {
      final String co2Rate = getCO2Rate();
      assert co2Rate != null;
      final String oxygenRate = getOxygenRate();
      assert oxygenRate != null;

      System.out.println("OxygenRate (Binary): " + oxygenRate + " (expected: 10111)");
      final int oxygenRateDec = Integer.parseInt(oxygenRate, 2);
      System.out.println("OxygenRate (Decimal): " + oxygenRateDec + " (expected: 23)");
      System.out.println("co2Rate (Binary): " + co2Rate + " (expected: 01010)");
      final int co2RateDec = Integer.parseInt(co2Rate, 2);
      System.out.println("co2Rate (Decimal): " + co2RateDec + " (expected: 10)");
      System.out.println("Result: " + oxygenRateDec * co2RateDec + " (expected 1032597)");
   }


   private static class Container {

      String gammaRate;
      String epsilonRate;
   }

}
