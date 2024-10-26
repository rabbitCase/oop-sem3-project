import java.util.InputMismatchException;
import java.util.Scanner;
interface attendanceDatabase{
    void showAttendance(int month, int day, int students);
    void markAttendance(int month, int day, int students);
    void rollNumberWise(int students);
}

class Attendance implements attendanceDatabase{
    public static int student=61;
    public static int day=31;
    public static int month=12;
    public static int [][][] attendance= new int [month][day][student];//3D array for month,day,no of students
    // Initialize array to some constant

    public Attendance(){ //Constructor will initialize array to -1 for every new object of Attendance. Use mutiple attendance objects for      multiple subjects
        for (int i=0;i<month;i++) {
            for (int j=0; j<day;j++) {
                for (int k=0; k<student;k++) {
                    attendance[i][j][k] = -1;
                }
            }
        }
    }
    
    public void markAttendance(int month, int day,int students){
        Scanner sc=new Scanner(System.in);
        int contchoice, choice;
        if(attendance[month-1][day-1][students-1]!=-1){
            System.out.println("Warning! Attendance for this date has already been marked.\nPress any number to overwrite this data\nPress 0 go back");
            contchoice=sc.nextInt();
            if(contchoice==0){
                return;
            }
        }
        System.out.println("Marking attendance for "+day+"/"+month+"/2024: ");
        System.out.println("Enter 1 to mark PRESENT\nEnter 0 to mark ABSENT:");
        for (int i = 0; i < students; i++) {
            boolean isValid=false;
            while (!isValid) {
                System.out.print("Roll no "+(i+1)+": ");
                try {
                    choice = sc.nextInt();
                    if (choice==1 || choice==0) {
                        attendance[month-1][day-1][i] = choice;
                        isValid=true;
                    } else {
                        System.out.println("Please enter either 0 or 1");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Only integers are allowed.");
                    sc.next();  //consume invalid character
                }
            }
        }
        System.out.println("Attendance Succesfully Marked");
    }   

    public void showAttendance(int month,int day ,int students){
        if(attendance[month-1][day-1][students-1]==-1){
            System.out.println("Attendance for this date hasn't been marked yet.");//if attendance is not marked then array will contain -1
            return;
        }
        System.out.println(attendance[month][day][students]);
        System.out.println("Showing attendance for "+day+"/"+month+"/2024: ");
        for(int i=0;i<students;i++){
            System.out.print("Roll no "+(i+1)+": ");
            if(attendance[month-1][day-1][i]==1){
                System.out.print("PRESENT");
            }
            else{
            System.out.print("ABSENT");
            }
            System.out.println();
        }
    }
    public void rollNumberWise(int roll){//display total attendance for the year(%)
        double present=0;
        for(int i=0;i<12;i++){
            for(int j=0;j<31;j++){
                if(attendance[i][j][roll-1]==1){
                    present++;
                }
            }
        }

        System.out.println("Attendance details for Roll Number "+roll+": ");
        System.out.printf("\nYou were present for %.0f/365 days ",present);
        System.out.printf("\nAttendance: %.1f%%.\n",((present/365)*100));
        if(((present/365)*100)<75)
            System.out.println("Warning! Attendance is less than 75%. ");
    }


    public static void main(String[] args) {
        Attendance a1=new Attendance();//Create multiple objects for multiple courses
        Scanner sc=new Scanner(System.in);
        System.out.println("ATTENDANCE DATABASE FOR OOP (CCE-B 2024-25)");

        int month,day,choice,flag=0;
        int headcount = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter the headcount of CCE-B to start marking/viewing attendance: ");
            if (sc.hasNextInt()) {
                headcount = sc.nextInt();
                if(headcount>60){
                    headcount=60;
                    System.out.println("Max capacity for CCE-B is 60. Program will use head count as 60 for further operations.");
                }
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.next(); //consume the invalid character
            }
        }
        
        do { 
            System.out.println("Press 1 to mark Attendance\nPress 2 to show Attendance\nPress 3 to EXIT");
            try{
            System.out.println("Enter your choice: ");
            choice=sc.nextInt();
            switch (choice) {

                case (1):

                    System.out.println("Enter month (1-12): ");
                    month=sc.nextInt();
                        System.out.println("Enter day: ");
                        day=sc.nextInt();
                        a1.markAttendance(month,day,headcount);
                    break;

                case (2):

                    int teacherOrStudent;
                    System.out.println("Press 1 to view class attendance for a date\nPress 2 to view total individual attendance for the year");
                    teacherOrStudent=sc.nextInt();
                    switch(teacherOrStudent){
                        case(1):
                        System.out.println("Enter month(1-12): ");
                        month=sc.nextInt();
                        System.out.println("Enter day: ");
                        day=sc.nextInt();
                        a1.showAttendance(month,day,headcount);
                        break;
                        
                        case(2):

                        System.out.println("Enter your Roll Number: ");
                        int roll=sc.nextInt();
                        a1.rollNumberWise(roll);
                        break;

                        default:
                            System.out.println("Invalid Choice");
                            break;
                    }
                break;

                case (3):

                    flag=1;
                    break;

                default:

                    System.out.println("Invalid Choice");
                    break;
            }
            }catch(InputMismatchException e){
                System.out.println("Invalid choice. Only Integers are allowed.");
                sc.next();//consume the invalid character
            }
        } while(flag==0);
        System.out.println("EXIT");
    }
}