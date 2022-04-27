import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ReadJSON
{
    public static void main(String[] args)
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("JsonFiles/coordinates01.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            ArrayList<Integer> X_values = new ArrayList<>();
            ArrayList<Integer> Y_values = new ArrayList<>();

            ParseJsonFile.parseRelativePotitionObject(obj, X_values, Y_values);
            System.out.println();


            //from here and all the way down needs refactor for more optimal methods.


            int max_Y = Collections.max(Y_values);
            int min_Y = Collections.min(Y_values);
            int max_X = Collections.max(X_values);
            int min_X = Collections.min(X_values);

            int Y;
            int X;
            double M = 0.2;

            if (max_Y < 0)
                Y = Math.abs(min_Y) - Math.abs(max_Y);
            else
                Y = max_Y - min_Y;

            if (max_X < 0)
                X = Math.abs(min_X) - Math.abs(max_X);
            else
                X = max_X - min_X;


            double G = 1 / M;

            int row, col;
            ArrayList<Integer> RowVal = new ArrayList<>();
            ArrayList<Integer> ColVal = new ArrayList<>();

            for (int k = 0; k < X_values.size(); ++k)
            {
                col = Math.abs(min_X - X_values.get(k));
                row = max_Y - Y_values.get(k);
                RowVal.add(row * (int) G);
                ColVal.add(col * (int) G);
            }

            int[][] intArray = new int[Y * (int) G + 1][X * (int) G + 1];

            for (int k = 0; k < RowVal.size(); ++k)
                intArray[RowVal.get(k)][ColVal.get(k)] = 1;

            int num_row = intArray.length;
            int num_col = intArray[0].length;

            for(int points = 0; points < RowVal.size() - 1; ++points)
            {
                int a = RowVal.get(points);
                int b = ColVal.get(points);

                int c = RowVal.get(points + 1);
                int d = ColVal.get(points + 1);

                int min_row = a;
                int max_row = a;

                if(c < a)
                    min_row = c;
                else
                    max_row = c;

                for (int k = 0; k < num_row; ++k)
                    if( k >= min_row && k <= max_row)
                        for (int l = 0; l < num_col; ++l)
                            if (l == ((d - b) * k - (d - b) * a + (c - a) * b) / (c - a))
                                intArray[k][l] = 1;
            }

            int flag, counter;
            for (int k = 0; k < num_row; ++k)
            {
                flag = 0;
                counter = 0;
                for (int l = 0; l < num_col; ++l)
                    if (intArray[k][l] == 1)
                        ++counter;

                if(counter % 2 == 0)
                    for (int l = 0; l < num_col; ++l)
                    {
                        if (intArray[k][l] == 1 && flag == 0)
                            flag = 1;
                        else if (intArray[k][l] == 1 && flag == 1)
                            flag = 0;
                        intArray[k][l] = flag;
                    }
            }

            for (int[] ints : intArray)
            {
                for (int l = 0; l < num_col; ++l)
                    System.out.print(ints[l] + " ");
                System.out.println();
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}