# JEEWMS — Open-Source Warehouse Management System

**JEEWMS** is an intelligent warehouse management platform built on the Java ecosystem, designed to serve both in-house logistics and third-party logistics (3PL). With PDA (handheld terminal) + WEB dual-end collaboration, it provides a full-chain digital solution covering **WMS / OMS / BMS / TMS**.

The project has been validated in cold-chain logistics, FMCG retail, automotive manufacturing, cross-border overseas warehouses and other industries. It is released under the **GPL-3.0** license.

## ✨ Key Features

- Multi-tenant architecture (multiple warehouse owners, warehouses and brands in one system)
- Public / private / hybrid cloud deployment
- Industrial IoT integration: PDA, RFID, AGV, electronic shelf labels
- Dynamic billing engine (BMS) for flexible 3PL cost rules
- Automated scheduling and task collaboration
- Business intelligence, reports and visualization
- PDA field operations: receiving, putaway, moving, picking, counting by barcode scanning

## 🧩 Tech Stack

```
Backend:    Java + Spring Cloud (microservices)
Frontend:   Vue
Persistence: Hibernate / Minidao (MyBatis-like dynamic SQL)
Cache:      Redis + Ehcache
Mobile PDA: UNI-APP
Runtime:    JDK 1.8 + MySQL 5.7
```

## 🚀 Quick Start

1. JDK 1.8 + MySQL 5.7 (MySQL 8.0 is not supported), apply the schema from the `database` directory
2. Run with Maven: `mvn tomcat7:run`
3. Default login: admin / llg123 (local)

## 🏭 Industry Solutions

| Scenario | Highlights |
|---|---|
| Cold-chain logistics | Temperature traceability chain + batch/lot management |
| Automotive manufacturing | JIT material collaboration + AGV scheduling |
| Third-party logistics (3PL) | Dynamic billing engine + multi-owner dashboards |
| FMCG retail / Cross-border | Wave picking, return sorting, overseas warehouse operations |

## 🔗 Repositories

- Gitee (primary): <https://gitee.com/erzhongxmu/JEEWMS>
- GitHub (mirror): <https://github.com/erzhongxmu/JeeWMS>
- Mobile PDA app: <https://gitee.com/erzhongxmu/JeeWMSapp-uni>
- Smart manufacturing platform JEEMES: <https://gitee.com/erzhongxmu/jeemes>

## 📧 Contact

- Email: **erzhongxmu@hotmail.com**

## ⚖️ License

[GPL-3.0](LICENSE) — free to use and modify for open-source projects; keep the same license for derivatives.
