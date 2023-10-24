package project3;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Date;
import java.lang.Math;
import java.util.NoSuchElementException;

/* *
*
* This class represents a single session in the terminal in the multi-user system.
* It is made of a login and logout record of the same user in the same terminal. 
* The login and logout records are represented by Record objects.
* If the user has not logged out yet, the logout record is null.
* This class implements Comparable interface to compare two Session objects.
* 
* @author Sandhya Sharma
*
*/

public class Session implements Comparable<Session>
{
    //private members
    private Record login;
    private Record logout; 

    /**
     * Constructs a new Session object with one Record object. 
     * @param login Record object signifying a single login record of a user
     * @throws IllegalArgumentException  if login is invalid
     */
    public Session(Record login) throws IllegalArgumentException
    {
        //calls another constructor in this class where two arguments are accepted
        this (login, null); 
    }

    /**
     * Constructs a new Session object with two Record object. 
     * @param login Record object signifying a single login record of a user into a terminal
     * @param logout Record object signifying the logout record of the same user in the same terminal
     * @throws IllegalArgumentException  if any parameters are invalid or login and logout do not match
     */
    public Session (Record login, Record logout) throws IllegalArgumentException
    {
        if (login == null)
            throw new IllegalArgumentException("Login cannot be empty."); 

        if (login.getTerminal() <= 0)
            throw new IllegalArgumentException("The terminal value should be a positive integer."); 

        if (logout != null && logout.getTerminal() != login.getTerminal())
            throw new IllegalArgumentException("The terminal value for logout should match with login.");
        
        if (logout != null && login.getUsername() != logout.getUsername())
            throw new IllegalArgumentException("The username of the user for a single session must match.");

        if (logout != null && logout.getTime().compareTo(login.getTime()) < 0)
            throw new IllegalArgumentException("Time mismatch.");

        if((login.isLogin() == false))
            throw new IllegalArgumentException("Login mismatch.");
        
        if(logout!= null && logout.isLogout() == false)
            throw new IllegalArgumentException("Logout mismatch."); 

        this.login = login; 
        this.logout = logout; 
    }

    /**
     * Returns the terminal value representing this Session object. 
     * @return the terminal value of this Session object 
     */
    public int getTerminal(){return login.getTerminal();}

    /**
     * Returns the login time of this Session object. 
     * @return the login time of this Session object 
     */
    public Date getLoginTime(){return login.getTime();}

    /**
     * Returns the logout time of this Session object. 
     * @return the logout time of this Session object if the session is inactive
     */
    public Date getLogoutTime(){
        if(logout != null)
            return logout.getTime();
        else 
            return null; 
    }

    /**
     * Returns the username of this Session object. 
     * @return the username of this Session object 
     */
    public String getUsername(){return login.getUsername();}

    /**
     * Returns the duration of this Session object.
     * If the session is still active, the duration is -1.
     * @return the duration of this Session object in milliseconds if the session is inactive
     */
    public long getDuration()
    {
        if (logout == null)
            return -1; 
        else
            return logout.getTime().getTime() - login.getTime().getTime(); 
    }

    /**
     * Returns the details of this Session object in a formatted string.  
     * @return the string containing details of username, terminal and log times with duration
     * if session is already inactive
     */
    @Override
    public String toString()
    {
        String login_time = DateConverter(this.getLoginTime());
        String logout_time = " "; 
        String duration_in_format = " "; 
        if(logout == null){
            logout_time = "still logged in";
            duration_in_format = "active session"; 
        }
        else{
            logout_time = DateConverter(this.getLogoutTime());
            duration_in_format = DurationConverter(this.getDuration()); 
        }
        
        return String.format("\n %s, terminal %d, duration %s \n logged in: %s \n logged out: %s \n", this.getUsername(), this.getTerminal(), duration_in_format, login_time, logout_time);  
    }


