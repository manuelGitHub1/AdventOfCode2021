import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;


// --- Day 4: Giant Squid ---
public class Day04 extends EveryDay {

   public static void main( String[] args ) {
      Day04 day = new Day04();
      day.testInput = true;
      day.firstPart();
      day.secondPart();
      day.testInput = false;
      day.firstPart();
      day.secondPart();
   }

   private void firstPart() {
      final List<String> input = input();
      final String drawnNumbers = input.get(0);
      input.remove(0);

      int rowCounter = 0;
      final List<BingoField> bingoFields = new ArrayList<>();
      BingoField bingoField = null;
      for ( String string : input ) {
         if ( string.isBlank() ) {
            if ( bingoField != null ) {
               bingoFields.add(bingoField);
            }
            bingoField = new BingoField();
            continue;
         }
         rowCounter++;

         Scanner scanner = new Scanner(string);
         int columnCounter = 0;
         while ( scanner.hasNext() ) {
            Field field = new Field();
            field.value = scanner.next();
            field.x = rowCounter;
            field.y = columnCounter;
            assert bingoField != null;
            bingoField.getColumn(columnCounter).add(field);
            bingoField.getRow(rowCounter).add(field);
            columnCounter++;
         }
      }
      if ( bingoField != null ) {
         bingoFields.add(bingoField);
      }
      lookupForBingo(bingoFields, drawnNumbers);

   }

   private void lookupForBingo( List<BingoField> bingoFields, String drawnNumbers ) {
      for ( final String drawnNumber : drawnNumbers.split(",") ) {
         for ( BingoField field : bingoFields ) {
            if ( field.applyDrawAndLookForBingo(drawnNumber) ) {
               System.out.println("BINGO");
               System.out.println("WinnerNumber: " + drawnNumber);
               AtomicInteger sum = new AtomicInteger(0);
               for ( List<Field> fields : field.columns.values() ) {
                  fields.stream().filter(f -> !f.marked).forEach(f -> sum.addAndGet(Integer.parseInt(f.value)));
               }
               System.out.println("Sum of all unmarked fields: " + sum);
               System.out.println("FinalScore: " + Integer.parseInt(drawnNumber) * sum.intValue());
               return;
            }
         }
      }
   }

   private void secondPart() {
   }

   private static class BingoField {

      Map<Integer, List<Field>> rows    = new HashMap<>();
      Map<Integer, List<Field>> columns = new HashMap<>();

      @Override
      public String toString() {
         StringBuilder stringBuilder = new StringBuilder();
         for ( List<Field> fields : rows.values() ) {
            stringBuilder.append(fields).append("\n");
         }
         return stringBuilder.toString();
      }

      boolean applyDrawAndLookForBingo( final String number ) {
         for ( final List<Field> value : columns.values() ) {
            value.stream().filter(f -> f.value.equals(number)).forEach(f -> f.marked = true);
            if ( value.stream().allMatch(f -> f.marked) ) {
               return true;
            }
            for ( final List<Field> row : rows.values() ) {
               if ( row.stream().allMatch(f -> f.marked) ) {
                  return true;
               }
            }
         }

         return false;
      }

      List<Field> getColumn( int index ) {
         return columns.computeIfAbsent(index, k -> new ArrayList<>());
      }

      List<Field> getRow( int index ) {
         return rows.computeIfAbsent(index, k -> new ArrayList<>());
      }
   }


   private static class Field {

      String  value;
      int     x;
      int     y;
      boolean marked;

      @Override
      public String toString() {
         return value;
      }
   }

}
