package Datetime;


public class Date {
    public int year_;
    public int month_;
    public int day_;

    public Date(int year, int month, int day)
    throws DateException
    {
        try{
            this.year_ = year;
            this.month_ = month;
            this.day_ = day;
        }
        catch(Exception e){
            throw new DateException("Illegal date "+ String.format("%02d", day)
                                + "." + String.format("%02d", month)
                                + "." + String.format("%02d", year));
        }

    }

    public int getYear(){
        return this.year_;
    }

    public int getMonth(){
        return this.month_;
    }

    public int getDay(){
        return this. day_;
    }

    public String toString(){
        return String.format("%02d.%02d.%02d", day_, month_, year_);
    }

}
