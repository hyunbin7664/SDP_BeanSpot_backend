CREATE TABLE announcement_challenge (
    announcement_id BIGINT NOT NULL,
    prize_scale VARCHAR(255),
    participant_target VARCHAR(255),
    team_size VARCHAR(255),
    PRIMARY KEY (announcement_id),
    CONSTRAINT fk_challenge_announcement
    FOREIGN KEY (announcement_id) REFERENCES announcement_common(id)
);