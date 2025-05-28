# Desafio 1.5

Projeto do curso 3035Teach, Módulo 5 (Back-End).

## 🚀 Tecnologias Utilizadas

- Java [24v].

## 📁 Como inciar

- Faça a criação do Banco de Dados usando o PgAdmin 4 e PostGreSql com o nome de 'escola-teach'.
- Altere no Arquivo Main.java:
    public static String url = "jdbc:postgresql://localhost:5432/escola-teach"; //Se seu banco se encontra com LocalHost diferente;
    public static String user = "postgres";
    public static String password = "root"; //se sua senha for diferente de root.

- Altere no Arquivo resources/META-INF/persistence.xml
    <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/escola-teach"/> //Se seu banco se encontra com LocalHost diferente;
    <property name="javax.persistence.jdbc.user" value="postgres"/>
    <property name="javax.persistence.jdbc.password" value="root"/> //se sua senha for diferente de root.

## ⚠️ Observações

- O Código já inicia as tabelas se elas ainda não foram criadas (não crie elas manualmente).

    
