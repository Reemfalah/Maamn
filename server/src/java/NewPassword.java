/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
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

/**
 *
 * @author Rawan
 */
@WebServlet(urlPatterns = {"/NewPassword"})
public class NewPassword extends HttpServlet {

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
            out.println("<title>Servlet NewPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewPassword at " + request.getContextPath() + "</h1>");
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
        String Password = request.getParameter("password");
        Password = md5(Password);
        String token = request.getParameter("token");
        Connection con;
        String username="";
        PrintWriter pw = response.getWriter();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(DBConnection.urlstring, DBConnection.username, DBConnection.password);
            
            Statement statement = con.createStatement(); //Statement is used to write queries. Read more about it.
            String sql0="SELECT user_name from patient WHERE token='" + token + "'";
            ResultSet resultSet0 = statement.executeQuery(sql0);
            
            while (resultSet0.next()) // Until next row is present otherwise it return false
            {
                 username=resultSet0.getString("user_name");
            }
            
            
            String sql = "UPDATE patient SET password='" + Password + "'  WHERE token='" + token + "'";
            int resultSet = statement.executeUpdate(sql);
            
            if(!(resultSet==0)){
                String sql2 = "UPDATE patient SET token='NULL' WHERE user_name='" + username + "'";
            int resultSet2 = statement.executeUpdate(sql2);
            pw.print("successful");
            }else if (resultSet == 0) {
                
             sql0="SELECT user_name from caregiver WHERE token='" + token + "'";
             resultSet0 = statement.executeQuery(sql0);
            
            while (resultSet0.next()) // Until next row is present otherwise it return false
            {
                 username=resultSet0.getString("user_name");
            }

             sql = "UPDATE caregiver SET password='" + Password + "'  WHERE token='" + token + "'";
             resultSet = statement.executeUpdate(sql);
            
            if(!(resultSet==0)){
                String sql2 = "UPDATE caregiver SET token='NULL' WHERE user_name='" + username + "'";
            int resultSet2 = statement.executeUpdate(sql2);
            pw.print("successful");
            }else{
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
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

