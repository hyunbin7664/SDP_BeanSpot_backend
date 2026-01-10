CREATE TABLE announcement_supporter (
    announcement_id BIGINT NOT NULL,
    benefits TEXT,
    selection_process TEXT,
    PRIMARY KEY (announcement_id),
    CONSTRAINT fk_supporter_announcement
    FOREIGN KEY (announcement_id) REFERENCES announcement_common(id)
);