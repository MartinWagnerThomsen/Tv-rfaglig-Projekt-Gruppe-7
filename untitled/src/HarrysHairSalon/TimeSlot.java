package HarrysHairSalon;
import java.time.LocalTime;
import java.time.Duration;

public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;

    public TimeSlot(LocalTime startTime, LocalTime endTime, boolean isAvailable){
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    public TimeSlot(LocalTime startTime, LocalTime endTime){
        this(startTime, endTime, true);
    }

//Getters
    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


//Setters

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public void setAvailable(boolean available){
        this.isAvailable = available;
    }


    public boolean overlaps(TimeSlot other){
        return this.startTime.isBefore(endTime) &&
                other.startTime.isBefore(this.endTime);
    }

    public long getDuration(){
        return Duration.between(startTime, endTime).toMinutes();
    }

    public String getTimeSlot(){
        return ("Start time: " + startTime + "\nEnd time" + endTime +
                "\nDuration: " + getDuration() + "\nAvailability: " + isAvailable);
    }

    @Override
    public String toString() {
        return getTimeSlot();
    }
}
