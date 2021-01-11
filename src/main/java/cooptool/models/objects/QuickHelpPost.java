package cooptool.models.objects;

import java.time.LocalDateTime;

/**
 * QuickHelpPost class
 */
public class QuickHelpPost extends Post {

    /**
     * Constructor
     * @param id QuickHelpPost ID
     * @param subject QuickHelpPost subject
     * @param description QuickHelpPost description
     * @param creator QuickHelpPost creator
     * @param creationDate QuickHelpPost creation date
     */
    public QuickHelpPost(int id, Subject subject, String description, User creator, LocalDateTime creationDate) {
        super(id, subject, description, creator, creationDate);
    }

    /**
     * Constructor
     * @param subject QuickHelpPost subject
     * @param description QuickHelpPost description
     * @param creator QuickHelpPost creator
     */
    public QuickHelpPost(Subject subject, String description, User creator) {
        this(1, subject, description, creator, LocalDateTime.now());
    }

    /**
     * Constructor
     * @param id QuickHelpPost ID
     * @param subject QuickHelpPost subject
     * @param description QuickHelpPost description
     * @param creationDate QuickHelpPost creation date
     */
    public QuickHelpPost(int id, Subject subject, String description, LocalDateTime creationDate) {
        super(id, subject, description, null, creationDate);
    }
}
