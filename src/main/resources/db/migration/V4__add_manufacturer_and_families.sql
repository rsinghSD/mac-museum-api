-- Adds a manufacturer so the museum can show non-Apple "contemporaries" next to Apple machines,
-- and the product families needed for the expanded catalog.

ALTER TABLE apple_model ADD COLUMN manufacturer VARCHAR(60) DEFAULT 'Apple' NOT NULL;

CREATE INDEX ix_apple_model_manufacturer ON apple_model (manufacturer);

INSERT INTO product_family (slug, name, description) VALUES
('apple-iii',       'Apple III',        'Apple''s first computer designed for business users, sold alongside the Apple II.'),
('performa',        'Performa',         'Consumer Macs of the 1990s, sold in department and electronics stores under many model numbers.'),
('emac',            'eMac',             'An all-in-one G4 desktop with a CRT display, built first for schools.'),
('xserve',          'Xserve',           'Rack-mounted servers that ran Mac OS X Server, sold from 2002 to 2011.'),
('mac-studio',      'Mac Studio',       'A compact high-performance desktop that sits between the Mac mini and the Mac Pro.'),
('apple-watch',     'Apple Watch',      'Wrist-worn computers focused on health, fitness and notifications.'),
('apple-vision',    'Apple Vision',     'Spatial computing headsets that blend apps with the room around you.'),
('contemporaries',  'Contemporaries (OS/2 era)', 'Non-Apple machines shown for context: IBM computers from the era of OS/2 and OS/2 Warp, the operating system IBM pitched against Mac OS and Windows.');
