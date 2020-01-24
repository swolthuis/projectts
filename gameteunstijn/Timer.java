import java.time.LocalTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Timer {
    private String timeStart;
    private String timeNow;
    public LocalTime endTime;
    public int value; 

    public void calculator(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime startTime = LocalTime.now();
        LocalTime endTime = startTime.plus(15, ChronoUnit.MINUTES);
        LocalTime now = LocalTime.now();
        String timeStart = now.format(formatter);
        this.endTime = endTime;
        System.out.println("It is now "+timeStart+ " I have 15 minutes to get President Trump.");
        System.out.println("To check the time type time");
        //System.out.println(MINUTES.between(startTime, endTime));

    }

    public boolean  getTime(){
        int value = LocalTime.now().compareTo(endTime);
        while(value == 0 || value > 0){
            System.out.println("Your time is up, you will have to start all over again.");
            System.out.println("Type quit to go back to the menu.");
            return true;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime var = LocalTime.now();
        String timeNow = var.format(formatter);
        System.out.println("My watch tells me it's "+timeNow+ " currently");

        if(MINUTES.between(LocalTime.now(),endTime)>0){
            System.out.println("I have "+MINUTES.between(LocalTime.now(),endTime)+" minute(s) left to get Trump");
        }else{
            System.out.println("I only have a few seconds left, I have to hurry up");}
        return false;
    }

}
