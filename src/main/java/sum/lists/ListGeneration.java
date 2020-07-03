package sum.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListGeneration {

    public static List<Integer> generate(int numberOfElements) {
        Random random = new Random();
        List<Integer> list = new ArrayList<>(numberOfElements);
        for (int i = 0; i < numberOfElements; i++) {
            list.add(random.nextInt(11));
        }
        return list;
    }
}
