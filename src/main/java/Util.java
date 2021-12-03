import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;


public class Util {

   private static final Logger _logger = Logger.getLogger(Util.class.getName());

   public static List<String> fileAsStrings( String filePath ) {
      final Path path = Paths.get(filePath);
      _logger.info("Read file from Path " + path);
      try {
         final List<String> lines = Files.readAllLines(path);
         _logger.info("Read " + lines.size() + " lines from file");
         return lines;
      }
      catch ( IOException e ) {
         throw new RuntimeException(e);
      }
   }

   public static List<String> getAoCInput(final String day ) {
      assert day != null;
      return getInput(day, "/input.txt");
   }

   public static List<Integer> getAoCInputAsInts(final String day ) {
      return getAoCInput(day).stream().map(Integer::parseInt).collect(Collectors.toList());
   }

   public static List<Integer> getAoCTestInputAsInts(final String day ) {
      return getAoCTestInput(day).stream().map(Integer::parseInt).collect(Collectors.toList());
   }

   public static List<String> getAoCTestInput(final String day ) {
      assert day != null;
      return getInput(day, "/test.txt");
   }

   @NotNull
   private static List<String> getInput( String day, String s ) {
      return fileAsStrings("src/main/resources/" + day.toLowerCase() + s);
   }

}
