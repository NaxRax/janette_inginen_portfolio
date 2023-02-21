package Datetime;



public class DateTime extends Date{
    public int year_;
    public int month_;
    public int day_;
    public int hour_;
    public int minute_;
    public int second_;

    public DateTime(int year, int month, int day, int hour, int minute, int second)
        throws DateException
    {
        super(year, month, day);
        try{
            this.hour_ = hour;
            this.minute_ = minute;
            this.second_ = second;
        }
        catch(Exception e){
            throw new DateException(String.format("Illegal time %02d.%02d.%02d", hour_, minute_, second_));
        }
    }

    public int getHour(){
        return hour_;
    }

    public int getMinute(){
        return minute_;
    }

    public int getSecond(){
        return second_;
    }

    public String toString(){
        String palautus = super.toString();
        palautus = palautus +" "+ String.format("%02d.%02d.%02d", hour_, minute_, second_);
        return palautus;
    }

}
