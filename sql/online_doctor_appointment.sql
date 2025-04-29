-- Bảng Users (Thông tin người dùng)
CREATE TABLE Users (
                       id BIGSERIAL PRIMARY KEY,                 -- ID người dùng (khóa chính)
                       email TEXT,                               -- Email của người dùng
                       phone_number TEXT,                        -- Số điện thoại của người dùng
                       username VARCHAR(50) NOT NULL,            -- Tên đăng nhập của người dùng
                       password VARCHAR(100) NOT NULL,           -- Mật khẩu của người dùng
                       full_name VARCHAR(100),                   -- Họ và tên đầy đủ của người dùng
                       gender TEXT CHECK (gender IN ('MALE', 'FEMALE', 'OTHER')),  -- Giới tính (NAM, NỮ, KHÁC)
                       date_of_birth DATE,                       -- Ngày sinh của người dùng
                       address TEXT,                             -- Địa chỉ của người dùng
                       role TEXT CHECK (role IN ('PATIENT', 'ADMIN', 'DOCTOR')) NOT NULL,  -- Vai trò (BỆNH NHÂN, ADMIN, BÁC SĨ)
                       status TEXT CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE',  -- Trạng thái người dùng (HOẠT ĐỘNG, KHÔNG HOẠT ĐỘNG)
                       created_at TIMESTAMP DEFAULT NOW()        -- Thời gian tạo tài khoản
);

-- Bảng Departments (Thông tin các chuyên khoa)
CREATE TABLE Departments (
                             id BIGSERIAL PRIMARY KEY,    -- ID chuyên khoa (khóa chính)
                             name VARCHAR(100) NOT NULL    -- Tên chuyên khoa
);

-- Bảng Doctors (Thông tin bác sĩ)
CREATE TABLE Doctors (
                         id BIGSERIAL PRIMARY KEY,                     -- ID bác sĩ (khóa chính)
                         name VARCHAR(100) NOT NULL,                    -- Tên bác sĩ
                         department_id BIGINT REFERENCES Departments(id), -- Mã chuyên khoa của bác sĩ (liên kết với bảng Departments)
                         phone_number TEXT,                             -- Số điện thoại của bác sĩ
                         email TEXT,                                    -- Email của bác sĩ
                         experience_years INT,                          -- Số năm kinh nghiệm của bác sĩ
                         degree VARCHAR(100),                           -- Chứng chỉ/độ học vấn của bác sĩ
                         consultation_hours INT,                        -- Số giờ làm việc của bác sĩ
                         rating NUMERIC(2,1),                           -- Đánh giá của bệnh nhân (thang điểm từ 0 đến 5)
                         about TEXT,                                    -- Giới thiệu về bác sĩ
                         image_url TEXT                                 -- URL ảnh của bác sĩ
);
-- Bảng Diseases (Danh mục bệnh)
CREATE TABLE Diseases (
                          id BIGSERIAL PRIMARY KEY,    -- ID bệnh (khóa chính)
                          name VARCHAR(100) NOT NULL,  -- Tên bệnh
                          description TEXT            -- Mô tả về bệnh
);

-- Bảng Appointments (Thông tin lịch hẹn)
CREATE TABLE Appointments (
                              id BIGSERIAL PRIMARY KEY,                     -- ID lịch hẹn (khóa chính)
                              user_id BIGINT REFERENCES Users(id),          -- ID người dùng (liên kết với bảng Users)
                              doctor_id BIGINT REFERENCES Doctors(id),      -- ID bác sĩ (liên kết với bảng Doctors)
                              schedule_id BIGINT,                           -- ID lịch làm việc của bác sĩ (liên kết với bảng Schedule)
                              disease_id BIGINT REFERENCES Diseases(id),    -- ID bệnh lý (liên kết với bảng Diseases)
                              appointment_time TIMESTAMP,                    -- Thời gian lịch hẹn
                              symptom_description TEXT,                     -- Mô tả triệu chứng của bệnh nhân
                              status TEXT CHECK (status IN ('SCHEDULED', 'CANCELLED', 'COMPLETED')),  -- Trạng thái lịch hẹn (ĐÃ LÊN LỊCH, HỦY, HOÀN THÀNH)
                              payment_status TEXT CHECK (payment_status IN ('PENDING', 'PAID', 'CANCELLED')) DEFAULT 'PENDING',  -- Trạng thái thanh toán (CHƯA THANH TOÁN, ĐÃ THANH TOÁN, HỦY)
                              created_at TIMESTAMP DEFAULT NOW(),           -- Thời gian tạo lịch hẹn
                              updated_at TIMESTAMP,                         -- Thời gian cập nhật thông tin lịch hẹn
                              completed_at TIMESTAMP,                       -- Thời gian hoàn thành lịch hẹn
                              cancellation_reason TEXT,                     -- Lý do hủy lịch hẹn
                              note TEXT                                     -- Ghi chú thêm về lịch hẹn
);

