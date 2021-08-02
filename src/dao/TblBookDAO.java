/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import javax.swing.JOptionPane;
import utils.DBUltis;

/**
 *
 * @author DELLA
 */
public class TblBookDAO {
    private String[] headeres = {"Book ID","Book name","Author","Publisher","Published year","For rent"};
    private int[] indexes ={0,1,2,3,4,5,};
    private CustomerTableModelBook modelBook = new CustomerTableModelBook(headeres, indexes);
    private CustomerTableModelBook modelFound = new CustomerTableModelBook(headeres, indexes);
    private Vector<String> listYear = new Vector<>();
    private Vector<BookDTO> ListSearchID = new Vector<>();

    public TblBookDAO() {
        try {
                loadData();
                loadYearComboBox();
              
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public CustomerTableModelBook getModelBook()
    {
        return modelBook;
    }
    
    public CustomerTableModelBook getModelBookFound()
    {
        return modelFound;
    }
    
    public Vector<String> getListYear()
    {
        return listYear;
    }
    //==========================================================================
    public void loadData() throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
             con = DBUltis.openConnection();
             if(con!=null)
             {   
                 modelBook.getList().clear();
                 String sql = "Select bookID, bookName, author, publisher, publishedYear, forRent from TblBook";
                 ps=con.prepareStatement(sql);
                 rs=ps.executeQuery();
                 while(rs.next())
                 {
                     BookDTO book = new BookDTO(rs.getString("bookID"),
                                                rs.getString("bookName"), 
                                                rs.getString("author"), 
                                                rs.getString("publisher"), 
                                                rs.getInt("publishedYear"), 
                                                rs.getBoolean("forRent"));
                    modelBook.getList().add(book);
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(ps!=null)
            {
                ps.close();
            }
            if(con!=null)
            {
                con.close();
            }
        }
    }
    
    public int insert(BookDTO book) throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        try {
             con = DBUltis.openConnection();
             if(con!=null)
             {
                 String sql = "insert into TblBook values(?,?,?,?,?,?)";
                 ps=con.prepareStatement(sql);
                 ps.setString(1, book.getBookID());
                 ps.setString(2, book.getBookName());
                 ps.setString(3, book.getAuthor());
                 ps.setString(4, book.getPublisher());
                 ps.setString(5, book.getPublishedYear()+"");
                 ps.setString(6, book.isForRent()+"");
                 return ps.executeUpdate();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(ps!=null)
            {
                ps.close();
            }
            if(con!=null)
            {
                con.close();
            }
        }
        return -1;
    }
    
    public int delete(BookDTO book) throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        try {
             con = DBUltis.openConnection();
             if(con!=null)
             {
                 String sql = "Delete from TblBook where bookID = ?";
                 ps=con.prepareStatement(sql);
                 ps.setString(1, book.getBookID());
                 return ps.executeUpdate();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(ps!=null)
            {
                ps.close();
            }
            if(con!=null)
            {
                con.close();
            }
        }
        return -1;
    }
    
    public int save(BookDTO book) throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBUltis.openConnection();
            if(con!=null)
            {
                String sql ="UPDATE TblBook SET bookName=?,author=?,publisher=?,publishedYear=?,forRent=? WHERE bookID=?";
                ps=con.prepareStatement(sql);
                ps.setString(1, book.getBookName());
                ps.setString(2, book.getAuthor());
                ps.setString(3, book.getPublisher());
                ps.setInt(4, book.getPublishedYear());
                ps.setBoolean(5, book.isForRent());
                ps.setString(6, book.getBookID());
                return ps.executeUpdate();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(ps!=null)
            {
                ps.close();
            }
            if(con!=null)
            {
                con.close();
            }
        }
        return -1;
    }
   
    public Vector<String> loadYearComboBox() throws Exception
    {
        listYear.clear();
        for(int i=1900;i<2022;i++)
        {
            listYear.add(i+"");
        }
        return listYear;
    }
    
    
    public boolean checkDuplicateBookID(String id) throws Exception
    { 
        try {
            Vector<BookDTO> list = new Vector<>();
        list = modelBook.getList();
        
        for(int i = 0;i<list.size();i++)
        {
            if(list.get(i).getBookID().trim().equalsIgnoreCase(id))
            {
                JOptionPane.showMessageDialog(null, "Duplicated Book ID: "+id);
                return false;
            }
        }   
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       return true;
    }
    
    public CustomerTableModelBook searchByID(String bookID) throws Exception
    {
         modelFound.getList().clear();
        for (int i = 0; i < modelBook.getList().size(); i++) {
            if(modelBook.getList().get(i).getBookID().contains(bookID.trim()))
                modelFound.getList().add(modelBook.getList().get(i));
        }
        return modelFound;

    }
  
    public CustomerTableModelBook sortByName(String priority)
    {
        Collections.sort(modelBook.getList());
        
        if(priority.equalsIgnoreCase("A-Z"))
        {    
            return modelBook;
        }
        else if(priority.equals("Z-A"))
        {
            Collections.reverse(modelBook.getList());
            return modelBook;
        }
        return modelBook;
       
    }
    
    public CustomerTableModelBook searchByName(String nameSearch)
    {
        modelFound.getList().clear();
        for (int i = 0; i < modelBook.getList().size(); i++) {
            if(modelBook.getList().get(i).getBookName().contains(nameSearch))
                modelFound.getList().add(modelBook.getList().get(i));
        }
        return modelFound;
    }
    
    public boolean valid(String bookID,String bookName,String author,String publisher,String publishedYear)
    {
        if(bookID.isEmpty()||bookName.isEmpty()||author.isEmpty()||publisher.isEmpty()||publishedYear.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "All fields must be unempty, please check again...");
            return false;
        }
        return true;
    }
    //========================================
    
     public String Login(int id, String pass) throws Exception
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String role = "error";
        try {
             con = DBUltis.openConnection();
             if(con!=null)
             {   
                 
                 String sql = "Select isAdmin from tblUser where id =? and pass=?";
                 ps=con.prepareStatement(sql);
                 ps.setInt(1, id);
                 ps.setString(2,pass);
                 rs=ps.executeQuery();
                 if(rs.next())
                 {
                     role = rs.getString("isAdmin");
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            if(ps!=null)
            {
                ps.close();
            }
            if(con!=null)
            {
                con.close();
            }
        }
        return role;
    }
}
