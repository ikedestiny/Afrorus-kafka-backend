# **AfroRusKafkaServices** 🚀

A **Spring Boot microservices** project powered by **Kafka, Docker, PostgreSQL, and Redis**, designed for scalable event-driven architecture.

---

## **📌 Features**
✅ **Authentication & Authorization** (`auth-service`)  
✅ **Spaces Management** (`space-service`)  
✅ **Email Notifications** (`email-service`)  
✅ **Event-Driven Communication** via **Kafka**  
✅ **Persistent Storage** with **PostgreSQL**  
✅ **Caching & Sessions** using **Redis**

---

## **⚡ Quick Start**

### **Prerequisites**
- Docker & Docker Compose
- Java 17+
- (Optional) Kafka CLI tools (for debugging)

### **1. Run the System**
```bash
docker-compose up -d
```
*(Add `--build` if you modify services.)*

### **2. Access Services**
| Service           | Port  | Description                     |
|-------------------|-------|---------------------------------|
| **auth-service**  | 8082  | User authentication & JWT       |
| **space-service** | 8083  | Manage user spaces              |
| **email-service** | 8084  | Send async email notifications  |
| **Kafka UI**      | 9092  | Monitor Kafka topics (`localhost:9092`) |
| **PostgreSQL**    | 5433  | DB: `app_db` (user: `admin`, pass: `secret`) |
| **Redis**         | 6379  | Session & cache storage         |

---

## **🔧 Configuration**

### **Environment Variables**
Update `docker-compose.yml` to modify:
- **Kafka brokers** (`kafka:29092`)
- **PostgreSQL** (user, password, DB name)
- **Redis** (host & port)

Example:
```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/app_db
  SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:29092
```

### **Kafka Topics**
Default topics are auto-created. To customize:
```yaml
# In a service's application.yml
spring:
  kafka:
    topics:
      user-events: user-events
```

---

## **🛠 Development**

### **Rebuild & Restart a Single Service**
```bash
docker-compose up -d --build auth-service
```

### **View Logs**
```bash
docker-compose logs -f auth-service
```

### **Reset Everything**
```bash
docker-compose down -v
docker-compose up -d
```

---

## **📂 Project Structure**
```
├── auth-service/      # Authentication & JWT  
├── space-service/     # User spaces management  
├── email-service/     # Async email notifications  
├── docker-compose.yml # Defines Kafka, Postgres, Redis, and services  
└── README.md  
```

---

## **🚀 Deployment**
1. **Build & Push Docker Images**
   ```bash
   docker-compose build
   docker push <your-registry>/auth-service:latest
   ```
2. **Use Kubernetes/ECS** *(Extend with `k8s/` or `terraform/` later)*

---

## **📜 License**
MIT

---

**✨ Happy Coding!**  
*Let’s build something amazing.* 🚀

---

### **Need Help?**
- **Kafka Issues?** Check topics with:
  ```bash
  docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092
  ```
- **PostgreSQL Access:**
  ```bash
  docker exec -it postgres psql -U admin app_db
  ```  

Here are all the essential Docker commands you'll need to work with PostgreSQL, Kafka, and Redis in your development environment:

### 1. **Start All Services**
```bash
docker-compose up -d
```

### 2. **Stop All Services**
```bash
docker-compose down
```

### 3. **PostgreSQL Commands**

#### Access PostgreSQL Shell:
```bash
docker exec -it postgres psql -U admin -d app_db
```

#### Common PSQL Commands:
```sql
\l                         # List databases
\dt                        # List tables
\c database_name           # Connect to database
SELECT * FROM table_name;  # Query table
\q                         # Quit
```

#### Backup Database:
```bash
docker exec -t postgres pg_dump -U admin app_db > backup.sql
```

#### Restore Database:
```bash
cat backup.sql | docker exec -i postgres psql -U admin app_db
```

### 4. **Redis Commands**

#### Access Redis CLI:
```bash
docker exec -it redis redis-cli
```

#### Common Redis Commands:
```bash
KEYS *                     # List all keys
GET key_name               # Get value
SET key_name value         # Set value
DEL key_name              # Delete key
FLUSHALL                  # Clear all data
```

### 5. **Kafka Commands**

#### List Topics:
```bash
docker exec kafka kafka-topics --list --bootstrap-server kafka:9092
```

#### Create Topic:
```bash
docker exec kafka kafka-topics --create \
  --topic your-topic \
  --partitions 1 \
  --replication-factor 1 \
  --bootstrap-server kafka:9092
```

#### Produce Messages:
```bash
docker exec -it kafka kafka-console-producer \
  --topic your-topic \
  --bootstrap-server kafka:9092
```

#### Consume Messages:
```bash
docker exec -it kafka kafka-console-consumer \
  --topic your-topic \
  --from-beginning \
  --bootstrap-server kafka:9092
```

#### Describe Topic:
```bash
docker exec kafka kafka-topics --describe \
  --topic your-topic \
  --bootstrap-server kafka:9092
```

### 6. **Service-Specific Management**

#### View Logs:
```bash
docker-compose logs -f service_name  # (postgres/kafka/redis)
```

#### Rebuild Single Service:
```bash
docker-compose build email-service
docker-compose up -d --no-deps email-service
```

#### Check Running Containers:
```bash
docker-compose ps
```

#### View Resource Usage:
```bash
docker stats
```

### 7. **Cleanup Commands**

#### Remove All Containers and Volumes:
```bash
docker-compose down -v
```

#### Remove Docker Cache:
```bash
docker system prune -a
```

### 8. **Network Inspection**

#### Check Container IPs:
```bash
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name
```

#### Test Connectivity Between Services:
```bash
docker exec email-service ping kafka
docker exec email-service ping postgres
docker exec email-service ping redis
```

These commands will help you manage your development environment with PostgreSQL, Kafka, and Redis in Docker. For production environments, you'll want to add proper security configurations and monitoring.