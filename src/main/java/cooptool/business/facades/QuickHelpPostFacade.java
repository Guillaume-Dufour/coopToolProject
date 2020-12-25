package cooptool.business.facades;

import cooptool.models.daos.MentoringDemandDAO;
import cooptool.models.objects.MentoringDemand;
import cooptool.models.objects.QuickHelpPost;

public class QuickHelpPostFacade {
    private static final QuickHelpPostFacade INSTANCE;
    private final QuickHelpPostDAO quickHelpPostDAO = QuickHelpPostDAO.getInstance();

    static{
        INSTANCE = new QuickHelpPostFacade();
    }

    private QuickHelpPostFacade(){}

    public static QuickHelpPostFacade getInstance(){
        return INSTANCE;
    }

    public void create(QuickHelpPost quickHelpPost){
        QuickHelpPostDAO.getInstance().create(quickHelpPost);
    }

    public void delete(QuickHelpPost quickHelpPost){

    }
}
