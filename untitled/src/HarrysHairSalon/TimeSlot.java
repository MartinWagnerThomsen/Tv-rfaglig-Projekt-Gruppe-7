package HarrysHairSalon;
import java.time.LocalTime;
import java.time.Duration;

public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;

    //Konstruktører
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


    // Tjekker om tider overlapper
    public boolean overlaps(TimeSlot other){
        return this.startTime.isBefore(other.endTime) &&
                other.startTime.isBefore(this.endTime);
    }

    // Får varigheden af aftalen i minutter
    public long getDuration(){
        return Duration.between(startTime, endTime).toMinutes();
    }

    // Tjekker om aftalen er inde for åbningstid
    public boolean contains(LocalTime time){
        return !time.isBefore(startTime) && time.isBefore(endTime);
    }

    //Viser valgte tid
    public String getTimeSlot(){
        return ("Start time: " + startTime + "\nEnd time" + endTime +
                "\nDuration: " + getDuration() + "\nAvailability: " + isAvailable);
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime + (isAvailable ? " (Available)" : "(Unavailable)");
    }
}
