<h4 align="center">  
  🎓 Universidad Nacional de General Sarmiento (UNGS)
</h4>

<h1 align="center">  
  🌲 Trabajo Práctico N°2: Red de Senderos en Parque Nacional - Programación III
</h1>

<p align="center">  
  Implementación de un sistema para optimizar la construcción de senderos con mínimo impacto ambiental usando teoría de grafos.
</p>

---

### 📅 Fecha de Entrega
**Martes 13 de mayo de 2025**

---

### 🧑‍🤝‍🧑 Integrantes del Grupo
- **Carrasco, Elias Emmanuel:** elias.e.carrasco@gmail.com  
- **Piacenti, Nicolas Exequiel:** nexp263@gmail.com  
- **Quevedo, Mauricio Santiago:** mauricioqueve@gmail.com  
- **Vildosa, Thiago Joel:** tvildosa@gmail.com  

---

### 📝 Descripción del Trabajo
Desarrollo de una aplicación visual que calcula la red óptima de senderos en un parque nacional mediante **algoritmos de árbol generador mínimo (AGM)**. El sistema debe:

1. Cargar estaciones (vértices) y senderos posibles (aristas con pesos de impacto ambiental 1-10).
2. Visualizar el grafo sobre un mapa interactivo.
3. Calcular el AGM usando **Kruskal** o **Prim**.
4. Mostrar los senderos seleccionados y el impacto ambiental total.

---

### 🔍 Objetivos Específicos
#### **Obligatorios**
- Implementar carga manual/archivo de estaciones y senderos.
- Visualización del grafo con JMapViewer.
- Cálculo del AGM con notificación de impacto total.
- Tests unitarios para todas las clases de negocio.

#### **Opcionales**  
- Comparar tiempos de ejecución Kruskal vs Prim.
- Codificación por colores según impacto ambiental (rojo=alto, verde=bajo).
- Persistencia de datos en archivos JSON/XML.

---

### 🛠 Herramientas y Tecnologías
| Componente          | Tecnología                          |
|---------------------|------------------------------------|
| Lenguaje            | Java 11+                           |
| Bibliotecas         | JGraphT (grafos), JMapViewer (mapas) |
| IDE                 | Eclipse con WindowBuilder          |
| Control de Versiones| Git/GitHub                         |
| Testing             | JUnit 5 + EclEmma (cobertura)      |

---

### 🏗 Estructura del Proyecto
```bash
src/
├── main/
│   ├── model/          # Clases de dominio
│   ├── algorithm/      # Kruskal/Prim
│   ├── ui/             # Interfaz gráfica
│   └── utils/          # Utilidades (FileLoader)
└── test/               # Tests unitarios