    /**
     * Returns the time in a particular format of "EEE MMM dd HH:mm:ss zzz yyyy"
     * 
     * EEE is the day of the week (Sun, Mon, Tue, Wed, Thu, Fri, Sat).
     * MMM is the month (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec).
     * dd is the day of the month (01 through 31), as two decimal digits.
     * HH is the hour of the day (00 through 23), as two decimal digits.
     * mm is the minute within the hour (00 through 59), as two decimal digits.
     * ss is the second within the minute (00 through 61, as two decimal digits.
     * zzz is the time zone (and may reflect daylight saving time). 
     * yyyy is the year, as four decimal digits.
     * 
     * @param time is converted into the format explained above 
     * @return the formatted time as a string
     * @throws IllegalArgumentException if the given time is invalid
     */
    public String DateConverter(Date time) throws IllegalArgumentException, NullPointerException
    {
        if (time == null)
            throw new NullPointerException("Time cannot be null.");
        
        if (!(time instanceof Date))
            throw new IllegalArgumentException("Time should be a valid Date object.");
    
        String formatted_time = " ";
        try {
            ZonedDateTime zoned_date_time = time.toInstant().atZone(ZoneId.systemDefault());
            String format = "EEE MMM dd HH:mm:ss zzz yyyy"; 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.US);
            formatted_time = zoned_date_time.format(formatter);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Incorrect value for time.");
        }
         
        return formatted_time; 
    }

    /**
     * Returns the duration of this Session object in a formatted string.  
     * @return the string containing the duration of this Session object
     * @throws IllegalArgumentException if the given duration is invalid
     */
    public String DurationConverter(long duration) throws IllegalArgumentException
    {
        if (duration < 0)
            throw new IllegalArgumentException("Duration cannot be negative.");
        
        long seconds = 0;
        long minutes = 0; 
        long hours = 0;
        long days = 0;
        try {
            seconds = duration / 1000;
            minutes = seconds / 60;
            hours = minutes / 60;
            days = hours / 24;
            seconds %= 60;
            minutes %= 60;
            hours %= 24;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Incorrect value for time.");
        }

        return String.format("%d days, %d hours, %d minutes, %d seconds", days, hours, minutes, seconds); 
    }

    /**
     * Compares this Session object with the specified Session object. 
     * @return 1 if this Session object is greater than the specified Session object,
     * -1 if this Session object is less than the specified Session object,
     * or 0 if both Session objects are equal. Here, the parameter being measured is the login time.
     * An earlier login time is considered smaller.
     * @throws NullPointerException if other Session object is null
     */
    @Override
    public int compareTo(Session other) throws NullPointerException
    {
        if (other == null)
            throw new NullPointerException("Cannot compare to null object.");
        
        if (this == other)
            return 0;

        if (this.getLoginTime().getTime() < other.getLoginTime().getTime())
            return -1; 
        else if (this.getLoginTime().getTime() > other.getLoginTime().getTime())
            return 1; 
        else 
            return 0; 
    }

    /**
     * Compares this Session object with the specified object for equality.  
     * A matched session is such with matching login and logout records. 
     * @return true if this Session object is equal to the specified Session object, false otherwise
     * @throws NullPointerException if other Session object is null
     */
    @Override
    public boolean equals(Object obj) throws NullPointerException
    {
        if (obj == null)
            throw new NullPointerException("Cannot compare to null object.");
        
        if (this == obj)
            return true;
        
        if (!this.getClass().equals(obj.getClass()))
            return false;

        Session other = (Session) obj;

        boolean login_match = false, logout_match = false; 

        if(this.getLoginTime().getTime() == other.getLoginTime().getTime() && this.getTerminal() == other.getTerminal() && this.getUsername().equalsIgnoreCase(other.getUsername()))
            login_match = true;
        
        if(this.logout == null){
            if(other.logout == null)
                logout_match = true;
            return login_match && logout_match;
        } 
        else if(this.getLogoutTime().equals(other.getLogoutTime()) && this.getTerminal() == other.getTerminal() && this.getUsername().equalsIgnoreCase(other.getUsername()))
            logout_match = true;
        

        return login_match && logout_match; 
    }
}
