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

    public JwtDTO(String username, Integer id, List<ProfileRole> roleList) {
        this.username = username;
        this.id = id;
        this.roleList = roleList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ProfileRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ProfileRole> roleList) {
        this.roleList = roleList;
    }
}
