import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AOCDay {
    List<String> input = new ArrayList<>();
   
    public void inputAsList(File in) {
        try {
            Scanner sc = new Scanner(in);
            while (sc.hasNextLine()) {
                input.add(sc.nextLine());
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
