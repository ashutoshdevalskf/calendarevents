import java.util.*;  
import java.lang.*;  
import java.io.*;
import java.time.*;

class Events{
    int date, month,year,hours,min;
    String first,event,DD,MM,YYYY,HH,mm;
    Events(){}
    public String[] search(String key){
        String filename = "eventList.txt";
        String searchElement = key;
        String[] foundevent= "0 0 0".split(" ");
        
        // Search for the element in the file
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(" ");
                if (elements[0].equals(searchElement)) {
                    
                    foundevent = elements;
                    break;
                }
            }
            bufferedReader.close();
            fileReader.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        return foundevent;

    }
    public void entry(){
        
        DD=Integer.toString(date);
        MM=Integer.toString(month);
        YYYY=Integer.toString(year);
        HH=Integer.toString(hours);
        mm=Integer.toString(min);
        first = DD+MM+YYYY;

        ArrayList<String> ev = new ArrayList<>();
        ev.add(first);
        ev.add(DD);
        ev.add(MM);
        ev.add(YYYY);
        ev.add(HH);
        ev.add(mm);
        ev.add(event);
        try {
            FileWriter writer = new FileWriter("eventlist.txt",true);
            for (String element : ev) {
                writer.write(element + " ");
            }
            writer.write("\n");
            writer.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred while writing the list to the file.");
            e.printStackTrace();
        }

        

    }
    Events(int d, int m, int y, int h, int t, String eventName){
        date=d;
        month=m;
        year=y;
        hours=h;
        min=t;
        event=eventName;

        entry();
    }
}

