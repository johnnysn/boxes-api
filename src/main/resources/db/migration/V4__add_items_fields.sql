ALTER TABLE items ADD name VARCHAR(100) NOT NULL;
ALTER TABLE items ADD description VARCHAR(400);

CREATE INDEX idx_items_lower_name ON items (lower(name));