-- Insert holidays with conflict handling
INSERT INTO holiday (date, description) VALUES ('2025-01-01', 'New Year''s Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-05-01', 'Labor Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-07-30', 'Throne Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-08-14', 'Oued Ed-Dahab Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-08-14', 'Oued Ed-Dahab Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-08-20', 'Revolution Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-08-21', 'Youth Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-11-06', 'Green March') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-11-18', 'Independence Day') ON CONFLICT (date) DO NOTHING;
INSERT INTO holiday (date, description) VALUES ('2025-12-25', 'Christmas Day') ON CONFLICT (date) DO NOTHING;