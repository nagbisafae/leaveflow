-- Reset sequence to start from 1
ALTER TABLE employee ALTER COLUMN id RESTART WITH 1;

-- Insert manager first (will get ID = 1)
INSERT INTO employee (name, email, team, manager_id) VALUES ('John Manager', 'john.manager@company.com', 'Backend', NULL);

-- Insert other employees (they can reference managerId = 1)
INSERT INTO employee (name, email, team, manager_id) VALUES ('Sara Johnson', 'sara.johnson@company.com', 'Backend', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('Amine Hassan', 'amine.hassan@company.com', 'Backend', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('Emily Chen', 'emily.chen@company.com', 'Frontend', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('Michael Brown', 'michael.brown@company.com', 'Frontend', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('Lisa Anderson', 'lisa.anderson@company.com', 'DevOps', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('David Wilson', 'david.wilson@company.com', 'DevOps', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('Maria Garcia', 'maria.garcia@company.com', 'Backend', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('James Taylor', 'james.taylor@company.com', 'Frontend', 1);
INSERT INTO employee (name, email, team, manager_id) VALUES ('Anna White', 'anna.white@company.com', 'QA', 1);