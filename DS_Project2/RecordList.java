package project2; 
import java.util.ArrayList; 
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

/* *
*
* RecordList is used to store a collection of Record objects.
* This class inherits all of its properties from an ArrayList<Record>. It 
* has Record-specific functions that allow retrieval of first or last session 
* details of a particular user.
* 
* This class stores Record objects in a chronological order. 
* 
* @author Sandhya Sharma 
*
*/

public class RecordList extends ArrayList<Record>
{
     /**
     * Constructs a new empty RecordList object by calling super() from ArrayList.
     */
    public RecordList()
    {
        super(); 
    }

    /**
     * Search through the list of Record objects for an object matching 
     * the given username. 
     * @param user the name of the user for which to search 
     * @return the first session of this user if the user is found in the inventory
     * @throws IllegalArgumentException is parameter is not given or is empty
     * @throws NoSuchElementException if this object is empty 
     */
    public Session getFirstSession(String user)
    {
        if(user == null || user.isEmpty())
            throw new IllegalArgumentException("User name must be given to get the first session.");

        if(this.isEmpty())
            throw new NoSuchElementException("Record is empty.");

        int first_terminal = 0; 
        boolean first_login = false; 
        Date first_login_time = new Date();
        boolean user_found = false; 
        int first_login_index = 0; 

        //iterate through the inventory to look for matches for the particular user 
        for(int i = 0; i < this.size(); i++){
            String current_name = this.get(i).getUsername();
            if (current_name.equalsIgnoreCase(user) && this.get(i).isLogin() == true){
                first_terminal = this.get(i).getTerminal();
                first_login = this.get(i).isLogin();  
                first_login_time = this.get(i).getTime(); 
                user_found = true; 
                first_login_index = i;
                break; 
            }
        }

        //in case of user name not found
        if (user_found == false){
            throw new NoSuchElementException("User not in the list."); 
        }

        //build a Record object using arguments retrieved from the matched login Record object in the list
        Record first_login_record = new Record(first_terminal, first_login, user, first_login_time);

        boolean first_logout = false; 
        Date first_logout_time = new Date(); 
        boolean still_logged_in = true; 

        //iterate through the inventory after the first login data index to look for matching logout data 
        for(int i = first_login_index; i < this.size(); i++){
            String current_name = this.get(i).getUsername();
            if (current_name.equalsIgnoreCase(user) && this.get(i).getTerminal() == first_terminal && this.get(i).isLogout() == true){
                first_logout = this.get(i).isLogout();  
                first_logout_time = this.get(i).getTime(); 
                still_logged_in = false; 
                break;
            }
        }
        
        //building a Session object according to the logout status of the user
        if(still_logged_in == false){
            //build a Record object using arguments retrieved from the matched logout Record object in the list
            Record first_logout_record = new Record(first_terminal, first_logout, user, first_logout_time);
            //Session object is constructed using login and logout Record objects
            Session first_session = new Session(first_login_record, first_logout_record);
            return first_session;
        }
        else{
            //if the user is still logged in, Session object is constructed using one argument only
            Session first_session = new Session(first_login_record); 
            return first_session;
        } 
    }

    /**
     * Search through the list of Record objects for an object matching 
     * the given username. 
     * @param user the name of the user for which to search 
     * @return the last session of this user if the user is found in the inventory
     * @throws IllegalArgumentException is parameter is not given or is empty
     * @throws NoSuchElementException if this object is empty 
     */
    public Session getLastSession(String user)
    {
        if(user == null || user.isEmpty())
            throw new IllegalArgumentException("User name must be given to get the first session.");
        
        if(this.isEmpty())
            throw new NoSuchElementException("Record is empty.");
        
        int last_terminal = 0;
        boolean last_login = false;
        Date last_login_time = new Date(); 
        boolean user_found = false; 
        int last_login_index = 0; 

        //iterate through the inventory to look for matches for the particular user
        for(int i = 0; i < this.size(); i++){
            String current_name = this.get(i).getUsername();
            if (current_name.equalsIgnoreCase(user) && this.get(i).isLogin() == true){
                last_terminal = this.get(i).getTerminal();
                last_login = this.get(i).isLogin();  
                last_login_time = this.get(i).getTime(); 
                user_found = true; 
                last_login_index = i;
            }
        }

        //in case of user name not found
        if (user_found == false){
            throw new NoSuchElementException("User not in the list."); 
        }

        //build a Record object using arguments retrieved from the matched login Record object in the list
        Record last_login_record = new Record(last_terminal, last_login, user, last_login_time);

        boolean last_logout = false;
        Date last_logout_time = new Date();
        boolean still_logged_in = true;

        //iterate through the inventory after the last login data index to look for matching logout data 
        for(int i = last_login_index; i < this.size(); i++){
            String current_name = this.get(i).getUsername();
            if (current_name.equalsIgnoreCase(user) && this.get(i).getTerminal() == last_terminal && this.get(i).isLogout() == true){
                last_logout = this.get(i).isLogout();  
                last_logout_time = this.get(i).getTime(); 
                still_logged_in = false; 
            }
        }

        //building a Session object according to the logout status of the user
        if(still_logged_in == false){
            //build a Record object using arguments retrieved from the matched logout Record object in the list
            Record last_logout_record = new Record(last_terminal, last_logout, user, last_logout_time);
            //Session object is constructed using login and logout Record objects
            Session last_session = new Session(last_login_record, last_logout_record);
            return last_session;
        }
        else{
            //if the user is still logged in, Session object is constructed using one argument only
            Session last_session = new Session(last_login_record);
            return last_session;
        }
    }
}
