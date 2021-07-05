package fr.manu.courses.controllers;

import fr.manu.courses.models.Category;
import fr.manu.courses.models.Role;
import fr.manu.courses.models.User;
import fr.manu.courses.services.CategoryService;
import fr.manu.courses.services.RoleService;
import fr.manu.courses.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final CategoryService categoryService;

    public CoursesController(RoleService roleService, UserService userService, CategoryService categoryService) {
        this.roleService = roleService;
        this.userService = userService;
        this.categoryService = categoryService;
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
            Role role1 = roleService.getRoleByRole("USER");
            roles.add(role);
            roles.add(role1);
            System.out.println(roles);
            User user = new User("admin", "admin@gmail.com", hashed);
            user.setRoles(roles);
            user.setActive(true);
            userService.addUser(user);
        }
    }

//          LOGIN MAPPING
    @GetMapping({ "/login" })
    public ModelAndView login() {

        return new ModelAndView("login");
    }

//          APP/HOME MAPPING

    @GetMapping({ "/app/home" })
    public ModelAndView apphomeGet() {
        return new ModelAndView("home");
    }

//          SIGNUP MAPPING

    @GetMapping({ "/signup" })
    public ModelAndView signupGet() {
        ModelAndView mav = new ModelAndView("signup");
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
                return new ModelAndView("redirect:/");
            } catch (DataIntegrityViolationException ex) {
                result.addError(new FieldError("user", "email", "Email déjà utilisé"));
                ModelAndView mav = signupGet();
                mav.addAllObjects(result.getModel());
                mav.addObject("user", user);
                return mav;
            }
        }
    }

//          PROFILE MAPPING

    @GetMapping({"/app/profile"})
    public ModelAndView profileGet() {
        return new ModelAndView("profile");
    }

//          ADMIN/CATEGORIES MAPPING

    @GetMapping({"/admin/categories"})
    public ModelAndView categoriesGet(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        ModelAndView mav = new ModelAndView("categories");
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoriesPageable = categoryService.getCategoriesPageable(pageable);
        mav.addObject("categoriesPages", categoriesPageable);
        mav.addObject("category", new Category());
        return mav;
    }

    @PostMapping({"/admin/categories"})
    public ModelAndView categoriesPost(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = categoriesGet(0, 5);
            mav.addObject("category", category);
            return mav;
        } else {
            try {
                categoryService.addCategory(category);
                return new ModelAndView("redirect:categories");
            } catch (Exception exception) {
                ModelAndView mav = categoriesGet(0, 5);
                mav.addObject("category", category);
                return mav;
            }
        }
    }

    //          APP/SHOPPING-LIST MAPPING

    @GetMapping({"/app/shopping-list"})
    public ModelAndView shoppingListGet() {
        return new ModelAndView("shoppinglist");
    }
}
