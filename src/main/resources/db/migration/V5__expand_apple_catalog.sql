-- Generated seed data. Facts limited to what we are confident about; unknown values are NULL.
-- Prices: US launch list price of the base configuration. Clock speeds rounded to MHz.
-- Apple does not publish clock speeds for recent A-series and M5/M6 chips, so those stay NULL.
-- Summaries are original text.

INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-ii-plus', 'Apple II Plus', 1979, NULL, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', 'MOS 6502', 1, NULL,
 'Put Applesoft BASIC with floating-point maths in ROM and started up on its own, which made the Apple II easier to use at home and in schools.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-iii', 'Apple III', 1980, NULL, (SELECT id FROM product_family WHERE slug = 'apple-iii'), 'MOS_6502', 'Synertek 6502A', 2, 128,
 'Apple''s first attempt at a business computer. Early units suffered from reliability problems, and it never matched the success of the Apple II.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-iic', 'Apple IIc', 1984, 1295.00, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', '65C02', 1, 128,
 'A compact, carry-handle version of the Apple II with built-in floppy drive and ports, aimed at home users who wanted something simple.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-iigs', 'Apple IIGS', 1986, 999.00, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', 'WDC 65C816', 3, 256,
 'A 16-bit Apple II with much better graphics and a wavetable sound chip, while still running most older Apple II software.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-iic-plus', 'Apple IIc Plus', 1988, 675.00, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', 'WDC 65C02', 4, 128,
 'The last new Apple II model. It ran noticeably faster than earlier machines and moved to a 3.5-inch floppy drive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('lisa-2', 'Lisa 2', 1984, 3495.00, (SELECT id FROM product_family WHERE slug = 'lisa'), 'M68K', 'Motorola 68000', 5, 512,
 'A cheaper revision of the Lisa that replaced the troublesome original floppy drives with a 3.5-inch drive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-xl', 'Macintosh XL', 1985, NULL, (SELECT id FROM product_family WHERE slug = 'lisa'), 'M68K', 'Motorola 68000', 5, NULL,
 'A Lisa 2 rebranded and sold with software that let it run Macintosh programs, before the line was discontinued.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-512ke', 'Macintosh 512Ke', 1986, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 8, 512,
 'An updated 512K with the newer ROM and a double-sided 800 KB floppy drive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-iix', 'Macintosh IIx', 1988, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 16, NULL,
 'Brought the 68030 processor to the modular Macintosh II design and introduced the high-density SuperDrive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-iicx', 'Macintosh IIcx', 1989, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 16, NULL,
 'A smaller Macintosh II with three expansion slots, designed to stand on its side or lie flat on a desk.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-iici', 'Macintosh IIci', 1989, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 25, NULL,
 'A faster IIcx with built-in video, which made it a popular and long-lived desktop.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-iifx', 'Macintosh IIfx', 1990, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 40, NULL,
 'The fastest and most expensive Mac of its day, promoted with the phrase wicked fast.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-iisi', 'Macintosh IIsi', 1990, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 20, NULL,
 'A lower-cost Macintosh II with built-in video and a single expansion slot.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-classic-ii', 'Macintosh Classic II', 1991, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 16, 2048,
 'Put a 68030 processor into the compact Classic case, roughly doubling the speed of the original Classic.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-quadra-900', 'Macintosh Quadra 900', 1991, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68040', 25, 4096,
 'A large tower Quadra with plenty of room for drives and expansion cards, aimed at professionals.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-lc-ii', 'Macintosh LC II', 1992, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 16, 4096,
 'An LC with a 68030 processor, keeping the low-cost pizza-box design.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-quadra-950', 'Macintosh Quadra 950', 1992, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68040', 33, 8192,
 'A faster successor to the Quadra 900 and the top of the 68k Mac line for a time.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-lc-iii', 'Macintosh LC III', 1993, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 25, 4096,
 'A quicker LC with a 32-bit data path, sold widely to schools and families.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-color-classic', 'Macintosh Color Classic', 1993, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 16, 4096,
 'The first compact Mac with a built-in colour screen.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-centris-650', 'Macintosh Centris 650', 1993, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68040', 25, NULL,
 'A mid-range desktop in the short-lived Centris line, later folded into the Quadra name.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-centris-660av', 'Macintosh Centris 660AV', 1993, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68040', 25, 8192,
 'Added a digital signal processor for audio and video input, plus early speech recognition.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-quadra-840av', 'Macintosh Quadra 840AV', 1993, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68040', 40, 8192,
 'The fastest 68k Mac, with a digital signal processor for audio, video and speech features.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macintosh-tv', 'Macintosh TV', 1993, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 32, 5120,
 'Combined a Mac with a television tuner in one black case. Only a small number were made.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('twentieth-anniversary-macintosh', 'Twentieth Anniversary Macintosh', 1997, 7499.00, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'POWERPC', 'PowerPC 603e', 250, 32768,
 'A limited-edition Mac with a flat LCD, a built-in sound system and a vertical CD drive, made to mark Apple''s twentieth year.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('performa-200', 'Macintosh Performa 200', 1992, NULL, (SELECT id FROM product_family WHERE slug = 'performa'), 'M68K', 'Motorola 68030', 16, 2048,
 'A Classic II sold under the Performa name through consumer stores, often bundled with software.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('performa-5200cd', 'Power Macintosh Performa 5200CD', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'performa'), 'POWERPC', 'PowerPC 603', 75, 8192,
 'An all-in-one PowerPC Performa with a built-in CD-ROM drive and display.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('performa-6400', 'Performa 6400', 1996, NULL, (SELECT id FROM product_family WHERE slug = 'performa'), 'POWERPC', 'PowerPC 603e', 180, 16384,
 'A mini-tower Performa aimed at families, with multimedia features and a built-in CD-ROM drive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-7100', 'Power Macintosh 7100', 1994, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 601', 66, 8192,
 'Part of the first wave of PowerPC Macs, in the desktop case of the Quadra 650.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-8100', 'Power Macintosh 8100', 1994, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 601', 80, 8192,
 'The high-end model of the first PowerPC Macs, in a tower case.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-7500', 'Power Macintosh 7500', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 601', 100, 16384,
 'A PCI-based desktop with video input, and a processor on a removable card that could be upgraded.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-8500', 'Power Macintosh 8500', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 604', 120, 16384,
 'A tower with built-in video capture, popular for desktop video editing.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-9500', 'Power Macintosh 9500', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 604', 120, 16384,
 'A professional tower with six PCI slots, one of the first Macs built on the PCI bus.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-4400', 'Power Macintosh 4400', 1996, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 603e', 160, 16384,
 'A low-cost desktop built largely from standard PC-style parts.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-9600', 'Power Macintosh 9600', 1997, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 604e', 200, 32768,
 'A large tower with six PCI slots and the last of the Power Macintosh 9000 series.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-macintosh-g3-blue-white', 'Power Macintosh G3 (Blue & White)', 1999, 1599.00, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 750 (G3)', 300, 65536,
 'Brought the iMac''s translucent style to the tower and added FireWire and a side door that swung open for easy access.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-mac-g4-pci', 'Power Mac G4 (PCI Graphics)', 1999, 1599.00, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 7400 (G4)', 350, 65536,
 'The first G4 desktop. Apple promoted the AltiVec vector unit inside the chip as a supercomputer-class feature.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-mac-g4-quicksilver', 'Power Mac G4 (Quicksilver)', 2001, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 7450 (G4)', 733, NULL,
 'A refreshed G4 tower with a silver front panel and faster processors.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-mac-g4-mdd', 'Power Mac G4 (Mirrored Drive Doors)', 2002, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 7455 (G4)', 867, NULL,
 'A dual-processor G4 tower known for its mirrored drive bay doors and, on some models, a loud fan.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('power-mac-g5-quad', 'Power Mac G5 Quad', 2005, 3299.00, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 970MP (G5)', 2500, 524288,
 'The final PowerPC Mac: two dual-core G5 chips kept cool by liquid cooling, released shortly before the move to Intel.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-140', 'PowerBook 140', 1991, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'M68K', 'Motorola 68030', 16, 2048,
 'The entry model of the first PowerBook range, with a passive-matrix screen.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-duo-210', 'PowerBook Duo 210', 1992, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'M68K', 'Motorola 68030', 25, 4096,
 'A very light laptop that slid into a desktop dock to gain extra ports and drives.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-520', 'PowerBook 520', 1994, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'M68K', 'Motorola 68LC040', 25, 4096,
 'Introduced the trackpad and built-in Ethernet to Apple laptops.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-540c', 'PowerBook 540c', 1994, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'M68K', 'Motorola 68LC040', 33, 4096,
 'The colour high-end model of the 500 series, with a trackpad and stereo speakers.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-5300', 'PowerBook 5300', 1995, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 603e', 100, 8192,
 'The first PowerPC PowerBook. Battery and build-quality problems led to a recall of early units.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-1400', 'PowerBook 1400', 1996, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 603e', 117, NULL,
 'A mid-range laptop with a swappable lid insert that owners could customise.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-3400', 'PowerBook 3400', 1997, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 603e', 180, 16384,
 'For a short time Apple billed it as the fastest laptop in the world.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-g3-wallstreet', 'PowerBook G3 (Wallstreet)', 1998, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 750 (G3)', 233, 32768,
 'A curvy black laptop with swappable drive bays and large screen options.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-g3-pismo', 'PowerBook G3 (Pismo)', 2000, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 750 (G3)', 400, 65536,
 'Added FireWire to the black G3 PowerBook, and kept two bays for batteries or drives.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-g4-12', 'PowerBook G4 (12-inch)', 2003, 1799.00, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 7455 (G4)', 867, 262144,
 'A small aluminium PowerBook that packed a G4 into a compact case.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('powerbook-g4-17', 'PowerBook G4 (17-inch)', 2003, 3299.00, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC 7455 (G4)', 1000, 524288,
 'Introduced a 17-inch widescreen to laptops, with an aluminium body and a backlit sensor that dimmed the display.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('newton-messagepad-120', 'Newton MessagePad 120', 1994, NULL, (SELECT id FROM product_family WHERE slug = 'newton'), 'ARM', 'ARM610', 20, NULL,
 'A revised MessagePad with more memory and a sturdier case.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('newton-messagepad-2000', 'Newton MessagePad 2000', 1997, NULL, (SELECT id FROM product_family WHERE slug = 'newton'), 'ARM', 'StrongARM SA-110', 162, NULL,
 'A much faster Newton with a larger screen, and handwriting recognition that had become genuinely good.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('emate-300', 'eMate 300', 1997, 799.00, (SELECT id FROM product_family WHERE slug = 'newton'), 'ARM', 'ARM710a', 25, NULL,
 'A clamshell Newton with a keyboard, made for students.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('newton-messagepad-2100', 'Newton MessagePad 2100', 1997, NULL, (SELECT id FROM product_family WHERE slug = 'newton'), 'ARM', 'StrongARM SA-110', 162, NULL,
 'The last Newton device before Apple ended the platform in 1998.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-g3-slot-loading', 'iMac G3 (Slot-loading)', 1999, 999.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'POWERPC', 'PowerPC 750 (G3)', 350, 65536,
 'A quieter, fanless redesign of the iMac with a slot-loading CD drive, priced under one thousand dollars.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-g5', 'iMac G5', 2004, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'POWERPC', 'PowerPC 970 (G5)', 1600, 262144,
 'Fitted the whole computer behind a flat panel, setting the shape of the iMac for years to come.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-aluminum-2007', 'iMac (Aluminum, 2007)', 2007, 1199.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'INTEL_X86', 'Intel Core 2 Duo', 2000, 1048576,
 'Switched the iMac to an aluminium and glass design with a thin keyboard to match.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-27-2009', 'iMac (27-inch, Late 2009)', 2009, 1699.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'INTEL_X86', 'Intel Core 2 Duo', 3060, 4194304,
 'Introduced the 27-inch iMac with a one-piece aluminium body.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-retina-5k-2014', 'iMac (Retina 5K, 27-inch, Late 2014)', 2014, 2499.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'INTEL_X86', 'Intel Core i5', 3500, 8388608,
 'One of the first desktops with a 5K display built in.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-pro', 'iMac Pro', 2017, 4999.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'INTEL_X86', 'Intel Xeon W', 3200, 33554432,
 'A space grey workstation iMac with Xeon processors and a cooling system designed for sustained loads.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-24-m3', 'iMac (24-inch, M3, 2023)', 2023, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'APPLE_SILICON', 'Apple M3', 4050, 8388608,
 'Kept the colourful thin design and moved to the M3 chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-24-m4', 'iMac (24-inch, M4, 2024)', 2024, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'APPLE_SILICON', 'Apple M4', NULL, 16777216,
 'Moved to the M4 chip and raised the base memory to 16 GB.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('imac-24-m6', 'iMac (24-inch, M6, 2026)', 2026, 1499.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'APPLE_SILICON', 'Apple M6', NULL, 16777216,
 'One of the first Macs with the M6 chip, keeping the thin, colourful design.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('emac', 'eMac', 2002, NULL, (SELECT id FROM product_family WHERE slug = 'emac'), 'POWERPC', 'PowerPC G4', 700, 131072,
 'A heavy all-in-one with a flat-faced CRT, designed for classrooms and later sold to consumers.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibook-g3-dual-usb', 'iBook G3 (Dual USB)', 2001, 1299.00, (SELECT id FROM product_family WHERE slug = 'ibook'), 'POWERPC', 'PowerPC 750 (G3)', 500, 65536,
 'A smaller, white iBook that dropped the clamshell shape and became a favourite with students.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ibook-g4', 'iBook G4', 2003, 1099.00, (SELECT id FROM product_family WHERE slug = 'ibook'), 'POWERPC', 'PowerPC G4', 800, 262144,
 'Brought the G4 processor to Apple''s consumer laptop for the first time.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-mini-early-2006', 'Mac mini (Early 2006)', 2006, 599.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'INTEL_X86', 'Intel Core Solo', 1500, 524288,
 'The first Intel Mac mini, adding Front Row media software and a remote control.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-mini-mid-2010', 'Mac mini (Mid 2010)', 2010, 699.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'INTEL_X86', 'Intel Core 2 Duo', 2400, 2097152,
 'Moved to a thin aluminium unibody with a built-in power supply and HDMI output.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-mini-2018', 'Mac mini (2018)', 2018, 799.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'INTEL_X86', 'Intel Core i3', 3600, 8388608,
 'A space grey revival of the Mac mini with quad-core processors and Thunderbolt 3.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-mini-m2', 'Mac mini (M2, 2023)', 2023, 599.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'APPLE_SILICON', 'Apple M2', 3500, 8388608,
 'Kept the familiar shape while moving to the M2 chip at a lower starting price.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-mini-m4', 'Mac mini (M4, 2024)', 2024, 599.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'APPLE_SILICON', 'Apple M4', NULL, 16777216,
 'Shrank the Mac mini to a five-inch square and added front ports.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-mini-m6', 'Mac mini (M6, 2026)', 2026, 899.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'APPLE_SILICON', 'Apple M6', NULL, 16777216,
 'The first Mac with the M6 chip, released alongside an updated Mac Studio.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-aluminum-2008', 'MacBook (13-inch, Aluminum, Late 2008)', 2008, 1299.00, (SELECT id FROM product_family WHERE slug = 'macbook'), 'INTEL_X86', 'Intel Core 2 Duo', 2000, 2097152,
 'Brought the aluminium unibody and glass trackpad to Apple''s consumer laptop.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-12-2015', 'MacBook (Retina, 12-inch, Early 2015)', 2015, 1299.00, (SELECT id FROM product_family WHERE slug = 'macbook'), 'INTEL_X86', 'Intel Core M', 1100, 8388608,
 'A fanless, very thin laptop with a single USB-C port and a new low-travel keyboard.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-neo', 'MacBook Neo', 2026, 599.00, (SELECT id FROM product_family WHERE slug = 'macbook'), 'APPLE_SILICON', 'Apple A18 Pro', NULL, 8388608,
 'Apple''s lowest-priced Mac laptop, built around a chip first used in the iPhone.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-17-2006', 'MacBook Pro (17-inch, 2006)', 2006, 2799.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'INTEL_X86', 'Intel Core Duo', 2160, 1048576,
 'The largest of the first Intel MacBook Pros, with a 17-inch screen.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-15-late-2008', 'MacBook Pro (15-inch, Late 2008)', 2008, 1999.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'INTEL_X86', 'Intel Core 2 Duo', 2400, 2097152,
 'Introduced the aluminium unibody MacBook Pro, carved from a single block of metal.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-13-2016-touch-bar', 'MacBook Pro (13-inch, 2016, Touch Bar)', 2016, 1799.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'INTEL_X86', 'Intel Core i5', 2900, 8388608,
 'Replaced the function keys with the Touch Bar strip and moved to USB-C ports only.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-16-2019', 'MacBook Pro (16-inch, 2019)', 2019, 2399.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'INTEL_X86', 'Intel Core i7', 2600, 16777216,
 'Brought back a scissor-switch keyboard and a physical Escape key, with a larger screen.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-13-m1', 'MacBook Pro (13-inch, M1, 2020)', 2020, 1299.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M1', 3200, 8388608,
 'One of the first three Macs with Apple''s own M1 chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-2021', 'MacBook Pro (14-inch, 2021)', 2021, 1999.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M1 Pro', 3200, 16777216,
 'A redesign with a mini-LED display, HDMI, an SD card slot and MagSafe, dropping the Touch Bar.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-16-2021', 'MacBook Pro (16-inch, 2021)', 2021, 2499.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M1 Pro', 3200, 16777216,
 'The larger model of the 2021 redesign, offered with the M1 Pro or M1 Max chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-2023', 'MacBook Pro (14-inch, 2023)', 2023, 1999.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M2 Pro', 3500, 16777216,
 'Updated the 14-inch model to the M2 Pro and M2 Max chips.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-m3', 'MacBook Pro (14-inch, M3, Nov 2023)', 2023, 1599.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M3', 4050, 8388608,
 'Brought the 14-inch Pro design down to a lower price with the base M3 chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-m4', 'MacBook Pro (14-inch, M4, 2024)', 2024, 1599.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M4', NULL, 16777216,
 'Moved to the M4 chip with 16 GB of memory as standard.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-m5', 'MacBook Pro (14-inch, M5, 2025)', 2025, 1599.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M5', NULL, 16777216,
 'The first Mac with the M5 chip, released alongside an M5 iPad Pro and Vision Pro.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-m5-pro', 'MacBook Pro (14-inch, M5 Pro, 2026)', 2026, 2199.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M5 Pro', NULL, NULL,
 'Brought the M5 Pro and M5 Max chips to the MacBook Pro in early 2026.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-14-m6', 'MacBook Pro (14-inch, M6, 2026)', 2026, 1999.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M6', NULL, 16777216,
 'Updated the base 14-inch MacBook Pro to the M6 chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-pro-proled-xdr-14', 'MacBook Pro with PROLED XDR Display (14-inch, 2026)', 2026, 2899.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'APPLE_SILICON', 'Apple M5 Pro', NULL, 25165824,
 'The first MacBook Pro with an OLED display, which also responds to touch.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-11-late-2010', 'MacBook Air (11-inch, Late 2010)', 2010, 999.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'INTEL_X86', 'Intel Core 2 Duo', 1400, 2097152,
 'A smaller, cheaper Air that used flash storage instead of a hard drive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-13-late-2010', 'MacBook Air (13-inch, Late 2010)', 2010, 1299.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'INTEL_X86', 'Intel Core 2 Duo', 1860, 2097152,
 'Redesigned the Air with all-flash storage and the tapered shape it kept for a decade.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-retina-2018', 'MacBook Air (Retina, 13-inch, 2018)', 2018, 1199.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'INTEL_X86', 'Intel Core i5', 1600, 8388608,
 'Finally gave the Air a Retina display, plus Touch ID and USB-C.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-15-m2', 'MacBook Air (15-inch, M2, 2023)', 2023, 1299.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'APPLE_SILICON', 'Apple M2', 3500, 8388608,
 'The first MacBook Air with a 15-inch screen.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-m3', 'MacBook Air (13-inch, M3, 2024)', 2024, 1099.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'APPLE_SILICON', 'Apple M3', 4050, 8388608,
 'Moved the Air to the M3 chip and added support for two external displays with the lid closed.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-m4', 'MacBook Air (13-inch, M4, 2025)', 2025, 999.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'APPLE_SILICON', 'Apple M4', NULL, 16777216,
 'Lowered the starting price and made 16 GB of memory standard.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('macbook-air-m5', 'MacBook Air (13-inch, M5, 2026)', 2026, 1099.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'APPLE_SILICON', 'Apple M5', NULL, 16777216,
 'Moved to the M5 chip and doubled the base storage.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-pro-2006', 'Mac Pro (2006)', 2006, 2499.00, (SELECT id FROM product_family WHERE slug = 'mac-pro'), 'INTEL_X86', '2 x Intel Xeon 5150', 2660, 1048576,
 'Completed the move to Intel with a tower holding two dual-core Xeon processors.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-pro-2019', 'Mac Pro (2019)', 2019, 5999.00, (SELECT id FROM product_family WHERE slug = 'mac-pro'), 'INTEL_X86', 'Intel Xeon W', 3500, 33554432,
 'Returned to a modular tower with a stainless steel frame and a lattice-patterned case.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-pro-2023', 'Mac Pro (M2 Ultra, 2023)', 2023, 6999.00, (SELECT id FROM product_family WHERE slug = 'mac-pro'), 'APPLE_SILICON', 'Apple M2 Ultra', 3500, 67108864,
 'Completed the move of the Mac line to Apple silicon, keeping PCIe slots for expansion cards.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-studio-2022', 'Mac Studio (M1 Max, 2022)', 2022, 1999.00, (SELECT id FROM product_family WHERE slug = 'mac-studio'), 'APPLE_SILICON', 'Apple M1 Max', 3200, 33554432,
 'Introduced a new desktop between the Mac mini and Mac Pro, offered with the M1 Max or M1 Ultra chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-studio-2023', 'Mac Studio (M2 Max, 2023)', 2023, 1999.00, (SELECT id FROM product_family WHERE slug = 'mac-studio'), 'APPLE_SILICON', 'Apple M2 Max', 3500, 33554432,
 'Updated the Mac Studio to the M2 Max and M2 Ultra chips.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-studio-2025', 'Mac Studio (M4 Max, 2025)', 2025, 1999.00, (SELECT id FROM product_family WHERE slug = 'mac-studio'), 'APPLE_SILICON', 'Apple M4 Max', NULL, 37748736,
 'Offered the M4 Max or the M3 Ultra chip, with Thunderbolt 5.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-studio-m5-max', 'Mac Studio (M5 Max, 2026)', 2026, 2499.00, (SELECT id FROM product_family WHERE slug = 'mac-studio'), 'APPLE_SILICON', 'Apple M5 Max', NULL, NULL,
 'Moved the Mac Studio to the M5 generation, at a higher starting price.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('mac-studio-m5-ultra', 'Mac Studio (M5 Ultra, 2026)', 2026, 5499.00, (SELECT id FROM product_family WHERE slug = 'mac-studio'), 'APPLE_SILICON', 'Apple M5 Ultra', NULL, NULL,
 'The most powerful Mac of 2026, configurable with up to 512 GB of unified memory.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('xserve-2002', 'Xserve (2002)', 2002, NULL, (SELECT id FROM product_family WHERE slug = 'xserve'), 'POWERPC', 'PowerPC G4', 1000, NULL,
 'Apple''s first rack-mounted server, sold with Mac OS X Server.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipod-mini', 'iPod mini', 2004, 249.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'ARM', 'PortalPlayer PP5020', NULL, NULL,
 'A small, colourful iPod with a click wheel and a tiny hard drive.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipod-nano-1st-gen', 'iPod nano (1st generation)', 2005, 199.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'ARM', 'PortalPlayer PP5021C', NULL, NULL,
 'Replaced the iPod mini with a much thinner player that stored music on flash memory.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipod-5th-gen', 'iPod (5th generation)', 2005, 299.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'ARM', 'PortalPlayer PP5021C', NULL, NULL,
 'Added a colour screen large enough to play video.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipod-classic', 'iPod classic', 2007, 249.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'ARM', 'Samsung ARM system on a chip', NULL, NULL,
 'The final hard-drive iPod, with a metal front and large storage.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipod-touch-1st-gen', 'iPod touch (1st generation)', 2007, 299.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'ARM', 'Samsung ARM11 (S5L8900)', 412, 131072,
 'Took the iPhone''s touchscreen and software and removed the phone.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipod-touch-7th-gen', 'iPod touch (7th generation)', 2019, 199.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'APPLE_SILICON', 'Apple A10 Fusion', NULL, NULL,
 'The last iPod. Apple discontinued the line in 2022.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-3g', 'iPhone 3G', 2008, NULL, (SELECT id FROM product_family WHERE slug = 'iphone'), 'ARM', 'Samsung ARM11 (S5L8900)', 412, 131072,
 'Added 3G data and GPS, and launched alongside the App Store.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-4', 'iPhone 4', 2010, NULL, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A4', NULL, NULL,
 'Introduced the Retina display and a glass and steel design.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-4s', 'iPhone 4S', 2011, NULL, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A5', NULL, NULL,
 'Launched Siri, the voice assistant.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-5', 'iPhone 5', 2012, NULL, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A6', NULL, NULL,
 'Brought a taller 4-inch screen and the small Lightning connector.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-5s', 'iPhone 5s', 2013, NULL, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A7', NULL, NULL,
 'Introduced Touch ID and the first 64-bit chip in a smartphone.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-6', 'iPhone 6', 2014, 649.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A8', NULL, NULL,
 'A larger, thinner iPhone with rounded edges, launched alongside Apple Pay.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-6s', 'iPhone 6s', 2015, 649.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A9', NULL, NULL,
 'Added 3D Touch, a pressure-sensitive screen.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-se-1st-gen', 'iPhone SE (1st generation)', 2016, 399.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A9', NULL, NULL,
 'Put current internals into the smaller 4-inch body of the iPhone 5s.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-7', 'iPhone 7', 2016, 649.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A10 Fusion', NULL, NULL,
 'Removed the headphone jack and added water resistance.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-8', 'iPhone 8', 2017, 699.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A11 Bionic', NULL, NULL,
 'Moved to a glass back to support wireless charging.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-x', 'iPhone X', 2017, 999.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A11 Bionic', NULL, NULL,
 'Dropped the home button for an edge-to-edge OLED screen and Face ID.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-xr', 'iPhone XR', 2018, 749.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A12 Bionic', NULL, NULL,
 'A more affordable Face ID iPhone with an LCD screen, sold in bright colours.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-11', 'iPhone 11', 2019, 699.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A13 Bionic', NULL, NULL,
 'Added an ultra wide camera and Night mode to the standard iPhone.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-12', 'iPhone 12', 2020, 799.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A14 Bionic', NULL, NULL,
 'Brought back flat edges, added 5G and introduced MagSafe accessories.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-13', 'iPhone 13', 2021, 799.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A15 Bionic', NULL, NULL,
 'Improved battery life and cameras, with a smaller notch.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-14', 'iPhone 14', 2022, 799.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A15 Bionic', NULL, NULL,
 'Added crash detection and emergency messaging by satellite.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-15', 'iPhone 15', 2023, 799.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A16 Bionic', NULL, NULL,
 'Switched from Lightning to USB-C and brought the Dynamic Island to the standard model.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-16', 'iPhone 16', 2024, 799.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A18', NULL, NULL,
 'Added a Camera Control button and was built for Apple Intelligence.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-16e', 'iPhone 16e', 2025, 599.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A18', NULL, NULL,
 'A lower-priced model that debuted Apple''s own C1 cellular modem.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-17', 'iPhone 17', 2025, 799.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A19', NULL, NULL,
 'Brought a 120 Hz ProMotion display to the standard iPhone and started at 256 GB of storage.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-air', 'iPhone Air', 2025, 999.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A19 Pro', NULL, NULL,
 'Apple''s thinnest iPhone at launch, with a single rear camera.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-17-pro', 'iPhone 17 Pro', 2025, 1099.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A19 Pro', NULL, NULL,
 'Moved the Pro models to an aluminium unibody with a camera plateau across the back.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-17e', 'iPhone 17e', 2026, 599.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A19', NULL, NULL,
 'The lower-priced iPhone, now with MagSafe and 256 GB of storage as standard.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-18-pro', 'iPhone 18 Pro', 2026, 1199.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A20 Pro', NULL, NULL,
 'Introduced the A20 Pro chip and a variable aperture main camera.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('iphone-duo', 'iPhone Duo', 2026, 1999.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'APPLE_SILICON', 'Apple A20 Pro', NULL, NULL,
 'Apple''s first foldable iPhone, opening into a small tablet.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-2', 'iPad 2', 2011, 499.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A5', NULL, NULL,
 'Thinner and lighter than the original, and added front and rear cameras.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-3rd-gen', 'iPad (3rd generation)', 2012, 499.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A5X', NULL, NULL,
 'The first iPad with a Retina display.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-mini-1st-gen', 'iPad mini (1st generation)', 2012, 329.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A5', NULL, NULL,
 'A smaller 7.9-inch iPad that was easy to hold in one hand.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-air-1st-gen', 'iPad Air (1st generation)', 2013, 499.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A7', NULL, NULL,
 'A much lighter full-size iPad with slimmer borders.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-pro-12-9-1st-gen', 'iPad Pro (12.9-inch, 1st generation)', 2015, 799.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A9X', NULL, NULL,
 'The first iPad Pro, with a large screen and support for the new Apple Pencil.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-10th-gen', 'iPad (10th generation)', 2022, 449.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A14 Bionic', NULL, NULL,
 'Redesigned the entry iPad with flat edges, USB-C and colourful finishes.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-pro-11-m4', 'iPad Pro (11-inch, M4)', 2024, 999.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple M4', NULL, NULL,
 'Debuted the M4 chip in a very thin iPad with a tandem OLED display.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-mini-a17-pro', 'iPad mini (A17 Pro)', 2024, 499.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A17 Pro', NULL, NULL,
 'Updated the iPad mini with a chip able to run Apple Intelligence.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-a16', 'iPad (A16)', 2025, 349.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A16', NULL, NULL,
 'Refreshed the entry iPad with the A16 chip and more storage.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-air-11-m3', 'iPad Air (11-inch, M3)', 2025, 599.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple M3', NULL, NULL,
 'Moved the iPad Air to the M3 chip.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-pro-11-m5', 'iPad Pro (11-inch, M5)', 2025, 999.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple M5', NULL, NULL,
 'Brought the M5 chip to the thin OLED iPad Pro.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-air-11-m4', 'iPad Air (11-inch, M4)', 2026, NULL, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple M4', NULL, 12582912,
 'Moved the iPad Air to the M4 chip with 12 GB of memory.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('ipad-mini-8th-gen', 'iPad mini (8th generation)', 2026, 699.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A20 Pro', NULL, NULL,
 'The first iPad mini with an OLED display.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-watch-1st-gen', 'Apple Watch (1st generation)', 2015, 349.00, (SELECT id FROM product_family WHERE slug = 'apple-watch'), 'APPLE_SILICON', 'Apple S1', NULL, NULL,
 'Apple''s first wearable, with the Digital Crown for scrolling and a heart rate sensor.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-watch-series-4', 'Apple Watch Series 4', 2018, 399.00, (SELECT id FROM product_family WHERE slug = 'apple-watch'), 'APPLE_SILICON', 'Apple S4', NULL, NULL,
 'Enlarged the display and added fall detection and an electrical heart sensor.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-watch-ultra', 'Apple Watch Ultra', 2022, 799.00, (SELECT id FROM product_family WHERE slug = 'apple-watch'), 'APPLE_SILICON', 'Apple S8', NULL, NULL,
 'A rugged titanium watch with a large battery, made for outdoor sports and diving.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-watch-series-11', 'Apple Watch Series 11', 2025, 399.00, (SELECT id FROM product_family WHERE slug = 'apple-watch'), 'APPLE_SILICON', 'Apple S10', NULL, NULL,
 'Added hypertension notifications and longer battery life.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-watch-ultra-3', 'Apple Watch Ultra 3', 2025, 799.00, (SELECT id FROM product_family WHERE slug = 'apple-watch'), 'APPLE_SILICON', 'Apple S10', NULL, NULL,
 'Added satellite messaging and a larger display to the rugged Ultra.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-watch-series-12', 'Apple Watch Series 12', 2026, 399.00, (SELECT id FROM product_family WHERE slug = 'apple-watch'), 'APPLE_SILICON', 'Apple S11', NULL, NULL,
 'Introduced the S11 chip with upgraded health sensors.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-vision-pro', 'Apple Vision Pro', 2024, 3499.00, (SELECT id FROM product_family WHERE slug = 'apple-vision'), 'APPLE_SILICON', 'Apple M2', 3500, 16777216,
 'Apple''s first spatial computer, a headset controlled with the eyes, hands and voice.', 'Apple');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary, manufacturer) VALUES
('apple-vision-pro-m5', 'Apple Vision Pro (M5)', 2025, 3499.00, (SELECT id FROM product_family WHERE slug = 'apple-vision'), 'APPLE_SILICON', 'Apple M5', NULL, NULL,
 'Updated the headset with the M5 chip and a more comfortable headband.', 'Apple');
