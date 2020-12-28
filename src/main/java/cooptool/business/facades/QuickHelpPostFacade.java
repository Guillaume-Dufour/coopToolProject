package cooptool.business.facades;

import cooptool.models.daos.QuickHelpPostDAO;
import cooptool.models.objects.QuickHelpPost;
import cooptool.models.objects.Subject;
import cooptool.models.objects.User;

import java.time.LocalDate;


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

    public void create(String description, Subject subject, User user){
        QuickHelpPost quickHelpPost =
                new QuickHelpPost(
                        -1,
                        subject.getDepartment(),
                        subject,
                        description,
                        LocalDate.now(),
                        user
                );
        QuickHelpPostDAO.getInstance().create(quickHelpPost);
    }

    public void delete(QuickHelpPost quickHelpPost){

    }
}