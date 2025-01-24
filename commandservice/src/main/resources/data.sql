CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    dob DATE,
    role VARCHAR(255)
);

INSERT INTO users (id, first_name, last_name, email, password, dob, role) VALUES
  ('1c9d0b8b-bd2b-4d54-a89f-3f7a4b1b5d7e', 'John', 'Doe', 'john.doe@example.com', '$2a$10$R40/wjvfvNB0wY2hMTnli.hTHzoBqq.lbLZBQf5bJ3OBYI/NIvBAC', '1985-07-12', 'ADMINISTRATOR'),
  ('2d9f0a1b-ad3c-45bc-8a1e-4c7a7a2b3f8e', 'Jane', 'Smith', 'jane.smith@example.com', '$2a$10$R40/wjvfvNB0wY2hMTnli.hTHzoBqq.lbLZBQf5bJ3OBYI/NIvBAC', '1990-02-15', 'APPLICANT'),
  ('3f8e1c7b-ce4d-42cd-9a5e-7f8a3a2b4f9d', 'Alice', 'Brown', 'alice.brown@example.com', '$2a$10$R40/wjvfvNB0wY2hMTnli.hTHzoBqq.lbLZBQf5bJ3OBYI/NIvBAC', '1995-01-02', 'APPLICANT')
ON CONFLICT (id) DO NOTHING;