-- Insert sample users
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

-- Insert sample insurance preference
INSERT INTO insurance_preferences (user_email, age, annual_income, has_family_dependents, has_health_issues, has_vehicle, vehicle_type, has_property, property_type, interested_in_life_insurance, interested_in_health_insurance, interested_in_vehicle_insurance, interested_in_property_insurance) VALUES
('test@example.com', 35, 75000, true, false, true, 'Car', true, 'House', true, true, true, true); 