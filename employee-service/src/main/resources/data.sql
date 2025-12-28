-- Insert employees with conflict handling
INSERT INTO employee (name, email, team, manager_id)
VALUES ('John Manager', 'john.manager@company.com', 'Engineering', NULL)
    ON CONFLICT (email) DO NOTHING;

INSERT INTO employee (name, email, team, manager_id)
VALUES
    ('Sara Johnson', 'sara.johnson@company.com', 'Engineering', 1),
    ('Amine Hassan', 'amine.hassan@company.com', 'Engineering', 1),
    ('Emily Chen', 'emily.chen@company.com', 'Marketing', 1),
    ('Michael Brown', 'michael.brown@company.com', 'Engineering', 1),
    ('Lisa Anderson', 'lisa.anderson@company.com', 'DevOps', 1),
    ('David Wilson', 'david.wilson@company.com', 'DevOps', 1),
    ('Maria Garcia', 'maria.garcia@company.com', 'Engineering', 1),
    ('James Taylor', 'james.taylor@company.com', 'Marketing', 1),
    ('Anna White', 'anna.white@company.com', 'QA', 1)
    ON CONFLICT (email) DO NOTHING;