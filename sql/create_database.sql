CREATE DATABASE exec_task_db;
USE exec_task_db;

CREATE TABLE executors (
name VARCHAR(255) NOT NULL PRIMARY KEY,
type VARCHAR(6) NOT NULL,
status VARCHAR(10) NOT NULL DEFAULT 'active'
);

create table tasks (
id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(255) NOT NULL,
executor_id VARCHAR(255),
type VARCHAR(5) NOT NULL,
priority INTEGER(2) DEFAULT 0,
status VARCHAR(11) NOT NULL DEFAULT 'not start',
create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (executor_id) REFERENCES executors(name)
);