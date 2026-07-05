

CREATE DATABASE IF NOT EXISTS saucedemo_db;
USE saucedemo_db;

-- Drop tables if they exist to allow a clean re-run
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

-- Users Table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL, -- Stored as plain text to match sauce_demo
    user_status VARCHAR(20) DEFAULT 'active'
);

-- Products Table
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL
);

-- Orders Table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Order Items Table (Many-to-Many Relationship)
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT NOT NULL,
    price_at_purchase DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Insert Users
INSERT INTO users (username, password, user_status) VALUES 
('standard_user', 'secret_sauce', 'active'),
('locked_out_user', 'secret_sauce', 'locked'),
('problem_user', 'secret_sauce', 'active'),
('performance_glitch_user', 'secret_sauce', 'active'),
('error_user', 'secret_sauce', 'active'),
('visual_user', 'secret_sauce', 'active');

-- Insert Products (With official names and prices)
INSERT INTO products (product_name, description, price, stock_quantity) VALUES 
('Sauce Labs Backpack', 'Streamlined stylish backpack with padded laptop sleeve.', 29.99, 50),
('Sauce Labs Bike Light', 'Water-resistant, directional light with 3 modes.', 9.99, 30),
('Sauce Labs Bolt T-Shirt', '100% ringspun cotton t-shirt with classic bolt graphic.', 15.99, 40),
('Sauce Labs Fleece Jacket', 'Midweight fleece jacket with full-zip front and zippered pockets.', 49.99, 15),
('Sauce Labs Onesie', 'Infant short sleeve bodysuit with easy three-snap closure.', 7.99, 25),
('Test.allTheThings() T-Shirt (Red)', 'Super-soft ringspun cotton t-shirt in classic red.', 15.99, 20);

-- Insert Sample Orders (For Testing Queries)
-- Order 1: standard_user buys a Backpack and a Bike Light
INSERT INTO orders (user_id, total_amount) VALUES (1, 39.98);
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES 
(1, 1, 1, 29.99),
(1, 2, 1, 9.99);

-- Order 2: problem_user buys a Fleece Jacket
INSERT INTO orders (user_id, total_amount) VALUES (3, 49.99);
INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES 
(2, 4, 1, 49.99);

-- 4. VERIFICATION QUERIES (Smoke Test)

SELECT 'Users Count' AS Table_Name, COUNT(*) FROM users
UNION ALL
SELECT 'Products Count', COUNT(*) FROM products
UNION ALL
SELECT 'Orders Count', COUNT(*) FROM orders;