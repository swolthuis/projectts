
import java.time.LocalTime; // import the LocalTime class
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
public class Timer {
    private String timeStart;
    private String timeNow;

    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        LocalTime now = LocalTime.now();
        String timeStart = now.format(formatter);

        System.out.println("It is now "+timeStart+ " you hava 30 minutes to ontvoer President Trump.");
        System.out.println("To check the time type time");
        //if()
    }

    public void getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        LocalTime today = LocalTime.now();
        timeNow = today.format(formatter);
        
        System.out.println("Current time is "+timeNow);
        
        //System.out.println("You have "+timeNow+ " Seconds left");

    }
}
