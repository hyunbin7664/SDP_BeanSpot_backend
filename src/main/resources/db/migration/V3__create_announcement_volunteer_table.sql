CREATE TABLE announcement_volunteer (
    announcement_id BIGINT NOT NULL,
    is_service_hours_verified BOOLEAN,
    fee INT,
    PRIMARY KEY (announcement_id),
    CONSTRAINT fk_volunteer_announcement
    FOREIGN KEY (announcement_id) REFERENCES announcement_common(id)
);