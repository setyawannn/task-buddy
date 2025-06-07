CREATE DATABASE IF NOT EXISTS taskbuddy_db;
USE taskbuddy_db;

CREATE TABLE users (
   id INT PRIMARY KEY AUTO_INCREMENT,
   username VARCHAR(50) UNIQUE NOT NULL,
   email VARCHAR(100) UNIQUE NOT NULL,
   password VARCHAR(255) NOT NULL,
   role ENUM('ADMIN', 'USER') DEFAULT 'USER',
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    parent_id INT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabel Tasks
CREATE TABLE tasks (
   id INT PRIMARY KEY AUTO_INCREMENT,
   title VARCHAR(200) NOT NULL,
   description TEXT,
   priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
   status ENUM('PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
   deadline DATE,
   category_id INT,
   user_id INT NOT NULL,
   parent_task_id INT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (parent_task_id) REFERENCES tasks(id) ON DELETE CASCADE
);

-- Tabel Activity Logs (untuk Double Linked List)
CREATE TABLE activity_logs (
   id INT PRIMARY KEY AUTO_INCREMENT,
   user_id INT NOT NULL,
   action VARCHAR(100) NOT NULL,
   description TEXT,
   task_id INT NULL,
   previous_log_id INT NULL,
   next_log_id INT NULL,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE SET NULL,
   FOREIGN KEY (previous_log_id) REFERENCES activity_logs(id) ON DELETE SET NULL,
   FOREIGN KEY (next_log_id) REFERENCES activity_logs(id) ON DELETE SET NULL
);

-- Tabel User Queue (untuk simulasi antrian)
CREATE TABLE user_queue (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    queue_position INT NOT NULL,
    status ENUM('WAITING', 'ACTIVE', 'COMPLETED') DEFAULT 'WAITING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample data
INSERT INTO users (username, email, password, role) VALUES
    ('admin', 'admin@taskbuddy.com', 'admin123', 'ADMIN'),
    ('john_doe', 'john@example.com', 'password123', 'USER'),
    ('jane_smith', 'jane@example.com', 'password123', 'USER');

INSERT INTO categories (name, parent_id, user_id) VALUES
      ('Work', NULL, 2),
      ('Personal', NULL, 2),
      ('Development', 1, 2),
      ('Meetings', 1, 2),
      ('Health', 2, 2);