package api.yandexgo.app.dto;

import api.yandexgo.app.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

//@Getter
//@Setter
public class ProfileDTO {

    private String name;
    private String username;
    private String phoneNumber;
    private List<ProfileRole> roleList;
    private String jwt;

    public ProfileDTO(String name, String username, String phoneNumber, List<ProfileRole> roleList, String jwt) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.roleList = roleList;
        this.jwt = jwt;
    }

    public ProfileDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ProfileRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ProfileRole> roleList) {
        this.roleList = roleList;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
