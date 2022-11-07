package solo_project.solo_project.domain.user.value;

public enum AuthorityLevel {

  ADMIN("관리자", "ROLE_AMDIN"),
  USER("회원", "ROLE_USER"),
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
}
