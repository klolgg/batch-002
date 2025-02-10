package site.klol.batch002.riot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountDTO {
    private String puuid;
    private String gameName;
    private String tagLine;
}
