package cooptool.business.facades;

import cooptool.exceptions.MailNotFound;
import cooptool.exceptions.UnmatchedPassword;
import cooptool.models.daos.persistent.UserDAO;
import cooptool.models.objects.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserFacadeTest {

    @Mock
    UserDAO mockUserDAO;

    @InjectMocks
    UserFacade userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void login_goodCredentials_returnUser() throws MailNotFound, UnmatchedPassword {

        try (
                MockedStatic<UserDAO> userDAOMockedStatic = Mockito.mockStatic(UserDAO.class)
        ) {
            User user = new User(27, "mathilde.tribot@etu.umontpellier.fr", "$2a$10$c.N1O5bApJ.b9w5hko.c4uF2i53LCPCMJswgHdFkzns3mm43DGUIW", null, 1);

            when(mockUserDAO.findUserByMail("mathilde.tribot@etu.umontpellier.fr")).thenReturn(user);
            userDAOMockedStatic.when(UserDAO::getInstance).thenReturn(mockUserDAO);

            userFacade.login("mathilde.tribot@etu.umontpellier.fr", "guillaume");

            assertEquals(user, userFacade.getCurrentUser());
        }
    }

    @Test
    public void login_badPassword_throwUnmatchedPassword() {

        try (
                MockedStatic<UserDAO> userDAOMockedStatic = Mockito.mockStatic(UserDAO.class)
        ) {
            User user = new User(27, "mathilde.tribot@etu.umontpellier.fr", "$2a$10$c.N1O5bApJ.b9w5hko.c4uF2i53LCPCMJswgHdFkzns3mm43DGUIW", null, 1);

            when(mockUserDAO.findUserByMail("mathilde.tribot@etu.umontpellier.fr")).thenReturn(user);
            userDAOMockedStatic.when(UserDAO::getInstance).thenReturn(mockUserDAO);

            assertThrows(UnmatchedPassword.class, () -> {
                UserFacade.getInstance().login("mathilde.tribot@etu.umontpellier.fr", "mathilde");
            });
        }
    }

    @Test
    public void login_mailNotExists_throwMailNotFound() {

        try (
                MockedStatic<UserDAO> userDAOMockedStatic = Mockito.mockStatic(UserDAO.class)
        ) {

            when(mockUserDAO.findUserByMail("test.test@etu.umontpellier.fr")).thenReturn(null);
            userDAOMockedStatic.when(UserDAO::getInstance).thenReturn(mockUserDAO);

            assertThrows(MailNotFound.class, () -> {
                userFacade.login("test.test@etu.umontpellier.fr", "test");
            });
        }
    }
}