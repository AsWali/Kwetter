package service.impl;

import dao.inter.UserDAO;
import utils.PermissionsEnum;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by Asror on 3/21/2017.
 */
@Stateless
public class AuthorizationServiceImpl {

    @Inject
    UserDAO userDAO;

    public PermissionsEnum getUserPermission(String username) {
        return userDAO.getUserPermission(username);
    }
}
