/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yumXpress.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import yumXpress.dbutil.DBConnection;
import yumXpress.pojo.CompanyPojo;
import yumXpress.pojo.CustomerPojo;


public class CustomerDAO {
    
    public static String getNewId() throws SQLException{
     Connection conn=DBConnection.getConnection();
     Statement st=conn.createStatement();
     ResultSet rs=st.executeQuery("Select max(customer_id)from customers");
     rs.next();
     String  id= rs.getString(1);
     String custId="";
     if(id!=null){
     id=id.substring(4);
     custId="CUS-"+(Integer.parseInt(id)+1);
     }
     else{
      custId="CUS-101";
     }
     return custId;
    }
    
    public static boolean addCustomer(CustomerPojo customer) throws SQLException{
        
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("insert into customers values(?, ?, ?, ?, ?, ?)");
    customer.setCustomerId(getNewId());
    ps.setString(1, customer.getCustomerId());
    ps.setString(2, customer.getCustomerName());
    ps.setString(3, customer.getEmailId());
    ps.setString(4, customer.getPassword());
    ps.setString(5, customer.getMobileNo());
    ps.setString(6, customer.getAddress());
    return(ps.executeUpdate()== 1); 
    
    }
    
    public static CustomerPojo  validate(String emailId, String password) throws SQLException{
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("Select * from customers where email_id=? and password=? ");
    
    
    ps.setString(1, emailId);
    ps.setString(2, password);
    ResultSet rs=ps.executeQuery();
    CustomerPojo cust=null;
    
     if(rs.next()){
      cust=new CustomerPojo();                    //this could be improved in more better way by returnig a value object instead of whole CompanyPojo.
      cust.setCustomerId(rs.getString(1));        //Value oject: it contains only those data whuch is required for that particular operation. 
      cust.setCustomerName(rs.getString(2));
      cust.setEmailId(emailId);                   //for ex: here company pojo contains 7 fields like company namee, id, password, email, security key, status, etc. But we only require three of them i.e.company name, id and owner name.
                                                  //probelm here: all the other fields except the 3 used here have default value set as null which will later throw exception in case some developer use it for the email or other field unknownigly.
    }
     
    return cust;
   }
    
    public static CustomerPojo getCustomerDetailsById(String custId)throws SQLException{
     
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("Select * from customers where customer_id=?");
    
    ps.setString(1, custId);
    ResultSet rs=ps.executeQuery();
    CustomerPojo cust=null;
    
    
     if(rs.next()){
      
      cust=new CustomerPojo(); 
      cust.setCustomerId(rs.getString(1));  
      cust.setCustomerName(rs.getString(2));
      cust.setEmailId(rs.getString("email_Id"));
      cust.setMobileNo(rs.getString("mobile_no"));
      cust.setAddress(rs.getString("address")); 
      cust.setPassword(rs.getString("password"));
      
    }
     
     return cust;
   }
    
    public static boolean updateCustomer(CustomerPojo customer)throws SQLException{
     
    Connection conn=DBConnection.getConnection();
    PreparedStatement ps=conn.prepareStatement("update customers set customer_name=?, password=?, mobile_no=?, address=? where customer_id=?");    
    ps.setString(5, customer.getCustomerId());
    ps.setString(1, customer.getCustomerName());
    ps.setString(2, customer.getPassword());
    ps.setString(3, customer.getMobileNo());
    ps.setString(4, customer.getAddress());
    return ps.executeUpdate()==1;
    }
}
