package columnreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFileChooser;

public class Columnreader {

    static Map map = new HashMap();
    static String outputPath = "";

    public static void main(String[] args) {

        long now = System.currentTimeMillis();

        try {
            JFileChooser filePicker = new JFileChooser();
            filePicker.setCurrentDirectory(new File(System.getProperty("user.dir")));
            filePicker.showOpenDialog(null);
            File file = filePicker.getSelectedFile();
            outputPath = file.getParent();
//            System.out.println(file.getParent());
//            System.out.println(file.getAbsolutePath());
            process(file);
        } catch (Exception e) {

        }

        System.out.println();

    }

    public static void process(File file) throws IOException {
        BufferedReader br = null;
        String line = "";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {

                if (!map.containsKey(line)) {
                    map.put(line, 1);
                } else {
                    int i = (int) map.get(line);
                    i++;
                    map.replace(line, i);
                }
            }
            write(map);

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Done");

    }

    public static void write(Map map) throws FileNotFoundException, IOException {

        Iterator<?> mapIterator = map.entrySet().iterator();
        String path = outputPath + "\\result.csv";
        PrintWriter pw = new PrintWriter(path);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        pw.write("Created " + date + "\n");
        pw.write("URL, Hits\n");
        while (mapIterator.hasNext()) {
            Map.Entry<?, ?> entry = (Entry<?, ?>) mapIterator.next();
            String line = entry.getKey() + "," + entry.getValue();
            pw.println(line);
        }
        pw.close();
    }

    public static void iteratePrintMapValues(Map<?, ?> map) {
        Iterator<?> mapIterator = map.entrySet().iterator();

        while (mapIterator.hasNext()) {
            Map.Entry<?, ?> entry = (Entry<?, ?>) mapIterator.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }
}
