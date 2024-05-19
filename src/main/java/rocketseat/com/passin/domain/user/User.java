package rocketseat.com.passin.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.event.Event;
import rocketseat.com.passin.domain.role.Role;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String cpf;

  @Column(nullable = false)
  private LocalDate birthdate;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToOne
  @JoinColumn(name = "address_id", nullable = false)
  private Address address;

  @ManyToMany
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles;

  @ManyToMany
  @JoinTable(
    name = "check_ins",
    joinColumns = @JoinColumn(name = "attendee_id"),
    inverseJoinColumns = @JoinColumn(name = "event_id")
  )
  private Set<Event> events;
}
