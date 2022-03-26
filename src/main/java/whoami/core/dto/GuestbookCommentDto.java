package whoami.core.dto;

import java.time.LocalDateTime;

public class GuestbookCommentDto {
    private Long guestbookCommentId;
    private Long guestbookId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;

    public Long getGuestbookCommentId() {
        return guestbookCommentId;
    }

    public void setGuestbookCommentId(Long guestbookCommentId) {
        this.guestbookCommentId = guestbookCommentId;
    }

    public Long getGuestbookId() {
        return guestbookId;
    }

    public void setGuestbookId(Long guestbookId) {
        this.guestbookId = guestbookId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
