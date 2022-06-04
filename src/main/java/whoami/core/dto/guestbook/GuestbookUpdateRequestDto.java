package whoami.core.dto.guestbook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestbookUpdateRequestDto {
    private Long guestbookId;
    private String contents;

    public void printAll() {
        System.out.println(this.guestbookId);
        System.out.println(this.contents);
    }
}
