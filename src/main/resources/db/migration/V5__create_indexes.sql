CREATE UNIQUE INDEX events_slug_key ON events(slug);
CREATE UNIQUE INDEX attendees_event_email_key ON attendees(email);
CREATE UNIQUE INDEX attendees_cpf_key ON attendees(cpf);
