INSERT INTO auth_users (id, email, password_hash, created_at)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa1', 'admin@verduritas.test', '$2b$10$s5cl4eqBEyfLVzb3dTfs7O4sHCsP47ORLyZYCB2Evj5Dxekv14KAG', now()
WHERE NOT EXISTS (SELECT 1 FROM auth_users WHERE email = 'admin@verduritas.test');

INSERT INTO auth_users (id, email, password_hash, created_at)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa2', 'seller@verduritas.test', '$2b$10$1R5oK3Yvxr4X8qQdeDB1o.uzebVO6R41Azqlntp0qECuRQYRv9D4i', now()
WHERE NOT EXISTS (SELECT 1 FROM auth_users WHERE email = 'seller@verduritas.test');

INSERT INTO auth_users (id, email, password_hash, created_at)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa3', 'cliente@verduritas.test', '$2b$10$dQQrXTsvgBmmFlQUuCJ7fu/uLck/CS35L9mEHzgqqHMNbSKgcd8Ui', now()
WHERE NOT EXISTS (SELECT 1 FROM auth_users WHERE email = 'cliente@verduritas.test');

INSERT INTO auth_user_roles (user_id, role)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa1', 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM auth_user_roles WHERE user_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa1' AND role = 'ROLE_ADMIN');

INSERT INTO auth_user_roles (user_id, role)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa2', 'ROLE_SELLER'
WHERE NOT EXISTS (SELECT 1 FROM auth_user_roles WHERE user_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa2' AND role = 'ROLE_SELLER');

INSERT INTO auth_user_roles (user_id, role)
SELECT 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa3', 'ROLE_CUSTOMER'
WHERE NOT EXISTS (SELECT 1 FROM auth_user_roles WHERE user_id = 'aaaaaaaa-aaaa-4aaa-8aaa-aaaaaaaaaaa3' AND role = 'ROLE_CUSTOMER');
