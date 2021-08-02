/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Khoa
 */
public class TblBookDAOTest {
    
    public TblBookDAOTest() {
    }

   TblBookDAO dao = new TblBookDAO();

    @Test
    public void testLoginAdminSuccess() throws Exception {
        String rs = dao.Login(1, "a");
        String expected = "y";
        assertEquals(expected, rs);
    }
    
    @Test
    public void testLoginUserSuccess() throws Exception {
        String rs = dao.Login(2, "b");
        String expected = "n";
        assertEquals(expected, rs);
    }
    
     @Test
    public void testLoginFail() throws Exception {
        String rs = dao.Login(3, "a");
        String expected = "error";
        assertEquals(expected, rs);
    }
    
}
