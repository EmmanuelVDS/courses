package fr.manu.courses.configurations;

import fr.manu.courses.dao.UserDao;
import fr.manu.courses.models.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import javax.transaction.Transactional;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private UserDao userDao;

    @Secured({"ADMIN"})
    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }
}
