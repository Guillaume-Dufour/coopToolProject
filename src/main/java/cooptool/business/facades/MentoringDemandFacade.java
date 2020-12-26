package cooptool.business.facades;

import cooptool.models.daos.MentoringDemandDAO;
import cooptool.models.objects.*;

import java.util.ArrayList;
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

    public Participation getCurrentUserParticipation(MentoringDemand demand){
        int idUser = UserFacade.getInstance().getCurrentUser().getId();
        for(Participation participation : demand.getParticipationArray()){
            if(idUser == participation.getParticipant().getId()){
                return participation;
            }
        }
        return null;
    }

    public void suppressCurrentUserParticipation(MentoringDemand demand){
        MentoringDemandDAO.getInstance().suppressParticipation(demand,UserFacade.getInstance().getCurrentUser());
    }

    public void participate(MentoringDemand demand,int participationType,ArrayList<Schedule> schedules){
        MentoringDemandDAO.getInstance().participate(
                demand,
                new Participation(UserFacade.getInstance().getCurrentUser(),participationType,schedules)
        );
    }
}
