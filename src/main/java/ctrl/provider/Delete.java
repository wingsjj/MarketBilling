package ctrl.provider;

import dao.ProviderDao;
import dao.impl.ProviderDaoImpl;
import entity.Provider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Delete extends HttpServlet {
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

        int id = Integer.parseInt(req.getParameter("id"));
        ProviderDao providerDao = new ProviderDaoImpl();
        try {
            providerDao.delProvider(id);
            List<Provider> providerList = providerDao.getAllproviders();
            Map<Integer, String> providerMap =
                    providerList.stream().collect(Collectors.toMap(Provider::getId, Provider::getName));

            session.setAttribute("providers", providerList);
            session.setAttribute("providersMap", providerMap);
            resp.sendRedirect("provider_list.jsp");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
