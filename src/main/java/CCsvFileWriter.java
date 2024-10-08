import forcemodel.COutput;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Fatema on 11/6/2016.
 */
public class CCsvFileWriter {

    static ArrayList<COutput> m_output;

    private static final String COMMA_DELIMITER = ",";

    private static final String NEW_LINE_SEPARATOR = "\n";

    private static final String FILE_HEADER = "self_w,self_h,force";

    public void writeCsvFile(String fileName,ArrayList<COutput> m_output)
    {
        FileWriter fileWriter = null;

        try {

            fileWriter = new FileWriter(fileName);

            try {
                fileWriter.append(FILE_HEADER.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            fileWriter.append(NEW_LINE_SEPARATOR);

            for ( COutput out : m_output ) {

                //if( out.m_force > 0.05 ) {
                    fileWriter.append(String.valueOf(out.m_selfX));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(out.m_selfY));
                    fileWriter.append(COMMA_DELIMITER);
                    fileWriter.append(String.valueOf(out.m_force));
                    fileWriter.append(NEW_LINE_SEPARATOR);
               // }

            }
            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();

            } catch (IOException e) {

                System.out.println("Error while flushing/closing fileWriter !!!");

                e.printStackTrace();

            }
        }
    }

}
