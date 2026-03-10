# ReliaQuest's Entry-Level Java Challenge: Miguel Mateo Osorio Vela

My Solution directory:

```
api/src/main/java/com/challenge/api/
├── controller/
│   └── EmployeeController.java       # REST endpoints (GET all, GET by UUID, POST)
├── exception/
│   ├── EmployeeAlreadyExistsException.java
│   └── EmployeeNotFoundException.java
├── model/
│   ├── Employee.java                 # binding contract
│   ├── EmployeeImpl.java             # Concrete implementation
│   └── EmployeeModelAssembler.java   # HATEOAS link builder
├── security/
│   ├── ApiKeyAuthFilter.java         # Validates X-API-Key header(Authentication)
│   └── SecurityConfig.java          # Spring Security filter chain configuration(Authorization)
└── service/
    └── EmployeeService.java          # Business logic + in-memory data store
```

## Architecture Description

This solution is a stateless and secure REST API built on **Spring Boot ** using the following resources as a guide:

- **Spring MVC (model-view-controller)** — https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html
- **Spring HATEOAS** — https://spring.io/guides/tutorials/rest
- **Spring Security** — https://docs.spring.io/spring-security/reference/servlet/architecture.html


## Usage

Start the application before running any commands:
```bash
./gradlew bootRun
```

All requests require the `X-API-Key` header. The default dev key is `dev-only-insecure-key` (set via `API_KEY` in application.yml).

---

### Security — Request without API key (expect 401)
```bash
curl -i http://localhost:8080/api/v1/employee
```
<details>
<summary><strong>Expected output</strong></summary>

```
HTTP/1.1 401 
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 10 Mar 2026 20:03:46 GMT

{"timestamp":"2026-03-10T20:03:46.186+00:00","status":401,"error":"Unauthorized","message":"Unauthorized","path":"/api/v1/employee"}
```

</details>

---

### Security — Request with invalid API key (expect 401)
```bash
curl -i -H "X-API-Key: wrong-key" http://localhost:8080/api/v1/employee
```
<details>
<summary><strong>Expected output</strong></summary>

```
HTTP/1.1 401 
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 10 Mar 2026 20:04:47 GMT

{"timestamp":"2026-03-10T20:04:47.855+00:00","status":401,"error":"Unauthorized","message":"Unauthorized","path":"/api/v1/employee"}
```

</details>

---

### GET all employees
```bash
curl -s -H "X-API-Key: dev-only-insecure-key" http://localhost:8080/api/v1/employee | jq
```
<details>
<summary><strong>Expected output</strong></summary>

```
{
  "_embedded": {
    "employeeImplList": [
      {
        "uuid": "550e8400-e29b-41d4-a716-446655440001",
        "firstName": "Alice",
        "lastName": "Johnson",
        "fullName": "Alice Johnson",
        "salary": 98000,
        "age": 30,
        "jobTitle": "Senior Engineer",
        "email": "alice.johnson@company.com",
        "contractHireDate": "2020-01-15T10:00:00Z",
        "contractTerminationDate": null,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/v1/employee/550e8400-e29b-41d4-a716-446655440001"
          },
          "All employees": {
            "href": "http://localhost:8080/api/v1/employee"
          }
        }
      },
      {
        "uuid": "550e8400-e29b-41d4-a716-446655440002",
        "firstName": "Bob",
        "lastName": "Smith",
        "fullName": "Bob Smith",
        "salary": 75000,
        "age": 28,
        "jobTitle": "Sales Manager",
        "email": "bob.smith@company.com",
        "contractHireDate": "2021-03-20T10:00:00Z",
        "contractTerminationDate": null,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/v1/employee/550e8400-e29b-41d4-a716-446655440002"
          },
          "All employees": {
            "href": "http://localhost:8080/api/v1/employee"
          }
        }
      },
      {
        "uuid": "550e8400-e29b-41d4-a716-446655440003",
        "firstName": "Carol",
        "lastName": "White",
        "fullName": "Carol White",
        "salary": 85000,
        "age": 35,
        "jobTitle": "HR Director",
        "email": "carol.white@company.com",
        "contractHireDate": "2019-06-10T10:00:00Z",
        "contractTerminationDate": null,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/v1/employee/550e8400-e29b-41d4-a716-446655440003"
          },
          "All employees": {
            "href": "http://localhost:8080/api/v1/employee"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/employee"
    }
  }
}
```

</details>

---

### GET employee by UUID
```bash
curl -s -H "X-API-Key: dev-only-insecure-key" \
  http://localhost:8080/api/v1/employee/550e8400-e29b-41d4-a716-446655440001 | jq
```
<details>
<summary><strong>Expected output</strong></summary>

```
{
  "uuid": "550e8400-e29b-41d4-a716-446655440001",
  "firstName": "Alice",
  "lastName": "Johnson",
  "fullName": "Alice Johnson",
  "salary": 98000,
  "age": 30,
  "jobTitle": "Senior Engineer",
  "email": "alice.johnson@company.com",
  "contractHireDate": "2020-01-15T10:00:00Z",
  "contractTerminationDate": null,
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/employee/550e8400-e29b-41d4-a716-446655440001"
    },
    "All employees": {
      "href": "http://localhost:8080/api/v1/employee"
    }
  }
}
```

</details>

---

