package StudentEnrolmentSystem;


import StudentEnrolmentSystem.DBConnection;
import StudentEnrolmentSystem.Course;
import java.sql.Connection;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import StudentEnrolmentSystem.Student;
//import StudentEnrolmentSystem.Course;
//import StudentEnrolmentSystem.User;



/**
 *
 * @author imaan
 */
public class ServerDAO {
    private Connection connection;
    User user, newUser;
    Course course;
    Student student;
    
public ServerDAO(){
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
}

//Change the database table when the new one is made.
public User Login(User user){
    String query = "SELECT * FROM Admin WHERE userID = ? ";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, user.getUserID());
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()){
           String userPassword = resultSet.getString("uPassword");
            String userName = resultSet.getString("uName");
            String userSurname = resultSet.getString("uSurname");
            boolean isAdmin = resultSet.getBoolean("Admin");
            
            if((user.getUserPassword()).equals(userPassword)){
                user = new User(user.getUserID(), userName, userSurname, userPassword, isAdmin);
                return user;
            } else {
                JOptionPane.showMessageDialog(null, "3");
                return null;
            }
        }
    } catch (SQLException SQLe){
        System.out.println("Error");
    }
    return null;
}

//This will be fine when new database is made
public void addUser(User user){
    String query = "INSERT INTO Admin (UserID, UName, USurname UPassword, IsAdmin) VALUES (?, ?, ?, ?, ?)";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, user.getUserID());
        stmt.setString(2, user.getUserName());
        stmt.setString(3, user.getUserSurname());
        stmt.setString(4, user.getUserPassword());
        stmt.setBoolean(5, user.IsAdmin());
        stmt.execute();
        connection.close();
        JOptionPane.showMessageDialog(null, "User successfully registered");
    } catch (SQLException SQLe){
        System.out.println("Error");
    }
}

//Irrelevant with new db
public void addStudent(Student student){
    String query = "INSERT INTO Student (Student_Number, Name, Surname, Course_Code) VALUES (?, ?, ?, ?)";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, student.getStudentNumber());
        stmt.setString(2, student.getName());
        stmt.setString(3, student.getSurname());
        stmt.setString(4, student.getCourseID());
        stmt.execute();
        connection.close();
        JOptionPane.showMessageDialog(null, "Student successfully added");
    } catch(SQLException e){
        e.printStackTrace();
    }
}

public void addCourse(Course course){
    String query = "INSERT INTO Courses(Course_Code, Course_Title, Course_Faculty) VALUES (?, ?, ?)";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, course.getCourseID());
        stmt.setString(2, course.getCourseTitle());
        stmt.setString(3, course.getCourseFaculty());
        stmt.execute();
        connection.close();
        JOptionPane.showMessageDialog(null, "Course successfully added");
    } catch(SQLException e){
        e.printStackTrace();
    }
}

public User getUser(User user){
    String query = "SELECT * FROM  Admin WHERE UserID = ?";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, user.getUserID());
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()){
            String userName = resultSet.getString("uName");
            String userSurname = resultSet.getString("USurname");
            String userPassword = resultSet.getString("uPasssword");
            boolean isAdmin = resultSet.getBoolean("isAdmin");
            
            newUser = new User(user.getUserID(), userPassword, userName, userSurname, isAdmin);
        }
    } catch (SQLException SQLException){
        
    }
     return newUser;
}

//Needs to send a course object back. i.e. no return.
public Course getCourse(Course course){
    String query = "SELECT * FROM Courses WHERE Course_Code = ?";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, course.getCourseID());
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()){
           String courseTitle = resultSet.getString("Course_Title");
           String courseFaculty = resultSet.getString("Course_Fcaulty");
            
        }
    } catch(SQLException e){
        e.printStackTrace();
    }
}

//Should be a void method, i.e. there shouldnt be a return method.
public User deleteUser(User user){
    String query = "DELETE FROM  Admin WHERE UserID = ?";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, user.getUserID());
        stmt.executeUpdate();
    } catch(SQLException e){
        e.printStackTrace();
    }
    return null;
}

//Should be a void method, i.e. there shouldnt be a return method.
public User deleteCourse(Course course){
    String query = "DELETE FROM Courses WHERE Course_Code = ?";
    try(PreparedStatement stmt = connection.prepareStatement(query)){
        stmt.setString(1, course.getCourseID());
        stmt.executeUpdate();
    } catch(SQLException e){
        e.printStackTrace();
    }
    return null;
}

//Need an enroll course, that takes both a User object and a course object.
//It will then go into the usercourse table with all the relevant info. When
//making ucID will need to convert both IDs to strings, then append them together
//Then convert them back to an integer to store in db.

}
