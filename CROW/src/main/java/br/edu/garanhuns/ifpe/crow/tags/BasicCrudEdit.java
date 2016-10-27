package br.edu.garanhuns.ifpe.crow.tags;


import br.edu.garanhuns.ifpe.crow.classes.StringUtil;
import br.edu.garanhuns.ifpe.crow.interfaces.CrowActionController;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author 1860915 and Adriano
 */
public class BasicCrudEdit extends SimpleTagSupport{
    
    private String usedBean;
    private CrowActionController usedActionController;
    private Object beanInstance;
    
    public void setUsedBean(String usedBean) {
        this.usedBean = usedBean;
    }
    
    public void setBeanInstance(Object beanInstance) {
        this.beanInstance = beanInstance;
    }

    public void setUsedController(CrowActionController usedActionController) {
        this.usedActionController = usedActionController;
    }
    
    
    @Override
    public void doTag() throws IOException{
        Class classBean = null;
        Class classController = null;
        try {
            classBean = Class.forName(usedBean);
            classController = usedActionController.getClass();
            
            
        } catch (ClassNotFoundException ex) {
            getJspContext().getOut().write("<h1>Erro the class "+ex.getMessage()+" was not found</h1>");
        }
        
        getJspContext().setAttribute("usedBean", classBean, PageContext.SESSION_SCOPE);
        getJspContext().setAttribute("usedController", classController, PageContext.SESSION_SCOPE);
        
        JspWriter out = getJspContext().getOut();

        out.println("<script src='http://code.jquery.com/jquery-3.1.1.js'></script>"
                + "<script>$(function(){\n"
                + "    \n"
                + "    $(\"[value='Cadastrar']\").click(function(){\n"
                + "       var fields = $(\"[name]\");\n"
                + "    \n"
                + "        var parametros = \"\";\n"
                + "    \n"
                + "        for(var i = 0;i<fields.length;i++){\n"
                + "            parametros += $(fields[i]).attr(\"name\")+\":\"+$(fields[i]).val()+\";\"; \n"
                + "        }\n"
                + "         console.log(parametros)"
                + "    \n"
                + "        $.post(\"GenericEditServlet\",{param:parametros},\n"
                + "        function(data){\n"
                + "            $(\"#mensagem\").html(data);\n"
                + "        }); \n"
                + "        });\n"
                + "    \n"
                + "    \n"
                + "    \n"
                + "    $(\"[value='Remover']\").click(function(){\n"
                + "       var field = $(\"[name='id']\");\n"
                + "    \n"
                + "        var parametros = \"\";\n"
                + "    \n"
                + "        parametros += $(field[0]).attr(\"name\")+\":\"+$(field[0]).val()+\";\"; \n"
                + "        console.log(parametros)"
                + "    \n"
                + "        $.post(\"GenericRemoveServlet\",{param:parametros},\n"
                + "        function(data){\n"
                + "            $(\"#mensagem\").html(data);\n"
                + "        }); \n"
                + "        });\n"
                + "});</script>\n\n");
        

        out.println("<p id='mensagem'></p>");
        out.println("<form>");
        Field[] fields = classBean.getDeclaredFields();
        Class[] paramsClassesEmpty = new Class[0];
        Object[] paramsNull = new Object[0];
        
        
        for (Field f : fields) {
            try {
                out.print(f.getName()
                        + ": <input type='text' name='"
                        + f.getName()
                        + "' value='"
                        + beanInstance.getClass().getMethod("get"+StringUtil.upperCaseFirst(f.getName()), paramsClassesEmpty)
                                .invoke(beanInstance, paramsNull)
                        +"'/><br/>");
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(BasicCrudEdit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(BasicCrudEdit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(BasicCrudEdit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(BasicCrudEdit.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(BasicCrudEdit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        out.print("<input type='button' value='Cadastrar' />");
        out.print("<input type='button' value='Remover' />");
        out.println("</form>");
    }
    
    
}
