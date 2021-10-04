package springsecurity.employeecrud.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AllUtils {

    public static Date stringToDate(String date)   {

        try {
            Date parsedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            return parsedDate;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Date Format Exception");

        }
        return null;
    }
}
