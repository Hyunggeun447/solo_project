package solo_project.solo_project.domain.user.security;

public enum JwtHeader {

  GRANT_TYPE("Jwt Header", "Bearer ");

  private String description;
  private String value;

  JwtHeader(String description, String value) {
    this.description = description;
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public String getValue() {
    return value;
  }
}
