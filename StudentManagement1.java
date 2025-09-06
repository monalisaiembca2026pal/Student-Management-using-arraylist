import java.util.ArrayList;
import java.util.Scanner;


class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

class DuplicateStudentException extends Exception {
    public DuplicateStudentException(String message) {
        super(message);
    }
}



class Student {
    int id;          // unique student identifier
    String name;     // student's name
    String course;   // course enrolled

    Student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    // Nice string representation for display in the console
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Course: " + course;
    }
}



interface StudentOperations {
    void addStudent(Student student) throws DuplicateStudentException;
    void viewStudents();
    Student searchStudent(int id) throws StudentNotFoundException;
    void deleteStudent(int id) throws StudentNotFoundException;
}



class StudentService implements StudentOperations {
    private ArrayList<Student> students = new ArrayList<>();  // acts as our mini “database”

    @Override
    public void addStudent(Student student) throws DuplicateStudentException {
      
        for (Student s : students) {
            if (s.id == student.id) {
                throw new DuplicateStudentException(
                    " Student with ID " + student.id + " already exists."
                );
            }
        }
        students.add(student);               // add the new student
        System.out.println(" Student Added Successfully!");
    }

    @Override
    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            System.out.println("\n--- Student List ---");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    @Override
    public Student searchStudent(int id) throws StudentNotFoundException {
        // Linear search in the list
        for (Student s : students) {
            if (s.id == id) {
                return s;                     // student found
            }
        }
        
        throw new StudentNotFoundException(" Student with ID " + id + " not found.");
    }

    @Override
    public void deleteStudent(int id) throws StudentNotFoundException {
        Student toRemove = null;

        // Find the student to remove
        for (Student s : students) {
            if (s.id == id) {
                toRemove = s;
                break;
            }
        }

        if (toRemove != null) {
            students.remove(toRemove);        // perform deletion
            System.out.println(" Student Deleted Successfully!");
        } else {
            throw new StudentNotFoundException(" Student with ID " + id + " not found.");
        }
    }
}



class StudentManagement1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);      // For reading user input
        StudentService service = new StudentService(); // Business-logic handler
        int choice;

        do {
            // Display menu
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Delete Student by ID");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();                // Read menu option

            try {
                switch (choice) {
                    case 1:
                        // Gather new-student details
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();            // consume trailing newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();

                        // Attempt to add the student
                        service.addStudent(new Student(id, name, course));
                        break;

                    case 2:
                        service.viewStudents();
                        break;

                    case 3:
                        System.out.print("Enter ID to search: ");
                        int searchId = sc.nextInt();
                        Student s = service.searchStudent(searchId);
                        System.out.println("Found: " + s);
                        break;

                    case 4:
                        System.out.print("Enter ID to delete: ");
                        int delId = sc.nextInt();
                        service.deleteStudent(delId);
                        break;

                    case 5:
                        System.out.println("Exiting... ");
                        break;

                    default:
                        System.out.println(" Invalid choice. Try again.");
                }
            } catch (DuplicateStudentException | StudentNotFoundException e) {
                // Handle custom exceptions in one place
                System.out.println(e.getMessage());
            }
        } while (choice != 5);

        sc.close(); 
    }
}