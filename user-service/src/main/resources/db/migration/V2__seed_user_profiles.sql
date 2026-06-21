INSERT INTO user_profiles (id, full_name, email, phone, created_at)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa1', 'Admin Verduritas', 'admin@verduritas.test', '+51900000001', now()
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE email = 'admin@verduritas.test');

INSERT INTO user_profiles (id, full_name, email, phone, created_at)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa2', 'Seller Verduritas', 'seller@verduritas.test', '+51900000002', now()
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE email = 'seller@verduritas.test');

INSERT INTO user_profiles (id, full_name, email, phone, created_at)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa3', 'Cliente Verduritas', 'cliente@verduritas.test', '+51900000003', now()
WHERE NOT EXISTS (SELECT 1 FROM user_profiles WHERE email = 'cliente@verduritas.test');
