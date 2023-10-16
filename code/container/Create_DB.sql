CREATE DATABASE pennywise_db;

USE pennywise_db;

CREATE TABLE User (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    budget DECIMAL(10, 2));
CREATE TABLE ExpenseCategory (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE Expense (
    expense_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    category_id INT,
    date DATE NOT NULL,
    user_id INT,
    FOREIGN KEY (category_id) REFERENCES ExpenseCategory(category_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
CREATE TABLE Income (
    income_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    date DATE NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
CREATE TABLE UserRole (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE TABLE User_UserRole (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id),
    FOREIGN KEY (role_id) REFERENCES UserRole(role_id)
);
CREATE TABLE UserSettings (
    settings_id INT AUTO_INCREMENT PRIMARY KEY,
    preferred_currency VARCHAR(3),
    notification_preferences TEXT,
    user_id INT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
CREATE TABLE ExpenseAlert (
    alert_id INT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    is_read BOOLEAN NOT NULL,
    date DATETIME NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES User(user_id)
);
CREATE TABLE UserRegistrationToken (
    token_id INT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    expiry_date DATETIME NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES User(user_id));
