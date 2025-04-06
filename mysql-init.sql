-- Create database if not exists
CREATE DATABASE IF NOT EXISTS productdb;
USE productdb;

-- Product Table
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT DEFAULT 0,
    category VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Category Table
CREATE TABLE IF NOT EXISTS category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

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

-- Order Table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'PENDING',
    total_amount DECIMAL(10, 2) NOT NULL,
    shipping_address TEXT,
    payment_method VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Order Item Table
CREATE TABLE IF NOT EXISTS order_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- Weather Cache Table
CREATE TABLE IF NOT EXISTS weather_cache (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    temperature DECIMAL(5, 2),
    weather_description VARCHAR(255),
    humidity INT,
    wind_speed DECIMAL(5, 2),
    fetch_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_time TIMESTAMP,
    UNIQUE KEY city_country_idx (city, country)
);

-- Insert sample data
INSERT INTO category (name, description) VALUES 
('Electronics', 'Electronic devices and accessories'),
('Clothing', 'Apparel and fashion items'),
('Books', 'Books and publications');

INSERT INTO product (name, description, price, quantity, category) VALUES
('Smartphone', 'Latest smartphone with high-end features', 899.99, 50, 'Electronics'),
('Laptop', 'Powerful laptop for professionals', 1299.99, 25, 'Electronics'),
('T-shirt', 'Comfortable cotton t-shirt', 19.99, 100, 'Clothing'),
('Jeans', 'Classic blue jeans', 49.99, 75, 'Clothing'),
('Programming Book', 'Learn microservices architecture', 39.99, 30, 'Books');

INSERT INTO users (username, password, email, full_name, role) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'admin@example.com', 'Admin User', 'ADMIN'),
('user1', '$2a$10$8K1p/a7yx1qoXwVscSJ32ukeeTRM/Lh5XDNVnbnas.ZP4vNGlhU/a', 'user1@example.com', 'Regular User', 'USER');

-- Passwords are bcrypt hashed: 'password' 