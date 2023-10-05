CREATE DATABASE shop_application;
USE shop_application;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    password VARCHAR(100) NOT NULL DEFAULT '',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    is_active TINYINT DEFAULT 1,
    birth_date DATE,
    facebook_account_id INT DEFAULT 0,
    google_account_id INT DEFAULT 0
);

CREATE TABLE tokens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revoked TINYINT NOT NULL,
    expired TINYINT NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
);

CREATE TABLE socical_accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    provider VARCHAR(50) NOT NULL,
    provider_id INT NOT NULL,
    email VARCHAR(150) NOT NULL,
    name VARCHAR(100) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
);


CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL DEFAULT ''
);


CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(350) NOT NULL,
    price FLOAT NOT NULL CHECK (price >= 0),
    thumbnail VARCHAR(300) DEFAULT '',
    description LONGTEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    category_id INT,
    FOREIGN KEY (category_id)
        REFERENCES categories (id)
);

CREATE TABLE roles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL
);

ALTER TABLE users ADD COLUMN role_id INT;
ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    full_name VARCHAR(100) DEFAULT '',
    email VARCHAR(200) DEFAULT '',
    phone_number VARCHAR(10) NOT NULL,
    address VARCHAR(200) NOT NULL,
    note VARCHAR(100) DEFAULT '',
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status ENUM('pending', 'processing', 'shipped', 'delivered', 'cancelled') NOT NULL,
    total_money FLOAT CHECK (total_money >= 0),
    shipping_method VARCHAR(100) NOT NULL,
    shipping_address VARCHAR(200) NOT NULL,
    shipping_date DATE,
    tracking_number VARCHAR(100) NOT NULL,
    payment_method VARCHAR(100) NOT NULL,
    active TINYINT DEFAULT 1,
    FOREIGN KEY (user_id)
        REFERENCES users (id)
);

create table order_details(
	id int primary key auto_increment,
    order_id int,
    product_id int,
    price float check(price >=0),
    quantity int check(quantity > 0),
    total_money float check (total_money >= 0),
    description varchar(100) default '',
    foreign key (order_id) references orders(id),
    foreign key (product_id) references products(id)
);
