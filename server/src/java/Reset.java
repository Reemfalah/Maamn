/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mail.MailMail;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.simplejavamail.email.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import static java.nio.charset.StandardCharsets.*;
import java.sql.PreparedStatement;
import java.util.UUID;

/**
 *
 * @author Rawan
 */
@WebServlet(urlPatterns = {"/Reset"})
public class Reset extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Reset</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Reset at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String email = request.getParameter("email");
        Connection con;
        String sql, sql2, sql3, sql4;
        ResultSet resultSet, resultSet3;
        int resultSet2, resultSet4;
        Statement statement;
        PrintWriter pw = response.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            statement = con.createStatement();
            sql = "select * from patient where email='" + email + "'";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                Resource r = new ClassPathResource("com/mail/applicationContext.xml");
                BeanFactory b = new XmlBeanFactory(r);
                MailMail m = (MailMail) b.getBean("mailMail");
           
                String sender = "MaamnApp@gmail.com";
                String receiver = email;
                String title, body, tokenB, token;
                tokenB = UUID.randomUUID().toString().replaceAll("-", "");
                token = tokenB.substring(0, 5);
                title = " إعادة تعيين كلمة المرور تطبيق مأمن";
                body = "مرحبا،\n\nالرمز : " + token + "\n\n\n شكرا، فريق مأمن";
//                String titleS = new String(title.getBytes("UTF-8"), "ISO_8859_1");
//                String bodyS = new String(body.getBytes("UTF-8"), "ISO_8859_1");
                sql2 = "UPDATE patient SET token='" + token + "' WHERE email='" + email + "'";
                resultSet2 = statement.executeUpdate(sql2);
                m.sendMail(sender, receiver, title, body);
                pw.print("successful");
            } else {
                sql3 = "select * from caregiver where email='" + email + "'";
                resultSet3 = statement.executeQuery(sql3);
                if (resultSet3.next()) {
                    Resource r = new ClassPathResource("com/mail/applicationContext.xml");
                    BeanFactory b = new XmlBeanFactory(r);
                    MailMail m = (MailMail) b.getBean("mailMail");
                    String sender = "MaamnApp@gmail.com";
                    String receiver = email;
                    String title, body, tokenB, token;
                    tokenB = UUID.randomUUID().toString().replaceAll("-", "");
                    token = tokenB.substring(0, 5);
                title = " إعادة تعيين كلمة المرور تطبيق مأمن";
                body = "مرحبا،\n\nالرمز : " + token + "\n\n\n شكرا، فريق مأمن";
//                   String titleS = new String(title.getBytes("UTF-8"));
//                   String bodyS = new String(body.getBytes("UTF-8"));
//                   String ti = new String(title.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//                   String bo = new String(body.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                    sql4 = "UPDATE caregiver SET token='" + token + "' WHERE email='" + email + "'";
                    resultSet4 = statement.executeUpdate(sql4);
                    m.sendMail(sender, receiver, title, body);
                    pw.print("successful");
                } else {
                    pw.print("unsuccessful");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
