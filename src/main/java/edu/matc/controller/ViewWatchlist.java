package edu.matc.controller;

import edu.matc.entity.WatchlistEntry;
import edu.matc.persistence.GenericDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** gets the watchlist entries out of the database and sends them to the watchlist page */
@WebServlet(
        urlPatterns = {"/watchlist"}
)

public class ViewWatchlist extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenericDao<WatchlistEntry> dao = new GenericDao<>(WatchlistEntry.class);
        req.setAttribute("entries", dao.getAll()); // everybody's entries for now, just the signed in user's once log in works

        RequestDispatcher dispatcher = req.getRequestDispatcher("/watchlist.jsp");
        dispatcher.forward(req, resp);
    }
}
