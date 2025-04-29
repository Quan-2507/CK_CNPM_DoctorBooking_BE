-- Thêm bác sĩ vào bảng Doctors
INSERT INTO Doctors (name, department_id, phone_number, email, experience_years, degree, consultation_hours, rating, about, image_url)
VALUES
    -- Allergy
    ('Dr. Nguyễn Văn A', 1, '0123456789', 'nguyenvana@example.com', 10, 'Thạc sĩ Y khoa', 40, 4.5, 'Bác sĩ chuyên khoa Dị ứng', 'https://example.com/images/dr_a.jpg'),
    ('Dr. Trần Thị B', 1, '0987654321', 'tranb@example.com', 8, 'Bác sĩ Chuyên khoa Dị ứng', 35, 4.2, 'Chuyên điều trị các bệnh lý liên quan đến dị ứng', 'https://example.com/images/dr_b.jpg'),
    ('Dr. Lê Minh C', 1, '0912345678', 'leminhc@example.com', 6, 'Bác sĩ Nội khoa', 30, 4.7, 'Bác sĩ Nội khoa, chuyên trị các bệnh dị ứng', 'https://example.com/images/dr_c.jpg'),

    -- Orthopedics
    ('Dr. Nguyễn Văn D', 2, '0123456790', 'nguyenvand@example.com', 12, 'Thạc sĩ Y khoa', 45, 4.8, 'Bác sĩ chuyên khoa Chấn thương chỉnh hình', 'https://example.com/images/dr_d.jpg'),
    ('Dr. Trần Thị E', 2, '0987654331', 'trane@example.com', 7, 'Bác sĩ Chuyên khoa Xương khớp', 38, 4.4, 'Chuyên phẫu thuật và điều trị bệnh lý cơ xương khớp', 'https://example.com/images/dr_e.jpg'),
    ('Dr. Lê Minh F', 2, '0912345679', 'leminhf@example.com', 9, 'Bác sĩ Ngoại khoa', 40, 4.6, 'Chuyên về phẫu thuật chỉnh hình và điều trị các bệnh lý khớp', 'https://example.com/images/dr_f.jpg'),

    -- Dentistry
    ('Dr. Nguyễn Văn G', 3, '0123456791', 'nguyenvang@example.com', 15, 'Thạc sĩ Nha khoa', 50, 4.9, 'Bác sĩ chuyên khoa Nha khoa', 'https://example.com/images/dr_g.jpg'),
    ('Dr. Trần Thị H', 3, '0987654332', 'tranh@example.com', 10, 'Bác sĩ Nha khoa', 30, 4.3, 'Chuyên điều trị các bệnh lý về răng miệng', 'https://example.com/images/dr_h.jpg'),
    ('Dr. Lê Minh I', 3, '0912345680', 'leminhi@example.com', 8, 'Bác sĩ Nha khoa', 35, 4.7, 'Chuyên thăm khám và điều trị các bệnh về răng miệng', 'https://example.com/images/dr_i.jpg'),

    -- Neurology
    ('Dr. Nguyễn Văn J', 4, '0123456792', 'nguyenvanj@example.com', 12, 'Thạc sĩ Thần kinh', 45, 4.8, 'Bác sĩ chuyên khoa Thần kinh', 'https://example.com/images/dr_j.jpg'),
    ('Dr. Trần Thị K', 4, '0987654333', 'trank@example.com', 10, 'Bác sĩ Thần kinh', 40, 4.5, 'Chuyên điều trị các bệnh lý thần kinh', 'https://example.com/images/dr_k.jpg'),
    ('Dr. Lê Minh L', 4, '0912345681', 'leminhl@example.com', 8, 'Bác sĩ Nội thần kinh', 35, 4.6, 'Chuyên trị các bệnh về thần kinh như động kinh, đau đầu', 'https://example.com/images/dr_l.jpg'),

    -- Gastroenterology
    ('Dr. Nguyễn Văn M', 5, '0123456793', 'nguyenvam@example.com', 10, 'Thạc sĩ Tiêu hóa', 45, 4.7, 'Bác sĩ chuyên khoa Tiêu hóa', 'https://example.com/images/dr_m.jpg'),
    ('Dr. Trần Thị N', 5, '0987654334', 'trann@example.com', 9, 'Bác sĩ Tiêu hóa', 40, 4.6, 'Chuyên điều trị các bệnh lý về dạ dày, ruột', 'https://example.com/images/dr_n.jpg'),
    ('Dr. Lê Minh O', 5, '0912345682', 'leminho@example.com', 8, 'Bác sĩ Tiêu hóa', 35, 4.5, 'Chuyên trị các bệnh lý tiêu hóa và dạ dày', 'https://example.com/images/dr_o.jpg'),

    -- Urology
    ('Dr. Nguyễn Văn P', 6, '0123456794', 'nguyenvap@example.com', 10, 'Thạc sĩ Tiết niệu', 45, 4.8, 'Bác sĩ chuyên khoa Tiết niệu', 'https://example.com/images/dr_p.jpg'),
    ('Dr. Trần Thị Q', 6, '0987654335', 'tranq@example.com', 8, 'Bác sĩ Tiết niệu', 35, 4.7, 'Chuyên điều trị các bệnh về thận, bàng quang', 'https://example.com/images/dr_q.jpg'),
    ('Dr. Lê Minh R', 6, '0912345683', 'leminhr@example.com', 9, 'Bác sĩ Tiết niệu', 40, 4.6, 'Chuyên phẫu thuật và điều trị bệnh lý tiết niệu', 'https://example.com/images/dr_r.jpg'),

    -- Hepatology
    ('Dr. Nguyễn Văn S', 7, '0123456795', 'nguyenvas@example.com', 12, 'Thạc sĩ Gan mật', 50, 4.9, 'Bác sĩ chuyên khoa Gan mật', 'https://example.com/images/dr_s.jpg'),
    ('Dr. Trần Thị T', 7, '0987654336', 'trant@example.com', 10, 'Bác sĩ Gan mật', 45, 4.8, 'Chuyên điều trị các bệnh lý gan, mật', 'https://example.com/images/dr_t.jpg'),
    ('Dr. Lê Minh U', 7, '0912345684', 'leminhu@example.com', 8, 'Bác sĩ Gan mật', 40, 4.7, 'Bác sĩ chuyên về điều trị các bệnh lý gan mật', 'https://example.com/images/dr_u.jpg'),

    -- Dermatology
    ('Dr. Nguyễn Văn V', 8, '0123456796', 'nguyenvav@example.com', 10, 'Thạc sĩ Da liễu', 40, 4.6, 'Bác sĩ chuyên khoa Da liễu', 'https://example.com/images/dr_v.jpg'),
    ('Dr. Trần Thị W', 8, '0987654337', 'tranw@example.com', 8, 'Bác sĩ Da liễu', 35, 4.5, 'Chuyên trị các bệnh lý về da, viêm nhiễm da', 'https://example.com/images/dr_w.jpg'),
    ('Dr. Lê Minh X', 8, '0912345685', 'leminhx@example.com', 7, 'Bác sĩ Da liễu', 30, 4.7, 'Bác sĩ chuyên về điều trị bệnh lý về da', 'https://example.com/images/dr_x.jpg'),

    -- Psychiatry
    ('Dr. Nguyễn Văn Y', 9, '0123456797', 'nguyenvay@example.com', 12, 'Thạc sĩ Tâm thần', 45, 4.7, 'Bác sĩ chuyên khoa Tâm thần', 'https://example.com/images/dr_y.jpg'),
    ('Dr. Trần Thị Z', 9, '0987654338', 'tranz@example.com', 10, 'Bác sĩ Tâm thần', 40, 4.5, 'Chuyên điều trị các bệnh tâm thần như lo âu, trầm cảm', 'https://example.com/images/dr_z.jpg'),
    ('Dr. Lê Minh AA', 9, '0912345686', 'leminhaa@example.com', 8, 'Bác sĩ Tâm thần', 35, 4.6, 'Bác sĩ chuyên về trị liệu tâm lý', 'https://example.com/images/dr_aa.jpg'),

    -- Otorhinolaryngology
    ('Dr. Nguyễn Văn AB', 10, '0123456798', 'nguyenvab@example.com', 10, 'Thạc sĩ Tai Mũi Họng', 40, 4.8, 'Bác sĩ chuyên khoa Tai Mũi Họng', 'https://example.com/images/dr_ab.jpg'),
    ('Dr. Trần Thị AC', 10, '0987654339', 'tranac@example.com', 9, 'Bác sĩ Tai Mũi Họng', 38, 4.6, 'Chuyên điều trị các bệnh lý về tai, mũi, họng', 'https://example.com/images/dr_ac.jpg'),
    ('Dr. Lê Minh AD', 10, '0912345687', 'leminhad@example.com', 8, 'Bác sĩ Tai Mũi Họng', 35, 4.7, 'Chuyên về các bệnh lý tai mũi họng', 'https://example.com/images/dr_ad.jpg'),

    -- General Medicine
    ('Dr. Nguyễn Văn AE', 11, '0123456799', 'nguyenvae@example.com', 12, 'Thạc sĩ Nội khoa', 45, 4.8, 'Bác sĩ chuyên khoa Nội tổng quát', 'https://example.com/images/dr_ae.jpg'),
    ('Dr. Trần Thị AF', 11, '0987654340', 'tranaf@example.com', 10, 'Bác sĩ Nội khoa', 40, 4.7, 'Chuyên khám chữa bệnh tổng quát', 'https://example.com/images/dr_af.jpg'),
    ('Dr. Lê Minh AG', 11, '0912345688', 'leminhag@example.com', 8, 'Bác sĩ Nội khoa', 35, 4.6, 'Chuyên điều trị bệnh lý tổng quát', 'https://example.com/images/dr_ag.jpg'),

    -- Obstetrics and Gynecology
    ('Dr. Nguyễn Văn AH', 12, '0123456800', 'nguyenvah@example.com', 10, 'Thạc sĩ Sản phụ khoa', 40, 4.9, 'Bác sĩ chuyên khoa Sản phụ khoa', 'https://example.com/images/dr_ah.jpg'),
    ('Dr. Trần Thị AI', 12, '0987654341', 'tranai@example.com', 9, 'Bác sĩ Sản phụ khoa', 38, 4.8, 'Chuyên chăm sóc sức khỏe phụ nữ, sinh nở', 'https://example.com/images/dr_ai.jpg'),
    ('Dr. Lê Minh AJ', 12, '0912345689', 'leminhaj@example.com', 7, 'Bác sĩ Sản phụ khoa', 35, 4.7, 'Bác sĩ chuyên về sản khoa và chăm sóc sức khỏe sinh sản', 'https://example.com/images/dr_aj.jpg'),

    -- Pulmonology
    ('Dr. Nguyễn Văn AK', 13, '0123456801', 'nguyenvak@example.com', 10, 'Thạc sĩ Hô hấp', 40, 4.8, 'Bác sĩ chuyên khoa Hô hấp', 'https://example.com/images/dr_ak.jpg'),
    ('Dr. Trần Thị AL', 13, '0987654342', 'tranal@example.com', 9, 'Bác sĩ Hô hấp', 38, 4.7, 'Chuyên điều trị các bệnh về hô hấp', 'https://example.com/images/dr_al.jpg'),
    ('Dr. Lê Minh AM', 13, '0912345690', 'leminham@example.com', 8, 'Bác sĩ Hô hấp', 35, 4.6, 'Chuyên về điều trị bệnh lý đường hô hấp', 'https://example.com/images/dr_am.jpg'),

    -- Endocrinology
    ('Dr. Nguyễn Văn AN', 14, '0123456802', 'nguyenvanan@example.com', 12, 'Thạc sĩ Nội tiết', 45, 4.8, 'Bác sĩ chuyên khoa Nội tiết', 'https://example.com/images/dr_an.jpg'),
    ('Dr. Trần Thị AO', 14, '0987654343', 'tranao@example.com', 10, 'Bác sĩ Nội tiết', 40, 4.7, 'Chuyên điều trị các bệnh lý nội tiết', 'https://example.com/images/dr_ao.jpg'),
    ('Dr. Lê Minh AP', 14, '0912345691', 'leminhap@example.com', 8, 'Bác sĩ Nội tiết', 35, 4.6, 'Chuyên điều trị các bệnh nội tiết như đái tháo đường', 'https://example.com/images/dr_ap.jpg'),

    -- Hematology
    ('Dr. Nguyễn Văn AQ', 15, '0123456803', 'nguyenvanq@example.com', 12, 'Thạc sĩ Huyết học', 45, 4.9, 'Bác sĩ chuyên khoa Huyết học', 'https://example.com/images/dr_aq.jpg'),
    ('Dr. Trần Thị AR', 15, '0987654344', 'tranar@example.com', 10, 'Bác sĩ Huyết học', 40, 4.8, 'Chuyên điều trị các bệnh lý huyết học', 'https://example.com/images/dr_ar.jpg'),
    ('Dr. Lê Minh AS', 15, '0912345692', 'leminhas@example.com', 8, 'Bác sĩ Huyết học', 35, 4.7, 'Chuyên về các bệnh lý máu', 'https://example.com/images/dr_as.jpg'),

    -- Nephrology
    ('Dr. Nguyễn Văn AT', 16, '0123456804', 'nguyenvat@example.com', 10, 'Thạc sĩ Thận', 40, 4.8, 'Bác sĩ chuyên khoa Thận', 'https://example.com/images/dr_at.jpg'),
    ('Dr. Trần Thị AU', 16, '0987654345', 'trnau@example.com', 9, 'Bác sĩ Thận', 38, 4.7, 'Chuyên điều trị bệnh lý thận', 'https://example.com/images/dr_au.jpg'),
    ('Dr. Lê Minh AV', 16, '0912345693', 'leminhav@example.com', 8, 'Bác sĩ Thận', 35, 4.6, 'Chuyên trị các bệnh lý về thận', 'https://example.com/images/dr_av.jpg'),

    -- Cardiology
    ('Dr. Nguyễn Văn AW', 17, '0123456805', 'nguyenvaw@example.com', 12, 'Thạc sĩ Tim mạch', 45, 4.8, 'Bác sĩ chuyên khoa Tim mạch', 'https://example.com/images/dr_aw.jpg'),
    ('Dr. Trần Thị AX', 17, '0987654346', 'tranax@example.com', 10, 'Bác sĩ Tim mạch', 40, 4.7, 'Chuyên điều trị các bệnh về tim mạch', 'https://example.com/images/dr_ax.jpg'),
    ('Dr. Lê Minh AY', 17, '0912345694', 'leminhay@example.com', 8, 'Bác sĩ Tim mạch', 35, 4.6, 'Chuyên chữa các bệnh lý tim mạch', 'https://example.com/images/dr_ay.jpg'),

    -- Rheumatology
    ('Dr. Nguyễn Văn AZ', 18, '0123456806', 'nguyenvaz@example.com', 10, 'Thạc sĩ Cơ xương khớp', 40, 4.8, 'Bác sĩ chuyên khoa Cơ xương khớp', 'https://example.com/images/dr_az.jpg'),
    ('Dr. Trần Thị BA', 18, '0987654347', 'tranba@example.com', 9, 'Bác sĩ Cơ xương khớp', 38, 4.7, 'Chuyên điều trị các bệnh lý cơ xương khớp', 'https://example.com/images/dr_ba.jpg'),
    ('Dr. Lê Minh BB', 18, '0912345695', 'leminhbb@example.com', 8, 'Bác sĩ Cơ xương khớp', 35, 4.6, 'Chuyên về điều trị các bệnh cơ xương khớp', 'https://example.com/images/dr_bb.jpg'),

    -- Ophthalmology
    ('Dr. Nguyễn Văn BC', 19, '0123456807', 'nguyenvbc@example.com', 12, 'Thạc sĩ Mắt', 45, 4.8, 'Bác sĩ chuyên khoa Mắt', 'https://example.com/images/dr_bc.jpg'),
    ('Dr. Trần Thị BD', 19, '0987654348', 'tranbd@example.com', 10, 'Bác sĩ Mắt', 40, 4.7, 'Chuyên điều trị các bệnh lý về mắt', 'https://example.com/images/dr_bd.jpg'),
    ('Dr. Lê Minh BE', 19, '0912345696', 'leminhbe@example.com', 8, 'Bác sĩ Mắt', 35, 4.6, 'Chuyên về các bệnh lý mắt', 'https://example.com/images/dr_be.jpg');
