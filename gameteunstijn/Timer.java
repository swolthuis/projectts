import java.time.LocalTime; // import the LocalTime class
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
public class Timer {
    private String timeStart;
    private String timeNow;
    public LocalTime endTime;

    public void calculator(){

        LocalTime startTime = LocalTime.now();
        System.out.println(startTime);
        LocalTime endTime = startTime.plus(1, ChronoUnit.MINUTES);
        System.out.println(endTime);
        System.out.println(MINUTES.between(startTime, endTime));
        this.endTime = endTime;
       
    }

    public void run() {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        LocalTime now = LocalTime.now();
        //String timeStart = now.format(formatter);
        //tijdMin = LocalTimeminusMinutes(30, ChronoUnit.HOURS);
        System.out.println("It is now "+timeStart+ " you hava 30 minutes to ontvoer President Trump.");
        System.out.println("To check the time type time");
        //if()
        this.endTime = endTime;
    }

    public boolean  getTime(){
    
        int value = LocalTime.now().compareTo(endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
        System.out.println("My watch tells me it's "+LocalTime.now() + " currently");
        System.out.println("I have "+MINUTES.between(LocalTime.now(),endTime)+" minute(s) left to get Trump");
        return false;
    }
}
