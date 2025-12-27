-- ==========================================
-- LEAVEFLOW - PostgreSQL Database Initialization
-- ==========================================
-- Person A databases
-- Person B databases
-- ==========================================

-- Create databases for Person A
CREATE DATABASE employeedb;
CREATE DATABASE calendardb;

-- Create databases for Person B
CREATE DATABASE leavedb;
CREATE DATABASE notificationdb;

-- Grant privileges (optional, postgres user already has full access)
GRANT ALL PRIVILEGES ON DATABASE employeedb TO postgres;
GRANT ALL PRIVILEGES ON DATABASE calendardb TO postgres;
GRANT ALL PRIVILEGES ON DATABASE leavedb TO postgres;
GRANT ALL PRIVILEGES ON DATABASE notificationdb TO postgres;

