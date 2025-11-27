# 📚 Student Notes: Audit Log Service

Welcome to the comprehensive guide for understanding the **Audit Log Service**! This document is designed to help beginners understand the project structure, code, and concepts used in this Spring Boot microservice.

---

## 📋 Table of Contents

1. [Project Overview](#project-overview)
2. [What is a Microservice?](#what-is-a-microservice)
3. [Tech Stack Explained](#tech-stack-explained)
4. [Project Structure](#project-structure)
5. [Understanding the Code](#understanding-the-code)
   - [Main Application](#main-application)
   - [Entity Classes](#entity-classes)
   - [Repository/DAO Layer](#repositorydao-layer)
   - [Service Layer](#service-layer)
   - [REST Controller](#rest-controller)
   - [Security Configuration](#security-configuration)
6. [Configuration Files](#configuration-files)
7. [Docker and Containerization](#docker-and-containerization)
8. [How to Run the Project](#how-to-run-the-project)
9. [Key Concepts Glossary](#key-concepts-glossary)

---

## 🎯 Project Overview

### What Does This Service Do?

The **Audit Log Service** is a microservice that records all significant events and actions in a system. Think of it like a "security camera" for your application - it keeps track of:

- **Who** did something (user email)
- **What** they did (action type)
- **When** they did it (timestamp)
- **The result** (success, error, or warning)
- **Additional details** (description, IP address)

### Why is This Important?

Audit logs are crucial for:
- **Security**: Detecting unauthorized access or suspicious activities
- **Compliance**: Meeting legal requirements for record-keeping
- **Debugging**: Understanding what happened when something goes wrong
- **Accountability**: Tracking who made what changes

---

## 🔧 What is a Microservice?

A **microservice** is a small, independent application that does ONE thing well. Instead of having one giant application (called a "monolith"), you break it into smaller pieces that:

- Can be developed independently
- Can be deployed separately
- Can be scaled individually
- Communicate with each other via APIs

```
Traditional Monolith:          Microservices Architecture:
┌─────────────────────┐        ┌─────────┐  ┌─────────┐  ┌─────────┐
│                     │        │  User   │  │  Audit  │  │  Vote   │
│   Entire App        │   →    │ Service │  │ Service │  │ Service │
│   (one big piece)   │        └────┬────┘  └────┬────┘  └────┬────┘
│                     │             │            │            │
└─────────────────────┘             └────────────┴────────────┘
                                        (communicate via APIs)
```

---

## ⚙️ Tech Stack Explained

### 1. Spring Boot

**Spring Boot** is a Java framework that makes it easy to create stand-alone, production-grade Spring applications. It's like a "starter kit" that provides:

- Auto-configuration (sensible defaults)
- Embedded web server (no need to install Tomcat separately)
- Easy dependency management

```java
// This single annotation enables all Spring Boot magic!
@SpringBootApplication
public class AuditLogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuditLogServiceApplication.class, args);
    }
}
```

### 2. Spring Data JPA

**JPA** (Java Persistence API) is a way to save Java objects to a database. **Spring Data JPA** makes this even easier by:

- Automatically creating SQL queries from method names
- Reducing boilerplate code
- Providing ready-to-use repository methods

### 3. PostgreSQL

**PostgreSQL** is a powerful, open-source relational database. It stores data in tables with rows and columns, like an Excel spreadsheet.

### 4. Spring Security + JWT

**Spring Security** protects your application from unauthorized access.

**JWT** (JSON Web Token) is a way to securely transmit information between parties. It's like a digital ID card that proves who you are.

```
JWT Structure:
┌─────────────┐.┌─────────────┐.┌─────────────┐
│   Header    │.│   Payload   │.│  Signature  │
│ (algorithm) │.│ (user data) │.│  (verify)   │
└─────────────┘.└─────────────┘.└─────────────┘
```

### 5. Lombok

**Lombok** is a library that reduces boilerplate code. Instead of writing getters, setters, and constructors manually, you use annotations:

```java
// Without Lombok - lots of code!
public class User {
    private String name;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    // ... more methods
}

// With Lombok - just one annotation!
@Data
public class User {
    private String name;
}
```

### 6. Spring Cloud (Eureka Client, OpenFeign)

**Eureka** is a service discovery tool. It's like a phone book for microservices - services register themselves, and other services can find them.

**OpenFeign** makes it easy to call other microservices using simple interface declarations.

---

## 📁 Project Structure

Here's how the project is organized:

```
lgvt-audit-log-service/
├── .devcontainer/              # Docker development environment setup
│   ├── devcontainer.json       # VS Code dev container configuration
│   ├── docker-compose.yml      # Docker compose for development
│   └── Dockerfile              # Development container image
├── audit-log-service/          # Main application code
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/lgvt/audit_log_service/
│   │   │   │   ├── AuditLogServiceApplication.java  # Entry point
│   │   │   │   ├── dao/                # Database access layer
│   │   │   │   │   └── AuditDAOImpl.java
│   │   │   │   ├── entity/             # Data models
│   │   │   │   │   ├── Audit.java
│   │   │   │   │   ├── AuditAction.java
│   │   │   │   │   └── AuditStatus.java
│   │   │   │   ├── rest/               # API endpoints
│   │   │   │   │   └── AuditRestController.java
│   │   │   │   ├── security/           # Security configuration
│   │   │   │   │   ├── JwtFilter.java
│   │   │   │   │   └── SecurityConfig.java
│   │   │   │   └── service/            # Business logic
│   │   │   │       ├── AuditService.java
│   │   │   │       ├── AuditServiceImpl.java
│   │   │   │       └── JwtService.java
│   │   │   └── resources/
│   │   │       └── application.properties  # Configuration
│   │   └── test/                       # Unit tests
│   ├── pom.xml                         # Maven dependencies
│   └── Dockerfile                      # Production Docker image
└── README.md                           # Project documentation
```

### Understanding the Layers

```
┌─────────────────────────────────────────────────────────────┐
│                      REST Controller                         │
│            (Handles HTTP requests from clients)              │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                      Service Layer                           │
│              (Contains business logic)                       │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Repository/DAO Layer                      │
│           (Handles database operations)                      │
└─────────────────────────────┬───────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                        Database                              │
│                     (PostgreSQL)                             │
└─────────────────────────────────────────────────────────────┘
```

---

## 💻 Understanding the Code

### Main Application

**File: `AuditLogServiceApplication.java`**

```java
package com.lgvt.audit_log_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuditLogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditLogServiceApplication.class, args);
    }

}
```

**Explanation:**

| Part | What it does |
|------|--------------|
| `@SpringBootApplication` | This is a "meta-annotation" that combines three annotations: `@Configuration`, `@EnableAutoConfiguration`, and `@ComponentScan`. It tells Spring Boot to start auto-configuring the application. |
| `main()` method | The entry point of the Java application. Every Java program starts here. |
| `SpringApplication.run()` | This method bootstraps the Spring application - it creates the Spring context, initializes beans, and starts the embedded web server. |

---

### Entity Classes

Entity classes represent the data that will be stored in the database. Each entity class maps to a database table.

#### Audit.java - The Main Entity

```java
package com.lgvt.audit_log_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "user_email", nullable = false)
    @NotBlank(message = "User email is required")
    private String user_email;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    private AuditAction action;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "ip_address")
    private String ipAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AuditStatus status;
}
```

**Annotations Explained:**

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Entity` | Marks this class as a JPA entity (database table) | The class becomes a table |
| `@Table(name = "audit_logs")` | Specifies the table name in the database | Creates table called "audit_logs" |
| `@Data` | Lombok annotation - generates getters, setters, toString, equals, hashCode | Reduces boilerplate code |
| `@Id` | Marks the primary key of the entity | `id` field is unique identifier |
| `@GeneratedValue(strategy = GenerationType.IDENTITY)` | Auto-generates the ID value | Database auto-increments the ID |
| `@Column` | Configures the column properties | `nullable = false` means required |
| `@NotBlank` | Validation - field cannot be null or empty | Shows error if validation fails |
| `@Enumerated(EnumType.STRING)` | Stores enum as string in database | "SUCCESS" instead of 0 |

**Database Table Visualization:**

```
Table: audit_logs
┌────┬───────────────────────┬─────────────────┬──────────────┬─────────────┬────────────────┬─────────┐
│ id │ timestamp             │ user_email      │ action       │ description │ ip_address     │ status  │
├────┼───────────────────────┼─────────────────┼──────────────┼─────────────┼────────────────┼─────────┤
│ 1  │ 2024-01-15 10:30:00   │ john@email.com  │ AUTH_SUCCESS │ User login  │ 192.168.1.1    │ SUCCESS │
│ 2  │ 2024-01-15 10:35:00   │ jane@email.com  │ VOTE_CAST    │ Voted for X │ 192.168.1.2    │ SUCCESS │
└────┴───────────────────────┴─────────────────┴──────────────┴─────────────┴────────────────┴─────────┘
```

#### AuditAction.java - Action Types Enum

```java
package com.lgvt.audit_log_service.entity;

public enum AuditAction {
    ADMIN_CREATE,
    VOTER_CREATE,
    VOTER_UPDATE,
    VOTER_DELETE,
    AUTH_SUCCESS,
    USER_UPDATE,
    ADMIN_DELETE,
    ADMIN_INVITE,
    CANDIDATE_CREATE,
    CANDIDATE_UPDATE,
    CANDIDATE_DELETE,
    AUTH_FAILURE,
    VOTE_CAST,
    REPORT_EXPORT,
    SETTINGS_UPDATE,
    EVM_DATA_IMPORT,
    BACKUP_CREATED,
    PASSWORD_RESET,
    ACCOUNT_LOCKED,
    USER_APPROVE
}
```

**What is an Enum?**

An **enum** (enumeration) is a special type that represents a fixed set of constants. It's like a dropdown menu with predefined options.

```
Think of it like:
┌─────────────────────────────┐
│ Select an Action:           │
├─────────────────────────────┤
│ ○ ADMIN_CREATE              │
│ ○ VOTER_CREATE              │
│ ○ AUTH_SUCCESS              │
│ ○ VOTE_CAST                 │
│ ... (more options)          │
└─────────────────────────────┘
```

**Why use enums?**
- **Type safety**: You can't accidentally use an invalid action
- **Readability**: Code is self-documenting
- **Maintainability**: Easy to add new actions in one place

#### AuditStatus.java - Status Types Enum

```java
package com.lgvt.audit_log_service.entity;

public enum AuditStatus {
    SUCCESS,
    ERROR,
    WARNING
}
```

**Status Meanings:**
- `SUCCESS`: The action completed successfully
- `ERROR`: The action failed
- `WARNING`: The action completed but with issues

---

### Repository/DAO Layer

**File: `AuditDAOImpl.java`**

```java
package com.lgvt.audit_log_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lgvt.audit_log_service.entity.Audit;

@Repository
public interface AuditDAOImpl extends JpaRepository<Audit, Long> {
}
```

**Explanation:**

| Part | What it does |
|------|--------------|
| `@Repository` | Marks this as a Spring-managed repository bean. It also enables exception translation for database errors. |
| `extends JpaRepository<Audit, Long>` | Inherits pre-built methods for database operations. `Audit` is the entity type, `Long` is the ID type. |

**Free Methods from JpaRepository:**

By extending `JpaRepository`, you get these methods for FREE (no code needed!):

```java
// Save an audit log
Audit savedAudit = auditDAO.save(audit);

// Find by ID
Optional<Audit> audit = auditDAO.findById(1L);

// Get all audit logs
List<Audit> allAudits = auditDAO.findAll();

// Delete an audit log
auditDAO.delete(audit);

// Count total records
long count = auditDAO.count();

// Check if exists
boolean exists = auditDAO.existsById(1L);
```

---

### Service Layer

The service layer contains **business logic** - the rules and operations that define what your application does.

#### AuditService.java - Interface

```java
package com.lgvt.audit_log_service.service;

import java.util.List;
import com.lgvt.audit_log_service.entity.Audit;

public interface AuditService {
    List<Audit> getAllAuditLogs();
    Audit createAuditLog(Audit audit);
}
```

**Why use an Interface?**

1. **Abstraction**: Hides implementation details
2. **Flexibility**: Easy to swap implementations
3. **Testing**: Easy to create mock implementations for testing

```
Interface (Contract):                 Implementation:
┌─────────────────────┐              ┌─────────────────────┐
│ AuditService        │              │ AuditServiceImpl    │
├─────────────────────┤   implements │─────────────────────┤
│ getAllAuditLogs()   │ ◄─────────── │ getAllAuditLogs() { │
│ createAuditLog()    │              │   // actual code    │
└─────────────────────┘              │ }                   │
                                     └─────────────────────┘
```

#### AuditServiceImpl.java - Implementation

```java
package com.lgvt.audit_log_service.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.lgvt.audit_log_service.dao.AuditDAOImpl;
import com.lgvt.audit_log_service.entity.Audit;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditDAOImpl auditDAO;

    // Constructor Injection
    public AuditServiceImpl(AuditDAOImpl auditDAO) {
        this.auditDAO = auditDAO;
    }

    @Override
    public List<Audit> getAllAuditLogs() {
        return auditDAO.findAll();
    }

    @Override
    public Audit createAuditLog(Audit audit) {
        audit.setTimestamp(LocalDateTime.now());
        return auditDAO.save(audit);
    }
}
```

**Key Concepts:**

| Concept | Explanation |
|---------|-------------|
| `@Service` | Marks this class as a Spring service bean. Spring will automatically create an instance and manage it. |
| Constructor Injection | The `auditDAO` is passed through the constructor. This is the recommended way to inject dependencies in Spring. |
| `implements AuditService` | This class provides the actual code for the methods defined in the interface. |

**What is Dependency Injection?**

Instead of creating dependencies ourselves:
```java
// BAD - tight coupling
public class AuditServiceImpl {
    private AuditDAOImpl auditDAO = new AuditDAOImpl(); // We create it ourselves
}
```

We let Spring inject them:
```java
// GOOD - loose coupling
public class AuditServiceImpl {
    private final AuditDAOImpl auditDAO;
    
    public AuditServiceImpl(AuditDAOImpl auditDAO) { // Spring injects it
        this.auditDAO = auditDAO;
    }
}
```

---

### REST Controller

**File: `AuditRestController.java`**

```java
package com.lgvt.audit_log_service.rest;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lgvt.audit_log_service.entity.Audit;
import com.lgvt.audit_log_service.service.AuditService;

@RestController
@RequestMapping("/api/audits")
public class AuditRestController {

    private final AuditService auditService;

    public AuditRestController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public ResponseEntity<List<Audit>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAllAuditLogs());
    }

    @PostMapping
    public ResponseEntity<Audit> createAudit(@RequestBody Audit audit) {
        return ResponseEntity.ok(auditService.createAuditLog(audit));
    }
}
```

**Annotations Explained:**

| Annotation | Purpose |
|------------|---------|
| `@RestController` | Combines `@Controller` and `@ResponseBody`. Tells Spring this class handles HTTP requests and returns JSON/XML responses. |
| `@RequestMapping("/api/audits")` | Sets the base URL path for all endpoints in this controller. |
| `@GetMapping` | Maps HTTP GET requests to this method. Used for reading data. |
| `@PostMapping` | Maps HTTP POST requests to this method. Used for creating data. |
| `@RequestBody` | Tells Spring to convert the incoming JSON body to a Java object. |
| `ResponseEntity` | Wrapper that allows you to customize the HTTP response (status code, headers, body). |

**API Endpoints:**

```
GET  /api/audits       → Get all audit logs (requires ADMIN or SUPER_ADMIN role)
POST /api/audits       → Create a new audit log (public access)
```

**Request/Response Examples:**

```bash
# GET all audits
GET /api/audits
Response:
[
    {
        "id": 1,
        "timestamp": "2024-01-15T10:30:00",
        "user_email": "john@email.com",
        "action": "AUTH_SUCCESS",
        "description": "User logged in successfully",
        "ipAddress": "192.168.1.1",
        "status": "SUCCESS"
    }
]

# POST create audit
POST /api/audits
Request Body:
{
    "user_email": "jane@email.com",
    "action": "VOTE_CAST",
    "description": "User cast their vote",
    "ipAddress": "192.168.1.2",
    "status": "SUCCESS"
}
```

---

### Security Configuration

The security layer protects your API from unauthorized access.

#### SecurityConfig.java

```java
package com.lgvt.audit_log_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, "/api/audits").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/audits").permitAll()
                .anyRequest().authenticated());
        httpSecurity.addFilterBefore(jwtAuthenticationFilter,
                org.springframework.security.web.authentication.AnonymousAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
```

**Security Rules Breakdown:**

```
┌────────────────────────────────────────────────────────────────┐
│                    Security Configuration                       │
├────────────────────────────────────────────────────────────────┤
│                                                                │
│  GET  /api/audits  →  Only ADMIN or SUPER_ADMIN can access    │
│                                                                │
│  POST /api/audits  →  Anyone can access (permitAll)           │
│                                                                │
│  Any other request →  Must be authenticated                   │
│                                                                │
└────────────────────────────────────────────────────────────────┘
```

**What is CSRF?**

**CSRF** (Cross-Site Request Forgery) is a security attack where a malicious website tricks a user's browser into performing unwanted actions on a trusted site. It's disabled here because:
- This is a REST API (stateless)
- Authentication is done via JWT tokens, not cookies

#### JwtFilter.java - JWT Authentication Filter

```java
package com.lgvt.audit_log_service.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lgvt.audit_log_service.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<String> roles = jwtService.extractRoles(token);
            List<GrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userName, null, authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
```

**How JWT Authentication Works:**

```
1. Client sends request with JWT token in header:
   ┌─────────────────────────────────────────────────────────────┐
   │ Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVC... │
   └─────────────────────────────────────────────────────────────┘
                              │
                              ▼
2. JwtFilter intercepts the request
   ┌─────────────────────────────────────────────────────────────┐
   │ Extract token → Validate → Extract username & roles        │
   └─────────────────────────────────────────────────────────────┘
                              │
                              ▼
3. If valid, set authentication in SecurityContext
   ┌─────────────────────────────────────────────────────────────┐
   │ User is now authenticated with their roles                  │
   └─────────────────────────────────────────────────────────────┘
                              │
                              ▼
4. Request continues to the controller
```

#### JwtService.java - JWT Token Operations

```java
package com.lgvt.audit_log_service.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private String secretKey = "thisismysecret19897donottouctouchit8329373743hhdjssmma89202";

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .claims().add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }
}
```

**JWT Token Structure:**

```
A JWT token looks like this:
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huQGVtYWlsLmNvbSIsInJvbGVzIjpbIkFETUlOIl19.signature

Decoded:
┌─────────────────────┐
│ Header:             │
│ {                   │
│   "alg": "HS256",   │  ← Algorithm used for signing
│   "typ": "JWT"      │  ← Token type
│ }                   │
├─────────────────────┤
│ Payload:            │
│ {                   │
│   "sub": "john@...",│  ← Subject (username)
│   "roles": ["ADMIN"]│  ← User roles
│   "iat": 1704067200,│  ← Issued at timestamp
│   "exp": 1704153600 │  ← Expiration timestamp
│ }                   │
├─────────────────────┤
│ Signature:          │
│ HMACSHA256(...)     │  ← Verifies token integrity
└─────────────────────┘
```

---

## ⚙️ Configuration Files

### application.properties

```properties
# Application name - used by Eureka for service discovery
spring.application.name=audit-log-service

# Database connection settings
spring.datasource.url=jdbc:postgresql://ep-delicate-paper-a4sp243v.us-east-1.aws.neon.tech/user_service_db?sslmode=require
spring.datasource.username=user_service_db_owner
spring.datasource.password=npg_20DtKcixOqFV
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Schema management - 'update' means Hibernate will update the schema automatically
spring.jpa.hibernate.ddl-auto=update

# SQL logging for debugging
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Server port
server.port=8086

# Eureka service discovery URL
eureka.client.serviceUrl.defaultZone=http://lgvt-service-discovery:8761/eureka/
```

**Configuration Explained:**

| Property | Purpose |
|----------|---------|
| `spring.application.name` | The name this service uses to register with Eureka |
| `spring.datasource.url` | JDBC connection URL to the PostgreSQL database |
| `spring.jpa.hibernate.ddl-auto=update` | Automatically creates/updates database tables based on entities |
| `spring.jpa.show-sql=true` | Prints SQL queries to console (useful for debugging) |
| `server.port=8086` | The port the application runs on |
| `eureka.client.serviceUrl.defaultZone` | URL of the Eureka service discovery server |

### pom.xml - Maven Dependencies

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.5</version>
    </parent>
    <groupId>com.lgvt</groupId>
    <artifactId>audit-log-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    
    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Actuator - Health checks, metrics -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- Spring Data JPA - Database access -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- Spring Security - Authentication & Authorization -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- Spring Web - REST API support -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- PostgreSQL Driver -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Lombok - Reduces boilerplate code -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- JWT Libraries -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.6</version>
        </dependency>

        <!-- Spring Cloud OpenFeign - Microservice communication -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <!-- Eureka Client - Service Discovery -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
</project>
```

**What is Maven?**

Maven is a **build automation tool** for Java projects. It:
- Downloads dependencies automatically
- Compiles code
- Runs tests
- Packages the application into a JAR file

```
pom.xml Structure:
┌─────────────────────────────────────────────────────────────┐
│ Project Info (groupId, artifactId, version)                 │
├─────────────────────────────────────────────────────────────┤
│ Properties (Java version, etc.)                             │
├─────────────────────────────────────────────────────────────┤
│ Dependencies (libraries your project needs)                 │
├─────────────────────────────────────────────────────────────┤
│ Build Configuration (plugins for compiling, packaging)      │
└─────────────────────────────────────────────────────────────┘
```

---

## 🐳 Docker and Containerization

### What is Docker?

Docker is a tool that packages your application with everything it needs to run (code, runtime, libraries) into a "container". It's like a shipping container - it works the same way everywhere.

```
Without Docker:                    With Docker:
┌─────────────────────┐           ┌─────────────────────┐
│ "Works on my        │           │  Works everywhere!  │
│  machine!"  😫      │           │     🎉               │
│                     │           │                     │
│ Different Java      │           │ Same container      │
│ Different OS        │   →       │ Same environment    │
│ Different settings  │           │ Same result         │
└─────────────────────┘           └─────────────────────┘
```

### Dockerfile Explained

```dockerfile
# Use official lightweight JDK 21 base image
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/audit-log-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Dockerfile Instructions:**

| Instruction | Purpose |
|-------------|---------|
| `FROM` | Base image to start from (like a template) |
| `WORKDIR` | Sets the working directory inside the container |
| `COPY` | Copies files from your computer into the container |
| `EXPOSE` | Documents which port the app uses |
| `ENTRYPOINT` | Command to run when the container starts |

### DevContainer Setup

The `.devcontainer` folder allows you to develop inside a Docker container using VS Code. This ensures everyone on the team has the same development environment.

```
devcontainer.json:
{
  "name": "lgvt-audit-log-service",
  "dockerComposeFile": "./docker-compose.yml",
  "service": "lgvt-audit-log-service",
  "workspaceFolder": "/workspaces",
  "features": {
    "ghcr.io/devcontainers/features/java:1": {},
    "ghcr.io/devcontainers/features/docker-in-docker:2": {},
    "ghcr.io/devcontainers-extra/features/springboot-sdkman:2": {}
  }
}
```

**Features Included:**
- Java development environment
- Docker-in-Docker (run Docker inside the dev container)
- Spring Boot tools

---

## 🚀 How to Run the Project

### Prerequisites

1. **Java 21** - The programming language runtime
2. **Maven** - Build tool (included via `mvnw` wrapper)
3. **Docker** - For containerized development (optional but recommended)

### Option 1: Run with Maven

```bash
# Navigate to the project directory
cd audit-log-service

# Run the application
./mvnw spring-boot:run
```

### Option 2: Run with Docker

```bash
# Build the JAR file first
./mvnw clean package -DskipTests

# Build the Docker image
docker build -t audit-log-service .

# Run the container
docker run -p 8086:8086 audit-log-service
```

### Option 3: Using DevContainer (Recommended)

1. Open the project in VS Code
2. Install the "Remote - Containers" extension
3. Press `Ctrl+Shift+P` → "Remote-Containers: Reopen in Container"
4. Wait for the container to build
5. Run `./mvnw spring-boot:run`

### Testing the API

Once running, test with curl or Postman:

```bash
# Create an audit log (no authentication needed)
curl -X POST http://localhost:8086/api/audits \
  -H "Content-Type: application/json" \
  -d '{
    "user_email": "test@email.com",
    "action": "AUTH_SUCCESS",
    "description": "Test login",
    "ipAddress": "127.0.0.1",
    "status": "SUCCESS"
  }'

# Get all audit logs (requires ADMIN or SUPER_ADMIN JWT token)
curl http://localhost:8086/api/audits \
  -H "Authorization: Bearer <your-jwt-token>"
```

---

## 📖 Key Concepts Glossary

| Term | Definition |
|------|------------|
| **Microservice** | A small, independent service that does one thing well |
| **REST API** | A way for applications to communicate using HTTP methods (GET, POST, PUT, DELETE) |
| **Entity** | A Java class that maps to a database table |
| **Repository** | A class that handles database operations |
| **Service** | A class that contains business logic |
| **Controller** | A class that handles HTTP requests and responses |
| **Dependency Injection** | A pattern where dependencies are provided rather than created |
| **JWT** | JSON Web Token - a secure way to transmit user identity |
| **ORM** | Object-Relational Mapping - converting between objects and database tables |
| **DTO** | Data Transfer Object - object used to transfer data between layers |
| **Bean** | An object managed by the Spring container |
| **Annotation** | Metadata added to code using @ symbol |
| **Maven** | Build automation and dependency management tool |
| **Docker** | Tool for containerizing applications |
| **Eureka** | Service discovery tool for microservices |

---

## 🎓 Learning Resources

### Official Documentation
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)

### Tutorials
- [Spring Boot Getting Started](https://spring.io/guides/gs/spring-boot/)
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [JWT Authentication Tutorial](https://www.baeldung.com/spring-security-oauth-jwt)

### Tools
- [Postman](https://www.postman.com/) - API testing tool
- [pgAdmin](https://www.pgadmin.org/) - PostgreSQL GUI
- [VS Code](https://code.visualstudio.com/) - Recommended IDE

---

## ✍️ Practice Exercises

1. **Add a new endpoint** to get a single audit log by ID
2. **Add a new action type** to the AuditAction enum
3. **Add pagination** to the getAllAudits endpoint
4. **Create a test** for the AuditService

---

## 📝 Notes

- This service is part of a larger Local Government Voting Technology (LGVT) system
- The audit logs are designed to be immutable (once created, they shouldn't be modified)
- Always handle sensitive data (like passwords) securely - never log them!

---

*Created for educational purposes. Happy learning! 🎉*
