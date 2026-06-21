ALTER TABLE products
    ADD COLUMN IF NOT EXISTS image_url VARCHAR(1024) NOT NULL DEFAULT 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Tomato%20(1).jpg?width=800';

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '11111111-1111-4111-8111-111111111111', 'Tomate fresco', 'VEG-TOM-001', 3.50, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Tomato%20(1).jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-TOM-001');

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '22222222-2222-4222-8222-222222222222', 'Zanahoria organica', 'VEG-ZAN-001', 2.80, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Carrots.jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-ZAN-001');

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '33333333-3333-4333-8333-333333333333', 'Papa blanca', 'VEG-PAP-001', 2.40, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Russet%20potato.jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-PAP-001');

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '44444444-4444-4444-8444-444444444444', 'Lechuga fresca', 'VEG-LEC-001', 2.20, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Iceberg%20lettuce%20in%20SB.jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-LEC-001');

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '55555555-5555-4555-8555-555555555555', 'Cebolla roja', 'VEG-CEB-001', 2.10, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Onion%20Red%20and%20White.jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-CEB-001');

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '66666666-6666-4666-8666-666666666666', 'Brocoli', 'VEG-BRO-001', 4.90, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Broccoli%20and%20cross%20section%20edit.jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-BRO-001');

INSERT INTO products (id, name, sku, price, image_url, active, created_at)
SELECT '77777777-7777-4777-8777-777777777777', 'Palta Hass', 'VEG-PAL-001', 6.50, 'https://commons.wikimedia.org/wiki/Special:Redirect/file/Avocado%20with%20cross%20section.jpg?width=800', true, now()
WHERE NOT EXISTS (SELECT 1 FROM products WHERE sku = 'VEG-PAL-001');
