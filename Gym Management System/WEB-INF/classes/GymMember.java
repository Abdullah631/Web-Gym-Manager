import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class GymMember {
    String cnic;
    String name;
    int age;
    String email;
    String phoneNumber;
    String membershipType;
    String startDate;
    public GymMember(){
        this.cnic=null;
        this.name=null;
        this.age=0;
        this.email=null;
        this.phoneNumber=null;
        this.membershipType=null;
        this.startDate=null;
    }
    public GymMember(String cnic, String name, int age, String email, String phone_number, String membership_type, String start_date){
        this.cnic=cnic;
        this.name=name;
        this.age=age;
        this.email=email;
        this.phoneNumber=phone_number;
        this.membershipType=membership_type;
        this.startDate=start_date;
    }
}
