import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class JSON {

    JSONObject JsonObj = new JSONObject();

    public void saveToJSON1(String isbn) {

        JsonObj.put("ISBN OF BORROWED BOOK: ", isbn);

        try {
            FileWriter file = new FileWriter("output.json", true);
            file.write(JsonObj.toString());
            file.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("JSON file created: " + JsonObj);
    }

    public void saveToJSON2(String isbn) {

        JsonObj.put("ISBN OF RETURNED BOOK: ", isbn);

        try {
            FileWriter file = new FileWriter("output.json", true);
            file.write(JsonObj.toString());
            file.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("JSON file created: " + JsonObj);
    }
}
