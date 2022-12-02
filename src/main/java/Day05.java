import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jetbrains.annotations.NotNull;


// --- Day 5: Hydrothermal Venture ---
public class Day05 extends EveryDay {

   public static void main( String[] args ) {
      Day05 day = new Day05();
      //      day.testInput = true;
      //      day.firstPart();
      //      day.secondPart();
      day.testInput = false;
      day.calculate();
      //      day.secondPart();
   }

   protected Pattern _compile = Pattern.compile("(\\d+),(\\d+).*->.*?(\\d+),(\\d+)");

   private void calculate( Mode mode ) {
      final Map myMap = new Map();
      final List<String> input = input();
      for ( String string : input ) {
         System.out.println(string);
         final Matcher matcher = _compile.matcher(string);
         if ( !matcher.matches() ) {
            System.err.println("Failed to parse line: " + string);
            System.exit(1);
         }
         final int x1 = toInt(matcher.group(1));
         final int y1 = toInt(matcher.group(2));
         final int x2 = toInt(matcher.group(3));
         final int y2 = toInt(matcher.group(4));

         if ( mode == Mode.HORIZONTAL_AND_VERTICAL || (x1 == x2 || y1 == y2) ) {
            final Field start = new Field(x1, y1);
            final Field end = new Field(x2, y2);
            myMap.register(start);
            myMap.register(end);
         }
         //         System.out.println("Start: " + start + ", End: " + end + ", input: " + string);
         //         System.out.println("Start: " + start.toString2() + ", End: " + end.toString2() + ", input: " + string);

         if ( x1 == x2 ) {
            final int missingPoints = Math.abs(y1 - y2);
            int base = y1 < y2 ? y1 : y2;
            //            System.out.println("missingPoints: " + missingPoints);
            IntStream.range(1, missingPoints).forEach(i -> myMap.register(x1, base + i));
         } else if ( y1 == y2 ) {
            final int missingPoints = Math.abs(x1 - x2);
            int base = x1 < x2 ? x1 : x2;
            //            System.out.println("missingPoints: " + missingPoints);
            IntStream.range(1, missingPoints).forEach(i -> myMap.register(base + i, y1));
         } else if (mode == Mode.HORIZONTAL_AND_VERTICAL) {
//            An entry like 1,1 -> 3,3 covers points 1,1, 2,2, and 3,3.
//            An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.

         }

      }

      final List<Field> collect = myMap.getAllFields().stream().filter(f -> f.crossed > 1).sorted().collect(Collectors.toList());
      collect.forEach(System.out::println);
      System.out.println("Found: " + collect.size());
   }

   private void secondPart() {

   }

   private int toInt( String value ) {
      return Integer.parseInt(value);
   }

   enum Mode {
      HORIZONTAL,
      HORIZONTAL_AND_VERTICAL;
   }


   private static class Field implements Comparable {

      int x;
      int y;
      int crossed = 1;

      public Field( int x, int y ) {
         this.x = x;
         this.y = y;
      }

      @Override
      public int compareTo( @NotNull Object o ) {
         if ( o instanceof Field ) {
            Field otherField = (Field)o;
            if ( x > otherField.x ) {
               return 1;
            }
            if ( x < otherField.x ) {
               return -1;
            }
         }
         return 0;
      }

      @Override
      public boolean equals( Object o ) {
         if ( this == o ) {
            return true;
         }
         if ( o == null || getClass() != o.getClass() ) {
            return false;
         }

         Field field = (Field)o;

         if ( x != field.x ) {
            return false;
         }
         return y == field.y;
      }

      @Override
      public int hashCode() {
         int result = x;
         result = 31 * result + y;
         return result;
      }

      public void incrementCrosscuts() {
         crossed++;
      }

      public String toDiagram() {
         return crossed == 0 ? "." : crossed + "";
      }

      public String toString() {
         return x + "|" + y + "(" + crossed + ")";
      }
   }


   private static class Map {

      final HashMap<Field, Field> _map = new HashMap<>();

      public Set<Field> getAllFields() {
         return Collections.unmodifiableSet(_map.keySet());
      }

      public void register( int x, int y ) {
         register(new Field(x, y));
      }

      public void register( final Field field ) {
         final Field field1 = _map.get(field);
         if ( field1 == null ) {
            _map.put(field, field);
         } else {
            field1.incrementCrosscuts();
         }
      }
   }
}

