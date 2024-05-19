CREATE TABLE check_ins (
  attendee_id SERIAL NOT NULL,
  event_id SERIAL NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (attendee_id, event_id),
  CONSTRAINT check_ins_attendee_id_fk FOREIGN KEY (attendee_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT check_ins_event_id_fk FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
)