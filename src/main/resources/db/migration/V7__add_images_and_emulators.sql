-- Optional photo (freely licensed, with attribution) and in-browser emulator link per model.

ALTER TABLE apple_model ADD COLUMN image_url VARCHAR(500);
ALTER TABLE apple_model ADD COLUMN image_credit VARCHAR(300);
ALTER TABLE apple_model ADD COLUMN image_license VARCHAR(80);
ALTER TABLE apple_model ADD COLUMN image_source_url VARCHAR(500);

ALTER TABLE apple_model ADD COLUMN emulator_provider VARCHAR(60);
ALTER TABLE apple_model ADD COLUMN emulator_label VARCHAR(200);
ALTER TABLE apple_model ADD COLUMN emulator_url VARCHAR(500);
ALTER TABLE apple_model ADD COLUMN emulator_embed_url VARCHAR(500);
ALTER TABLE apple_model ADD COLUMN emulator_exact_machine BOOLEAN;
