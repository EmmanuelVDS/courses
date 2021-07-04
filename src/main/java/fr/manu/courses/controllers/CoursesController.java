package fr.manu.courses.controllers;

import fr.manu.courses.models.Role;
import fr.manu.courses.models.User;
import fr.manu.courses.services.RoleService;
import fr.manu.courses.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CoursesController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RoleService roleService;
    private final UserService userService;

    public CoursesController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    private void init() {
        if (roleService.getRoleList().isEmpty()) {
            roleService.addRole("ADMIN");
            roleService.addRole("USER");
        }
        if (userService.getUsers().isEmpty()) {
            String password = "administrateur";
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            Set<Role> roles = new HashSet<>();
            Role role = roleService.getRoleByRole("ADMIN");
            roles.add(role);
            System.out.println(roles);
            User user = new User("admin", "admin@gmail.com", hashed);
            user.setRoles(roles);
            user.setActive(true);
            userService.addUser(user);
        }
    }

//      LOGIN MAPPING
    @GetMapping({ "/login" })
    public ModelAndView login() {

        return new ModelAndView("login");
    }

//        APP/HOME MAPPING

    @GetMapping({ "/app/home" })
    public ModelAndView apphomeGet() {
        return new ModelAndView("home");
    }

//          SIGNUP MAPPING

    @GetMapping({ "/signup" })
    public ModelAndView signupGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");
        mav.addObject("user", new User());
        return mav;
    }

    @PostMapping({ "/signup" })
    public ModelAndView signupPost(@Valid @ModelAttribute("user") User user, BindingResult result) {
        logger.info("signup was successfully intercepted!");
        if (result.hasErrors()) {
            ModelAndView mav = signupGet();
            mav.addObject("user", user);
            return mav;
        } else {
            try {
                String password = user.getPassword();
                String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
                user.setPassword(hashed);
                Role role;
                Set<Role> roles = new HashSet<>();
                role = roleService.getRoleByRole("USER");
                roles.add(role);
                user.setRoles(roles);
                user.setActive(true);
                userService.addUser(user);
                System.out.println("BONJOUR");
                return new ModelAndView("redirect:/");
            } catch (Exception exception) {
                System.out.println("coucou");
                ModelAndView mav = signupGet();
                mav.addObject("user", user);
                return mav;
            }
        }
    }

    @GetMapping({"/app/profile"})
    public ModelAndView profileGet() {
        return new ModelAndView("profile");
    }
}
