package rocketseat.com.passin.domain.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
  @Id
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private String uf;

  @Column(nullable = false)
  private String street;

  @Column()
  private String district;

  @Column()
  private String complement;

  @Column(nullable = false)
  private String zipcode;

  @Column(nullable = false, name = "maximum_attendees")
  private Integer maximumAttendees;
}