package rocketseat.com.passin.domain.event;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rocketseat.com.passin.domain.address.Address;
import rocketseat.com.passin.domain.user.User;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String details;

  @Column(nullable = false, name = "maximum_attendees")
  private Integer maximumAttendees;

  @ManyToOne
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @Column(name = "start_date")
  private LocalDateTime startDate;

  @Column(name = "end_date")
  private LocalDateTime endDate;

  @Column(name = "number_of_attendees", nullable = false)
  private Integer numberOfAttendees;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @ManyToMany
  @JoinTable(
    name = "check_ins",
    joinColumns = @JoinColumn(name = "event_id"),
    inverseJoinColumns = @JoinColumn(name = "attendee_id")
  )
  private Set<User> attendees;
  
  @OneToOne
  @JoinColumn(name = "address_id", nullable = true)
  private Address address;
}
