## 🧾 audit-log-service

This microservice records all significant events and actions into immutable logs, supporting election transparency.

### 🔍 Purpose
- Record user and system actions for audits

### ⚙️ Tech Stack
- Spring Boot, Spring Security, JWT
- postgresSQL

## ▶️ How to Run This Service

1. **Clone the repository**:
   ```bash
   git clone git@github.com:KN-Thinley/lgvt-audit-log-service.git
   cd lgvt-audit-log-service
   ```

2. **Open inside a Docker container** (recommended with VS Code Remote - Containers):
   - Ensure Docker is installed and running.
   - Open the folder in VS Code.
   - When prompted, reopen in the container (or use the `Remote-Containers: Reopen in Container` command).

3. **Run the Spring Boot service** inside the container:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or using Docker Compose:
   ```bash
   docker-compose up --build
   ```

4. **Access the service**:
   - The backend will usually be accessible at `http://localhost:8086`

## 🌐 Live Deployment
- 🔗 [Live Site — Replace this with your actual deployed domain.](https://lgvt-audit-log-service.onrender.com)
  
## 👥 Project Authors
- **Kinley Norbu Thinley** – Full Stack Developer, Project Manager
- **Kuenzang Namgyel** – Full Stack Developer