### GET employee by UUID — not found (expect 404)
```bash
curl -i -H "X-API-Key: dev-only-insecure-key" \
  http://localhost:8080/api/v1/employee/00000000-0000-0000-0000-000000000000
```
<details>
<summary><strong>Expected output</strong></summary>

```
HTTP/1.1 404 
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 10 Mar 2026 20:08:01 GMT

{"timestamp":"2026-03-10T20:08:01.503+00:00","status":404,"error":"Not Found","message":"Could not find employee with UUID: 00000000-0000-0000-0000-000000000000","path":"/api/v1/employee/00000000-0000-0000-0000-000000000000"}
```

</details>

---

### POST create a new employee
```bash
curl -s -X POST \
  -H "X-API-Key: dev-only-insecure-key" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Juan",
    "lastName": "Perez",
    "salary": 90000,
    "age": 32,
    "jobTitle": "Product Manager",
    "email": "juanperez@company.com",
    "contractHireDate": "2024-06-01T09:00:00Z"
  }' \
  http://localhost:8080/api/v1/employee | jq
```
<details>
<summary><strong>Expected output</strong></summary>

```
{
  "uuid": "657bfd78-54d3-4aa2-b1f9-a5a6f05c9405",
  "firstName": "Juan",
  "lastName": "Perez",
  "fullName": "Juan Perez",
  "salary": 90000,
  "age": 32,
  "jobTitle": "Product Manager",
  "email": "juanperez@company.com",
  "contractHireDate": "2024-06-01T09:00:00Z",
  "contractTerminationDate": null,
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/employee/657bfd78-54d3-4aa2-b1f9-a5a6f05c9405"
    },
    "All employees": {
      "href": "http://localhost:8080/api/v1/employee"
    }
  }
}
```

</details>

---

### POST create employee with duplicate UUID (expect 409)
```bash
curl -i -X POST \
  -H "X-API-Key: dev-only-insecure-key" \
  -H "Content-Type: application/json" \
  -d '{
    "uuid": "550e8400-e29b-41d4-a716-446655440001",
    "firstName": "Duplicate",
    "lastName": "User",
    "salary": 50000,
    "age": 25,
    "jobTitle": "Intern",
    "email": "dup@company.com",
    "contractHireDate": "2024-01-01T00:00:00Z"
  }' \
  http://localhost:8080/api/v1/employee
```
<details>
<summary><strong>Expected output</strong></summary>

```
HTTP/1.1 409 
X-Content-Type-Options: nosniff
X-XSS-Protection: 0
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Pragma: no-cache
Expires: 0
X-Frame-Options: DENY
Content-Type: application/json
Transfer-Encoding: chunked
Date: Tue, 10 Mar 2026 20:11:31 GMT

{"timestamp":"2026-03-10T20:11:31.256+00:00","status":409,"error":"Conflict","message":"Employee with UUID already exists: 550e8400-e29b-41d4-a716-446655440001","path":"/api/v1/employee"}
```

</details>

## Security Model

This API uses **API key authentication** via custom Spring Security filter.
https://docs.spring.io/spring-security/reference/servlet/architecture.html
Every request must include a pre-shared secret in the `X-API-Key` header. I considered this pattern is well-suited for machine-to-machine integrations like webhooks where a single trusted consumer holds a shared secret.

---

### Real-World Considerations for Future Features

| Concern | Recommendation |
|---|---|
| **Secret storage** | Store the API key in a secrets manager (like AWS Secrets Manager). Retrieve it at startup via the `API_KEY` environment variable and never hardcode it in source code |
| **Rate limiting** | Add rate limiting to prevent brute-force key guessing |

---

# INSTRUCTIONS:

Please keep the following in mind while working on this challenge:
* Code implementations will not be graded for **correctness** but rather on practicality
* Articulate clear and concise design methodologies, if necessary
* Use clean coding etiquette
  * E.g. avoid liberal use of new-lines, odd variable and method names, random indentation, etc...
* Test cases are not required

## Problem Statement

Your employer has recently purchased a license to top-tier SaaS platform, Employees-R-US, to off-load all employee management responsibilities.
Unfortunately, your company's product has an existing employee management solution that is tightly coupled to other services and therefore 
cannot be replaced whole-cloth. Product and Development leads in your department have decided it would be best to interface
the existing employee management solution with the commercial offering from Employees-R-US for the time being until all employees can be
migrated to the new SaaS platform.

Your ask is to expose employee information as a protected, secure REST API for consumption by Employees-R-US web hooks.
The initial REST API will consist of 3 endpoints, listed in the following section. If for any reason the implementation 
of an endpoint is problematic, the team lead will accept **pseudo-code** and a pertinent description (e.g. java-doc) of intent.

Good luck!

## Endpoints to implement (API module)

_See `com.challenge.api.controller.EmployeeController` for details._

getAllEmployees()

    output - list of employees
    description - this should return all employees, unfiltered

getEmployeeByUuid(...)

    path variable - employee UUID
    output - employee
    description - this should return a single employee based on the provided employee UUID

createEmployee(...)

    request body - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

## Code Formatting

This project utilizes Gradle plugin [Diffplug Spotless](https://github.com/diffplug/spotless/tree/main/plugin-gradle) to enforce format
and style guidelines with every build.

To format code according to style guidelines, you can run **spotlessApply** task.
`./gradlew spotlessApply`

The spotless plugin will also execute check-and-validation tasks as part of the gradle **build** task.
`./gradlew build`
