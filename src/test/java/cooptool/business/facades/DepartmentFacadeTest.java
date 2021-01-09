package cooptool.business.facades;

import cooptool.exceptions.DepartmentNotConformed;
import cooptool.models.daos.persistent.DepartmentDAO;
import cooptool.models.objects.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DepartmentFacadeTest {

    @Mock
    DepartmentDAO mockDepartmentDAO;

    @InjectMocks
    DepartmentFacade departmentFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void create_goodParameters_returnTrue() throws DepartmentNotConformed {

        try (
                MockedStatic<DepartmentDAO> departmentFacadeMockedStatic = Mockito.mockStatic(DepartmentDAO.class)
        ) {

            when(mockDepartmentDAO.create(any())).thenReturn(true);
            departmentFacadeMockedStatic.when(DepartmentDAO::getInstance).thenReturn(mockDepartmentDAO);

            assertTrue(departmentFacade.create("Informatique et Gestion", "IGA", 1));
        }

    }

    @Test
    public void create_badParameters_throwDepartmentNotConformed() {

        try (
                MockedStatic<DepartmentDAO> departmentFacadeMockedStatic = Mockito.mockStatic(DepartmentDAO.class)
        ) {
            departmentFacadeMockedStatic.when(DepartmentDAO::getInstance).thenReturn(mockDepartmentDAO);

            assertThrows(DepartmentNotConformed.class, () -> {
                departmentFacade.create("Informatique et Gestion", "IG", 7);
            });
        }
    }

    @Test
    public void udpate_goodParameters_returnTrue() throws DepartmentNotConformed {

        try (
                MockedStatic<DepartmentDAO> departmentFacadeMockedStatic = Mockito.mockStatic(DepartmentDAO.class)
        ) {

            Department department = new Department("Informatique et Gestion", 4, "IG");

            when(mockDepartmentDAO.update(any())).thenReturn(true);
            departmentFacadeMockedStatic.when(DepartmentDAO::getInstance).thenReturn(mockDepartmentDAO);

            boolean res = departmentFacade.update(department, "Informatique", "INFO", 3);

            assertTrue(res);
            assertEquals(department.getYear(), 3);
            assertEquals(department.getAbbreviation(), "INFO");
        }

    }

    @Test
    public void update_badParameters_throwDepartmentNotConformed() {

        try (
                MockedStatic<DepartmentDAO> departmentDAOMockedStatic = Mockito.mockStatic(DepartmentDAO.class)
        ) {
            Department department = new Department("Informatique et Gestion", 4, "IG");

            when(mockDepartmentDAO.update(any())).thenReturn(true);
            departmentDAOMockedStatic.when(DepartmentDAO::getInstance).thenReturn(mockDepartmentDAO);


            assertThrows(DepartmentNotConformed.class, () -> {
                departmentFacade.update(department, "Informatique et Gestion", "IG", 7);
            });

            assertThrows(DepartmentNotConformed.class, () -> {
                departmentFacade.update(department, "Informatique et Gestion", "IGIGIGIGIGIGIGIGIGIGIGIGIGIG", 3);
            });

            assertEquals(department.getYear(), 4);
            assertEquals(department.getAbbreviation(), "IG");
        }
    }
}