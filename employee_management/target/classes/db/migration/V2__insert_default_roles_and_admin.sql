-- V2__insert_default_roles_and_admin.sql

-- Insert default roles
INSERT INTO roles (name) VALUES ('ADMIN'), ('USER');

-- Insert default admin (password: admin123)
INSERT INTO users (username, password, role_id)
VALUES (
    'admin',
    '$2a$10$irNLsHGaF79DgsjQLDKOneX62z5144PDTak/V9/MEIJeqAhvzvUkO',
    (SELECT id FROM roles WHERE name = 'ADMIN')
);

-- Insert default user (password: user123)
INSERT INTO users (username, password, role_id)
VALUES (
    'user',
    '$2a$10$lo76PrjzGHes7tSbu/9yMOMoLQMo2ga4tpMOxgMzKQw2lEPHb8LzK',
    (SELECT id FROM roles WHERE name = 'USER')
);