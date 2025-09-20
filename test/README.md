# SADAD Auth Starter (Spring Boot + Oracle + JWT)

## What you get
- JWT login/register/refresh
- Roles: ENTRY, APPROVER, RELEASE, ADMIN
- Oracle-ready JPA entities (sequences), schema.sql + data.sql
- Sample secured endpoints

## How to run
1. Create Oracle user/schema and run `schema.sql`, then `data.sql`.
2. Update `src/main/resources/application.yml` with your Oracle credentials and a Base64 secret for `app.jwt.secret`.
3. `mvn spring-boot:run`

## Endpoints
- POST /api/auth/register  `{username,password, role?}`
- POST /api/auth/login     `{username,password}`
- POST /api/auth/refresh   `{refreshToken}`
- GET  /api/auth/me
- GET  /api/public/hello
- GET  /api/entry/hello (ENTRY/ADMIN)
- GET  /api/approve/hello (APPROVER/ADMIN)
- GET  /api/release/hello (RELEASE/ADMIN)
- GET  /api/admin/hello (ADMIN)

## Notes
- Oracle JDBC: add `ojdbc11` via Maven Central or Oracle Maven repo.
- Default JPA `ddl-auto=update` can create tables, but `schema.sql` is provided for full control.
