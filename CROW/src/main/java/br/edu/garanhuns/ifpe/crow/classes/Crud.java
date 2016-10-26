/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author casa01
 */
public class Crud<T>{
    
    private static final String TEXT="text";
    private static final String SUBMIT="submit";
    
    public static final String GET="GET";
    public static final String POST="POST";
    
    private String input(String name,String type,String value){
        StringBuilder sb = new StringBuilder();
        sb.append("<input type=\"");
        sb.append(type);
        sb.append("\" name=\"");
        sb.append(name);
        sb.append("\" value=\"");
        sb.append(value);
        sb.append("\">");
        
        return sb.toString();
    }
   
    
    private String input(String name,String type,String value,String label){
        StringBuilder sb = new StringBuilder();
        sb.append(label);
        sb.append(": <input type=\"");
        sb.append(type);
        sb.append("\" name=\"");
        sb.append(name);
        sb.append("\" value=\"");
        sb.append(value);
        sb.append("\">");
        
        return sb.toString();
    }
    
    
    
    public String create(Class classBean,String method,String action){
        String m;
        m = method.isEmpty()? Crud.POST : method;
        
        StringBuilder sb = new StringBuilder();
        sb.append("<form ");
        sb.append(" method=\"");
        sb.append(m);
        sb.append("\" action=\"");
        sb.append(action);
        sb.append("\"");
        sb.append(">");
        
        
        Field[] fields = classBean.getDeclaredFields();
        for(Field f :fields){
            try {
                sb.append(this.input(f.getName(), Crud.TEXT,"", f.getName()));
            } catch (SecurityException | IllegalArgumentException ex) {
                Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            sb.append("<br/>");
        }
        sb.append(this.input("",Crud.SUBMIT,"enviar"));
        sb.append("</form>");
        
        return sb.toString();
    }
    
    
    public String update(T objectBean,String method,String action){
        Class classBean;
        classBean = objectBean.getClass();
        String m;
        m = method.isEmpty()? Crud.POST : method;
        
        StringBuilder sb = new StringBuilder();
        sb.append("<form ");
        sb.append(" method=\"");
        sb.append(m);
        sb.append("\" action=\"");
        sb.append(action);
        sb.append("\"");
        sb.append(">");
        
        
        Field[] fields = classBean.getDeclaredFields();
        for(Field f :fields){
            try {
                Method meth = classBean.getMethod("get"+StringUtil.upperCaseFirst(f.getName()));
                String value = String.valueOf(meth.invoke(objectBean)).equals("null")?"":String.valueOf(meth.invoke(objectBean));
                
                sb.append(this.input(f.getName(), Crud.TEXT,value, f.getName()));
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            sb.append("<br/>");
        }
        sb.append(this.input("",Crud.SUBMIT,"enviar"));
        sb.append("</form>");
        
        return sb.toString();
    }
    
    
    public String list(Class classBean, List<T> list,List<String> atributtes){

        
        StringBuilder sb = new StringBuilder();
        sb.append("<table ");
        sb.append(">");
        
        sb.append("<tr>");
        for (String value : atributtes) {
           sb.append("<th>");
           sb.append(value);
           sb.append("</th>");
        }          
        sb.append("</tr>");
        Field[] fields = classBean.getDeclaredFields();
        for (Object objectBean : list) {
            sb.append("<tr>");
            for(String atributte: atributtes){
                Method meth;
                try {
                    meth = classBean.getMethod("get"+StringUtil.upperCaseFirst(atributte));
                    String value = String.valueOf(meth.invoke(objectBean)).equals("null")?"":String.valueOf(meth.invoke(objectBean));
                    
                    sb.append("<td>");
                    sb.append(value);
                    sb.append("</td>");
                } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
             sb.append("<td>");
                sb.append("Editar");
                sb.append("</td>");
                sb.append("<td>");
                sb.append("X");
                sb.append("</td>");
            sb.append("</tr>");
        }
        
      
      
        sb.append("</table>");
        
        return sb.toString();
    }
    
    
    public String crud(Class classBean,String method, String action,List<T> list,List<String> atributtes){
        return this.create(classBean,method,action)+
               this.list(classBean, list, atributtes);
    }
   
    
    
    
            
            
}
