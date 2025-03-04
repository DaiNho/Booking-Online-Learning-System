/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.manager;

import dal.WalletDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Account;
import models.Hold;
import models.Transaction;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PagingHomePageServlet", urlPatterns = {"/transaction"})
public class PagingHomePageServlet extends HttpServlet {

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
            out.println("<title>Servlet PagingHomePageServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PagingHomePageServlet at " + request.getContextPath() + "</h1>");
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
        try {
            String action = request.getParameter("action");
            Account user = (Account) request.getSession().getAttribute("user");
            String indexs = request.getParameter("index");
            int index;
            if (indexs != null) {
                index = Integer.parseInt(indexs);
            } else {
                index = 1;
            }
            WalletDAO dao = new WalletDAO();
            if (action.equals("manager")) {
                if (user == null) {
                    response.sendRedirect("LoginManager");
                    return;
                }
                List<Transaction> list = dao.getTransactionByPaging(user.getUserName(), index);
                int numPage = dao.getNumberPageByUserNameTransaction(user.getUserName());
                request.setAttribute("listTran", list);
                request.setAttribute("numPage", numPage);
                request.getRequestDispatcher("Homes_manager.jsp").forward(request, response);
                //paging for manager

            } else if (action.equals("mentee")) {
                if (user == null) {
                    response.sendRedirect("login");
                    return;
                }
                List<Transaction> list = dao.getTransactionByPaging(user.getUserName(), index);
                int numPage = dao.getNumberPageByUserNameTransaction(user.getUserName());
                request.setAttribute("listTran", list);
                request.setAttribute("numPage", numPage);
                
                //listHold
                List<Hold> listHold = dao.getHoldByPaging(user.getUserName(), 1);
                int numPageHold = dao.getNumberPageByUserNameHold(user.getUserName());
                request.setAttribute("listHold", listHold);
                request.setAttribute("numPageHold", numPageHold);
                
                request.getRequestDispatcher("Wallet.jsp").forward(request, response);
            }
        } catch (IOException | NumberFormatException e) {
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