class calendar{

    
    public static boolean isevent(int day, int month, int year) {
        String key =  "" + day + month + year; 
        Events e = new Events()  ;
        String[] event = e.search(key);
        
        return event[0].length()>1;
    }
    public static boolean insert(int date,int month,int year,int hours,int min,String event){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to insert this event? Press(Y/y) or Press (r/R) to retry: ");
        
        char query =  sc.nextLine().charAt(0);
        if(query=='r'||query=='R'){
            return false;
        }
        else if(query=='y'||query=='Y'){
            Events e = new Events(date, month,year,hours,min,event);
            System.out.println("Event Created Successfully!");
            return true;
        }
        else{
            System.out.println("Invalid Query!");
            return insert(date, month,year,hours,min,event);
        }
    }
    public static void createEvent(){
        
        Scanner sc = new Scanner(System.in); 
        while(true){
            int date, month,year,hours,min;
          
            
            System.out.println("Create a new event here.");
            System.out.println("");
            System.out.println("Event Name: ");
            String event=sc.nextLine();
            System.out.println("Enter date: ");
            date=sc.nextInt();
            System.out.println("Enter month: ");
            month=sc.nextInt();
            System.out.println("Enter year: ");
            year=sc.nextInt();
            System.out.println("Enter time (Hour): ");
            hours=sc.nextInt();
            System.out.println("Enter time (Minutes): ");
            min=sc.nextInt();
           
            
            System.out.println("Event Date: " + date +"-" + month + "-" + year);
            System.out.println("Event Time: " + hours + ":" + min);
            System.out.println("Event name: " + event );
            boolean check = insert(date, month,year,hours,min,event);
            if(check){
                break;
            }
            else{
                createEvent();
                return;
            }
        }

        
    }
    public static void viewEvents(){
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_PURPLE = "\u001B[35m";
        System.out.println("View monthly Events");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter year: ");
        int year = sc.nextInt();
        System.out.print("Enter month (1-12): ");
        int month = sc.nextInt() - 1; 
        int mnth = month+1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1); 
        
        
        System.out.println(ANSI_GREEN+"    S    M    T    W    T    F    S"+ANSI_RESET);
        
        
        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
            System.out.print("     ");
        }
        
        
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isevent(i,mnth,year)) {
                System.out.print(ANSI_RED);
                System.out.printf(" (%2d)", i);
                System.out.print(ANSI_RESET);
            } else {

                System.out.printf("%5d", i);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println();
            }
            calendar.add(Calendar.DATE, 1);
        }
        
        System.out.println("");
        while(true){
            System.out.println("");
            System.out.print("Enter day to view event or press 0 to exit: ");
            int i = sc.nextInt();
            if(i==0){
                break;
            }
            else{
                String key =  "" + i + mnth + year; 
                Events e = new Events()  ;
                String[] event = e.search(key);
                //System.out.println(key + event[0]);
                if(event[0].length()>1){
                    System.out.println("Event name: " +ANSI_PURPLE+  event[6]+ANSI_RESET);
                    System.out.println("Event date: " + i+"-"+mnth+"-"+year);
                }
                else {
                    System.out.println(ANSI_RED+"No event on this day"+ANSI_RESET);
                }
        

            }

            
        }
    }
    public static void upcomingEvents(){
        LocalDate currentDate = LocalDate.now();
        int dayOfMonth = currentDate.getDayOfMonth();
        LocalDateTime now = LocalDateTime.now();
        Calendar cal = Calendar.getInstance();
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_PURPLE = "\u001B[35m";
        System.out.println("\n"+"\u001B[33m"+"Events this month: "+"\u001B[0m\n");
        Scanner sc = new Scanner(System.in);
        
        int year = cal.get(Calendar.YEAR);
        
        int month = now.getMonthValue() - 1; 
        int mnth = month+1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1); 
        
        
        System.out.println(ANSI_GREEN+"    S    M    T    W    T    F    S"+ANSI_RESET);
        
        ArrayList<Integer> datesofevent = new ArrayList<Integer>();
        for (int i = 1; i < calendar.get(Calendar.DAY_OF_WEEK); i++) {
            System.out.print("     ");
        }
        
        
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (isevent(i,mnth,year)) {
                if(i>=dayOfMonth){
                    datesofevent.add(i);
                }
                System.out.print(ANSI_RED);
                System.out.printf(" (%2d)", i);
                System.out.print(ANSI_RESET);
            } else {

                System.out.printf("%5d", i);
            }
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                System.out.println();
            }
            calendar.add(Calendar.DATE, 1);
        }
        
        System.out.println("\n");
        while(true){
            if(datesofevent.size()==0){
                System.out.println(ANSI_RED+"No Events This Month"+"\n"+ANSI_RESET);

            }
            else{
                System.out.println("Upcoming events this month: "+"\n");
                for(int i = 0; i < datesofevent.size(); i++) {
                    String key =  "" + datesofevent.get(i) + mnth + year; 
                    Events e = new Events();
                    String[] event = e.search(key);
                    
                    
                    System.out.println("Event name: " +ANSI_GREEN+  event[6]+ANSI_RESET);
                    System.out.println("Event date: " + datesofevent.get(i)+"-"+mnth+"-"+year+"\n");
                    
                }
                
            }
            System.out.println("Press any key to go to previous menu");
            sc.nextLine();
            break;

        }
        
    }
    public static void main(String args[]){ 
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_CYAN = "\u001B[34m";
        Scanner sc = new Scanner(System.in); 
	    System.out.println("Welcome to Calendar Events");
        System.out.println("Project by:-");
        System.out.println("Name: " + "Abhinav Gautam");
        System.out.println("Reg. No:" + "12115671"); 
        System.out.println("");
        System.out.println("Name: " + "Musharaf Ali");
        System.out.println("Reg. No:" + "12114514"); 
        System.out.println("");
        System.out.println("Name: " + "Ashutosh Deval");
        System.out.println("Reg. No:" + "12115428"); 
        System.out.println("");

        while(true){
            
            
            System.out.println("");
            System.out.println("");
            System.out.println(ANSI_CYAN +"░"+ ANSI_RED+"█████╗"+ ANSI_CYAN+"░░"+ ANSI_RED+"█████╗"+ ANSI_CYAN+"░"+ ANSI_RED+"██╗"+ ANSI_CYAN+"░░░░░"+ ANSI_RED+"███████╗███╗"+ ANSI_CYAN+"░░"+ ANSI_RED+"██╗██████╗"+ ANSI_CYAN+"░░"+ ANSI_RED+"█████╗"+ ANSI_CYAN+"░"+ ANSI_RED+"██████╗"+ ANSI_CYAN+"░"+ ANSI_RESET);
            System.out.println(ANSI_RED +"██╔══██╗██╔══██╗██║"+ ANSI_CYAN+"░░░░░"+ ANSI_RED+"██╔════╝████╗"+ ANSI_CYAN+"░"+ ANSI_RED+"██║██╔══██╗██╔══██╗██╔══██╗"+ ANSI_RESET);
            System.out.println(ANSI_RED +"██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"╚═╝███████║██║"+ ANSI_CYAN+"░░░░░"+ ANSI_RED+"█████╗"+ ANSI_CYAN+"░░"+ ANSI_RED+"██╔██╗██║██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"██║███████║██████╔╝"+ ANSI_RESET);
            System.out.println(ANSI_RED +"██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"██╗██╔══██║██║"+ ANSI_CYAN+"░░░░░"+ ANSI_RED+"██╔══╝"+ ANSI_CYAN+"░░"+ ANSI_RED+"██║╚████║██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"██║██╔══██║██╔══██╗"+ ANSI_RESET);
            System.out.println(ANSI_RED +"╚█████╔╝██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"██║███████╗███████╗██║"+ ANSI_CYAN+"░"+ ANSI_RED+"╚███║██████╔╝██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"██║██║"+ ANSI_CYAN+"░░"+ ANSI_RED+"██║"+ ANSI_RESET);
            System.out.println(ANSI_CYAN +"░"+ ANSI_RED+"╚════╝"+ ANSI_CYAN+"░"+ ANSI_RED+"╚═╝"+ ANSI_CYAN+"░░"+ ANSI_RED+"╚═╝╚══════╝╚══════╝╚═╝"+ ANSI_CYAN+"░░"+ ANSI_RED+"╚══╝╚═════╝"+ ANSI_CYAN+"░"+ ANSI_RED+"╚═╝"+ ANSI_CYAN+"░░"+ ANSI_RED+"╚═╝╚═╝"+ ANSI_CYAN+"░░"+ ANSI_RED+"╚═╝"+ ANSI_RESET);
            System.out.println(ANSI_GREEN +"                                          █▀▀ █░█ █▀▀ █▄░█ ▀█▀ █▀"+ ANSI_RESET);
            System.out.println(ANSI_GREEN +"                                          ██▄ ▀▄▀ ██▄ █░▀█ ░█░ ▄█"+ ANSI_RESET);
            System.out.println("");
            System.out.println("");
            System.out.println(ANSI_GREEN +"[C]"+ANSI_RESET+" Create New Event");
            System.out.println(ANSI_GREEN +"[V]"+ANSI_RESET+" View Month wise Events");
            System.out.println(ANSI_GREEN +"[R]"+ANSI_RESET+" View Recent Upcoming Events");
            System.out.println(ANSI_GREEN +"[Q]"+ANSI_RESET+" Quit");
            System.out.println("");
            System.out.println("");
            System.out.println("Press any query C/V/R/Q, to continue: ");
            char query = sc.nextLine().charAt(0);
            if(query=='c'||query=='C'){
                createEvent();
            }
            else if(query=='v'||query=='V'){
                viewEvents();
            }
            else if(query=='r'||query=='R'){
                upcomingEvents();
            }
            
            else if(query=='q'||query=='Q'){
                break;
            }
            else{
                System.out.println("Invalid Query!");
                continue;
            }


        }
        
        
    }


}

