package whoami.core.dto.guestbook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestbookSaveRequestDto {
    private String contents;
    private Long ownerId;

    public void printAll() {
        System.out.println(this.contents);
        System.out.println(this.ownerId);
    }
}