-- Bảng Appointment_History (Lịch sử thay đổi trạng thái lịch hẹn)
CREATE TABLE Appointment_History (
                                     id BIGSERIAL PRIMARY KEY,                     -- ID lịch sử thay đổi trạng thái (khóa chính)
                                     appointment_id BIGINT REFERENCES Appointments(id), -- ID lịch hẹn (liên kết với bảng Appointments)
                                     status TEXT,                                  -- Trạng thái mới của lịch hẹn
                                     changed_at TIMESTAMP DEFAULT NOW(),           -- Thời gian thay đổi trạng thái
                                     note TEXT                                     -- Ghi chú về thay đổi trạng thái
);

-- Bảng Symptoms (Danh mục triệu chứng)
CREATE TABLE Symptoms (
                          id BIGSERIAL PRIMARY KEY,    -- ID triệu chứng (khóa chính)
                          name VARCHAR(100) NOT NULL    -- Tên triệu chứng
);

-- Bảng Schedule (Lịch làm việc của bác sĩ)
CREATE TABLE Schedule (
                          id BIGSERIAL PRIMARY KEY,              -- ID lịch làm việc (khóa chính)
                          doctor_id BIGINT REFERENCES Doctors(id), -- ID bác sĩ (liên kết với bảng Doctors)
                          date DATE,                             -- Ngày làm việc của bác sĩ
                          start_time TIME,                       -- Giờ bắt đầu làm việc
                          end_time TIME,                         -- Giờ kết thúc làm việc
                          remaining_seats INT,                   -- Số ghế còn lại trong lịch làm việc
                          num_of_seats INT                       -- Tổng số ghế trong lịch làm việc
);

-- Bảng Medicines (Thông tin thuốc)
CREATE TABLE Medicines (
                           id BIGSERIAL PRIMARY KEY,                -- ID thuốc (khóa chính)
                           name VARCHAR(100) NOT NULL,              -- Tên thuốc
                           description TEXT,                        -- Mô tả về thuốc
                           unit VARCHAR(50),                        -- Đơn vị thuốc (ví dụ: viên, lọ, ống, ml)
                           price NUMERIC(10,2),                     -- Giá của thuốc
                           stock_quantity INT,                      -- Số lượng tồn kho
                           manufacturer VARCHAR(100),               -- Nhà sản xuất thuốc
                           expiry_date DATE                         -- Ngày hết hạn thuốc
);

-- Bảng Prescriptions (Kê đơn thuốc)
CREATE TABLE Prescriptions (
                               id BIGSERIAL PRIMARY KEY,                     -- ID đơn thuốc (khóa chính)
                               appointment_id BIGINT REFERENCES Appointments(id), -- ID lịch hẹn (liên kết với bảng Appointments)
                               doctor_id BIGINT REFERENCES Doctors(id),      -- ID bác sĩ (liên kết với bảng Doctors)
                               patient_id BIGINT REFERENCES Users(id),       -- ID bệnh nhân (liên kết với bảng Users)
                               created_at TIMESTAMP DEFAULT NOW(),           -- Thời gian tạo đơn thuốc
                               total_cost NUMERIC(12,2),                     -- Tổng chi phí đơn thuốc
                               status TEXT CHECK (status IN ('ACTIVE', 'CANCELLED')) DEFAULT 'ACTIVE', -- Trạng thái đơn thuốc (ĐANG CÒN HIỆU LỰC, ĐÃ HỦY)
                               note TEXT                                     -- Ghi chú về đơn thuốc
);

-- Bảng Prescription_Details (Chi tiết đơn thuốc)
CREATE TABLE Prescription_Details (
                                      id BIGSERIAL PRIMARY KEY,                        -- ID chi tiết đơn thuốc (khóa chính)
                                      prescription_id BIGINT REFERENCES Prescriptions(id),  -- ID đơn thuốc (liên kết với bảng Prescriptions)
                                      medicine_id BIGINT REFERENCES Medicines(id),          -- ID thuốc (liên kết với bảng Medicines)
                                      dosage VARCHAR(100),                                 -- Liều dùng thuốc (ví dụ: 2 viên/ngày)
                                      duration VARCHAR(50),                                -- Thời gian sử dụng thuốc (ví dụ: 5 ngày)
                                      note TEXT                                            -- Ghi chú về thuốc
);


