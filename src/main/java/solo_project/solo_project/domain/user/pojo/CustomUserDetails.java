package solo_project.solo_project.domain.user.pojo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import solo_project.solo_project.domain.user.entity.User;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomUserDetails implements UserDetails {

  private Long id;

  private String email;

  private String password;

  private String nickname;

  private Boolean isNonLocked;

  @Builder.Default
  private List<String> authorities = new ArrayList<>();

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public static CustomUserDetails of(User user) {

    return CustomUserDetails.builder()
        .id(user.getId())
        .email(user.getEmail().getEmailAddress())
        .nickname(user.getNickname())
        .isNonLocked(user.getIsNonLocked())
        .password(user.getPassword())
        .authorities(user.getAuthorities())
        .build();
  }

}
