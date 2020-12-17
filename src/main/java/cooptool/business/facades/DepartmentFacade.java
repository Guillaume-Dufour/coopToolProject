package cooptool.business.facades;

import cooptool.models.objects.Department;

public class DepartmentFacade {

    private static DepartmentFacade departmentFacade = null;

    public static DepartmentFacade getInstance(){
        if(departmentFacade == null){
            departmentFacade = new DepartmentFacade();
        }
        return departmentFacade;
    }

    public Department getDepartment(){
        return null;
    }
}
