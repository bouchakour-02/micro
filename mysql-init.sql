-- Create database if not exists
CREATE DATABASE IF NOT EXISTS insurancedb;
USE insurancedb;

-- User Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    full_name VARCHAR(100),
    role VARCHAR(20) DEFAULT 'USER',
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL
);

-- Insurance Preference Table
CREATE TABLE IF NOT EXISTS insurance_preferences (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(100) NOT NULL,
    age INT,
    annual_income DECIMAL(12, 2),
    has_family_dependents BOOLEAN,
    has_health_issues BOOLEAN,
    has_vehicle BOOLEAN,
    vehicle_type VARCHAR(50),
    has_property BOOLEAN,
    property_type VARCHAR(50),
    interested_in_life_insurance BOOLEAN,
    interested_in_health_insurance BOOLEAN,
    interested_in_vehicle_insurance BOOLEAN,
    interested_in_property_insurance BOOLEAN,
    risk_tolerance VARCHAR(20),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insurance Recommendation Table
CREATE TABLE IF NOT EXISTS insurance_recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    preference_id BIGINT NOT NULL,
    insurance_type VARCHAR(50) NOT NULL,
    insurance_name VARCHAR(255) NOT NULL,
    description TEXT,
    monthly_cost DECIMAL(10, 2),
    annual_cost DECIMAL(10, 2),
    coverage_details TEXT,
    recommendation_score INT,
    recommendation_reason TEXT,
    is_primary_recommendation BOOLEAN DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (preference_id) REFERENCES insurance_preferences(id)
);

-- Recommendation Rule Table
CREATE TABLE IF NOT EXISTS recommendation_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    insurance_type VARCHAR(50) NOT NULL,
    rule_name VARCHAR(255) NOT NULL,
    rule_description TEXT,
    rule_condition TEXT,
    rule_score_weight INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO users (username, password, email, full_name, role) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'admin@example.com', 'Admin User', 'ADMIN'),
('user1', '$2a$10$8K1p/a7yx1qoXwVscSJ32ukeeTRM/Lh5XDNVnbnas.ZP4vNGlhU/a', 'user1@example.com', 'Regular User', 'USER');

-- Insert sample recommendation rules
INSERT INTO recommendation_rules (insurance_type, rule_name, rule_description, rule_condition, rule_score_weight, is_active) VALUES
('LIFE', 'Age Factor', 'Score based on age range', 'age > 30', 20, TRUE),
('LIFE', 'Family Dependents', 'Higher priority for those with dependents', 'has_family_dependents = true', 40, TRUE),
('HEALTH', 'Health Issues', 'Higher priority for those with health issues', 'has_health_issues = true', 50, TRUE),
('VEHICLE', 'Vehicle Ownership', 'Only recommend if user has a vehicle', 'has_vehicle = true', 30, TRUE),
('PROPERTY', 'Property Ownership', 'Only recommend if user owns property', 'has_property = true', 25, TRUE);

-- Passwords are bcrypt hashed: 'password' 