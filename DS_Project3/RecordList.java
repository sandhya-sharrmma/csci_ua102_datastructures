package project3;

import java.util.ArrayList; 
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.crypto.NullCipher;
import java.util.Iterator;

/* *
*
* RecordList is used to store a collection of Record objects.
* This class inherits all of its properties from an SortedLinkedList<Record>
* which is a generic implementation of a doubly-linked list. 
* It has Record-specific functions that allow retrieval of first session, last session,
* all sessions and total time of a particular user. 
* 
* This class stores Record objects in a chronological order. 
* 
* @author Sandhya Sharma 
*
*/

public class RecordList extends SortedLinkedList<Record>
{
     /**
     * Constructs a new empty RecordList object by calling super() from SortedLinkedList.
     */
    public RecordList()
    {
        super(); 
    }

    /**
     * Search through the list of Record objects for the first object matching 
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

        if(this.size() == 0)
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
        if (user_found == false)
            throw new NoSuchElementException("User not in the list."); 

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
            Record first_logout_record = new Record(first_terminal, !first_logout, user, first_logout_time);
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
     * Search through the list of Record objects for the last object matching 
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
        
        if(this.size() == 0)
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
            Record last_logout_record = new Record(last_terminal, !last_logout, user, last_logout_time);
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
     /**
     * This function calls on the getAllSessions() method to get all the sessions of a particular user, 
     * then computes the total time of all sessions if the user is not still logged in.
     * @return total time of all sessions of a particular user across terminals
     * @throws IllegalArgumentException if the user name is not given or is empty
     * @throws NoSuchElementException if this object is empty
     */
    public long getTotalTime(String user) throws IllegalArgumentException, NoSuchElementException
    {
        if(user == null || user.isEmpty())
            throw new IllegalArgumentException("User name must be given to get the first session.");
        
        if(this.size() == 0)
            throw new NoSuchElementException("Record is empty.");
        
        long total_time = 0;

        SortedLinkedList user_all_sessions = this.getAllSessions(user);

        Iterator<Session> itr = user_all_sessions.iterator();

        while(itr.hasNext()){
            Session current_session = itr.next();
            if(current_session.getLogoutTime() != null){
                long session_time = current_session.getDuration();
                if(session_time > 0)
                    total_time += session_time;
            }
        }

        return total_time; 
    }

    /**
     * This function returns all the sessions of a particular user across terminals.
     * @return a SortedLinkedList of Session objects for the given user 
     * @throws IllegalArgumentException if argument is null
     * @throws NoSuchElementException if this object is empty
     */
    public SortedLinkedList<Session> getAllSessions(String user) throws IllegalArgumentException, NoSuchElementException
    {
        SortedLinkedList<Session> all_sessions = new SortedLinkedList<Session>(); 

        if(user == null || user.isEmpty()) 
            throw new IllegalArgumentException("User name must be given to get the sessions.");
        
        if(this.size() == 0)
            throw new NoSuchElementException("Record is empty."); 

        String current_name = " "; 
        int terminal = 0;
        boolean login = false;  
        Date login_time = new Date(); 
        int login_index = -1; 
        boolean user_found = false;

        boolean logout = false;
        Date logout_time = new Date();
        boolean still_logged_in = true;

        //using an iterator object to iterate throught the list
        Iterator<Record> itr = this.iterator(); 

        while(itr.hasNext()){
            Record current_record = itr.next();
            current_name = current_record.getUsername();
            
            if(current_name.equalsIgnoreCase(user) && current_record.isLogin() == true){
                //get the login data from the matched Record object
                terminal = current_record.getTerminal();
                login = current_record.isLogin();  
                login_time = current_record.getTime(); 
                user_found = true; 
                login_index = this.indexOf(current_record);
                still_logged_in = true;

                //build a Record object using arguments retrieved from the matched login Record object in the list
                Record login_record = new Record(terminal, login, user, login_time); 

                //iterate through the inventory after the login data index to look for matching logout data since the is in a chronological order
                for (int i = login_index; i < this.size(); i++){
                    if (current_name.equalsIgnoreCase(user) && this.get(i).getTerminal() == terminal && this.get(i).isLogout() == true){
                        logout_time = this.get(i).getTime(); 
                        logout = this.get(i).isLogout();
                        still_logged_in = false; 
                        break;
                    }
                }

                //build a Record object using arguments retrieved from the matched logout Record object in the list
                //check for both cases: if the user is stil logged in or not
                if(!still_logged_in){
                    Record logout_record = new Record(terminal, !logout, user, logout_time);
                    Session session = new Session(login_record, logout_record);
                    all_sessions.add(session); 
                }
                else{
                    Session session = new Session(login_record);
                    all_sessions.add(session); 
                }
            }
        }

        //in case of user name not found in the list
        if(!user_found)
            throw new NoSuchElementException("User not in the list.");

        return all_sessions; 
    }  

    /**
     * This function iterates through returns a string of all the objects in the list
     * @return a string of all the objects in the list
     */
    @Override
    public String toString()
    {
        Iterator<Record> itr = this.iterator(); 
        String output = " ";

        while(itr.hasNext()){
            Record current_record = itr.next();
            output += current_record.toString();
        }
        
        return output;
    }
}
