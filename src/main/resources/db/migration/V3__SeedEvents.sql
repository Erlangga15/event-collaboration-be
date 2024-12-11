-- Insert organizers
INSERT INTO users (id, email, password, full_name, phone, role, status, created_at, updated_at) VALUES 
(
    '89389df8-7e38-43d8-873e-3f67c002e7b7',
    'organizer1@example.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG',
    'Event Organizer One',
    '+6281234567890',
    'ORGANIZER',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'organizer2@example.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG',
    'Event Organizer Two',
    '+6281234567891',
    'ORGANIZER',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert customers
INSERT INTO users (id, email, password, full_name, phone, role, status, created_at, updated_at) VALUES 
(
    'b9939e9e-ddc9-4d34-a1d3-5f04cf96611e',
    'customer1@example.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG',
    'John Customer',
    '+6287654321098',
    'CUSTOMER',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'a736027a-f895-4da7-85ce-08050ee6690b',
    'customer2@example.com',
    '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG',
    'Jane Customer',
    '+6287654321099',
    'CUSTOMER',
    'ACTIVE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert events
INSERT INTO events (
    id, name, description, start_date, end_date, 
    venue_name, venue_address, category, organizer_id, 
    status, image, created_at, updated_at
) VALUES 
(
    '04b0e80c-ff9d-499a-a33d-ce4c724c04e7',
    'Jakarta Music Festival 2024',
    'The biggest music festival featuring various genres and artists.',
    '2024-05-24 15:00:00',
    '2024-05-26 23:00:00',
    'JIExpo Kemayoran',
    'Jl. Benyamin Sueb No.1, Jakarta Utara',
    'MUSIC',
    '89389df8-7e38-43d8-873e-3f67c002e7b7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543524/jazz_koyra8.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '47033a9e-67a3-4c6b-b3d0-143e99f1bc22',
    'Bandung Art Exhibition',
    'Contemporary art exhibition featuring local artists.',
    '2024-06-15 10:00:00',
    '2024-06-20 18:00:00',
    'Trans Convention Center',
    'Jl. Gatot Subroto No.289, Bandung',
    'ART',
    '89389df8-7e38-43d8-873e-3f67c002e7b7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'fb5ed352-ad2d-4819-8490-f23074ef7fa5',
    'Surabaya Jazz Night',
    'A night of jazz music under the stars.',
    '2024-07-01 19:00:00',
    '2024-07-01 23:00:00',
    'Surabaya Town Square',
    'Jl. Adityawarman No.55, Surabaya',
    'MUSIC',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543524/jazz_koyra8.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '3bbdd687-7026-411b-87bf-375b74a17d80',
    'Bali ART Festival',
    'Traditional and modern Balinese ART showcase.',
    '2024-07-15 09:00:00',
    '2024-07-20 21:00:00',
    'Bali ART Center',
    'Jl. Nusa Indah, Denpasar, Bali',
    'ART',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '85ff4950-a495-4a57-b9de-b7faa640cf28',
    'Yogyakarta Classical Concert',
    'Orchestra performance of classical masterpieces.',
    '2024-08-05 19:30:00',
    '2024-08-05 22:00:00',
    'Jogja National Museum',
    'Jl. Prof. Ki Amri Yahya No.1, Yogyakarta',
    'MUSIC',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543524/jazz_koyra8.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'e6cbb40c-7e69-4383-b9b1-14acf46a18f4',
    'Jakarta Tech Week',
    'Week-long technology conference and exhibition.',
    '2024-08-20 08:00:00',
    '2024-08-25 17:00:00',
    'Jakarta Convention Center',
    'Jl. Gatot Subroto, Jakarta',
    'EDUCATION',
    '89389df8-7e38-43d8-873e-3f67c002e7b7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'ccbb9e77-f665-4196-b87e-00e683ed837b',
    'Digital Marketing Workshop',
    'Intensive workshop on digital marketing strategies.',
    '2024-09-10 09:00:00',
    '2024-09-11 17:00:00',
    'Bandung Digital Valley',
    'Jl. Gegerkalong Hilir, Bandung',
    'EDUCATION',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '739b252c-26c7-4f3e-8c06-fbda5ed3bc6e',
    'Surabaya Education Fair',
    'Annual education exhibition and seminar.',
    '2024-09-25 08:00:00',
    '2024-09-27 16:00:00',
    'Surabaya Grand City',
    'Jl. Walikota Mustajab, Surabaya',
    'EDUCATION',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '1cd4c5a3-5eea-4f02-85f6-c5d94ffc451d',
    'Bali Tech Summit',
    'Technology summit for startups and tech companies.',
    '2024-10-05 09:00:00',
    '2024-10-07 17:00:00',
    'Bali Nusa Dua Convention Center',
    'Kawasan ITDC, Nusa Dua, Bali',
    'EDUCATION',
    '89389df8-7e38-43d8-873e-3f67c002e7b7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    'd09469b6-0add-4a49-bf55-1042b12aae5e',
    'Yogyakarta Learning Festival',
    'Educational festival for students and educators.',
    '2024-10-20 08:00:00',
    '2024-10-22 16:00:00',
    'Jogja Expo Center',
    'Jl. Janti, Yogyakarta',
    'EDUCATION',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '0b70ab70-f1c2-4538-bdef-96300c79830c',
    'Jakarta Business Forum',
    'Annual business conference and networking event.',
    '2024-11-05 08:00:00',
    '2024-11-07 17:00:00',
    'The Ritz-Carlton Jakarta',
    'Jl. DR Ide Anak Agung Gde Agung, Jakarta',
    'BUSINESS',
    'ee315c9c-2bc4-4d56-8ef4-98ab688ae1a7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    '3ad9d9b6-45e2-47a0-8369-9826e455fa85',
    'Bandung Food Festival',
    'Culinary festival featuring local and international cuisine.',
    '2024-11-20 10:00:00',
    '2024-11-24 22:00:00',
    'Paris Van Java',
    'Jl. Sukajadi, Bandung',
    'FOOD',
    '89389df8-7e38-43d8-873e-3f67c002e7b7',
    'PUBLISHED',
    'https://res.cloudinary.com/dlcfhalzu/image/upload/v1733543525/conference_a3rmmf.jpg',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert tickets
INSERT INTO tickets (
    id, event_id, name, price, quantity, type, created_at, updated_at
) VALUES 
(
    gen_random_uuid(),
    '04b0e80c-ff9d-499a-a33d-ce4c724c04e7',
    'Festival Pass',
    850000.00,
    1000,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '47033a9e-67a3-4c6b-b3d0-143e99f1bc22',
    'Exhibition Entry',
    0.00,
    500,
    'FREE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    'fb5ed352-ad2d-4819-8490-f23074ef7fa5',
    'Jazz Night Pass',
    500000.00,
    300,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '3bbdd687-7026-411b-87bf-375b74a17d80',
    'Festival Pass',
    0.00,
    1000,
    'FREE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '85ff4950-a495-4a57-b9de-b7faa640cf28',
    'Concert Ticket',
    750000.00,
    200,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    'e6cbb40c-7e69-4383-b9b1-14acf46a18f4',
    'Tech Week Pass',
    0.00,
    2000,
    'FREE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    'ccbb9e77-f665-4196-b87e-00e683ed837b',
    'Workshop Access',
    1500000.00,
    50,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '739b252c-26c7-4f3e-8c06-fbda5ed3bc6e',
    'Education Fair Entry',
    0.00,
    5000,
    'FREE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '1cd4c5a3-5eea-4f02-85f6-c5d94ffc451d',
    'Summit Pass',
    2500000.00,
    300,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    'd09469b6-0add-4a49-bf55-1042b12aae5e',
    'Learning Festival Pass',
    0.00,
    3000,
    'FREE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '0b70ab70-f1c2-4538-bdef-96300c79830c',
    'Business Forum Access',
    3500000.00,
    150,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    '3ad9d9b6-45e2-47a0-8369-9826e455fa85',
    'Food Festival Pass',
    100000.00,
    1000,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert sample transactions
INSERT INTO transactions (
    id, user_id, event_id, ticket_id, quantity, subtotal_price, total_price,
    status, created_at, updated_at
) VALUES 
(
    gen_random_uuid(),
    'b9939e9e-ddc9-4d34-a1d3-5f04cf96611e',
    '04b0e80c-ff9d-499a-a33d-ce4c724c04e7',
    (SELECT id FROM tickets WHERE event_id = '04b0e80c-ff9d-499a-a33d-ce4c724c04e7' LIMIT 1),
    2,
    1700000.00,
    1700000.00,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
),
(
    gen_random_uuid(),
    'a736027a-f895-4da7-85ce-08050ee6690b',
    '47033a9e-67a3-4c6b-b3d0-143e99f1bc22',
    (SELECT id FROM tickets WHERE event_id = '47033a9e-67a3-4c6b-b3d0-143e99f1bc22' LIMIT 1),
    1,
    0.00,
    0.00,
    'PAID',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

