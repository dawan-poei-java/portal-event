INSERT INTO user (id, first_name, last_name, email, password, created_at, phone_number, address, address_complement, city_id, zip_code, user_role) VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password123', NOW(), '0123456789', '123 Rue Exemple', 'Appartement 4B', NULL, '75001', 'ORGANIZER');
INSERT INTO users (id, first_name, last_name, email, password, created_at, phone_number, address, address_complement, city_id, zip_code, user_role) VALUES
                                                                                                                                                        (2, 'Alice', 'Smith', 'alice.smith@example.com', 'password456', NOW(), '0123456788', '456 Avenue Exemple', 'Appartement 2A', NULL, '75002', 'USER'),
                                                                                                                                                        (3, 'Bob', 'Johnson', 'bob.johnson@example.com', 'password789', NOW(), '0123456787', '789 Boulevard Exemple', NULL, NULL, '75003', 'ADMIN');
