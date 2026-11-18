-- Non-Apple machines from the OS/2 era, shown for context. Facts limited to what we are confident about; unknown values are NULL.
-- Prices: US launch list price of the base configuration. Clock speeds rounded to MHz.
-- Apple does not publish clock speeds for recent A-series and M5/M6 chips, so those stay NULL.
-- Summaries are original text.

INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibm-ps2-model-50', 'IBM PS/2 Model 50', 1987, NULL, (SELECT id FROM product_family WHERE slug = 'contemporaries'), 'INTEL_X86', 'Intel 80286', 10, 1024,
 'Part of IBM''s 1987 PS/2 line, the hardware that IBM and Microsoft''s OS/2 was first developed for. It arrived the same year as the Macintosh II.', 'IBM');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibm-ps2-model-80', 'IBM PS/2 Model 80', 1987, NULL, (SELECT id FROM product_family WHERE slug = 'contemporaries'), 'INTEL_X86', 'Intel 80386', 16, NULL,
 'IBM''s first PS/2 built on the 32-bit 386 processor, a floor-standing machine for demanding business users.', 'IBM');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibm-thinkpad-701c', 'IBM ThinkPad 701C', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'contemporaries'), 'INTEL_X86', 'Intel 486DX4', 75, NULL,
 'Famous for its butterfly keyboard, which unfolded to be wider than the laptop itself. It competed with the PowerBook in the year of OS/2 Warp.', 'IBM');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibm-thinkpad-365cs', 'IBM ThinkPad 365CS', 1995, 1999.00, (SELECT id FROM product_family WHERE slug = 'contemporaries'), 'INTEL_X86', 'Cyrix Cx486DX4', 75, 8192,
 'An affordable ThinkPad that IBM could ship with OS/2 Warp 3.0 preinstalled, as an alternative to DOS and Windows.', 'IBM');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibm-power-series-830', 'IBM Power Series 830', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'contemporaries'), 'POWERPC', 'PowerPC 604', 100, 16384,
 'A PowerPC desktop that used the same processor family as the Power Mac. It was one of only two machines that ran OS/2 Warp PowerPC Edition, released in December 1995.', 'IBM');
