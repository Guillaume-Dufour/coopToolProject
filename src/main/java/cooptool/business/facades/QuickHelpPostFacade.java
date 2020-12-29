package cooptool.business.facades;

import cooptool.models.daos.persistent.QuickHelpPostDAO;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;

import java.time.LocalDateTime;


public class QuickHelpPostFacade {

    private static final QuickHelpPostFacade INSTANCE;
    private QuickHelpPostDAO quickHelpPostDAO = QuickHelpPostDAO.getInstance();

    static{
        INSTANCE = new QuickHelpPostFacade();
    }

    private QuickHelpPostFacade(){}

    public static QuickHelpPostFacade getInstance(){
        return INSTANCE;
    }

    public void create(String description, Subject subject, User user){
        QuickHelpPost quickHelpPost =
                new QuickHelpPost(
                        -1,
                        subject,
                        description,
                        user,
                        LocalDateTime.now()
                );
        quickHelpPostDAO.create(quickHelpPost);
    }

    public void delete(QuickHelpPost quickHelpPost){

    }
}
