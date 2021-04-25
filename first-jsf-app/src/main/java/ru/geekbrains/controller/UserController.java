package ru.geekbrains.controller;


import ru.geekbrains.persist.dto.RoleDto;
import ru.geekbrains.persist.dto.UserDto;
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.UserService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class UserController implements Serializable {

    @EJB
    private UserService userService;

    @EJB
    private RoleService roleService;

    @Inject
    private HttpSession session;

    private UserDto userDto;

    private List<RoleDto> roles;

    private List<UserDto> users;

    public void preLoad() {
        this.roles = roleService.getAllRoles();
        this.users = userService.getAllUsers();
    }

    public UserDto getUser() {
        return userDto;
    }

    public void setUser(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<UserDto> getAllUsers() {
        return users;
    }

    public String editUser(UserDto userDto) {
        this.userDto = userDto;
        return "/admin/user_form.xhtml?faces-redirect=true";
    }

    public void deleteUser(UserDto userDto) {
        userService.delete(userDto.getId());
    }

    public String createUser() {
        this.userDto = new UserDto();
        return "/admin/user_form.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        userService.saveOrUpdate(this.userDto);
        return "/admin/user.xhtml?faces-redirect=true";
    }

    public List<RoleDto> getAllRoles() {
        return roles;
    }

    public String logout() {
        session.invalidate();
        return "/product.xhtml?faces-redirect=true";
    }
}
