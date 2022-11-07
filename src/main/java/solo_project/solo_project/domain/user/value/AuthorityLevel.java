package solo_project.solo_project.domain.user.value;

import solo_project.solo_project.domain.user.entity.Authority;
import solo_project.solo_project.domain.user.entity.User;

public enum AuthorityLevel {

  ADMIN("관리자", "ROLE_ADMIN") {

    @Override
    public void giveAuth(User user) {
      Authority authority = Authority.builder()
          .role(this.getRole())
          .user(user)
          .build();
      user.addAuthority(authority);
    }
  },
  USER("회원", "ROLE_USER") {

    @Override
    public void giveAuth(User user) {
      Authority authority = Authority.builder()
          .role(this.getRole())
          .user(user)
          .build();
      user.addAuthority(authority);
    }
  },
  ;

  private String description;

  private String role;

  AuthorityLevel(String description, String role) {
    this.description = description;
    this.role = role;
  }

  public String getRole() {
    return role;
  }

  public abstract void giveAuth(User user);
}
