/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.BookDTO;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DELLA
 */
public class CustomerTableModelBook extends AbstractTableModel
{

    private String[] headeres;
    private int[] indexes;
    private Vector<BookDTO> data;
    
    public Vector<BookDTO> getList()
    {
        return data;
    }
    
    public CustomerTableModelBook(String[] headeres, int[] indexes)
    {
        this.headeres=new String[headeres.length];
        for(int i=0;i<headeres.length;i++)
        {
            this.headeres[i] = headeres[i];
        }
        this.indexes=new int[indexes.length];
        for(int i=0;i<indexes.length;i++)
        {
            this.indexes[i]=indexes[i];
        }
        data = new Vector<>();
    }
    
    public String getColumnName(int column)
    {
        if(column>=0||column<headeres.length)
        {
            return headeres[column];
        }
        else
        return"";
    }
    
    @Override
    public int getRowCount() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return data.size();
    }

    @Override
    public int getColumnCount() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        return headeres.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(rowIndex<0||rowIndex>=data.size()||columnIndex<0||columnIndex>=headeres.length)
        {
            return null;
        }
        BookDTO book =data.get(rowIndex);
        switch(indexes[columnIndex])
        {
            case 0: return book.getBookID();
            case 1: return book.getBookName();
            case 2: return book.getAuthor();
            case 3: return book.getPublisher();
            case 4: return book.getPublishedYear();
            case 5: return book.isForRent();
        }
        return null;
    }
    
    
   
}
