CREATE TABLE announcement_education (
    announcement_id BIGINT NOT NULL,
    fee INT,
    curriculum_url VARCHAR(500),
    PRIMARY KEY (announcement_id),
    CONSTRAINT fk_education_announcement
    FOREIGN KEY (announcement_id) REFERENCES announcement_common(id)
);