-- postgresql script

-- drop tables if they exist
DROP TABLE IF EXISTS titles;
DROP TABLE IF EXISTS salaries;
DROP TABLE IF EXISTS dept_manager;
DROP TABLE IF EXISTS dept_emp;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS departments;

-- create a table of departments
CREATE TABLE departments
(
    dept_no   CHAR(4)     NOT NULL,
    dept_name VARCHAR(40) NOT NULL,
    PRIMARY KEY (dept_no),
    CONSTRAINT ux_d_dept_name UNIQUE (dept_name)
);

-- create a table of employees
CREATE TABLE employees
(
    emp_no     INT         NOT NULL,
    birth_date DATE        NOT NULL,
    first_name VARCHAR(14) NOT NULL,
    last_name  VARCHAR(16) NOT NULL,
    hire_date  DATE        NOT NULL,
    PRIMARY KEY (emp_no)
);

-- create a table of department-employee relationships
CREATE TABLE dept_emp
(
    emp_no    INT     NOT NULL,
    dept_no   CHAR(4) NOT NULL,
    from_date DATE    NOT NULL,
    to_date   DATE    NOT NULL,
    PRIMARY KEY (emp_no, dept_no),
    CONSTRAINT fk_de_employees_emp_no FOREIGN KEY (emp_no) REFERENCES employees (emp_no) ON DELETE CASCADE,
    CONSTRAINT fk_de_departments_dept_no FOREIGN KEY (dept_no) REFERENCES departments (dept_no) ON DELETE CASCADE
);
CREATE INDEX ix_de_emp_no ON dept_emp (emp_no);
CREATE INDEX ix_de_dept_no ON dept_emp (dept_no);

-- create a table of department-manager relationships
CREATE TABLE dept_manager
(
    emp_no    INT     NOT NULL,
    dept_no   CHAR(4) NOT NULL,
    from_date DATE    NOT NULL,
    to_date   DATE    NOT NULL,
    PRIMARY KEY (emp_no, dept_no),
    CONSTRAINT fk_dm_employees_emp_no FOREIGN KEY (emp_no) REFERENCES employees (emp_no) ON DELETE CASCADE,
    CONSTRAINT fk_dm_departments_dept_no FOREIGN KEY (dept_no) REFERENCES departments (dept_no) ON DELETE CASCADE
);
CREATE INDEX ix_dm_emp_no ON dept_manager (emp_no);
CREATE INDEX ix_dm_dept_no ON dept_manager (dept_no);

-- create a table of salaries
CREATE TABLE salaries
(
    emp_no    INT  NOT NULL,
    salary    INT  NOT NULL,
    from_date DATE NOT NULL,
    to_date   DATE NOT NULL,
    PRIMARY KEY (emp_no, from_date),
    CONSTRAINT fk_s_employees_emp_no FOREIGN KEY (emp_no) REFERENCES employees (emp_no) ON DELETE CASCADE
);
CREATE INDEX ix_s_emp_no ON salaries (emp_no);

-- create a table of titles
CREATE TABLE titles
(
    emp_no    INT         NOT NULL,
    title     VARCHAR(50) NOT NULL,
    from_date DATE        NOT NULL,
    to_date   DATE        NOT NULL,
    PRIMARY KEY (emp_no, from_date),
    CONSTRAINT fk_t_employees_emp_no FOREIGN KEY (emp_no) REFERENCES employees (emp_no) ON DELETE CASCADE
);
CREATE INDEX ix_t_emp_no ON titles (emp_no);