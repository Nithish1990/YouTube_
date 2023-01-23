package application.modal.video;

import application.modal.users.SignedViewer;

public class Comments {
    private final SignedViewer commentedBy;
    private String comment;

    public Comments(SignedViewer commentedBy, String comment) {
        this.commentedBy = commentedBy;
        this.comment = comment;
    }

    public SignedViewer getCommentedBy() {
        return commentedBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {

        // when need to edit the comment
        this.comment = comment;
    }
}
