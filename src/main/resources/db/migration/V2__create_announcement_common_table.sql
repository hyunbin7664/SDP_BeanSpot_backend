CREATE TABLE announcement_common (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    organizer VARCHAR(255),

    start_date DATE,
    end_date DATE,

    img_url VARCHAR(500),
    link_url VARCHAR(500),

    view_count INT DEFAULT 0,

    location VARCHAR(255),

    closingment VARCHAR(255),

    recruitment_start DATE,
    recruitment_end DATE,

    type VARCHAR(50),

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;