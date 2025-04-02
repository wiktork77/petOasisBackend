CREATE UNIQUE INDEX IX_pk_favoriteanimals ON favorite_animals (person_id, animal_id);

ALTER TABLE badge
ALTER
COLUMN badge_name TYPE VARCHAR(64) USING (badge_name::VARCHAR(64));

ALTER TABLE comment
ALTER
COLUMN content TYPE VARCHAR(2048) USING (content::VARCHAR(2048));