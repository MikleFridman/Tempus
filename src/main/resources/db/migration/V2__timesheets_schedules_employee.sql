CREATE TABLE schedules_employees
(
    employee_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL
);

ALTER TABLE timesheets
    ADD month INTEGER;

ALTER TABLE timesheets
    ADD year INTEGER;

ALTER TABLE timesheets
    ALTER COLUMN month SET NOT NULL;

ALTER TABLE timesheets
    ALTER COLUMN year SET NOT NULL;

ALTER TABLE schedules_employees
    ADD CONSTRAINT fk_schemp_on_employee FOREIGN KEY (employee_id) REFERENCES employees (id);

ALTER TABLE schedules_employees
    ADD CONSTRAINT fk_schemp_on_schedule FOREIGN KEY (schedule_id) REFERENCES schedules (id);