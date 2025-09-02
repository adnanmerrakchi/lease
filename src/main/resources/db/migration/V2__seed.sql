-- Demo customers
INSERT INTO customer (id, name) VALUES
    ('00000000-0000-0000-0000-00000000A001','Adnan'),
    ('00000000-0000-0000-0000-00000000A002','Mohamed Ali')
    ON CONFLICT (id) DO NOTHING;

-- Demo cars (Ford Fiesta, Renault Clio 5, VW T-Roc, Hyundai Tucson)
INSERT INTO car (id, crn, brand, model, release_year, rent_price) VALUES
    ('00000000-0000-0000-0000-000000000101','27001A44','Ford','Fiesta', 2022, 250),
    ('00000000-0000-0000-0000-000000000102','27002A44','Renault','Clio 5', 2023, 250),
    ('00000000-0000-0000-0000-000000000103','27003A44','Volkswagen','T-Roc', 2022, 400),
    ('00000000-0000-0000-0000-000000000104','27004A44','Hyundai','Tucson', 2022, 500)
    ON CONFLICT (id) DO NOTHING;

INSERT INTO lease (id, car_id, customer_id, start_at, expected_return_at, returned_at, status) VALUES
    ('11111111-2222-3333-4444-555555555555',
     '00000000-0000-0000-0000-000000000101','00000000-0000-0000-0000-00000000A001',
     '2025-09-02 09:00:00',
     '2025-09-04 09:00:00',
     NULL,
     'ACTIVE')
    ON CONFLICT (id) DO NOTHING;