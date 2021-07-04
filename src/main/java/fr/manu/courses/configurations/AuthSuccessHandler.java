package fr.manu.courses.configurations;

import fr.manu.courses.dao.UserDao;
import fr.manu.courses.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("Security login was successfully intercepted!");

        HttpSession session = request.getSession();

        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(authUser);

        session.setAttribute("user", user);

//        Permet de récupérer les rôles
//        session.setAttribute("authorities", authentication.getAuthorities());

        response.sendRedirect("/app/home"); //requestUrl!=null?requestUrl:
    }
}