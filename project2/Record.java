package project2; 
import java.util.Date;

/* *
*
* This class represents a single login or logout record into the terminal in the multi-user system.
*
* @author Sandhya Sharma
*
*/

public class Record 
{
    //private members 
    private int terminal; 
    private boolean login;
    private String username;
    private Date time;  

    /**
     * Constructs a new Record object with given username, terminal value and time since epoch. 
     * @param terminal takes the absolute value of the terminal value
     * @param login is a boolean value that is true when terminal value is postive signifying a login. 
     * @throws IllegalArgumentException  if terminal value is invalid
     */
    public Record (int terminal, boolean login, String username, Date time) throws IllegalArgumentException { 
        this.terminal = setTerminal(terminal);
        this.login = login; 
        this.username = username;
        this.time = time; 
    }

    /**
     * Returns the terminal value representing this Record object. 
     * @return the terminal value of this Record object 
     */
    public int getTerminal() {return terminal;}

    /**
     * Returns the login status this Record object. 
     * @return the login status of this Record object as true or false
     */
    public boolean isLogin() {return login;} 

    /**
     * Returns the logout status this Record object. 
     * @return the logout status of this Record object as true or false
     */
    public boolean isLogout() {return !login;} 

    /**
     * Returns the username in this Record object. 
     * @return the terminal value of this Record object 
     */
    public String getUsername() {return username;}

    /**
     * Returns the login or logout time of this Record object. 
     * @return the login or logout time of  of this Record object 
     */
    public Date getTime() {return time;}

    /**
     * Validates and returns the terminal value for this Record object. 
     * @param terminal terminal value to be examined and set. 
     * @throws IllegalArgumentException if the terminal value is invalid 
     */
    private int setTerminal (int terminal) throws IllegalArgumentException
    {
        if (terminal <= 0)
            throw new IllegalArgumentException("The terminal value should be a positive integer.");

        if (terminal > Integer.MAX_VALUE)
            throw new IllegalArgumentException("The terminal value should be a valid positive integer.");

        try {
            terminal = Integer.parseInt(String.valueOf(terminal));
        } catch (NumberFormatException e) {
            System.err.println("Terminal value should be a valid positive integer.");
        }

        return terminal; 
    }
}