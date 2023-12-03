import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AOCDay {
    List<String> input = new ArrayList<>();
    List<List<String>> matrix = new ArrayList<>();
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

    public void inputAsMatrix(File in) {
        inputAsList(in);
        for (int i = 0; i < input.size(); i++) {
            matrix.add(new ArrayList<>());
            for (Character c : input.get(i).toCharArray()) {
                matrix.get(i).add(Character.toString(c));
            }
        }
    }
}
