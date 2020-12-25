package cooptool.business.facades;

import cooptool.models.daos.MentoringDemandDAO;
import cooptool.models.objects.AdminRole;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.StudentRole;
import cooptool.models.objects.UserRole;

import java.util.List;

public class MentoringDemandFacade {

    private static final MentoringDemandFacade INSTANCE;
    private final MentoringDemandDAO mentoringDemandDAO = MentoringDemandDAO.getInstance();

    static{
        INSTANCE = new MentoringDemandFacade();
    }

    private MentoringDemandFacade(){}

    public static MentoringDemandFacade getInstance(){
        return INSTANCE;
    }

    public void create(MentoringDemand mentoringDemand){
        MentoringDemandDAO.getInstance().create(mentoringDemand);
    }

    public void delete(MentoringDemand mentoringDemand){

    }

    public void update(MentoringDemand mentoringDemand){

    }

    public MentoringDemand getMentoringDemand(int id){
        return MentoringDemandDAO.getInstance().getMentoringDemand(id);
    }

    public List<MentoringDemand> getMentoringDemands(){
        UserRole userRole = UserFacade.getInstance().getCurrentUser().getRole();
        if(userRole instanceof StudentRole){
            return MentoringDemandDAO.getInstance().getPartialMentoringDemands(((StudentRole) userRole).getDepartment());
        }
        else{
            return MentoringDemandDAO.getInstance().getPartialMentoringDemands();
        }
    }
}
