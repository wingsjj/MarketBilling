package ctrl.billing;

import dao.BillDao;
import dao.impl.BillDaoImpl;
import entity.Bill;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=utf-8");

        HttpSession session = req.getSession();
        Object o = session.getAttribute("user");
        if (o == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        BillDao billDao = new BillDaoImpl();
        try {
            List<Bill> bills = billDao.getBillsByNameAndPayment(
                    req.getParameter("name"),
                    Short.parseShort(req.getParameter("isPay"))
            );

            req.setAttribute("bills", bills);
            req.getRequestDispatcher("bill_result.jsp").forward(req, resp);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
