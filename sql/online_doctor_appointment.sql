create database online_doctor_appointment;
use online_doctor_appointment;

CREATE TABLE Users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email TEXT,
    phone_number TEXT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('PATIENT', 'ADMIN','DOCTOR') NOT NULL
);

CREATE TABLE Doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    department_id INT,
    FOREIGN KEY (department_id) REFERENCES Departments(id)
);

CREATE TABLE Appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    doctor_id INT,
    appointment_date DATETIME,
    status ENUM('SCHEDULED', 'CANCELLED', 'COMPLETED'),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(id)
);

CREATE TABLE Departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Symptom(
	id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE Symtom_department(
	id INT AUTO_INCREMENT PRIMARY KEY,
    sympton_id INT,
    depertment_id INT,
    FOREIGN KEY (sympton_id) REFERENCES Sympton(id),
    FOREIGN KEY (department_id) REFERENCES Departments(id)
);

CREATE TABLE Schedule(
	id INT AUTO_INCREMENT PRIMARY KEY,
    time TEXT,
	remaining_seats int,
    num_of_seats int
);