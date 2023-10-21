// package project3; 
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.Date;
import java.util.NoSuchElementException;

/* *
*
* This class accesses the input file consisting of login and logout details of users
* is a multi-user system. The input file name (with extension) must be given as the program's
* single command line argument. 
* A single login or logout record is stored as an object Record in a list which
* is a RecordList object in a chronological manner. 
* It also consists of an interactive part where users can extract the first and last
* session details of a particular user if such a user exists in the database. 
*
* @author Sandhya Sharma
*
*/

public class LoginStats
{
    /**
     * The main() method of this program. 
     * @param args array of Strings provided on the command line when the program is started.  
     * The first string should be the name of the input file containing the list of login/logout records.
     * 
     * @throws NoSuchElementException if input username is not found in the databse
     * @throws IllegalArgumentException if a Record object is being constructed with invalid arguments 
     * @throws FileNotFoundException if an input file cannot be accessed to read 
     * Some parts have been inspired by main program of Project 1 (ColorConverter.java, @author Joanna Klukowska) 
     * 
     */
    public static void main(String[] args)
    {
        //check that the input file name has been given
        if(args.length == 0){
            System.err.println("Error: input file name not given.");
            System.exit(1); 
        }

        //check if the given file name exists and can be read 
        File input_file = new File(args[0]); 

        if(!input_file.exists()){
            System.err.println("\n Error 404: input file " + input_file.getAbsolutePath() + "name not found. \n");
            System.exit(1); 
        }

        if(!input_file.canRead()){
            System.err.println("\n Error: input file" + input_file.getAbsolutePath() + " cannot be opened. \n");
            System.exit(1); 
        }

        //access data from the input file name and store a single entry as a Record object and add it to RecordList object
        Scanner read_file = null; 

        try {
            read_file = new Scanner (input_file); 
        } catch (FileNotFoundException e) {
            System.err.println("\n Error: input file" + input_file.getAbsolutePath() + "cannot be opened to read. \n"); 
            System.exit(1); 
        }

        RecordList inventory = new RecordList(); 

        String line = " "; 
        Scanner parse_line = null; 
        int given_terminal = 0; 
        int terminal = 0; 
        boolean login = false;
        Date time = new Date(); 
        String username = " ";

        while(read_file.hasNextLine()){
            try {
                line = read_file.nextLine(); 
                parse_line = new Scanner(line); 
                parse_line.useDelimiter(" "); 
                given_terminal = Integer.parseInt(parse_line.next()); 
                
                login = given_terminal > 0;

                terminal = Math.abs(given_terminal); 

                time = new Date(Long.parseLong(parse_line.next())); 
                username = parse_line.next();

            } catch (NoSuchElementException e) {
                System.err.println("Error caused by the following line in input file: \n" + line);
                continue; 
            }

            try {
                Record new_record = new Record(terminal, login, username, time);
                inventory.add(new_record);  
            } catch (IllegalArgumentException e) {
                // TODO: handle exception
            }
        }

        parse_line.close(); 
        
        Record rec1 = new Record(1, true, "user1", new Date(1000));
        Record rec2 = new Record(2, true, "user2", new Date(2000));

        //USER INTERFACE 
        Scanner user_input = new Scanner(System.in); 
        String input = " "; 

        System.out.println();
        System.out.println("\n \n---Welcome to LoginStats!--- \n");
        //calling the method to print the menu
        printMenu(); 
        
        // call for user input in a loop till the user says 'quit'
        do{
            //prompting the user for a command
            System.out.println("\nYour input: \n"); 
            input = user_input.nextLine();
            input = input.trim();

            //if the input is empty or blank 
            if(input.isEmpty() || input.isBlank()){
                System.out.println("Input is empty. Please enter a valid command. \n");
                continue;
            }

            //if the command is quit 
            if(input.equalsIgnoreCase("quit")){
                System.out.println("You have successfully exited the database. Thank you :)) \n"); 
                System.exit(0);
            }

            //if the command is valid but is missing the user name
            if(input.equalsIgnoreCase("first") || input.equalsIgnoreCase("last") || input.equalsIgnoreCase("all") || input.equalsIgnoreCase("total")) {
                System.out.println("Please give a username. \n");
                continue;
            }

            //scanning and parsing the input to command and user name
            Scanner parse_input = new Scanner(input);
            parse_input.useDelimiter(" "); 
            String command = parse_input.next(); 
            String user = " ";

            //if the username is missing after the delimiter 
            if (!parse_input.hasNext()) {
                    System.err.println("Please provide a valid input of command and username.");
                    continue;
                }
            else
                user = parse_input.next();
        
            //check if the username contains valid characters (alphanumeric and underscore acceptable)
            if(!user.matches("^[a-zA-Z0-9_]+$")){
                System.out.println("Please enter a valid user name containing alphanumeric characters (underscores accepted).");
                continue;
            }

            //if the command is 'first'
            if(command.equalsIgnoreCase("first")) {
                try {
                    Session output = inventory.getFirstSession(user); 
                    System.out.println(output); 
                } catch (NoSuchElementException e) {
                    System.err.println("No such user found."); 
                    continue;  
                }
            }
            //if the command is 'last'
            else if(command.equalsIgnoreCase("last")) {
                try {
                    Session output = inventory.getLastSession(user);
                    System.out.println(output);  
                } catch (NoSuchElementException e) {
                    System.err.println("No such user found."); 
                    continue;   
                }
            }
            //if the command is 'all'
            else if(command.equalsIgnoreCase("all")) {
                try {
                    DLL output = inventory.getAllSessions(user); 
                    System.out.println(output); 
                } catch (NoSuchElementException e) {
                    System.err.println("No such user found."); 
                    continue;  
                }
            }
            //if the command is 'total'
            else if(command.equalsIgnoreCase("total")) {
                try {
                    long total_time = inventory.getTotalTime(user);

                    if(total_time == 0){
                        System.out.println("User has not logged out yet.");
                        continue; 
                    }

                    if (total_time < 0)
                        throw new IllegalArgumentException("Duration cannot be negative.");
        
                    long seconds = 0;
                    long minutes = 0; 
                    long hours = 0;
                    long days = 0;
                    try {
                        seconds = total_time/1000;
                        minutes = seconds / 60;
                        hours = minutes / 60;
                        days = hours / 24;
                        seconds %= 60;
                        minutes %= 60;
                        hours %= 24;
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Incorrect value for time.");
                    }
                    String formatted_time = String.format("%d days %d hours %d minutes %d seconds", days, hours, minutes, seconds);
                    System.out.println(user + ", total duration " + formatted_time);

                } catch (Exception e) {
                    System.err.println("No such user found."); 
                    continue; 
                }
            }
            // anything else is an invalid command, if there is a third word (eg: last name) after the user name, 
            // it will be ignored and only the entry after 'first' or 'last' will be taken into consideration
            else {
                System.err.println("Please enter a valid command.");
                continue; 
            }
            parse_input.close();
        } while(!input.equalsIgnoreCase("quit")); 
        
        user_input.close(); 
    }

    /**
     * This method prints out the menu when the program is first run. 
     */
    public static void printMenu()
    {
        System.out.println();
        System.out.println("Available commands: ");
        System.out.println();
        System.out.println("first USERNAME \t \t : displays the information about the first login session for the specified user");
        System.out.println("last USERNAME \t \t : displays the information about the last login session for the specified user");
        System.out.println("all USERNAME \t \t : displays the information about all login sessions for the specified user");
        System.out.println("total USERNAME \t \t : displays the total duration of all login sessions for the specified user");
        System.out.println("quit \t \t \t : exits the program \n");
    }
}
