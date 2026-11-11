-- Seed catalog. Facts kept to what we are confident about; unknown values are NULL.
-- Clock speeds are rounded to the nearest MHz. Summaries are original text.

INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('apple-i', 'Apple I', 1976, 666.66, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', 'MOS 6502', 1, 4,
 'Sold mostly as a bare circuit board, buyers supplied their own case, keyboard and display. It was the first product of the young company and is now a prized collector''s item.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('apple-ii', 'Apple II', 1977, 1298.00, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', 'MOS 6502', 1, 4,
 'A complete, ready-to-use home computer with a plastic case, built-in keyboard and colour graphics. Its open expansion slots and the VisiCalc spreadsheet made it a commercial hit.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('apple-iie', 'Apple IIe', 1983, 1395.00, (SELECT id FROM product_family WHERE slug = 'apple-i-ii'), 'MOS_6502', 'MOS 6502', 1, 64,
 'The longest-lived member of the Apple II family. It added lowercase text and 64 KB of memory as standard and stayed on sale for about a decade.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('lisa', 'Lisa', 1983, 9995.00, (SELECT id FROM product_family WHERE slug = 'lisa'), 'M68K', 'Motorola 68000', 5, 1024,
 'One of the first personal computers sold with a mouse and a graphical desktop. Its very high price limited sales, but many of its ideas carried over to the Macintosh.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-128k', 'Macintosh 128K', 1984, 2495.00, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 8, 128,
 'The original Macintosh brought the mouse-driven graphical interface to a much wider audience in a compact all-in-one case with a built-in 9-inch screen.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-512k', 'Macintosh 512K', 1984, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 8, 512,
 'Nicknamed the Fat Mac, it quadrupled the memory of the original model, making larger documents and more capable software practical.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-plus', 'Macintosh Plus', 1986, 2599.00, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 8, 1024,
 'Added a SCSI port for external hard drives and shipped with 1 MB of memory. It remained in production for several years as a dependable workhorse.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-se', 'Macintosh SE', 1987, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 8, 1024,
 'A compact Mac with an internal expansion slot and room for an internal hard disk, plus a cooling fan that earlier compact models lacked.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-ii', 'Macintosh II', 1987, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68020', 16, 1024,
 'The first modular Mac, with a separate monitor, expansion slots and support for colour. It opened the platform to professional graphics and publishing work.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-se-30', 'Macintosh SE/30', 1989, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68030', 16, 1024,
 'Packed the power of a 68030 desktop into the classic compact case. Many enthusiasts consider it the best compact Mac ever made.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-portable', 'Macintosh Portable', 1989, 6500.00, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 16, 1024,
 'Apple''s first battery-powered Mac. It had a sharp active-matrix screen but was heavy and expensive, and it was soon replaced by the PowerBook.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-classic', 'Macintosh Classic', 1990, 999.00, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68000', 8, 1024,
 'An affordable compact Mac that brought the price of entry below one thousand dollars, aimed at schools and first-time buyers.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macintosh-lc', 'Macintosh LC', 1990, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68020', 16, 2048,
 'A low-cost colour Mac in a slim pizza-box case. It became very popular in classrooms.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('powerbook-100', 'PowerBook 100', 1991, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'M68K', 'Motorola 68000', 16, 2048,
 'Part of the first PowerBook range, which set the modern laptop layout with the keyboard pushed back and a pointing device in front of it.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('powerbook-170', 'PowerBook 170', 1991, NULL, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'M68K', 'Motorola 68030', 25, 4096,
 'The high-end model of the first PowerBook range, offering an active-matrix display and a 68030 processor with a maths coprocessor.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('quadra-700', 'Macintosh Quadra 700', 1991, NULL, (SELECT id FROM product_family WHERE slug = 'macintosh'), 'M68K', 'Motorola 68040', 25, 4096,
 'One of the first Macs with the 68040 processor, in a compact tower case aimed at demanding professional users.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('newton-messagepad', 'Newton MessagePad', 1993, 699.00, (SELECT id FROM product_family WHERE slug = 'newton'), 'ARM', 'ARM610', 20, 640,
 'The first Newton device, a handheld computer that tried to read handwriting. Its recognition problems became famous, but it was an early example of a pocket computer.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('power-macintosh-6100', 'Power Macintosh 6100', 1994, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 601', 60, 8192,
 'Among the first Macs with a PowerPC processor. An emulator let it run existing 68k software during the transition.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('power-macintosh-g3', 'Power Macintosh G3', 1997, NULL, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 750 (G3)', 233, 32768,
 'Introduced the G3 processor to Apple''s desktops and offered strong performance at a time when the company was rebuilding its product line.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('imac-g3', 'iMac G3', 1998, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'POWERPC', 'PowerPC 750 (G3)', 233, 32768,
 'A translucent all-in-one designed for getting online easily. It dropped the floppy drive and legacy ports in favour of USB and helped revive Apple.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('ibook-g3', 'iBook G3', 1999, 1599.00, (SELECT id FROM product_family WHERE slug = 'ibook'), 'POWERPC', 'PowerPC 750 (G3)', 300, 32768,
 'A colourful clamshell laptop with a built-in handle. It was built with antennas for Apple''s optional AirPort card, helping popularise wireless networking on laptops.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('power-mac-g4-cube', 'Power Mac G4 Cube', 2000, 1799.00, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC G4 (7400)', 450, 65536,
 'A fanless desktop in a clear acrylic cube. It is celebrated for its design but sold poorly and was discontinued after about a year.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('ipod-1st-gen', 'iPod (1st generation)', 2001, 399.00, (SELECT id FROM product_family WHERE slug = 'ipod'), 'ARM', 'PortalPlayer PP5002', NULL, 32768,
 'A pocket music player with a 5 GB hard drive and a mechanical scroll wheel, sold with the promise of a thousand songs in your pocket.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('powerbook-g4-titanium', 'PowerBook G4 Titanium', 2001, 2599.00, (SELECT id FROM product_family WHERE slug = 'powerbook'), 'POWERPC', 'PowerPC G4', 400, 131072,
 'A slim laptop with a titanium shell and a wide screen, notably thin for a professional machine of its time.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('imac-g4', 'iMac G4', 2002, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'POWERPC', 'PowerPC G4', 700, 131072,
 'Its flat-panel display floated on a chrome arm above a dome-shaped base, earning it the nickname the lamp.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('power-mac-g5', 'Power Mac G5', 2003, 1999.00, (SELECT id FROM product_family WHERE slug = 'power-macintosh'), 'POWERPC', 'PowerPC 970 (G5)', 1600, 262144,
 'Apple''s first 64-bit desktop, housed in an aluminium tower with a perforated front. It was the last PowerPC tower before the switch to Intel.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('mac-mini-g4', 'Mac mini G4', 2005, 499.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'POWERPC', 'PowerPC G4', 1250, 262144,
 'A tiny desktop sold without keyboard, mouse or display, aimed at people switching from Windows PCs.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('imac-core-duo', 'iMac (Early 2006)', 2006, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'INTEL_X86', 'Intel Core Duo', 1830, 524288,
 'One of the first two Macs with Intel processors, keeping the design of the G5 iMac while delivering a large jump in speed.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macbook-pro-15-2006', 'MacBook Pro (15-inch, Early 2006)', 2006, 1999.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'INTEL_X86', 'Intel Core Duo', 1830, 524288,
 'The first Intel laptop from Apple. It introduced the MagSafe power connector, which detaches safely if someone trips over the cable.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macbook-2006', 'MacBook (2006)', 2006, 1099.00, (SELECT id FROM product_family WHERE slug = 'macbook'), 'INTEL_X86', 'Intel Core Duo', 1830, 524288,
 'A consumer laptop in white or black plastic that replaced both the iBook and the small PowerBook.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('iphone', 'iPhone', 2007, 499.00, (SELECT id FROM product_family WHERE slug = 'iphone'), 'ARM', 'Samsung ARM11 (S5L8900)', 412, 131072,
 'Combined a phone, a music player and an internet device behind a multi-touch screen with no physical keyboard.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macbook-air-2008', 'MacBook Air (2008)', 2008, 1799.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'INTEL_X86', 'Intel Core 2 Duo', 1600, 2097152,
 'Famously introduced by being pulled from an office envelope. It dropped the optical drive and most ports to be extremely thin.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('ipad', 'iPad', 2010, 499.00, (SELECT id FROM product_family WHERE slug = 'ipad'), 'APPLE_SILICON', 'Apple A4', 1000, 262144,
 'The first iPad brought a large touchscreen to a device for reading, browsing and media. It was also the first product with an Apple-designed system on a chip.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macbook-pro-retina-15-2012', 'MacBook Pro with Retina display (15-inch, 2012)', 2012, 2199.00, (SELECT id FROM product_family WHERE slug = 'macbook-pro'), 'INTEL_X86', 'Intel Core i7', 2300, 8388608,
 'Brought a very high resolution display to the Mac and a thinner body without an optical drive.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('mac-pro-2013', 'Mac Pro (Late 2013)', 2013, 2999.00, (SELECT id FROM product_family WHERE slug = 'mac-pro'), 'INTEL_X86', 'Intel Xeon E5', 3700, 12582912,
 'A compact cylinder built around a central thermal core, relying on external expansion instead of internal slots.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macbook-air-m1', 'MacBook Air (M1, 2020)', 2020, 999.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'APPLE_SILICON', 'Apple M1', 3200, 8388608,
 'One of the first Macs with Apple''s own M1 chip. It runs without a fan while offering long battery life and strong performance.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('mac-mini-m1', 'Mac mini (M1, 2020)', 2020, 699.00, (SELECT id FROM product_family WHERE slug = 'mac-mini'), 'APPLE_SILICON', 'Apple M1', 3200, 8388608,
 'Launched alongside the M1 laptops as the cheapest way to try Apple silicon on the desktop.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('imac-24-m1', 'iMac (24-inch, M1, 2021)', 2021, 1299.00, (SELECT id FROM product_family WHERE slug = 'imac'), 'APPLE_SILICON', 'Apple M1', 3200, 8388608,
 'A very thin all-in-one offered in a range of colours, a nod to the original iMac''s playful spirit.');
INSERT INTO apple_model (slug, name, release_year, launch_price_usd, family_id, cpu_architecture, cpu_name, clock_speed_mhz, base_ram_kb, summary) VALUES
('macbook-air-m2', 'MacBook Air (M2, 2022)', 2022, 1199.00, (SELECT id FROM product_family WHERE slug = 'macbook-air'), 'APPLE_SILICON', 'Apple M2', 3500, 8388608,
 'Redesigned the Air with a flat, uniform-thickness body, a larger display and the return of MagSafe charging.');
