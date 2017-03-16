package impl;//package service.impl;

import dao.inter.UserDAO;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import service.impl.UserServiceImpl;

import static org.junit.Assert.*;

/**
 * Created by Asror on 12-3-2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserServiceImpl service;
    @Mock
    private UserDAO ud;

    public UserServiceImplTest() {
    }

    @Before
    public void setUp() {
        service = new UserServiceImpl();
        service.setUd(ud);
    }

    @Test
    public void getUser() throws Exception {
        User t = service.getUser(1L);
        Mockito.verify(ud).find(1L);
    }

    @Test
    public void getUser2() throws Exception {
        User t = service.getUser(2L);
        assertEquals(t, null);
    }

    @Test
    public void addUser() throws Exception {
        User u = new User("Asror", "", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        service.addUser(u);
        Mockito.verify(ud).create(u);
    }

    @Test
    public void addUser2() throws Exception {
        User u = new User("Asror", "", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        User u2 = new User("Asror", "", "Asror", "Hallo, ik ben Asror", "Rijen", "walicorp,com");
        service.addUser(u);
        Mockito.verify(ud).create(u2);
    }

}