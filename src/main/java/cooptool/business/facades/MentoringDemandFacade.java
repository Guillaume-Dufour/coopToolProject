package cooptool.business.facades;

import cooptool.models.daos.MentoringDemandDAO;
import cooptool.models.objects.MentoringDemand;

import java.util.ArrayList;

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

    public ArrayList<MentoringDemand> getMentoringDemands(){
        return null;
    }
}
