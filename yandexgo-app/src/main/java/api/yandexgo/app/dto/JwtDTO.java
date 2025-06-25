package api.yandexgo.app.dto;

import api.yandexgo.app.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {

    private String username;
    private Integer id;
    private List<ProfileRole> roleList;
}
